package com.jd.appoint.service.order.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jd.appoint.common.security.LocalSecurityClient;
import com.jd.appoint.domain.enums.EncryptTypeEnum;
import com.jd.appoint.domain.enums.ServerTypeEnum;
import com.jd.appoint.domain.enums.SourceEnum;
import com.jd.appoint.domain.rpc.ServiceTypeInfo;
import com.jd.appoint.domain.rpc.SystemStatusInfo;
import com.jd.appoint.service.config.EsConfig;
import com.jd.appoint.service.order.AppointOrderService;
import com.jd.appoint.service.order.PopConfigService;
import com.jd.appoint.service.shop.ShopFormControlItemService;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.appoint.vo.page.Page;
import com.jd.common.util.StringUtils;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by shaohongsen on 2018/5/10.
 */
@Service
public class EsOrderService {
    private Logger logger = LoggerFactory.getLogger(EsOrderService.class);

    private static final String APPOINT_CREATE_INDEX = "com.jd.appoint.service.order.es.index";
    @Autowired
    private Client client;
    @Autowired
    private EsConfig esConfig;
    @Autowired
    private EsQueryBuilder esQueryBuilder;
    private static final long ES_TIME_OUT_SECONDS = 10;
    @Autowired
    private ShopFormControlItemService shopFormControlItemService;
    @Autowired
    private LocalSecurityClient localSecurityClient;
    private final Map<String, Method> APPOINT_ORDER_READ_METHOD_MAP = Maps.newHashMap();
    ParserConfig serializeConfig = new ParserConfig();
    private static final String CUSTOMER_PHONE_ALIAS = "customerPhone";
    private static final String ENCRYPT_PREFIX = "encrypt_";
    private static final String INDEX_PREFIX = "index_";
    private static final String EQ = ".EQ";
    private static final String ID = "id";
    @Autowired
    private PopConfigService popConfigService;
    @Autowired
    private AppointOrderService appointOrderService;

    @PostConstruct
    public void init() {
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(AppointOrderDetailVO.class);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyDescriptor.getReadMethod() != null) {
                APPOINT_ORDER_READ_METHOD_MAP.put(propertyDescriptor.getName(), propertyDescriptor.getReadMethod());
            }
        }
        APPOINT_ORDER_READ_METHOD_MAP.remove("formItems");
        APPOINT_ORDER_READ_METHOD_MAP.remove("class");
        APPOINT_ORDER_READ_METHOD_MAP.remove(CUSTOMER_PHONE_ALIAS);//电话有特殊的处理方式
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.CamelCase;
    }

    public void batchIndex(Set<String> ids) {
        CallerInfo profiler = Profiler.registerInfo(APPOINT_CREATE_INDEX, false, true);
        try {
            logger.info("刷新es数据的Id列表为ids={}", JSONArray.toJSONString(ids));
            List<Map<String, Object>> documents = ids.
                    parallelStream()
                    .map(idStr -> Long.parseLong(idStr))
                    .map(id -> appointOrderService.detail(id))
                    .map(detail -> this.createDocument(detail))
                    .collect(Collectors.toList());
            BulkRequest bulkRequest = new BulkRequest();
            documents.forEach(document -> {
                IndexRequest indexRequest = new IndexRequest();
                indexRequest.id(document.get(ID).toString()).source(document).index(esConfig.getIndexName()).type(esConfig.getTypeName());
                bulkRequest.add(indexRequest);
            });
            client.bulk(bulkRequest);
        } catch (Exception e) {
            Profiler.functionError(profiler);
            throw e;
        } finally {
            Profiler.registerInfoEnd(profiler);
        }
    }

    /**
     * 将订单索引到es中
     *
     * @param detail
     */
    public void index(AppointOrderDetailVO detail) {
        Map<String, Object> document = this.createDocument(detail);
        client.prepareIndex(esConfig.getIndexName(), esConfig.getTypeName())
                .setId(detail.getId().toString())
                .setSource(document).execute().actionGet();
    }

    private Map<String, Object> createDocument(AppointOrderDetailVO detail) {
        Map<String, Object> document = Maps.newHashMap();
        //
        Map<String, EncryptTypeEnum> encryptTypeEnumMap = shopFormControlItemService.queryEncryptByBusinessCode(detail.getBusinessCode());
        Map<String, String> formItems = detail.getFormItems();
        if (formItems != null && !formItems.isEmpty()) {
            encryptTypeEnumMap.keySet().stream()
                    .filter(alias -> encryptTypeEnumMap.get(alias) == EncryptTypeEnum.SAFETY_ENCRYPT)
                    .filter(alias -> formItems.containsKey(alias))//该订单填写了加密内容
                    .forEach(alias -> {
                        //解密内容
                        String decryptValue = formItems.remove(alias);
                        String key = esQueryBuilder.toUnderScore(alias);
                        document.put(INDEX_PREFIX + key, localSecurityClient.getIndexStr(decryptValue));//添加用于筛选列
                        document.put(ENCRYPT_PREFIX + key, localSecurityClient.encrypt(decryptValue));//用于解密的内容
                    });
            //from_item 转换成es动态字段的格式 如 user_remark
            formItems.entrySet().stream().forEach(entry -> {
                //驼峰转下划线
                String key = esQueryBuilder.toUnderScore(entry.getKey());
                String value = entry.getValue();
                document.put(key, value);
            });
        }
        //把formItems 和其他属性调节成同级
        detail.setFormItems(null);
        /**
         * java 对象转map 属性名称 驼峰转下划线
         */
        for (Map.Entry<String, Method> entry : APPOINT_ORDER_READ_METHOD_MAP.entrySet()) {
            try {
                Method getMethod = entry.getValue();
                Object value = getMethod.invoke(detail);
                if (null != value) {
                    document.put(esQueryBuilder.toUnderScore(entry.getKey()), value);
                }
            } catch (Exception e) {
                logger.error("detail to map error fieldName is:{}", entry.getKey(), e);
            }
        }
        document.put(INDEX_PREFIX + esQueryBuilder.toUnderScore(CUSTOMER_PHONE_ALIAS), localSecurityClient.getIndexStr(detail.getCustomerPhone()));
        document.put(ENCRYPT_PREFIX + esQueryBuilder.toUnderScore(CUSTOMER_PHONE_ALIAS), localSecurityClient.encrypt(detail.getCustomerPhone()));
        fixDateAndStr(document);
        document.put(ID, detail.getId().toString());
        return document;
    }


    /**
     * @param document
     */
    private void fixDateAndStr(Map<String, Object> document) {
        Set<String> dateFieldNames = Sets.newHashSet();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        for (Map.Entry<String, Object> field : document.entrySet()) {
            Object value = field.getValue();
            if (value != null) {
                if (value.getClass().equals(Date.class)) {//日期统一用的date
                    dateFieldNames.add(field.getKey());
                }

            }
        }
        for (String fieldName : dateFieldNames) {
            //date 转成 字符串 去除时区问题
            Date date = (Date) document.get(fieldName);
            String dateStr = sf.format(date);
            document.put(fieldName, dateStr);
        }
    }


    /**
     * 分页查询订单
     *
     * @param page
     * @param businessCode 加解密，扩展字段的businessCode
     * @return
     */
    public Page<AppointOrderDetailVO> list(Page page, String businessCode) {
        /**
         * 当前业务类型下，需要填写的字段对应的加密类型
         */
        Map<String, EncryptTypeEnum> encryptTypeEnumMap = null;
        if (!Strings.isNullOrEmpty(businessCode)) {
            encryptTypeEnumMap = shopFormControlItemService.queryEncryptByBusinessCode(businessCode);
        }
        Page<Map<String, Object>> nativeList = this.nativeList(page, businessCode);
        Page<AppointOrderDetailVO> result = new Page<>();
        result.setPageNumber(nativeList.getPageNumber());
        result.setPageSize(nativeList.getPageSize());
        result.setTotalCount(nativeList.getTotalCount());
        if (nativeList.getTotalCount() > 0) {
            Map<String, EncryptTypeEnum> finalEncryptTypeEnumMap = encryptTypeEnumMap;
            List<SystemStatusInfo> systemStatusInfos = popConfigService.queryStatusMappingList(businessCode);
            Map<Integer, ServiceTypeInfo> statusMap = parseListToMap(systemStatusInfos);

            List<AppointOrderDetailVO> list = nativeList.getList().stream().map(source -> {
                /**
                 * 先反序列化，除了formItems信息都可以绑定
                 */
                AppointOrderDetailVO detailVo = JSON.parseObject(JSON.toJSONString(source), AppointOrderDetailVO.class);
                specialDeal(detailVo, statusMap);//特殊处理
                Map<String, String> formItems = bindFromItems(finalEncryptTypeEnumMap, source);
                detailVo.setFormItems(formItems);
                return detailVo;
            }).collect(Collectors.toList());
            result.setList(list);
        } else {
            result.setList(Collections.EMPTY_LIST);
        }
        return result;
    }

    private Map<Integer, ServiceTypeInfo> parseListToMap(List<SystemStatusInfo> systemStatusInfos) {
        Map<Integer, ServiceTypeInfo> map = new HashMap<>();
        for (SystemStatusInfo systemStatusInfo :
                systemStatusInfos) {
            map.put(systemStatusInfo.getSystemStatusCode(), systemStatusInfo.getServiceTypeInfo());
        }
        return map;
    }

    /**
     * 特殊处理
     */
    private void specialDeal(AppointOrderDetailVO detailVo, Map<Integer, ServiceTypeInfo> statusMap) {
        //设置订单状态中文
        setChStatus(detailVo, statusMap);
        //设置预约来源中文
        detailVo.setChSource(SourceEnum.getFromCode(detailVo.getSource()).getPerson());
        //设置服务模式中文
        detailVo.setChServerType(ServerTypeEnum.getFromCode(detailVo.getServerType()).getAlias());
        //加密
        encryptInfo(detailVo);
    }

    /**
     * 中文映射
     *
     * @param appointOrderDetailVO
     */
    private void setChStatus(AppointOrderDetailVO appointOrderDetailVO, Map<Integer, ServiceTypeInfo> statusMap) {

        Integer serverType = appointOrderDetailVO.getServerType();
        if (serverType.equals(ServerTypeEnum.DAODIAN.getIntValue())) {
            appointOrderDetailVO.setChAppointStatus(statusMap.get(appointOrderDetailVO.getAppointStatus()).getToShopStatusName());
        } else {
            appointOrderDetailVO.setChAppointStatus(statusMap.get(appointOrderDetailVO.getAppointStatus()).getToHomeStatusName());
        }

    }

    /**
     * 加密敏感信息
     *
     * @param appointOrderDetailVO
     */
    private void encryptInfo(AppointOrderDetailVO appointOrderDetailVO) {
        if (StringUtils.isNotBlank(appointOrderDetailVO.getCardNo())) {
            String cardNo = appointOrderDetailVO.getCardNo();
            appointOrderDetailVO.setOriCardNo(cardNo);
            StringBuilder builder = new StringBuilder();
            builder.append(cardNo.substring(0, 1));
            builder.append("******");
            builder.append(cardNo.substring(cardNo.length() - 1, cardNo.length()));
            appointOrderDetailVO.setCardNo(builder.toString());
        }
    }

    /**
     * 分页查询订单
     *
     * @param page
     * @param businessCode 加解密，扩展字段的businessCode
     * @return
     */
    public Page<Map<String, Object>> nativeList(Page page, String businessCode) {
        /**
         * 当前业务类型下，需要填写的字段对应的加密类型
         */
        Map<String, EncryptTypeEnum> encryptTypeEnumMap = null;
        if (!Strings.isNullOrEmpty(businessCode)) {
            encryptTypeEnumMap = shopFormControlItemService.queryEncryptByBusinessCode(businessCode);
        }
        Map<String, String> searchMap = page.getSearchMap();
        //对需要加密的字段，query进行特殊的处理
        this.encryptQuery(searchMap, encryptTypeEnumMap);
        //构建查询请求
        QueryBuilder queryBuilder = esQueryBuilder.buildQuery(searchMap);
        List<SortBuilder> sorts = esQueryBuilder.buildSorts(page.getSorts());
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(esConfig.getIndexName())//指定index
                .setTypes(esConfig.getTypeName())//指定type
                .setQuery(queryBuilder)//指定query
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize());//指定分页信息
        sorts.forEach(sortBuilder -> searchRequestBuilder.addSort(sortBuilder));//指定排序
        System.out.println(searchRequestBuilder);
        SearchResponse searchResponse = searchRequestBuilder.get(TimeValue.timeValueSeconds(ES_TIME_OUT_SECONDS));//获取查询内容，10秒超时
        page.setTotalCount(searchResponse.getHits().getTotalHits());
        if (page.getTotalCount() == 0)
            return page;
        Map<String, EncryptTypeEnum> finalEncryptTypeEnumMap = encryptTypeEnumMap;
        List<Map<String, Object>> list = Arrays.stream(searchResponse.getHits().getHits())
                .map(hit -> {
                    Map<String, Object> source = hit.getSource();
                    source.put("id", hit.getId());
                    source = decryptSource(source, finalEncryptTypeEnumMap);
                    return source;
                })
                .collect(Collectors.toList());
        page.setList(list);
        return page;
    }

    private Map<String, Object> decryptSource(Map<String, Object> source, Map<String, EncryptTypeEnum> finalEncryptTypeEnumMap) {
        //先把不需要加密解密的搞定
        Map<String, Object> result = source.entrySet().stream()
                .filter(entry -> !entry.getKey().startsWith(INDEX_PREFIX) && !entry.getKey().startsWith(ENCRYPT_PREFIX))
                .filter(entry -> null != entry.getValue())
                .collect(Collectors.toMap(entry -> esQueryBuilder.toLowerCamel(entry.getKey()), entry -> entry.getValue()));
        String phone = (String) source.get(ENCRYPT_PREFIX + esQueryBuilder.toUnderScore(CUSTOMER_PHONE_ALIAS));
        if (!Strings.isNullOrEmpty(phone)) {
            phone = localSecurityClient.decrypt(phone);
            result.put(CUSTOMER_PHONE_ALIAS, phone);
        }
        if (finalEncryptTypeEnumMap != null && !finalEncryptTypeEnumMap.isEmpty()) {
            finalEncryptTypeEnumMap.entrySet().stream()
                    .filter(entry -> EncryptTypeEnum.SAFETY_ENCRYPT == entry.getValue())
                    .forEach(entry -> {
                        String value = (String) source.get(ENCRYPT_PREFIX + esQueryBuilder.toUnderScore(entry.getKey()));
                        if (!Strings.isNullOrEmpty(value)) {
                            value = localSecurityClient.decrypt(value);
                            result.put(entry.getKey(), value);
                        }
                    });
        }
        return result;
    }

    /**
     * 绑定formItems
     *
     * @param encryptTypeEnumMap
     * @param source
     * @return
     */
    private Map<String, String> bindFromItems(Map<String, EncryptTypeEnum> encryptTypeEnumMap, Map<String, Object> source) {
        Map<String, String> formItems = Maps.newHashMap();
        if (encryptTypeEnumMap != null && !encryptTypeEnumMap.isEmpty()) {
            encryptTypeEnumMap.entrySet().stream()
                    .filter(entry -> entry.getValue() != null)
                    .forEach(
                            entry -> {
                                formItems.put(entry.getKey(), (String) source.get(entry.getKey()));
                            }
                    );
        }
        return formItems;
    }

    private void encryptQuery(Map<String, String> searchMap, Map<String, EncryptTypeEnum> encryptTypeEnumMap) {
        if (searchMap == null) {
            return;
        }
        if (!Strings.isNullOrEmpty(searchMap.get(CUSTOMER_PHONE_ALIAS + EQ))) {
            //手机号替换成索引列
            searchMap.put(INDEX_PREFIX + CUSTOMER_PHONE_ALIAS + EQ,
                    localSecurityClient.getIndexStr(searchMap.remove(CUSTOMER_PHONE_ALIAS + EQ)));
        }
        if (encryptTypeEnumMap == null) {
            return;
        }
        encryptTypeEnumMap.entrySet().stream()
                .filter(encrypt -> encrypt.getValue() == EncryptTypeEnum.SAFETY_ENCRYPT)//需要加密的字段
                .filter(encrypt -> searchMap.containsKey(encrypt.getKey() + EQ))//有加密字段的筛选
                .forEach(encrypt -> {
                    String decryptValue = searchMap.remove(encrypt.getKey() + EQ);//筛选请求内肯定是解密的值
                    searchMap.put(INDEX_PREFIX + encrypt.getKey() + EQ, localSecurityClient.getIndexStr(decryptValue));//筛选对应的加密列
                });
    }

}

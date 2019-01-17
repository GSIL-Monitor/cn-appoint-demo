package com.jd.appoint.service.shop.impl;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.google.common.collect.Maps;
import com.jd.appoint.api.vo.VenderConfigVO;
import com.jd.appoint.common.security.LocalSecurityClient;
import com.jd.appoint.dao.shop.ShopFormControlItemDao;
import com.jd.appoint.domain.enums.EncryptTypeEnum;
import com.jd.appoint.domain.order.AppointOrderFormItemPO;
import com.jd.appoint.domain.shop.ShopFormControlItemPO;
import com.jd.appoint.domain.shop.query.FormControlItemQuery;
import com.jd.appoint.rpc.jmi.dto.IdentityDTO;
import com.jd.appoint.rpc.jmi.jicc.RpcIdentityService;
import com.jd.appoint.service.order.vo.ValueFromControlItem;
import com.jd.appoint.service.shop.IdcardUtil;
import com.jd.appoint.service.shop.ShopFormControlItemService;
import com.jd.appoint.service.sys.VenderConfigConstant;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.jmi.jicc.client.enums.JICCPapersTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by yangyuan on 5/15/18.
 */
@Service
public class ShopFormControlItemServiceImpl implements ShopFormControlItemService {

    @Autowired
    private ShopFormControlItemDao shopFormControlItemDao;
    @Autowired
    private RpcIdentityService rpcIdentityService;
    @Autowired
    private VenderConfigService venderConfigService;
    @Autowired
    private LocalSecurityClient localSecurityClient;
    private final Map<String, Method> APPOINT_ORDER_READ_METHOD_MAP = Maps.newHashMap();
    private Logger logger = LoggerFactory.getLogger(ShopFormControlItemServiceImpl.class);

    @PostConstruct
    public void init() {
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(AppointOrderDetailVO.class);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (propertyDescriptor.getReadMethod() != null) {
                APPOINT_ORDER_READ_METHOD_MAP.put(propertyDescriptor.getName(), propertyDescriptor.getReadMethod());
            }
        }
    }

    @Override
    public List<ShopFormControlItemPO> query(FormControlItemQuery formControlItemQuery) {
        return shopFormControlItemDao.query(formControlItemQuery);
    }

    @Override
    public List<ShopFormControlItemPO> queryByBusinessCode(String businessCode) {
        return shopFormControlItemDao.queryByBusinessCode(businessCode);
    }

    @Override
    @Transactional
    public boolean batchInsert(List<ShopFormControlItemPO> itemPOList) {
        if (CollectionUtils.isNotEmpty(itemPOList)) {
            return shopFormControlItemDao.batchInsert(itemPOList) > 0;
        }
        return false;
    }

    @Override
    public void insert(ShopFormControlItemPO shopFormControlItemPO) {
        shopFormControlItemDao.insert(shopFormControlItemPO);
    }

    @Override
    public boolean update(ShopFormControlItemPO shopFormControlItemPO) {
        return shopFormControlItemDao.update(shopFormControlItemPO) > 0;
    }

    @Override
    public void delete(Long id) {
        shopFormControlItemDao.delete(id);
    }

    @Override
    public Map<String, ValueFromControlItem> createValueMap(AppointOrderDetailVO appointOrderDetailVo) throws IllegalArgumentException {
        if (appointOrderDetailVo.getFormItems() == null) {//初始化
            appointOrderDetailVo.setFormItems(Maps.newHashMap());
        }
        Map<String, ValueFromControlItem> result = createSimpleValueMap(appointOrderDetailVo.getFormItems(), appointOrderDetailVo.getBusinessCode(), appointOrderDetailVo.getServerType());
        //参数合法校验
        valid(result.values(), appointOrderDetailVo);
        //加密
        encrypt(result.values());
        dealIdentity(result, appointOrderDetailVo);
        return result;
    }

    private void dealIdentity(Map<String, ValueFromControlItem> result, AppointOrderDetailVO appointOrderDetailVo) {
        Optional<ValueFromControlItem> identityNumOption = result.values().stream()
                .filter(item -> item.getEncryptType() == EncryptTypeEnum.JMI_ENCRYPT)
                .findFirst();
        if (identityNumOption.isPresent()) {
            ValueFromControlItem identity = identityNumOption.get();
            IdentityDTO identityDTO = this.createIdentityDTO(identity, appointOrderDetailVo);
            validIdentityDTO(identityDTO);
            //调用户簿
            String identityId = rpcIdentityService.addUserIdentity(identityDTO);
            identity.setAttrValue(identityId);
            result.put(identity.getAttrNameAlias(), identity);
        }
    }

    @Override
    public Map<String, EncryptTypeEnum> queryEncryptByBusinessCode(String businessCode) {
        List<ShopFormControlItemPO> itemPOS = this.shopFormControlItemDao.queryByBusinessCode(businessCode);
        Map<String, EncryptTypeEnum> result = itemPOS.stream()
                .collect(Collectors.toMap(ShopFormControlItemPO::getAlias, ShopFormControlItemPO::getEncryptType));
        return result;
    }

    @Override
    public List<ShopFormControlItemPO> findByBusinessCodeAndPageNoAndVenderId(String businessCode, String pageNo, Long venderId) {
        return shopFormControlItemDao.findByBusinessCodeAndPageNoAndVenderId(businessCode, pageNo, venderId);
    }

    private Map<String, ValueFromControlItem> createSimpleValueMap(Map<String, String> formItems, String businessCode, Integer serverType) {
        List<ShopFormControlItemPO> shopFormControlItemPOS = this.queryByBusinessCode(businessCode);
        Map<String, ValueFromControlItem> result = shopFormControlItemPOS.stream()//表单控制要求的，即使没有填值也需要构建到result中以便于之后校验
                .filter(shopFormControlItemPO -> {
                    if (serverType == 1) {
                        return shopFormControlItemPO.getOnSiteDisplay();
                    } else {
                        return shopFormControlItemPO.getToShopDisplay();
                    }
                })
                //转变成ValueFromControlItem
                .map(shopFormControlItemPO -> convertValueFromControlItem(shopFormControlItemPO, formItems.get(shopFormControlItemPO.getAlias())))
                //to map
                .collect(Collectors.toMap(valueItem -> valueItem.getAttrNameAlias(), valueItem -> valueItem));
        return result;
    }

    private void validIdentityDTO(IdentityDTO identityDTO) throws IllegalArgumentException {
        if (identityDTO.getCertType() == JICCPapersTypeEnum.IDENTITY && !IdcardUtil.isIdCard(identityDTO.getCertNum())) {
            throw new IllegalArgumentException("身份证号码校验失败，当前身份证号:" + identityDTO.getCertNum());
        }
    }

    /**
     * 加密
     *
     * @param values
     */
    private void encrypt(Collection<ValueFromControlItem> values) {
        values.stream()
                .filter(item -> item.getEncryptType() == EncryptTypeEnum.SAFETY_ENCRYPT)//只处理本地加密
                .forEach(item -> item.setAttrValue(localSecurityClient.encrypt(item.getAttrValue())));
    }

    /**
     * 合法校验
     *
     * @param values
     * @param detailVO
     * @throws IllegalArgumentException
     */
    private void valid(Collection<ValueFromControlItem> values, AppointOrderDetailVO detailVO) throws IllegalArgumentException {
        values.stream().filter(item -> item.isOrderFiled())//处理静态属性
                .forEach(item -> {
                    //配置为静态属性，但是没有getMethod
                    if (!APPOINT_ORDER_READ_METHOD_MAP.containsKey(item.getAttrNameAlias())) {
                        throw new IllegalArgumentException(item.getAttrNameAlias() + "为静态属性，但是AppointOrderDetailVO中没用次属性，请修改配置");
                    }
                    Method getMethod = APPOINT_ORDER_READ_METHOD_MAP.get(item.getAttrNameAlias());
                    try {
                        Object value = getMethod.invoke(detailVO);
                        if (value != null) {
                            if (value instanceof Date) {
                                value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) value);
                            }
                            item.setAttrValue(value.toString());
                        }
                    } catch (Exception e) {
                        logger.error("AppointOrderDetailVO.{}方法执行报错", getMethod.getName(), e);
                    }
                });
        values.stream().forEach(item -> {
            if (item.isNeedInput() && Strings.isNullOrEmpty(item.getAttrValue())) {
                throw new IllegalArgumentException(item.getAttrNameAlias() + "为必填项，当前未填写");
            }
            if (!Strings.isNullOrEmpty(item.getRegular())) {//如果需要正则校验
                boolean isOk = Pattern.matches(item.getRegular(), item.getAttrValue() == null ? "" : item.getAttrValue());
                if (!isOk) {
                    throw new IllegalArgumentException(item.getAttrNameAlias() + "校验失败，校验正则表达式：" + item.getRegular() + "，实际值：" + item.getAttrValue());
                }
            }
        });
    }

    private IdentityDTO createIdentityDTO(ValueFromControlItem identity, AppointOrderDetailVO detailVo) {
        IdentityDTO identityDTO = new IdentityDTO();
        if (Strings.isNullOrEmpty(detailVo.getCustomerName())) {
            throw new IllegalArgumentException("customerName is null");
        }
        identityDTO.setName(detailVo.getCustomerName());
        identityDTO.setCertNum(identity.getAttrValue());
        VenderConfigVO config = venderConfigService.getConfig(detailVo.getBusinessCode(), detailVo.getVenderId(), VenderConfigConstant.IDENTITY_KEY);
        if (config == null || Strings.isNullOrEmpty(config.getKey())) {
            throw new IllegalStateException("配置表" + VenderConfigConstant.IDENTITY_KEY
                    + "未配置,业务类型：" + detailVo.getBusinessCode()
                    + "venderId:" + detailVo.getVenderId());
        }
        //获取证件类型别名
        String identityTypeAlias = config.getKey();
        //证件类型
        String identityType = detailVo.getFormItems().get(identityTypeAlias);
        identityDTO.setCertType(JICCPapersTypeEnum.ExistEnum(Integer.parseInt(identityType)));
        return identityDTO;


    }

    private ValueFromControlItem convertValueFromControlItem(ShopFormControlItemPO shopFormControlItemPO, String value) {
        ValueFromControlItem valueFromControlItem = new ValueFromControlItem();
        valueFromControlItem.setAttrNameAlias(shopFormControlItemPO.getAlias());
        valueFromControlItem.setAttrValue(value);
        valueFromControlItem.setFormControlId(shopFormControlItemPO.getId());
        valueFromControlItem.setRegular(shopFormControlItemPO.getRegular());
        valueFromControlItem.setNeedInput(shopFormControlItemPO.getNeedInput());
        valueFromControlItem.setOrderFiled(shopFormControlItemPO.getOrderField());
        return valueFromControlItem;
    }

}

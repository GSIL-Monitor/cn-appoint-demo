package com.jd.appoint.service.api.outer;

import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jd.appoint.rpc.context.RpcContextService;
import com.jd.appoint.rpc.pop.impl.JDStoreService;
import com.jd.appoint.service.product.ProductService;
import com.jd.fastjson.JSON;
import com.jd.pop.wxo2o.spi.Paginate;
import com.jd.pop.wxo2o.spi.lbs.to.StoreTO;
import com.jd.pop.wxo2o.spi.lbs.to.VenderStoreTO;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.aspects.ServerEnum;
import com.jd.virtual.appoint.ContextKeys;
import com.jd.virtual.appoint.StoreGwService;
import com.jd.virtual.appoint.common.CommonRequest;
import com.jd.virtual.appoint.common.CommonResponse;
import com.jd.virtual.appoint.common.Page;
import com.jd.virtual.appoint.common.PageRequest;
import com.jd.virtual.appoint.store.StoreItem;
import com.jd.virtual.appoint.store.StoreItemRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by yangyuan on 6/27/18.
 */
@Service(value = "storeGwService")
public class StoreGwServiceImpl implements StoreGwService {

    private static final Logger log = LoggerFactory.getLogger(StoreGwServiceImpl.class);

    @Autowired
    private RpcContextService rpcContextService;

    @Autowired
    private ProductService productService;

    @Autowired
    private JDStoreService storeService;


    @Override
    @UmpMonitor(serverType = ServerEnum.NONE, logCollector =
    @LogCollector(description = "获取门店列表接口", classify = StoreGwServiceImpl.class))
    public CommonResponse<Page<StoreItem>> getStoreList(CommonRequest commonRequest, PageRequest pageRequest) {
        Optional<Map<String, String>> result = Optional.ofNullable(rpcContextService.getContext(commonRequest.getContextId()));
        int pageSize = pageSize(pageRequest);
        int offset = pageSize*(pageNum(pageRequest) - 1);
        Page<StoreItem> page ;
        String skuId = result.map(t -> t.get(ContextKeys.SKU_ID)).orElse("");
        String venderId = result.map(t -> t.get(ContextKeys.VENDER_ID)).orElse("");
        if(StringUtils.isNotBlank(skuId)){
            page = getPageBySkuId(Long.parseLong(skuId), offset, pageSize);
        }else{
            log.info("skuId not exist, query by venderId.");
            page = getPageByVenderId(Long.parseLong(venderId), pageNum(pageRequest), pageSize);
        }
        page.setPageSize(pageSize);
        page.setPageNumber(pageNum(pageRequest));
        return new CommonResponse<>(page);
    }

    private int pageNum(PageRequest pageRequest){
        Optional<PageRequest> pageRequestOptional = Optional.ofNullable(pageRequest);
        int pageNum = pageRequestOptional.map(t -> t.getPageNumber()).orElse(1);
        if(pageNum < 1){
            pageNum = 1;
        }
        return pageNum;
    }

    private int pageSize(PageRequest pageRequest){
        Optional<PageRequest> pageRequestOptional = Optional.ofNullable(pageRequest);
        int pageSize = pageRequestOptional.map(t -> t.getPageSize()).orElse(20);
        if(pageSize < 1){
            pageSize = 20;
        }
        return pageSize;
    }

    /**
     * 按skuId获取分页数据
     * @param skuId
     * @param offset
     * @param pageSize
     * @return
     */
    private Page<StoreItem> getPageBySkuId(Long skuId, Integer offset, Integer pageSize){
        return buildPage(getStoreListInfo(productService.getStoreIdsBySkuIdOnPage(skuId, offset, pageSize)),
                productService.getTotalCountBySkuId(skuId));
    }

    /**
     * 按商家id获取分页数据
     * @param venderId
     * @param pageNum
     * @param pageSize
     * @return
     */
    private Page<StoreItem> getPageByVenderId(Long venderId, Integer pageNum, Integer pageSize){
        Paginate<VenderStoreTO>  page = storeService.getStoreOnPage(venderId, pageNum, pageSize);
        log.info("调用lbsService.queryStoreInfo接口，查询门店列表返回结果：{}", JSON.toJSONString(page));
        if(page == null || CollectionUtils.isEmpty(page.getItemList())){
            return buildPage(Collections.EMPTY_LIST, 0l);
        }
        return buildPage(page.getItemList()
                .stream()
                .map(s -> new StoreItemConverter1().convert(s))
                .collect(Collectors.toList()),
                (long)page.getTotalItem());
    }


    private Page<StoreItem> buildPage(List<StoreItem> storeItemList, Long totalCount){
        Page<StoreItem> page = new Page<>();
        page.setList(storeItemList);
        page.setTotalCount(totalCount);
        return page;
    }

    /**
     * 获取门店详情
     * @param storeIds
     * @return
     */
    private List<StoreItem> getStoreListInfo(List<Long> storeIds){
        if(CollectionUtils.isEmpty(storeIds)){
            return Collections.EMPTY_LIST;
        }
        return storeService
                .getStoresInfo(storeIds.stream().map(t -> String.valueOf(t)).collect(Collectors.toList()))
                .stream()
                .map(s -> new StoreItemConverter().convert(s))
                .collect(Collectors.toList());
    }


    private static class StoreItemConverter extends Converter<StoreTO, StoreItem> {

        @Override
        protected StoreItem doForward(StoreTO storeTO) {
            StoreItem storeItem = new StoreItem();
            storeItem.setStoreName(storeTO.getName());
            storeItem.setStoreCode(String.valueOf(storeTO.getId()));
            storeItem.setStoreAddress(storeTO.getFullAddress());
            storeItem.setStorePhone(storeTO.getMobilePhone() == null ? storeTO.getPhone() : storeTO.getMobilePhone());
            return storeItem;
        }

        @Override
        protected StoreTO doBackward(StoreItem storeItem) {
            return null;
        }
    }


    private class StoreItemConverter1 extends Converter<VenderStoreTO, StoreItem>{

        @Override
        protected StoreItem doForward(VenderStoreTO venderStoreTO) {
            StoreItem storeItem = new StoreItem();
            storeItem.setStoreName(venderStoreTO.getStoreName());
            storeItem.setStoreCode(String.valueOf(venderStoreTO.getStoreId()));
            return storeItem;
        }

        @Override
        protected VenderStoreTO doBackward(StoreItem storeItem) {
            return null;
        }
    }

    @Override
    public CommonResponse<StoreItem> getStoreItemByStoreCode(StoreItemRequest storeItemRequest) {
        Preconditions.checkNotNull(storeItemRequest.getStoreCode());
        List<StoreTO> storeList =  storeService.getStoresInfo(Lists.newArrayList(storeItemRequest.getStoreCode()));
        if(CollectionUtils.isNotEmpty(storeList)){
            return new CommonResponse<>(new StoreItemConverter().convert(storeList.get(0)));
        }
        return new CommonResponse<>();
    }


    public void setRpcContextService(RpcContextService rpcContextService) {
        this.rpcContextService = rpcContextService;
    }
}

package com.jd.appoint.service.api;

import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import com.jd.appoint.api.FormControlFacade;
import com.jd.appoint.api.vo.FormControlQuery;
import com.jd.appoint.api.vo.FormItemCheckRequest;
import com.jd.appoint.common.utils.Copier;
import com.jd.appoint.domain.enums.ItemTypeEnum;
import com.jd.appoint.domain.shop.ShopBusinessPO;
import com.jd.appoint.domain.shop.ShopFormControlItemPO;
import com.jd.appoint.domain.shop.query.FormControlItemQuery;
import com.jd.appoint.rpc.context.RpcContextService;
import com.jd.appoint.service.shop.ShopBusinessService;
import com.jd.appoint.service.shop.ShopFormControlItemService;
import com.jd.appoint.vo.*;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import com.jd.travel.monitor.aspects.ServerEnum;
import com.jd.virtual.appoint.ContextKeys;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by yangyuan on 5/14/18.
 */
@Service
public class FormControlFacadeImpl implements FormControlFacade {

    private static Logger log = LoggerFactory.getLogger(FormControlFacadeImpl.class);


    @Autowired
    private ShopBusinessService shopBusinessService;

    @Autowired
    private ShopFormControlItemService shopFormControlItemService;

    @Autowired
    private RpcContextService rpcContextService;

    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "根据业务编码获取业务信息及相关配置项", classify = FormControlFacadeImpl.class))
    public SoaResponse<FormControlVO> getFormControl(@ValideGroup SoaRequest<FormControlQuery> formControlQuery) {
        Optional<ShopBusinessPO> shopBusinessPO = Optional.ofNullable(shopBusinessService
                .queryByCondition(new FormItemQueryConvert().convert(formControlQuery.getData())));
        return shopBusinessPO.map(t -> new SoaResponse<>(new FormControlConvert().convert(t))).orElse(new SoaResponse<>());
    }

    private static class FormControlConvert extends Converter<ShopBusinessPO, FormControlVO> {
        @Override
        protected FormControlVO doForward(ShopBusinessPO shopBusinessPO) {
            FormControlVO formControlVO = new FormControlVO();
            formControlVO.setOnSite(shopBusinessPO.isOnSite());
            formControlVO.setToShop(shopBusinessPO.isToShop());
            if (CollectionUtils.isNotEmpty(shopBusinessPO.getItemList())) {
                formControlVO.setItemVOList(new ArrayList<>(Lists.transform(shopBusinessPO.getItemList(),
                        t -> new FormItemControlConvert().convert(t))));
            }
            return formControlVO;
        }

        @Override
        protected ShopBusinessPO doBackward(FormControlVO formControlVO) {
            return null;
        }
    }

    private static class FormItemControlConvert extends Converter<ShopFormControlItemPO, FormControlItemVO> {

        @Override
        protected FormControlItemVO doForward(ShopFormControlItemPO itemPO) {
            FormControlItemVO formControlItemVO = new FormControlItemVO();
            BeanUtils.copyProperties(itemPO, formControlItemVO);
            formControlItemVO.setControlItemType(Copier.enumCopy(itemPO.getItemType(), ControlItemType.class));
            if (StringUtils.isNotBlank(itemPO.getItemData())) {
                formControlItemVO.setControlItemData(new FormItemDataConvert().convert(itemPO.getItemData()));
            }
            return formControlItemVO;
        }

        @Override
        protected ShopFormControlItemPO doBackward(FormControlItemVO formControlItemVO) {
            return null;
        }
    }

    public static class FormItemDataConvert extends Converter<String, ControlItemData> {

        @Override
        protected ControlItemData doForward(String s) {
            ControlItemData itemData = new ControlItemData();
            if (isUrl(s)) {
                itemData.setUrl(s);
                return itemData;
            }
            itemData.setDataDetail(stringToParis(s));
            return itemData;

        }

        private List<Pair<Integer, String>> stringToParis(String str) {
            return Arrays.stream(str.split(",")).map(s -> Pair.toPair(s.trim())).collect(Collectors.toList());
        }

        private boolean isUrl(String str) {
            return str.contains("http");
        }

        @Override
        protected String doBackward(ControlItemData controlItemData) {
            return null;
        }
    }

    @Override
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "表单验证", classify = FormControlFacadeImpl.class))
    public SoaResponse<Boolean> checkFormItems(@ValideGroup SoaRequest<FormItemCheckRequest> formItemCheckRequest) {
        if(formItemCheckRequest.getData() == null || formItemCheckRequest.getData().getItemData() == null){
            log.info("no data check");
            return new SoaResponse<>(true);
        }
        List<ShopFormControlItemPO> itemList = shopFormControlItemService
                .query(new FormItemQueryConvert1().convert(formItemCheckRequest.getData()));
        Map<String, String> checkData = formItemCheckRequest.getData().getItemData();
        for (Map.Entry<String, String> entry : checkData.entrySet()) {
            if (!match(getRegular(itemList, entry.getKey()), entry.getValue())) {
                log.info("field check failed , fieldName = {}, value = {}", entry.getKey(), entry.getValue());
                return new SoaResponse(false);
            }
        }
        return new SoaResponse(true);
    }

    /**
     * regular为空，说明没有配置 不用校验
     *
     * @param regular
     * @param value
     * @return
     */
    private boolean match(String regular, String value) {
        if (StringUtils.isNotBlank(regular)) {
            return Pattern.matches(regular, value);
        }
        return true;
    }

    private String getRegular(List<ShopFormControlItemPO> shopFormControlItemPOs, String alias) {
        return shopFormControlItemPOs
                .stream()
                .filter(i -> i.getAlias().equals(alias))
                .findFirst()
                .map(i -> i.getRegular())
                .orElse("");
    }


    private static class FormItemQueryConvert extends Converter<FormControlQuery, FormControlItemQuery> {

        @Override
        protected FormControlItemQuery doForward(FormControlQuery formControlQuery) {
            FormControlItemQuery formControlItemQuery = new FormControlItemQuery();
            BeanUtils.copyProperties(formControlQuery, formControlItemQuery);
            return formControlItemQuery;
        }

        @Override
        protected FormControlQuery doBackward(FormControlItemQuery formControlItemQuery) {
            return null;
        }
    }

    private class FormItemQueryConvert1 extends Converter<FormItemCheckRequest, FormControlItemQuery> {

        @Override
        protected FormControlItemQuery doForward(FormItemCheckRequest formItemCheckRequest) {
            FormControlItemQuery formControlItemQuery = new FormControlItemQuery();
            Optional<Map<String, String>> result = Optional.ofNullable(formItemCheckRequest.getContextId() == null
                    ? null
                    : rpcContextService.getContext(formItemCheckRequest.getContextId()));
            formControlItemQuery.setBusinessCode(getBusinessCodeFilter(result, formItemCheckRequest));
            formControlItemQuery.setVenderId(getVenderIdFilter(result, formItemCheckRequest));
            String serverType = getServerType(result, formItemCheckRequest);
            if ("1".equals(serverType)) {//到店，自提
                formControlItemQuery.setToShopDisplay(true);
            }
            if ("2".equals(serverType)) {//上门，快递
                formControlItemQuery.setOnSiteDisplay(true);
            }
            return formControlItemQuery;
        }

        private String getBusinessCodeFilter(Optional<Map<String, String>> result, FormItemCheckRequest formItemCheckRequest){
            return result.map(t -> t.get(ContextKeys.BUSINESS_CODE))
                    .orElseGet(() -> {
                        String businessCode = formItemCheckRequest.getItemData().get(ContextKeys.BUSINESS_CODE);
                        if (StringUtils.isBlank(businessCode)) {
                            throw new IllegalArgumentException("business code not found.");
                        }
                        return businessCode;
                    });
        }

        private Long getVenderIdFilter(Optional<Map<String, String>> result, FormItemCheckRequest formItemCheckRequest){
            return result.map(t -> t.get(ContextKeys.VENDER_ID))
                    .map(t -> Long.valueOf(t))
                    .orElseGet(() -> {
                        if (formItemCheckRequest.getItemData() != null) {
                            String venderId = formItemCheckRequest.getItemData().get(ContextKeys.VENDER_ID);
                            return venderId == null ? null : Long.valueOf(venderId);
                        }
                        return null;
                    });
        }

        private String getServerType(Optional<Map<String, String>> result, FormItemCheckRequest formItemCheckRequest){
            return result.map(t -> t.get(ContextKeys.SERVER_TYPE))
                    .orElseGet(() -> {
                        if(formItemCheckRequest.getItemData() != null) {
                            return formItemCheckRequest.getItemData().get(ContextKeys.SERVER_TYPE);
                        }
                        return null;
                    });
        }

        @Override
        protected FormItemCheckRequest doBackward(FormControlItemQuery formControlItemQuery) {
            return null;
        }
    }


    public static void main(String[] args) {
        ShopFormControlItemPO itemPO = new ShopFormControlItemPO();
        itemPO.setItemType(ItemTypeEnum.BUTTON);
        FormControlItemVO formControlItemVO = new FormControlItemVO();
        BeanUtils.copyProperties(itemPO, formControlItemVO);
        System.out.println(formControlItemVO);
        System.out.println(Copier.enumCopy(ItemTypeEnum.BUTTON, ControlItemType.class));
    }
}


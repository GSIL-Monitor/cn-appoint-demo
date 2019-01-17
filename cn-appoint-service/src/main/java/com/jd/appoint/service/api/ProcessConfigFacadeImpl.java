package com.jd.appoint.service.api;

import com.google.common.base.Converter;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jd.appoint.api.ProcessConfigFacade;
import com.jd.appoint.api.vo.*;
import com.jd.appoint.common.enums.SoaCodeEnum;
import com.jd.appoint.common.utils.Copier;
import com.jd.appoint.domain.enums.ItemTypeEnum;
import com.jd.appoint.domain.enums.ServerTypeEnum;
import com.jd.appoint.domain.shop.ShopBusinessPO;
import com.jd.appoint.domain.shop.ShopFormControlItemPO;
import com.jd.appoint.domain.shop.query.FormControlItemQuery;
import com.jd.appoint.rpc.context.RpcContextService;
import com.jd.appoint.service.order.es.EsOrderService;
import com.jd.appoint.service.shop.ShopBusinessService;
import com.jd.appoint.service.shop.ShopFormControlItemService;
import com.jd.appoint.service.sys.ProcessConfigService;
import com.jd.appoint.vo.ControlItemType;
import com.jd.appoint.vo.Pair;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by shaohongsen on 2018/6/15.
 */
@Service("processConfigFacade")
public class ProcessConfigFacadeImpl implements ProcessConfigFacade {
    @Autowired
    private ProcessConfigService processConfigService;
    @Autowired
    private ShopFormControlItemService shopFormControlItemService;
    @Autowired
    private RpcContextService rpcContextService;

    @UmpMonitor(logCollector =
    @LogCollector(description = "获取流程配置", classify = ProcessConfigFacadeImpl.class))
    @Override
    public SoaResponse<ProcessConfigVO> getProcessConfig(@ValideGroup SoaRequest<ProcessConfigRequest> request) {
        SoaResponse<ProcessConfigVO> result = new SoaResponse<>();
        ProcessConfigRequest data = request.getData();
        Map<String, String> context = rpcContextService.getContext(data.getContextId());
        String businessCode = context.get("businessCode");
        String venderIdStr = context.get("venderId");
        if (Strings.isNullOrEmpty(businessCode) || Strings.isNullOrEmpty(venderIdStr)) {
            return new SoaResponse<>(SoaError.PARAMS_EXCEPTION, "businessCode OR venderIdStr IS null");
        }
        long venderId = Long.parseLong(venderIdStr);
        ProcessConfigVO processConfig = processConfigService.getProcessConfig(businessCode, data.getPageNo(), venderId);
        String serverTypeStr = context.get("serverType");
        Integer serverType = null;
        if (!Strings.isNullOrEmpty(serverTypeStr)) {
            serverType = Integer.parseInt(serverTypeStr);
        }
        List<TabControlVO> tabControlVOS = this.getTabList(businessCode, processConfig.getCurrentPageNo(), venderId, serverType);
        processConfig.setTabControlVOList(tabControlVOS);
        result.setResult(processConfig);
        return result;
    }

    private List<TabControlVO> getTabList(String businessCode, String pageNo, Long venderId, Integer serverType) {
        List<ShopFormControlItemPO> list = shopFormControlItemService.findByBusinessCodeAndPageNoAndVenderId(businessCode, pageNo, venderId);
        List<TabControlVO> result = this.convertToTabControl(list);
        //指定要某种模式表单项
        if (serverType != null) {
            result = result.stream().filter(tabControlVO -> serverType.equals(tabControlVO.getServerType())).collect(Collectors.toList());
        }
        return result;
    }

    private List<TabControlVO> convertToTabControl(List<ShopFormControlItemPO> list) {
        List<TabControlVO> result = Lists.newArrayList();
        //到家的tab
        TabControlVO toHome = new TabControlVO();
        toHome.setServerType(ServerTypeEnum.SHANGMEN.getIntValue());
        List<DynamicFormItemVo> homeItemVos = list.stream().filter(shopFormControlItemPO -> shopFormControlItemPO.getOnSiteDisplay())
                .map(shopFormControlItemPO -> convertToDynamicFormItemVo(shopFormControlItemPO)).collect(Collectors.toList());
        toHome.setFormItemVOList(homeItemVos);
        if (!homeItemVos.isEmpty()) {
            result.add(toHome);
        }
        //到店的tab
        TabControlVO toShop = new TabControlVO();
        toShop.setServerType(ServerTypeEnum.DAODIAN.getIntValue());
        List<DynamicFormItemVo> shopItemVos = list.stream().filter(shopFormControlItemPO -> shopFormControlItemPO.getToShopDisplay())
                .map(shopFormControlItemPO -> convertToDynamicFormItemVo(shopFormControlItemPO)).collect(Collectors.toList());
        toShop.setFormItemVOList(shopItemVos);
        if (!shopItemVos.isEmpty()) {
            result.add(toShop);
        }
        //到点到家至少选一项，过滤一下有一个没选的，看看有没有到店和到家不一样的内容
        boolean isDifferent = list.stream()
                .anyMatch(shopFormControlItemPO -> !shopFormControlItemPO.getToShopDisplay()
                        || !shopFormControlItemPO.getOnSiteDisplay());
        //填写的信息项都一样，并且支持到店和到家，返回任意一个就OK
        if (isDifferent && result.size() > 1) {
            //此时前端不应该选择serverType
            result.get(0).setServerType(null);
            return result.subList(0, 1);
        }
        //否则填写的信息项不一样，返回多tab页
        return result;
    }

    private DynamicFormItemVo convertToDynamicFormItemVo(ShopFormControlItemPO shopFormControlItemPO) {
        DynamicFormItemVo vo = new DynamicFormItemVo();
        vo.setAlias(shopFormControlItemPO.getAlias());
        vo.setLabel(shopFormControlItemPO.getName());
        vo.setPlaceHolder(shopFormControlItemPO.getTips());
        vo.setType(shopFormControlItemPO.getItemType().getIntValue());
        //有值并且是下拉框或者单选框
        if (!Strings.isNullOrEmpty(shopFormControlItemPO.getItemData())
                && (shopFormControlItemPO.getItemType() == ItemTypeEnum.SELECT
                || shopFormControlItemPO.getItemType() == ItemTypeEnum.BUTTON)) {
            List<Pair<String, String>> list = Splitter.on(",")
                    .omitEmptyStrings()
                    .splitToList(shopFormControlItemPO.getItemData())
                    .stream().map(str -> Pair.toStrPair(str))
                    .collect(Collectors.toList());
            vo.setData(list);
        }
        return vo;
    }

}

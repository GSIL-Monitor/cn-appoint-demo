package com.jd.appoint.service.api.outer;

import com.google.common.collect.Lists;
import com.jd.appoint.rpc.card.dto.CardDTO;
import com.jd.appoint.rpc.context.RpcContextService;
import com.jd.appoint.service.card.CardService;
import com.jd.appoint.service.card.convent.CardSkuConverter;
import com.jd.appoint.service.card.dto.SkuRequestDto;
import com.jd.appoint.service.card.dto.SkuRespDto;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.aspects.ServerEnum;
import com.jd.virtual.appoint.SkuGwService;
import com.jd.virtual.appoint.common.CommonRequest;
import com.jd.virtual.appoint.common.CommonResponse;
import com.jd.virtual.appoint.enums.StatusCode;
import com.jd.virtual.appoint.sku.SkuInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/22 11:30
 */
@Service(value = "skuGwService")
public class SkuGwServiceImpl implements SkuGwService {

    private static final Logger logger = LoggerFactory.getLogger(SkuGwServiceImpl.class);
    @Resource
    private RpcContextService rpcContextService;
    @Resource
    private CardService cardService;

    /**
     * 获取sku的信息
     *
     * @param commonRequest
     * @return
     */
    @Override
    @UmpMonitor(serverType = ServerEnum.NONE, logCollector =
    @LogCollector(description = "获取SKU信息", classify = SkuGwServiceImpl.class))
    public CommonResponse<List<SkuInfo>> getSkuInfo(CommonRequest commonRequest) {
        Map<String, String> maps = rpcContextService.getContext(commonRequest.getContextId());
        SkuRequestDto request = new SkuRequestDto();

        List<SkuInfo> skuInfos = Lists.newArrayList();
        try {
            BeanUtils.populate(request, maps);
            //业务代码不能为空
            if (null == request.getBusinessCode()) {
                return new CommonResponse<>(StatusCode.BUSINESS_CODE_NIL_ERROR, true);
            }
            //商家id不能为空
            if (null == request.getVenderId()) {
                return new CommonResponse<>(StatusCode.VENDERID_NIL_ERROR, true);
            }
            //校验卡号不能为空
            if (null == request.getCardNo()) {
                return new CommonResponse<>(StatusCode.CODE_PWD_NIL_ERROR, true);
            }

            CardDTO cardDTO = CardSkuConverter.convert(request);
            //请求返回对应的sku数据
            SkuRespDto skuRespDto = cardService.getCardSkuInfos(cardDTO);
            if (null == skuRespDto) {
                //如果为空
                return new CommonResponse<>(StatusCode.FAIL, true);
            }
            //添加缓存
            SkuInfo skuInfo = new SkuInfo();
            skuInfo.setSkuId(String.valueOf(skuRespDto.getSkuId()));
            skuInfo.setSkuName(skuRespDto.getSkuName());
            skuInfos.add(skuInfo);
            return new CommonResponse(skuInfos);
        } catch (Exception e) {
            logger.error("通过卡密信息获取卡密sku的信息异常，e={}", e);
        }
        return new CommonResponse<>(StatusCode.FAIL, true);
    }
}

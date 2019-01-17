package com.jd.appoint.service.card.executes.vsc;

import com.jd.appoint.rpc.card.dto.CardDTO;
import com.jd.appoint.rpc.card.dto.SkuInfoDTO;
import com.jd.appoint.rpc.card.vsc.RpcVscCardService;
import com.jd.appoint.service.card.CardConfigConstants;
import com.jd.appoint.service.card.dto.SkuRespDto;
import com.jd.appoint.service.card.operate.CardExcecuteOperate;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/25 17:38
 */
@Service(CardConfigConstants.VSC_GET_SKUINFO_EXECUTOR)
public class VscGerSkuInfoExecutor implements CardExcecuteOperate<SkuRespDto, CardDTO> {
    @Resource
    private RpcVscCardService rpcVscCardService;


    @Override
    public SkuRespDto execute(CardDTO cardDTO) {
        SoaResponse<SkuInfoDTO> soaResponse = rpcVscCardService.getSkuInfo(new SoaRequest<>(cardDTO));
        if (500 == soaResponse.getCode()) {
            throw new RuntimeException("rpcVscCardService.getSkuInfo 异常");
        }
        SkuRespDto skuRespDto = null;
        if (soaResponse.isSuccess()) {
            SkuInfoDTO skuInfoDTO = soaResponse.getResult();
            skuRespDto = new SkuRespDto();
            skuRespDto.setSkuId(skuInfoDTO.getSkuId());
            skuRespDto.setSkuName(skuInfoDTO.getSkuName());
            skuRespDto.setRelatedErpOrderId(skuInfoDTO.getOrderId());
        }
        return skuRespDto;
    }
}

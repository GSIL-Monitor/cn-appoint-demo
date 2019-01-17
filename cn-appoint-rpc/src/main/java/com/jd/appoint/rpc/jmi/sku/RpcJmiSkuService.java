package com.jd.appoint.rpc.jmi.sku;

import com.jd.appoint.rpc.card.dto.SkuInfoDTO;
import com.jd.fastjson.JSON;
import com.jd.jmi.ware.client.domain.ware.JmiWareParam;
import com.jd.jmi.ware.client.domain.ware.JmiWareSkuParam;
import com.jd.jmi.ware.client.response.JmiClientResponse;
import com.jd.jmi.ware.client.service.JmiWareSkuSoaService;
import com.jd.jmi.ware.client.service.JmiWareSoaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RpcJmiSkuService {
    private Logger logger = LoggerFactory.getLogger(RpcJmiSkuService.class);
    @Autowired
    private JmiWareSoaService jmiWareSoaService;
    @Autowired
    private JmiWareSkuSoaService jmiWareSkuSoaService;

    public SkuInfoDTO getSku(long skuId) {
        logger.info("jmiWareSkuSoaService.getSkuBaseInfoByJSkuId request", skuId);
        JmiClientResponse<JmiWareSkuParam> skuResponse = jmiWareSkuSoaService.getSkuBaseInfoByJSkuId(skuId);
        logger.info("jmiWareSkuSoaService.getSkuBaseInfoByJSkuId response", JSON.toJSONString(skuResponse));
        JmiWareSkuParam skuData = skuResponse.getData();
        Long wareId = skuData.getjWareId();
        logger.info("jmiWareSoaService.getWareBaseInfoByJWareId request", wareId);
        JmiClientResponse<JmiWareParam> wareResponse = jmiWareSoaService.getWareBaseInfoByJWareId(wareId);
        logger.info("jmiWareSoaService.getWareBaseInfoByJWareId response", JSON.toJSONString(wareResponse));
        JmiWareParam wareData = wareResponse.getData();
        SkuInfoDTO skuInfoDTO = new SkuInfoDTO();
        skuInfoDTO.setSkuId(skuData.getSkuId());
        skuInfoDTO.setSkuName(wareData.getTitle());
        return skuInfoDTO;
    }

}

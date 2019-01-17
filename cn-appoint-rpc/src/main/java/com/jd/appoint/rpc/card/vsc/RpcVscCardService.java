package com.jd.appoint.rpc.card.vsc;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.jd.appoint.common.utils.IPUtil;
import com.jd.appoint.domain.enums.CheckCardResultEnum;
import com.jd.appoint.rpc.RpcConfig;
import com.jd.appoint.rpc.card.dto.*;
import com.jd.travel.base.soa.SoaError;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.ValideGroup;
import com.jd.travel.monitor.aspects.ServerEnum;
import com.jd.vsc.soa.domain.RequestWrap;
import com.jd.vsc.soa.domain.ResponseWrap;
import com.jd.vsc.soa.domain.lock.LockCerts;
import com.jd.vsc.soa.domain.lock.LockCertsResult;
import com.jd.vsc.soa.domain.verify.*;
import com.jd.vsc.soa.service.VerificationSoaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/15 15:45
 */
@Service(value = "rpcVscCardService")
public class RpcVscCardService {
    private static final Logger logger = LoggerFactory.getLogger(RpcVscCardService.class);
    @Resource
    private VerificationSoaService verificationSoaService;

    /**
     * 校验卡密
     *
     * @param request
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA,
            logCollector = @LogCollector(description = "调用VSC校验卡密", classify = RpcVscCardService.class))
    public SoaResponse<CheckCardResultEnum> checkCard(SoaRequest<CardDTO> request) {
        ResponseWrap<PreviewResult> response = dopreview(request);
        if(200 == response.getCode()){
            PreviewResult result = response.getData();
            if(null != result &&
                    null != result.getLeftTimes() &&
                    result.getLeftTimes() > 0){
                //等于200的情况，剩余次数必须大于0才可预约
                return new SoaResponse<>(CheckCardResultEnum.SUCCESS);
            }
            if(result.getReserved() || result.getVerified()){
                return new SoaResponse<>(CheckCardResultEnum.APPOINTED);
            }
        }
        return new SoaResponse<>(statusConvert(response.getCode()));
    }


    /**
     * 执行调用
     *
     * @param request 请求参数
     * @return
     */
    private ResponseWrap<PreviewResult> dopreview(SoaRequest<CardDTO> request) {
        RequestWrap requestWrap = getRequestInstance(request);
        CardDTO cardDTO = request.getData();
        Preview preview = new Preview();
        //TODO 需要抽取加密的包
        preview.setCardNum(AesUtils.encrypt(RpcConfig.VSC_AESKEY, cardDTO.getCardNumber()));
        preview.setPwd(AesUtils.encrypt(RpcConfig.VSC_AESKEY, cardDTO.getCardPwd()));
        requestWrap.setBusinessType(Integer.valueOf(cardDTO.getBusinessCode()));
        requestWrap.setMerchantCode(String.valueOf(cardDTO.getVenderId()));
        requestWrap.setTrackerId(request.getTraceId());
        requestWrap.setData(preview);
        requestWrap.setMerchantCode(String.valueOf(cardDTO.getVenderId()));
        logger.info("调用VSC校验卡密请求：requestWrap={}", JSONArray.toJSON(requestWrap));
        ResponseWrap<PreviewResult> resultResponseWrap = verificationSoaService.preview(requestWrap);
        logger.info("调用VSC校验卡密返回结果：resultResponseWrap={}", JSONArray.toJSON(resultResponseWrap));
        if (500 == resultResponseWrap.getCode()) {
            logger.error("调用VSC校验卡密校验失败，返回信息result={}", JSONArray.toJSON(resultResponseWrap));
            throw new RuntimeException("调用VSC校验卡密校验服务异常");
        }
        return resultResponseWrap;
    }

    /**
     * 通过卡密获得商品信息的属性
     *
     * @param request
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA, logCollector =
    @LogCollector(description = "调用VSC查询卡密SKU信息", classify = RpcVscCardService.class))
    public SoaResponse<SkuInfoDTO> getSkuInfo(SoaRequest<CardDTO> request) {
        ResponseWrap<PreviewResult> resultResponseWrap = dopreview(request);
        PreviewResult previewResult = resultResponseWrap.getData();
        SkuInfoDTO skuInfoDTO = null;
        if (null == previewResult
                || null == previewResult.getSkuId()
                || null == previewResult.getSkuName()) {
            logger.error("没有返回对应的数据，previewResult={}", JSONArray.toJSONString(previewResult));
            return new SoaResponse(SoaError.PARAMS_EXCEPTION);
        }
        skuInfoDTO = new SkuInfoDTO();
        skuInfoDTO.setSkuId(previewResult.getSkuId());
        skuInfoDTO.setSkuName(previewResult.getSkuName());
        skuInfoDTO.setOrderId(previewResult.getOrderId());
        return new SoaResponse<>(skuInfoDTO);
    }

    /**
     * 锁定卡密
     *
     * @param request
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA,
            logCollector = @LogCollector(description = "调用VSC锁定卡密", classify = RpcVscCardService.class))
    public SoaResponse<Boolean> lockCard(@ValideGroup SoaRequest<LockCardDTO> request) {
        RequestWrap requestWrap = getRequestInstance(request);
        LockCardDTO lockCardDTO = request.getData();
        requestWrap.setBusinessType(Integer.valueOf(lockCardDTO.getBusinessCode()));
        requestWrap.setMerchantCode(String.valueOf(lockCardDTO.getVenderId()));
        //--
        LockCerts lockCerts = new LockCerts();
        //FIXME 传递参数有调整
        lockCerts.setTransactionId(lockCardDTO.getTransactionId() + "-lockCard");
        //卡密信息
        List<Cert> lockCertList = transferCardDTO2Cert(lockCardDTO.getLockCards());
        lockCerts.setCertList(lockCertList);
        requestWrap.setData(lockCerts);
        requestWrap.setTrackerId(request.getTraceId());
        logger.info("调用VSC锁定卡密请求参数：requestWrap={}", JSONArray.toJSONString(requestWrap));
        ResponseWrap<LockCertsResult> vcResultResponseWrap =
                verificationSoaService.lockCerts(requestWrap);
        logger.info("调用VSC锁定卡密返回值：vcResultResponseWrap={}", JSONArray.toJSONString(vcResultResponseWrap));
        if (500 == vcResultResponseWrap.getCode()) {
            logger.error("调用VSC锁定卡密失败，返回信息result={}", JSONArray.toJSON(vcResultResponseWrap));
            throw new RuntimeException("调用VSC锁定卡密服务异常");
        }
        if (200 != vcResultResponseWrap.getCode()) {
            logger.error("调用VSC锁定卡密失败，返回信息result={}", JSONArray.toJSON(vcResultResponseWrap));
            return new SoaResponse<Boolean>(false);
        }
        LockCertsResult verifyCertsResult = vcResultResponseWrap.getData();
        //，Status 1： 成功， 3：失败
        if (1 == verifyCertsResult.getStatus()) {
            return new SoaResponse<>(true);
        }
        logger.error("调用VSC锁定卡密失败，返回信息result={}", JSONArray.toJSON(verifyCertsResult));
        return new SoaResponse<>(false);
    }

    /**
     * 解除锁定
     *
     * @param request
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA,
            logCollector = @LogCollector(description = "调用VSC解锁卡密", classify = RpcVscCardService.class))
    public SoaResponse<Boolean> unLockCard(@ValideGroup SoaRequest<UnLockCardDTO> request) {
        RequestWrap requestWrap = getRequestInstance(request);
        UnLockCardDTO unLockCardDTO = request.getData();
        LockCerts unLockCerts = new LockCerts();
        requestWrap.setBusinessType(Integer.valueOf(unLockCardDTO.getBusinessCode()));
        requestWrap.setMerchantCode(String.valueOf(unLockCardDTO.getVenderId()));
        unLockCerts.setTransactionId(unLockCardDTO.getTransactionId() + "-unLockCard");
        //卡密信息
        List<Cert> unLockCertList = transferCardDTO2Cert(unLockCardDTO.getUnlockCards());
        unLockCerts.setCertList(unLockCertList);
        requestWrap.setData(unLockCerts);
        requestWrap.setTrackerId(request.getTraceId());
        logger.info("调用VSC解锁卡密请求参数：requestWrap={}", JSONArray.toJSONString(requestWrap));
        ResponseWrap<LockCertsResult> resultResponseWrap = verificationSoaService.unLockCerts(requestWrap);
        logger.info("调用VSC解锁卡密返回值：resultResponseWrap={}", JSONArray.toJSONString(resultResponseWrap));
        if (500 == resultResponseWrap.getCode()) {
            logger.error("调用VSC解锁卡密失败，返回信息result={}", JSONArray.toJSON(resultResponseWrap));
            throw new RuntimeException("调用VSC解锁卡密服务异常");
        }
        if (200 != resultResponseWrap.getCode()) {
            logger.error("调用VSC解锁卡密失败，返回信息result={}", JSONArray.toJSON(resultResponseWrap));
            return new SoaResponse<Boolean>(false);
        }
        LockCertsResult lockCertsResult = resultResponseWrap.getData();
        //status 凭证锁定处理结果（必填， 1： 成功， 3：失败）
        int status = lockCertsResult.getStatus();
        if (1 == status) {
            return new SoaResponse<>(true);
        }
        logger.warn("调用VSC解锁卡密失败，返回信息result={}", JSONArray.toJSON(lockCertsResult));
        return new SoaResponse<>(false);
    }


    /**
     * 卡密核销
     *
     * @param request
     * @return
     */
    @UmpMonitor(serverType = ServerEnum.SOA,
            logCollector = @LogCollector(description = "调用VSC卡密核销接口", classify = RpcVscCardService.class))
    public SoaResponse<Boolean> verifyCard(SoaRequest<VerifyCards> request) {
        RequestWrap requestWrap = getRequestInstance(request);
        VerifyCards verifyCards = request.getData();
        requestWrap.setBusinessType(Integer.valueOf(verifyCards.getBusinessCode()));
        requestWrap.setMerchantCode(String.valueOf(verifyCards.getVenderId()));
        Optional<VerifyCardUnit> cardsOptional = verifyCards.getVerifyCards().stream().findFirst();
        VerifyCerts verifyCerts = new VerifyCerts();
        if (cardsOptional.isPresent()) {
            //核销卡密的最小单元
            VerifyCardUnit verifyCardUnit = cardsOptional.get();
            verifyCerts.setTransactionId(verifyCardUnit.getTransactionId() + "-verifyCard");
            List<Cert> verifyingCertList = transferCardDTO2Cert(verifyCardUnit.getVerifyCards());
            verifyCerts.setVerifyingCertList(verifyingCertList);
        }
        requestWrap.setData(verifyCerts);
        requestWrap.setTrackerId(request.getTraceId());
        //	status 凭证核销操作处理结果（必填， 1： 成功， 3：失败）
        logger.info("调用VSC卡密核销接口请求参数：requestWrap={}", JSONArray.toJSON(requestWrap));
        ResponseWrap<VerifyCertsResult> vcResult = verificationSoaService.verify(requestWrap);
        logger.info("调用VSC卡密核销接口返回值：vcResult={}", JSONArray.toJSON(vcResult));
        if (500 == vcResult.getCode()) {
            logger.error("调用VSC卡密核销失败，返回信息result={}", JSONArray.toJSON(vcResult));
            throw new RuntimeException("调用VSC卡密核销接口异常");
        }
        if (200 != vcResult.getCode()) {
            logger.error("调用VSC卡密核销接口失败，返回信息result={}", JSONArray.toJSON(vcResult));
            return new SoaResponse<Boolean>(false);
        }
        VerifyCertsResult verifyCertsResult = vcResult.getData();
        if (1 == verifyCertsResult.getStatus()) {
            return new SoaResponse<>(true);
        }
        return new SoaResponse<>(false);
    }


    /**
     * 转换参数
     *
     * @param cardDTOs
     * @return
     */
    private List<Cert> transferCardDTO2Cert(List<CardDTO> cardDTOs) {
        return Lists.transform(cardDTOs, new Function<CardDTO, Cert>() {
            @Nullable
            @Override
            public Cert apply(@Nullable CardDTO input) {
                Cert cert = new Cert();
                cert.setCardNum(AesUtils.encrypt(RpcConfig.VSC_AESKEY, input.getCardNumber()));
                cert.setPwd(AesUtils.encrypt(RpcConfig.VSC_AESKEY, input.getCardPwd()));
                cert.setVerifyingTimes(1);
                return cert;
            }
        });
    }

    /**
     * 通用的接口调用
     *
     * @return
     */
    private RequestWrap getRequestInstance(SoaRequest request) {
        RequestWrap requestWrap = new RequestWrap();
        requestWrap.setAppCode(RpcConfig.VSC_APPCODE);
        requestWrap.setClientIp(IPUtil.LOCAL_IP);
        requestWrap.setSource(RpcConfig.VSC_SOURCE);
        requestWrap.setToken(RpcConfig.VSC_TOKEN);
        return requestWrap;
    }

    /**
     * 卡密校验结果状态码转换
     * @param code
     * @return
     */
    private CheckCardResultEnum statusConvert(Integer code){
        switch (code){
            case 404 : return CheckCardResultEnum.CODE_PASSWORD_ERROR;
            case 414 : return CheckCardResultEnum.CODE_NOT_EXISTS;
            case 417 : return CheckCardResultEnum.DISABLE;
            case 418 : return CheckCardResultEnum.EXPIRED;
            default : return CheckCardResultEnum.FAIL;
        }
    }
}


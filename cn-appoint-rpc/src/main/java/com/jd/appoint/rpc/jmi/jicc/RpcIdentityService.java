package com.jd.appoint.rpc.jmi.jicc;

import com.jd.appoint.rpc.ex.RpcException;
import com.jd.appoint.rpc.jmi.dto.IdentityDTO;
import com.jd.fastjson.JSON;
import com.jd.jmi.jicc.client.domain.*;
import com.jd.jmi.jicc.client.enums.JICCPapersTypeEnum;
import com.jd.jmi.jicc.client.exception.JICCRetryAbleException;
import com.jd.jmi.jicc.client.exception.JICCUnReTryAbleException;
import com.jd.jmi.jicc.client.service.impl.JICCIdentityInfoJSFService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by shaohongsen on 2018/5/21.
 */
@Service
public class RpcIdentityService {
    private Logger logger = LoggerFactory.getLogger(RpcIdentityService.class);
    @Autowired
    private JICCIdentityInfoJSFService jiccIdentityInfoJSFService;
    private static final int MAX_RETRY_TIME = 3;

    /**
     * jmi 添加户簿信息
     *
     * @param identityDTO
     * @return
     */
    public String addUserIdentity(IdentityDTO identityDTO) {
        JICCRequest jiccRequest = this.createJICCRequest(identityDTO);
        logger.info("调用jmi户簿addUserIdentity request:{}", JSON.toJSONString(jiccRequest));
        long retryTime = 0;//尝试次数
        while (retryTime++ <= MAX_RETRY_TIME) {
            try {
                JICCAddUserIdentityResult jiccAddUserIdentityResult = jiccIdentityInfoJSFService.addUserIdentity(jiccRequest);
                logger.info("调用jmi户簿addUserIdentity response:{}", JSON.toJSONString(jiccAddUserIdentityResult));
                if (jiccAddUserIdentityResult != null) {
                    return jiccAddUserIdentityResult.getIdentityId();
                }
            } catch (JICCRetryAbleException e) {
                logger.error("调用jmi户簿addUserIdentity，尝试次数：{},可重试失败，失败原因：{}", retryTime, e);
            } catch (JICCUnReTryAbleException e) {
                throw new RpcException(e);
            }
        }
        logger.warn("调用jmi户簿，尝试次数3,仍未成功");
        return null;
    }

    public IdentityDTO getUserIdentityInfo(String identity, String userPin) {
        JICCQueryRequest jiccQueryRequest = this.createJiccQueryRequest(identity, userPin);
        long retryTime = 0;//尝试次数
        logger.info("调用jmi户簿addUserIdentity request:{}", JSON.toJSONString(jiccQueryRequest));
        while (retryTime++ <= MAX_RETRY_TIME) {
            try {
                List<JICCUserIdentityInfoResult> userIdentityInfo = jiccIdentityInfoJSFService.getUserIdentityInfo(jiccQueryRequest);
                logger.info("调用jmi户簿addUserIdentity response:{}", JSON.toJSONString(userIdentityInfo));
                if (userIdentityInfo != null && userIdentityInfo.size() == 1) {
                    IdentityDTO identityDTO = this.createIdentityDTO(userIdentityInfo.get(0));
                    return identityDTO;
                }
            } catch (JICCRetryAbleException e) {
                logger.error("调用jmi户簿getUserIdentityInfo，尝试次数：{},可重试失败，失败原因：{}", retryTime, e);
            } catch (JICCUnReTryAbleException e) {
                throw new RpcException(e);
            }
        }
        return null;
    }

    private JICCQueryRequest createJiccQueryRequest(String identity, String userPin) {
        JICCQueryRequest jiccQueryRequest = new JICCQueryRequest();
        jiccQueryRequest.setUuid(UUID.randomUUID().toString());
        jiccQueryRequest.setUserPin(userPin);
        jiccQueryRequest.setIp("106.38.115.24");
        jiccQueryRequest.setPort("8080");
        jiccQueryRequest.setOperator("system");
        return jiccQueryRequest;
    }

    private IdentityDTO createIdentityDTO(JICCUserIdentityInfoResult jiccUserIdentityInfoResult) {
        IdentityDTO identityDTO = new IdentityDTO();
        identityDTO.setUserPin(jiccUserIdentityInfoResult.getUserPin());
        identityDTO.setName(jiccUserIdentityInfoResult.getName());
        identityDTO.setCertType(JICCPapersTypeEnum.ExistEnum(jiccUserIdentityInfoResult.getCertType()));
        identityDTO.setCertNum(jiccUserIdentityInfoResult.getCertNum());
        // JICCCertNumUtil.decrypt(jiccUserIdentityInfoResult.getEncryptText(),key);
        return identityDTO;
    }

    /**
     * @param identityDTO
     * @return
     */
    private JICCRequest createJICCRequest(IdentityDTO identityDTO) {
        JICCRequest jiccRequest = new JICCRequest();
        jiccRequest.setUuid(UUID.randomUUID().toString());
        JICCUserIdentityInfoParam param = new JICCUserIdentityInfoParam();
        param.setUserPin(identityDTO.getUserPin());
        param.setName(identityDTO.getName());
        param.setAuditStatus(1);
        param.setAppId("33662");
        param.setCertNum(identityDTO.getCertNum());
        param.setCertType(identityDTO.getCertType().getCode());
        jiccRequest.setUserIdentityInfoParam(param);
        return jiccRequest;
    }

}

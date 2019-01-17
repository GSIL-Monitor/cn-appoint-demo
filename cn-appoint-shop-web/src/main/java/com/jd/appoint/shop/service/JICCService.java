package com.jd.appoint.shop.service;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.shop.exception.RpcException;
import com.jd.appoint.shop.vo.IdentityVO;
import com.jd.bdp.jdq.control.jsf.JsfConf;
import com.jd.jmi.jicc.client.domain.JICCCertImgParam;
import com.jd.jmi.jicc.client.domain.JICCQueryRequest;
import com.jd.jmi.jicc.client.domain.JICCRequest;
import com.jd.jmi.jicc.client.domain.JICCSaveCertInfoRequest;
import com.jd.jmi.jicc.client.domain.JICCSaveCertInfoResult;
import com.jd.jmi.jicc.client.domain.JICCUpdateUserIdentityResult;
import com.jd.jmi.jicc.client.domain.JICCUserIdentityInfoParam;
import com.jd.jmi.jicc.client.domain.JICCUserIdentityInfoResult;
import com.jd.jmi.jicc.client.domain.img.JICCImgInfoQueryParam;
import com.jd.jmi.jicc.client.domain.img.JICCImgInfoQueryResult;
import com.jd.jmi.jicc.client.domain.img.JICCImgUploadParam;
import com.jd.jmi.jicc.client.domain.img.JICCImgUploadResult;
import com.jd.jmi.jicc.client.enums.JICCAuditStatusEnum;
import com.jd.jmi.jicc.client.enums.JICCPapersTypeEnum;
import com.jd.jmi.jicc.client.exception.JICCRetryAbleException;
import com.jd.jmi.jicc.client.exception.JICCUnReTryAbleException;
import com.jd.jmi.jicc.client.service.JICCImgInfoService;
import com.jd.jmi.jicc.client.service.impl.JICCIdentityInfoJSFService;
import com.jd.jsf.gd.util.JSFContext;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by bjliuyong on 2018/5/30.
 */
@Service
public class JICCService {

    private static Logger LOG = LoggerFactory.getLogger(JICCService.class);

    @Resource
    private JICCIdentityInfoJSFService jiccIdentityInfoJSFService ;

    @Resource
    private JICCImgInfoService jiccImgInfoService;
    private String appId;
    private String componentKey;

    /*@PostConstruct
    public void init() {
        if(null == JSFContext.get("appId")) {
            JSFContext.put("appId", this.appId);
        }
    }*/

    public String add(IdentityVO identityVO ,String userPin) throws JICCUnReTryAbleException, JICCRetryAbleException {

        JICCSaveCertInfoRequest request = new JICCSaveCertInfoRequest() ;
        if(StringUtils.isNotBlank(identityVO.getImgSid())) {
            JICCCertImgParam jiccCertImgParam = build(identityVO.getImgSid() , userPin ,null , identityVO.getType()) ;
            request.setCertImgParam(jiccCertImgParam);
        }

        JICCUserIdentityInfoParam param = build(identityVO , userPin) ;
        request.setUserIdentityInfoParam(param);
        LOG.info("jiccIdentityInfoJSFService.saveCertInfo param{} " , request);

        JICCSaveCertInfoResult result = jiccIdentityInfoJSFService.saveCertInfo(request) ;
        if(result == null || StringUtils.isBlank(result.getIdentityId()))
            throw new RpcException("JICCService.add.result =>" + result) ;

        return result.getIdentityId() ;
    }

    public String updateIdentity(IdentityVO identityVO ,String userPin ) throws JICCUnReTryAbleException, JICCRetryAbleException {

        //判断身份证信息是否修改
        /*IdentityVO oldIdentityVO = queryIdentity(userPin , identityVO.getSecurityId()) ;
        if(oldIdentityVO.equals(identityVO)) {
            return  identityVO.getSecurityId() ;
        }*/

        String no = identityVO.getNo() ;
        //这里导致只修改姓名和图片不能向户簿发起请求，造成姓名与列表不一致
        /*if(no.indexOf("*") >= 0)
            return identityVO.getSecurityId() ;*/

        JICCRequest jiccRequest = new JICCRequest() ;
        JICCUserIdentityInfoParam param = build(identityVO , userPin) ;
        jiccRequest.setUserIdentityInfoParam(param);
        JICCUpdateUserIdentityResult result = jiccIdentityInfoJSFService.updateUserIdentityInfo(jiccRequest) ;
        if(result == null || StringUtils.isBlank(result.getIdentityId()))
            throw new RpcException("JICCService.update.result =>" + result) ;

        return result.getIdentityId() ;
    }


    public IdentityVO queryIdentityAndImg(String userPin , String identityId ,String operator) throws JICCUnReTryAbleException, JICCRetryAbleException {

        IdentityVO identityVO = queryIdentity(userPin , identityId ) ;

        JICCImgInfoQueryParam jiccImgInfoQueryParam = new  JICCImgInfoQueryParam() ;
        jiccImgInfoQueryParam.setIdentityId(identityId);
        jiccImgInfoQueryParam.setUserPin(userPin);
        jiccImgInfoQueryParam.setAppId(appId);
        jiccImgInfoQueryParam.setComponentKey(componentKey);
        jiccImgInfoQueryParam.setOperator(operator);
        LOG.info("jiccImgInfoService.queryImgInfos 入参 ：【{}】", JSON.toJSONString(jiccImgInfoQueryParam));

        List<JICCImgInfoQueryResult> jiccImgInfoQueryResults = jiccImgInfoService.queryImgInfos(jiccImgInfoQueryParam) ;
        LOG.info("jiccImgInfoService.queryImgInfos 返回结果 ：【{}】", JSON.toJSONString(jiccImgInfoQueryResults));

        if(jiccImgInfoQueryResults == null || jiccImgInfoQueryResults.isEmpty())
            throw new RpcException("JICCService.queryImgInfos.jiccImgInfoQueryResults =>" + jiccImgInfoQueryResults) ;

        for(JICCImgInfoQueryResult jiccImgInfoQueryResult : jiccImgInfoQueryResults) {
            int imgNo = jiccImgInfoQueryResult.getImgNo() ;
            if( imgNo == 1) {
                identityVO.setFrontImg(jiccImgInfoQueryResult.getImgAccessUrl());
            } else if(imgNo == 2) {
                identityVO.setReverseImg(jiccImgInfoQueryResult.getImgAccessUrl());
            }
        }
        return identityVO ;
    }

    private IdentityVO queryIdentity(String userPin , String identityId ) throws JICCUnReTryAbleException, JICCRetryAbleException {
        JICCQueryRequest request = new JICCQueryRequest();
        request.setAppId(appId);
        request.setUserPin(userPin);
        request.setIdentityId(identityId);//精确查询，这个id会在添加身份信息返回，业务线自行保存
        request.setVerifyCert(false); // 为了提高速度这个参数可以写false，不去京东金融做主身份校验
        List<JICCUserIdentityInfoResult> result = jiccIdentityInfoJSFService.getUserIdentityInfo(request);
        if(result == null || result.isEmpty())
            throw new RpcException("JICCService.getUserIdentityInfo.result =>" + result) ;
        JICCUserIdentityInfoResult result1 = result.get(0) ;

        IdentityVO identityVO = new IdentityVO() ;
        identityVO.setType(result1.getCertType());
        identityVO.setName(result1.getName());
        identityVO.setNo(result1.getCertNum());

        return  identityVO ;
    }

    public void uploadImg(String userPin ,int certType , String sid ,  int imgNo , byte[] data ) throws JICCUnReTryAbleException, JICCRetryAbleException {
        // 图像数据
        JICCImgUploadParam imageUpload = new JICCImgUploadParam();
        imageUpload.setSessionId(sid);
        imageUpload.setAppId(appId);
        imageUpload.setComponentKey(componentKey);
        imageUpload.setTransactionId(sid+imgNo);
        imageUpload.setCertType(certType); //JICCPapersTypeEnum

        //身份证：1=正面，2=反面，3=手持；其他证件类型：4=正面，5=反面，6=手持
        if(certType != JICCPapersTypeEnum.IDENTITY.getCode()) {
              if(imgNo == 1)
                  imgNo = 4 ;
              else
                  imgNo = 5 ;
        }
        imageUpload.setImgNo(imgNo);
        imageUpload.setImgData(data); // 图像数据
        imageUpload.setUserPin(userPin);
        JICCImgUploadResult result = jiccImgInfoService.imageUpload(imageUpload);
        if(result == null || StringUtils.isBlank(result.getImageKey()))
            throw new RpcException("JICCService.uploadImg.result =>" + result) ;
    }

    public String  updateImg(String identityId  , int certType, String userPin , String sid ) throws JICCUnReTryAbleException, JICCRetryAbleException {
        JICCCertImgParam certImgParam = build(sid , userPin , identityId,certType) ;
        JICCSaveCertInfoResult result = jiccIdentityInfoJSFService.saveCertInfoByIdentityId(certImgParam);
        if(result == null || StringUtils.isBlank(result.getIdentityId()))
            throw new RpcException("JICCService.uploadImg.result =>" + result) ;
        return  result.getIdentityId();
    }


    private JICCCertImgParam build(String sid , String userPin , String identityId , int certType) {
        JICCCertImgParam certImgParam = new JICCCertImgParam();
        certImgParam.setSessionId(sid);
        certImgParam.setAppId(appId);
        certImgParam.setUserPin(userPin);
        certImgParam.setIdentityId(identityId);
        certImgParam.setComponentKey(componentKey);
        certImgParam.setTransactionId(sid);
        certImgParam.setCertType(certType);
        return certImgParam ;
    }

    private JICCUserIdentityInfoParam build(IdentityVO identityVO ,String userPin) {
        JICCUserIdentityInfoParam param = new JICCUserIdentityInfoParam();
        param.setAppId(appId);
        param.setUserPin(userPin);
        param.setName(identityVO.getName());
        param.setCertNum(identityVO.getNo());
        param.setCertType(identityVO.getType());
        param.setAuditStatus(JICCAuditStatusEnum.UN_AUDIT.getCode());
        param.setIdentityId(identityVO.getSecurityId());
        return param ;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setComponentKey(String componentKey) {
        this.componentKey = componentKey;
    }
}

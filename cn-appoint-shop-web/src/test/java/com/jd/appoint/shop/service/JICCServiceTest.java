package com.jd.appoint.shop.service;

import com.alibaba.fastjson.JSON;
import com.jd.jmi.jicc.client.domain.JICCCertImgParam;
import com.jd.jmi.jicc.client.domain.JICCSaveCertInfoRequest;
import com.jd.jmi.jicc.client.domain.JICCSaveCertInfoResult;
import com.jd.jmi.jicc.client.domain.JICCUserIdentityInfoParam;
import com.jd.jmi.jicc.client.domain.img.JICCImgUploadParam;
import com.jd.jmi.jicc.client.domain.img.JICCImgUploadResult;
import com.jd.jmi.jicc.client.enums.JICCAuditStatusEnum;
import com.jd.jmi.jicc.client.enums.JICCPapersTypeEnum;
import com.jd.jmi.jicc.client.service.JICCImgInfoService;
import com.jd.jmi.jicc.client.service.impl.JICCIdentityInfoJSFService;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by bjliuyong on 2018/6/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config.xml" })
public class JICCServiceTest extends AbstractJUnit4SpringContextTests {

    @Resource
    private JICCIdentityInfoJSFService jiccIdentityInfoJSFService ;

    @Resource
    private JICCImgInfoService jiccImgInfoService;

    @Test
    public void uploadImageTest() throws Exception {

        String path = "C://temp//0001.jpg"; // 图像需替换
        byte[] data = this.readImgData(path);
        String appId = "33362";
        for (int i = 1; i <= 2; i++) {
            JICCImgUploadParam imageUpload = new JICCImgUploadParam();
            imageUpload.setSessionId("33362_session_0001");
            imageUpload.setAppId(appId);
            imageUpload.setComponentKey("24c72f428e60");
            imageUpload.setTransactionId("T001");
            imageUpload.setCertType(JICCPapersTypeEnum.IDENTITY.getCode());
            imageUpload.setImgNo(i);
            imageUpload.setImgData(data);
            imageUpload.setUserPin("33362_user_0001");
            imageUpload.setOperator("33362_user_0001");
            imageUpload.setIp("10.0.0.1");
            JICCImgUploadResult result = jiccImgInfoService.imageUpload(imageUpload);
            System.out.println(result.getImageKey());
        }
    }

    private byte[] readImgData(String path) throws Exception {
        FileInputStream fis = new FileInputStream(path);
        BufferedInputStream bis = new BufferedInputStream(fis);
        byte[] data = new byte[bis.available()];
        bis.read(data);
        bis.close();
        return data;
    }

    @Test
    public void saveCertInfoTest() throws Exception {
        String appId = "33362";
        String componentKey = "24c72f428e60";
        String sessionId = "33362_session_0001";
        String userPin = "33362_user_0001";
        Integer certType = JICCPapersTypeEnum.IDENTITY.getCode();

        JICCUserIdentityInfoParam infoParam = new JICCUserIdentityInfoParam();
        infoParam.setAppId(appId);
        infoParam.setUserPin(userPin);
        infoParam.setName("33362_name_001");
        infoParam.setCertType(certType);
        infoParam.setCertNum("33362_num_0001");
        infoParam.setAuditStatus(JICCAuditStatusEnum.UN_AUDIT.getCode());
        infoParam.setIp("10.0.0.1");

        JICCCertImgParam certImgParam = new JICCCertImgParam();
        certImgParam.setSessionId(sessionId);
        certImgParam.setAppId(appId);
        certImgParam.setUserPin(userPin);
        certImgParam.setComponentKey(componentKey);
        certImgParam.setTransactionId("1234567890");
        certImgParam.setCertType(certType);

        JICCSaveCertInfoRequest certInfo = new JICCSaveCertInfoRequest();
        certInfo.setCertImgParam(certImgParam);
        certInfo.setUserIdentityInfoParam(infoParam);

        // 33362
        String key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJXEzuZwOrezBJNksW46Bdxs8ekvfYmQ1uHIPEZQbIfsQv5Of3WBeCbAmmX1usOQ9OSZK+2pqhuCRlZLyx/c6vrMc26N1NzUdKHR1P4kM4SKfrDDQ/SmiEKOZjZzF1yxftuWX5KyR5psnBBXSMyJk1wFdh5jEsKTaaeFUd4b1gv9AgMBAAECgYB5RrY32ddiW58v75DR7kBCvyGhLuicFeI7UJamZYeNBV82aMBDmcR+tOYR/ErxBBeaaQIuYH4z6mkkhhmEwrKviGR/u4jybGw0cDAWgR44l9nzWDTRhNxINNmWEpdTImtmxc2rJTMzOhYMpOMR6fwC9SAznZr0y2jyHOtmtcixgQJBAM/BxBuKpqxoBR4pM41V+lyhTury9+mYuFBCCKZ8FlY2NhAnq6CHWNM6Hy4lbwGdDETxkMau/lq0vv+/mCYMT10CQQC4i+dVal9sVptR/7RprmC3ogrYVH2hLNFbLWwAsDi4eqZ5PqgFgF3Iq4b46PuIe8ZfC40uuGA8DkOLdD3NTwUhAkEAsYSBSn84V0wyQ0k1MWjrmcaDJiIhTsodgwZJWefhNhzwKPGcpne/oAyjo8x8g0Zru2UlLX9M+rhCE0jkem4+SQJAaYMjyoQ89pY1y5YlMl8O0S8GgFUeVu5m7Hh1zZbzdxY45A1cx3hzJm5bhyi913TdIC5clB+6ddu55puZUi8hwQJAQL58wyDM2KMv9PSFBS07LAYKMG5ECeP1oX2I2uHtd//dsXCEFMwEDxI9WG3Ok7ejNUbfy9nPPClMhkzDhVZEig==";
        this.jiccIdentityInfoJSFService.setKey(key);
        JICCSaveCertInfoResult result = this.jiccIdentityInfoJSFService.saveCertInfo(certInfo);
        System.out.println(JSON.toJSONString(result));
    }
}
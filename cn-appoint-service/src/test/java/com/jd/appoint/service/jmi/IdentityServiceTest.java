package com.jd.appoint.service.jmi;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.rpc.jmi.dto.IdentityDTO;
import com.jd.appoint.rpc.jmi.jicc.RpcIdentityService;
import com.jd.jmi.jicc.client.domain.JICCAddUserIdentityResult;
import com.jd.jmi.jicc.client.domain.JICCRequest;
import com.jd.jmi.jicc.client.domain.JICCUserIdentityInfoParam;
import com.jd.jmi.jicc.client.enums.JICCAuditStatusEnum;
import com.jd.jmi.jicc.client.enums.JICCPapersTypeEnum;
import com.jd.jmi.jicc.client.service.impl.JICCIdentityInfoJSFService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by shaohongsen on 2018/5/21.
 */
public class IdentityServiceTest extends JUnit4SpringContextTests {
    @Autowired
    private RpcIdentityService rpcIdentityService;

    @Autowired
    private JICCIdentityInfoJSFService jiccIdentityInfoJSFService;
    @Test
    public void addTest() {
        IdentityDTO identityDTO = new IdentityDTO();
        identityDTO.setName("邵宏森");
        identityDTO.setCertType(JICCPapersTypeEnum.IDENTITY);
        identityDTO.setCertNum("371081199006178410");
        identityDTO.setUserPin("jd_77ca150ff3d19");
        String identity = rpcIdentityService.addUserIdentity(identityDTO);
        System.out.println(identity);
        IdentityDTO jd_77ca150ff3d19 = rpcIdentityService.getUserIdentityInfo(identity, "jd_77ca150ff3d19");

        System.out.println(jd_77ca150ff3d19);
    }


    @Test
    public void testAddUserIdentity() throws Exception {

        String appId = "32662";
        String userPin = "32662_0522002";
        String name = "32662_0522002";
        String cerNum = "110106198601080019";

        JICCRequest request = new JICCRequest();
        JICCUserIdentityInfoParam param = new JICCUserIdentityInfoParam();
        request.setUserIdentityInfoParam(param);

        param.setAppId(appId);
        param.setUserPin(userPin);
        param.setName(name);
        param.setCertNum(cerNum);
        param.setCertType(JICCPapersTypeEnum.IDENTITY.getCode());
        param.setAuditStatus(JICCAuditStatusEnum.UN_AUDIT.getCode());

        String key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKKq2JRT8uQ2yegZH9pulv7V0uJxX39O7W/hdTztakCj+AtMbbBepN0a+wx4hrrO1eAiztxpByORYPoagBBeGse1EgBtDll/wC+qTSmVmbRgsNWcGnUdEyAeMaP1qVRSnqTSdXYjf6pXvP8+RGiYNh9XCkbugs5H6vF0pTLmDnc3AgMBAAECgYAGQ8YrkEAege2svqr3YqL2FY4VooAnCwch8/Dr88jPzIDVDpB65mo7Cbr5c/rcXvLluvF0k6buu76cY0DjYHxvfH/Ic371DBPCbHfd3OmyVfrhF1ISUF9C8/NlTNbUdKzfHb8XMqPcXl1ZYdtTWeYFk66LuFKxNaOGPLMlSq0FAQJBAPrbRPDS7K1HvE+QX6qU2l5NufMynIN8QkKz2RrZ+yk2Nb5pW2T5gds6GM5/je4gg71JrIQRBLctMr6dTyp5OVcCQQCmAK1b1fGV8EkjVEk6/NBOIXpfJvhnH0CGDhi570SFXk/6q3NrC1R7hpPA1pUqJeOksacbfjVzDN69MXmor6UhAkEAxx8H8TUsIzue8XUDSw8O6u49nRree8xFeFb0DUlcAVGDhZpxrowTFKZgDIHZclyi1bzGUKqFmLUq431Q2sEQzwJBAIPPadfP60IJR6m71WH3OFexkX9YbElFsXBAZTXOXPQhTRRXaxAGMRDcbRnjyRFsgHNXdL8J2dr+zyYe0OE2hsECQQDY70kT791loMEi61bZNlroNjUVN7Cuph3GXiAsWIyVgphLYcDUvpPkYCvy31TcD25fMZHQ6S286FQEwVqovgrB";
        jiccIdentityInfoJSFService.setKey(key);
        JICCAddUserIdentityResult result = this.jiccIdentityInfoJSFService.addUserIdentity(request);
        System.out.println(JSON.toJSONString(result));

    }
}

package com.jd.appoint.service.serurity;

import com.jd.appoint.common.security.LocalSecurityClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

/**
 * Created by luqiang3 on 2018/5/11.
 */
public class LocalSecurityTest extends JUnit4SpringContextTests {

    private Logger logger = LoggerFactory.getLogger(LocalSecurityTest.class);
    @Autowired
    private LocalSecurityClient localSecurityClient;

    @Test
    public void test() {
        //p/TjI09BlrqHJoRKon14vyVBZn1Pjh6yPlqpTgy94Fw=
        String str = localSecurityClient.encrypt("17600501756");
        System.out.println(str);
        String decrypt = localSecurityClient.decrypt(str);
        System.out.println(decrypt);
        String indexStr = localSecurityClient.getIndexStr(decrypt);
        System.out.println(indexStr);
    }
}

package com.jd.appoint.service.innner.man;

import com.jd.appoint.inner.man.ManEggshellFacade;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.TransactionalJUnit4SpringContextCommonTests;

/**
 * Created by luqiang3 on 2018/5/28.
 */
public class ManEggshellFacadeTest extends TransactionalJUnit4SpringContextCommonTests {

    private static Logger logger = LoggerFactory.getLogger(ManEggshellFacadeTest.class);

    @Autowired
    private ManEggshellFacade manEggshellFacade;

    @Test
    public void testExcuteUpdate(){
        String sql = "UPDATE appoint_order SET customer_address = \"测试\" WHERE id = 4";
        Integer result = manEggshellFacade.excuteUpdate(sql,"");
        logger.info("................test excute update result = {}", result);
    }
}

package webJunit.fast;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 建议测试使用sping环境无事物时候使用
 * Created by lijianzhen1 on 2017/3/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"}, loader = LocalLoader.class)
public class FastJUnit4Tests {

}

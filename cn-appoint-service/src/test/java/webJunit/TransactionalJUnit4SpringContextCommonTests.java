package webJunit;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;


/**
 * 使用事物测试时候继承该类
 * 默认会回滚
 * Created by lijianzhen1 on 2017/3/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class TransactionalJUnit4SpringContextCommonTests extends AbstractTransactionalJUnit4SpringContextTests {
    protected DataSource dataSource;

    /**
     * 注入要测试的数据源
     */
    @Autowired
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
}

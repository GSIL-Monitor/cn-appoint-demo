package com.jd.appoint.service.order.key;

import ch.qos.logback.core.spi.ContextAware;
import com.jd.appoint.api.vo.VenderConfigVO;
import com.jd.appoint.service.sys.VenderConfigConstant;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import org.elasticsearch.common.Strings;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Created by shaohongsen on 2018/7/4.
 */
@Service
public class UniqueGeneratorService implements ApplicationContextAware {
    @Autowired
    private VenderConfigService venderConfigService;
    private ApplicationContext applicationContext;

    public String createUniqueKey(AppointOrderDetailVO data) {
        VenderConfigVO config = venderConfigService.getConfig(data.getBusinessCode(), data.getVenderId(), VenderConfigConstant.UNIQUE_KEY_BEAN);
        if (config != null && !Strings.isNullOrEmpty(config.getValue())) {
            String beanName = config.getValue();
            UniqueGenerator uniqueGenerator = (UniqueGenerator) applicationContext.getBean(beanName);
            return uniqueGenerator.generator(data);
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

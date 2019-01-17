package com.jd.appoint.service.order.operate.finish;

import com.jd.appoint.api.vo.VenderConfigVO;
import com.jd.appoint.service.order.dto.FinishAppointDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/25 16:33
 */
@Service
public class FinishOperateService extends AbstractLocalFinishAppointExcutor implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(FinishOperateService.class);

    /**
     * 获得完成的解控
     *
     * @param finishAppointDto
     * @return
     */
    public FinishAppointExcutor getOperate(FinishAppointDto finishAppointDto) {
        VenderConfigVO configPO = getVenderConfig(finishAppointDto);
        if (null == configPO || null == configPO.getValue() || "".equals(configPO.getValue())) {
            logger.error("没有找到相应的配置");
        }
        return (FinishAppointExcutor) applicationContext.getBean(configPO.getValue());
    }


    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

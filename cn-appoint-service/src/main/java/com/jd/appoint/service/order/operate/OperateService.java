package com.jd.appoint.service.order.operate;

import com.google.common.base.Splitter;
import com.jd.appoint.api.vo.VenderConfigVO;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shaohongsen on 2018/6/14.
 */
@Service
public class OperateService implements ApplicationContextAware {
    private Logger log = LoggerFactory.getLogger(OperateService.class);
    @Autowired
    private VenderConfigService venderConfigService;
    private static final String OPERATE_CONFIG_PREFIX = "OPERATE_";
    private ApplicationContext applicationContext;

    private static final ThreadLocal<Integer> ERROR_CODE = new ThreadLocal<>();

    public enum OperateEnum {
        SUBMIT_ORDER("提交订单"),
        CANCLE_OPRDER("取消订单"),
        APPOINT_FINISH("预约完成"),
        CHANGE_SCHEDULE("改期");

        OperateEnum(String description) {
        }
    }

    public OperateResultEnum execute(AppointOrderDetailVO detailVO, OperateEnum operateEnum) {
        try {
            VenderConfigVO config = venderConfigService.getConfig(detailVO.getBusinessCode(), -1, OPERATE_CONFIG_PREFIX + operateEnum);
            if (config == null || Strings.isNullOrEmpty(config.getValue())) {
                return OperateResultEnum.SUCCESS;
            }
            /**
             * 操作存的样式如  gwSubmitAppoint,reduceStock 指定beanID多个用，分割
             */
            List<BaseOperate> operates = Splitter.on(",").omitEmptyStrings()
                    .splitToList(config.getValue())
                    .stream()
                    .map(beanId -> (BaseOperate) applicationContext.getBean(beanId))
                    .collect(Collectors.toList());
            for (BaseOperate baseOperate : operates) {
                //任意操作执行不成功，就返回失败或者执行报错
                OperateResultEnum resultEnum = baseOperate.execute(detailVO);
                if (resultEnum != OperateResultEnum.SUCCESS) {
                    return resultEnum;
                }
            }
        } catch (Exception e) {
            log.error("execute " + operateEnum + " error ", e);
            return OperateResultEnum.RETRY;
        }
        return OperateResultEnum.SUCCESS;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

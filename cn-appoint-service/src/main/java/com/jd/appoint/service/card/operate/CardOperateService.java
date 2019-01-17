package com.jd.appoint.service.card.operate;

import com.jd.appoint.api.vo.VenderConfigVO;
import com.jd.appoint.rpc.card.dto.CardRountBase;
import com.jd.appoint.service.card.rount.ICardRouter;
import com.jd.appoint.service.sys.VenderConfigService;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/25 16:33
 */
@Service
public class CardOperateService implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(CardOperateService.class);
    @Autowired
    private VenderConfigService venderConfigService;
    private static final String OPERATE_CONFIG_PREFIX = "OPERATE_CARD_";

    /**
     * 执行返回结果
     *
     * @param s           请求参数
     * @param iCardRouter
     * @param <T>
     * @param <S>
     * @return
     */
    public <T, S> T execute(S s, ICardRouter iCardRouter) {
        if (!(s instanceof CardRountBase)) {
            throw new RuntimeException("不能获得对应超类的cardRountBase");
        }
        T result = null;
        CardRountBase cardRountBase = (CardRountBase) s;
        VenderConfigVO config = venderConfigService.getConfig(cardRountBase.getBusinessCode(),
                cardRountBase.getVenderId(),
                OPERATE_CONFIG_PREFIX + iCardRouter.getOperateKey());
        if (config == null || Strings.isNullOrEmpty(config.getValue())) {
            logger.error("检查配置文件是否已经配置校验的数据");
            return result;
        }
        //执行对应的操作
        result = (T) ((CardExcecuteOperate) applicationContext.getBean(config.getValue())).execute(s);
        return result;
    }


    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

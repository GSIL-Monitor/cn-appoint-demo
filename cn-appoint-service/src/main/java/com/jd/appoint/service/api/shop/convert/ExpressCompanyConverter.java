package com.jd.appoint.service.api.shop.convert;

import com.google.common.base.Converter;
import com.jd.appoint.vo.express.ExpressCompanyVO;
import com.jd.etms.third.service.dto.ExpressCheckDto;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by yangyuan on 6/27/18.
 */
public class ExpressCompanyConverter  extends Converter<ExpressCheckDto, ExpressCompanyVO> {

    private static final Logger log = LoggerFactory.getLogger(ExpressCompanyConverter.class);

    @Override
    protected ExpressCompanyVO doForward(ExpressCheckDto expressCheckDto) {
        ExpressCompanyVO expressCompanyVO = new ExpressCompanyVO();
        expressCompanyVO.setThirdId(getFirstInt(expressCheckDto.getThirdIdList()));
        expressCompanyVO.setName(expressCheckDto.getName());
        return expressCompanyVO;
    }

    private Integer getFirstInt(List<String> thirdIds) {
        if (CollectionUtils.isEmpty(thirdIds)) {
            log.error("data illegal. thirdId not found");
            return 0;
        }
        return Integer.parseInt(thirdIds.get(0));
    }

    @Override
    protected ExpressCheckDto doBackward(ExpressCompanyVO expressCompanyVO) {
        throw new RuntimeException("not implement. ");
    }
}

package com.jd.appoint.service.api.convert;

import com.jd.appoint.api.vo.ApiReschuleVO;
import com.jd.appoint.domain.dto.ReschuleDTO;
import com.jd.appoint.domain.enums.PlatformEnum;
import com.jd.appoint.vo.order.ReschduleVO;
import org.springframework.beans.BeanUtils;

/**
 * Api端改期转换
 * Created by gaoxiaoqing on 2018/7/2.
 */
public class ReschduleConvert {

    /**
     * api端改期
     * @param apiReschuleVO
     * @return
     */
    public static ReschuleDTO apiToReschduleDTO(ApiReschuleVO apiReschuleVO){
        ReschuleDTO reschuleDTO = new ReschuleDTO();
        BeanUtils.copyProperties(apiReschuleVO,reschuleDTO);
        reschuleDTO.setPlatformEnum(PlatformEnum.TO_C);
        return reschuleDTO;
    }

    /**
     * shop端改期
     * @param reschduleVO
     * @return
     */
    public static ReschuleDTO shopToReschduleDTO(ReschduleVO reschduleVO){
        ReschuleDTO reschuleDTO = new ReschuleDTO();
        BeanUtils.copyProperties(reschduleVO,reschuleDTO);
        reschuleDTO.setUserPin(reschduleVO.getLoginUserPin());
        reschuleDTO.setPlatformEnum(PlatformEnum.SHOP);
        return reschuleDTO;
    }

    /**
     * 门店端改期
     * @param reschduleVO
     * @return
     */
    public static ReschuleDTO storeToReschduleDTO(ReschduleVO reschduleVO){
        ReschuleDTO reschuleDTO = new ReschuleDTO();
        BeanUtils.copyProperties(reschduleVO,reschuleDTO);
        reschuleDTO.setUserPin(reschduleVO.getLoginUserPin());
        reschuleDTO.setPlatformEnum(PlatformEnum.STORE);
        return reschuleDTO;
    }
}

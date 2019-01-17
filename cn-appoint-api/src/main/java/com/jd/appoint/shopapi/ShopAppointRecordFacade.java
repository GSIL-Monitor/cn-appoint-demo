package com.jd.appoint.shopapi;
import com.jd.appoint.shopapi.vo.AppointRecordQueryVO;
import com.jd.appoint.shopapi.vo.AppointRecordVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

import java.util.List;

/**
 * 预约记录信息
 * Created by gaoxiaoqing on 2018/5/15.
 */
public interface ShopAppointRecordFacade {

    /**
     * 获取预约记录信息
     * @param soaRequest
     * @return
     */
    SoaResponse<List<AppointRecordVO>> getAppointRecordInfo(SoaRequest<AppointRecordQueryVO> soaRequest);


    void insertBuriedTest(String traceNO,String content);
}

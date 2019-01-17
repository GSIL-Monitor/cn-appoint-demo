package com.jd.appoint.service.record;

import com.jd.appoint.domain.rpc.BuriedAppoint;
import com.jd.appoint.domain.rpc.query.AppointRecordQuery;
import com.jd.appoint.shopapi.vo.AppointRecordVO;

import java.util.List;

/**
 * 预约记录信息
 * Created by gaoxiaoqing on 2018/5/22.
 */
public interface AppointRecordService {

    List<AppointRecordVO> getAppointRecordInfo(String traceNo);
}

package com.jd.appoint.service.order.operate.impl.gw;

import com.jd.appoint.service.order.gw.GwAppointService;
import com.jd.appoint.service.order.operate.BaseOperate;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import org.springframework.stereotype.Service;

/**
 * Created by shaohongsen on 2018/6/14.
 * 网关提交预约单
 */
@Service("gwSubmitAppoint")
public class GWSubmitAppoint implements BaseOperate {
    private GwAppointService gwAppointService;

    @Override
    public OperateResultEnum execute(AppointOrderDetailVO detailVO) {
        try {
            boolean success = gwAppointService.submitGwAppoint(detailVO);
            if (success) {
                return OperateResultEnum.SUCCESS;
            }
            return OperateResultEnum.FAIL;
        } catch (Exception e) {

        }
        return OperateResultEnum.RETRY;
    }
}

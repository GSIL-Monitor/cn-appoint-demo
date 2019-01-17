package com.jd.appoint.api;

import com.jd.appoint.api.vo.FormControlQuery;
import com.jd.appoint.api.vo.FormItemCheckRequest;
import com.jd.appoint.vo.FormControlVO;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;

/**
 * Created by yangyuan on 5/11/18.
 */
public interface FormControlFacade {
    /**
     * 根据业务类型获取表单控制信息
     * @param businessCode 业务编码
     * @return
     */
    SoaResponse<FormControlVO> getFormControl(SoaRequest<FormControlQuery> businessCode);

    /**
     * 检查表单数据合法性
     * 如果存在多个服务类型需要传递具体服务类型
     * 如果只配置一个服务类型可以不传
     * @param formItemCheckRequest
     * @return
     */
    SoaResponse<Boolean> checkFormItems(SoaRequest<FormItemCheckRequest> formItemCheckRequest);


}

package com.jd.appoint.vo;

import com.jd.appoint.vo.page.Page;

/**
 * 预约单导出
 * Created by gaoxiaoqing on 2018/6/20.
 */
public class ExportOrderVO extends CommonRequest{

    //分页实体
    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}

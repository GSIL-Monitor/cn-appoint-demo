package com.jd.appoint.domain.rpc;

import java.util.List;

/**
 * Created by gaoxiaoqing on 2018/7/3.
 */
public class AppointStatusInfo {
    List<SystemStatusInfo> systemStatusInfoList;

    public List<SystemStatusInfo> getSystemStatusInfoList() {
        return systemStatusInfoList;
    }

    public void setSystemStatusInfoList(List<SystemStatusInfo> systemStatusInfoList) {
        this.systemStatusInfoList = systemStatusInfoList;
    }
}

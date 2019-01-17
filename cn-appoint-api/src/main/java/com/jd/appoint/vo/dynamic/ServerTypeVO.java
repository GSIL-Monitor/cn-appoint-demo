package com.jd.appoint.vo.dynamic;

/**
 * Created by shaohongsen on 2018/6/17.
 */
public class ServerTypeVO {
    /**
     * 服务类型
     */
    private int serverType;
    /**
     * 服务类型中文
     */
    private String chServerType;

    public int getServerType() {
        return serverType;
    }

    public void setServerType(int serverType) {
        this.serverType = serverType;
    }

    public String getChServerType() {
        return chServerType;
    }

    public void setChServerType(String chServerType) {
        this.chServerType = chServerType;
    }
}

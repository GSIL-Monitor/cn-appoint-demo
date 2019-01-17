package com.jd.appoint.vo.order;

import java.io.Serializable;

/**
 * Created by luqiang3 on 2018/5/3.
 * 服务人员信息
 */
public class Staff implements Serializable{

    /**
     * 服务人员姓名
     */
    private String name;

    /**
     * 服务人员电话
     */
    private String phone;

    //get set
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

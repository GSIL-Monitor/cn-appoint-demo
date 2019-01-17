package com.jd.appoint.inner.man.dto;

import com.jd.appoint.BaseQuery;

/**
 * Created by yangyuan on 5/16/18.
 */
public class ShopBusinessQueryDTO extends BaseQuery{

    private String name;

    private String code;

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}

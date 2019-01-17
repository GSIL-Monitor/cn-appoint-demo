package com.jd.appoint.domain.shop.query;

import com.jd.appoint.domain.base.BaseQuery;

/**
 * Created by yangyuan on 5/16/18.
 */
public class ShopBusinessQuery extends BaseQuery {

    private String name;

    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ShopBusinessQuery{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}

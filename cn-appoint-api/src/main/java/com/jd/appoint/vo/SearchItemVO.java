package com.jd.appoint.vo;

import java.io.Serializable;

/**
 * 查询条件项
 * Created by yangyuan on 5/11/18.
 */
public class SearchItemVO implements Serializable {

    private static final long serialVersionUID = 1l;

    /**
     * 名称
     */
    private String name;

    /**
     * 别名
     */
    private String alias;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}

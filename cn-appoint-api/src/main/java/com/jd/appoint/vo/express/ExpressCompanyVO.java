package com.jd.appoint.vo.express;

/**
 * Created by yangyuan on 6/27/18.
 */
public class ExpressCompanyVO {

    private String name;//

    private Integer thirdId;//

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getThirdId() {
        return thirdId;
    }

    public void setThirdId(Integer thirdId) {
        this.thirdId = thirdId;
    }

    @Override
    public String toString() {
        return "ExpressCompanyVO{" +
                "name='" + name + '\'' +
                ", thirdId=" + thirdId +
                '}';
    }
}

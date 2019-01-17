package com.jd.appoint.domain.enums;

/**
 * Created by lishuaiwei on 2018/5/30
 */
public enum SourceEnum{

    CUSTOMER_SELF(1,"客户预约"),
    CUSTOM_SERVICE(2,"客服预约");

    private Integer code;

    private String person;

    SourceEnum(Integer code, String person){
        this.code = code;
        this.person = person;
    }

    public static SourceEnum getFromCode(Integer code){
        for(SourceEnum sourceEnum: SourceEnum.values()){
            if(sourceEnum.code.equals(code)){
                return sourceEnum;
            }
        }
        return CUSTOMER_SELF;
    }

    public String getPerson() {
        return person;
    }
}

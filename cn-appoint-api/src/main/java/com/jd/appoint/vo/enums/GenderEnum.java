package com.jd.appoint.vo.enums;

/**
 * Created by yangyuan on 5/11/18.
 */
public enum GenderEnum {
    MALE(1,"男"),
    FEMALE(2,"女");

    private int value;

    private String name;

    GenderEnum(int value, String name){
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

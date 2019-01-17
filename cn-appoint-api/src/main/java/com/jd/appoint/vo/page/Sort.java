package com.jd.appoint.vo.page;


import javax.validation.constraints.NotNull;

/**
 * Created by shaohongsen on 2018/5/19.
 */
public class Sort {
    /**
     * 属性名
     */
    @NotNull
    private String fieldName;
    /**
     * 升序，降序
     */
    @NotNull
    private Direction direction;

    public Sort() {
    }

    public Sort(String fieldName, Direction direction) {
        this.fieldName = fieldName;
        this.direction = direction;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}

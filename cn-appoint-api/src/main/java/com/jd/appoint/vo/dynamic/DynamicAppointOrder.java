package com.jd.appoint.vo.dynamic;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shaohongsen on 2018/6/17.
 */
public class DynamicAppointOrder {
    /**
     * 预约单id
     */
    private long id;
    /**
     * 预约单需要展示的一般属性
     */
    private List<FieldVo> fieldVos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<FieldVo> getFieldVos() {
        return fieldVos;
    }

    public void setFieldVos(List<FieldVo> fieldVos) {
        this.fieldVos = fieldVos;
    }
}

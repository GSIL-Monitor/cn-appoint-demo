package com.jd.appoint.service.sys.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.jd.appoint.api.vo.VenderConfigVO;
import com.jd.appoint.rpc.context.RpcContextService;
import com.jd.appoint.service.order.vo.OrderStaticFieldConfigVO;
import com.jd.appoint.service.sys.ContextService;
import com.jd.appoint.service.sys.VenderConfigConstant;
import com.jd.appoint.service.sys.VenderConfigService;
import com.jd.fastjson.JSON;
import org.elasticsearch.common.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by shaohongsen on 2018/6/28.
 */
@Service
public class ContextServiceImpl implements ContextService {
    @Autowired
    private RpcContextService rpcContextService;
    @Autowired
    private VenderConfigService venderConfigService;

    @Override
    public <T> T getBean(String contextId, Class<T> tClass) {
        Map<String, String> context = rpcContextService.getContext(contextId);
        return getBean(context, tClass);
    }

    @Override
    public <T> T getBean(Map<String, String> context, Class<T> tClass) {
        if (context == null) {
            return null;
        }
        //保护性拷贝
        Map<String, String> copyContext = Maps.newHashMap();
        copyContext.putAll(context);
        T bean;
        try {
            bean = tClass.newInstance();
            //beanUtil本身有缓存，性能不用担心
            PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(tClass);
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (propertyDescriptor.getWriteMethod() != null) {
                    String name = propertyDescriptor.getName();
                    //先设置基本属性
                    String strValue = copyContext.remove(name);
                    if (strValue != null) {
                        Class<?> propertyType = propertyDescriptor.getPropertyType();
                        Object value = cast(propertyType, strValue);
                        Method writeMethod = propertyDescriptor.getWriteMethod();
                        writeMethod.invoke(bean, value);
                    }
                }
            }
            return bean;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Map<String, String> validateContext(Map<String, String> context) {
        String businessCode = context.get("businessCode");
        if (Strings.isNullOrEmpty(businessCode)) {
            throw new IllegalArgumentException("businessCode为必填项，当前未填写");
        }
        String serverType = context.get("serverType");
        if (Strings.isNullOrEmpty(serverType)) {
            throw new IllegalArgumentException("serverType为必填项，当前未填写");
        }
        VenderConfigVO config = venderConfigService.getConfig(businessCode, -1, VenderConfigConstant.STATIC_CHECK_NOT_NULL_FIELD);
        if (config == null || Strings.isNullOrEmpty(config.getValue())) {
            return context;
        }
        OrderStaticFieldConfigVO vo = JSON.parseObject(config.getValue(), OrderStaticFieldConfigVO.class);
        //获取所有服务模式都需要校验的字段
        Set<String> validateSet = vo.getAllNeedFiled();
        //业务与模式需要单独校验
        if (vo.getServerTypeNeedField().containsKey(Integer.parseInt(serverType))) {
            validateSet.addAll(vo.getServerTypeNeedField().get(Integer.parseInt(serverType)));
        }
        Map<String, String> map = Maps.newHashMap();
        //开始校验
        for (String fieldName : validateSet) {
            String value = context.get(fieldName);
            if (Strings.isNullOrEmpty(value)) {
                throw new IllegalArgumentException(fieldName + "为必填项，当前未填写");
            }
            map.put(fieldName, value);
        }
        return map;
    }

    private Class<?> getBoxClass(Class<?> tClass) {
        if (tClass == int.class) {
            return Integer.class;
        }
        if (tClass == boolean.class) {
            return Boolean.class;
        }
        if (tClass == short.class) {
            return Short.class;
        }
        if (tClass == long.class) {
            return Long.class;
        }
        if (tClass == char.class) {
            return Character.class;
        }
        if (tClass == byte.class) {
            return Byte.class;
        }
        if (tClass == double.class) {
            return Double.class;
        }
        if (tClass == float.class) {
            return Float.class;
        }
        return null;
    }

    private Object cast(Class<?> propertyType, String strValue) throws Exception {
        if (propertyType.equals(Date.class)) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sf.parse(strValue);
        }
        if (String.class.equals(propertyType)) {
            return strValue;
        }
        if (propertyType.isPrimitive()) {
            propertyType = getBoxClass(propertyType);
        }
        Method valueOf = propertyType.getMethod("valueOf", String.class);
        return valueOf.invoke(null, strValue);
    }


}

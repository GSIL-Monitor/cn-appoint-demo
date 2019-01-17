package com.jd.appoint.service.order.vo;

import com.google.common.collect.Maps;
import com.jd.fastjson.JSON;

import java.util.*;

/**
 * Created by shaohongsen on 2018/7/16.
 */
public class OrderStaticFieldConfigVO {
    private Set<String> allNeedFiled;
    private Map<Integer, Set<String>> serverTypeNeedField;

    public Set<String> getAllNeedFiled() {
        return allNeedFiled;
    }

    public void setAllNeedFiled(Set<String> allNeedFiled) {
        this.allNeedFiled = allNeedFiled;
    }

    public Map<Integer, Set<String>> getServerTypeNeedField() {
        return serverTypeNeedField;
    }

    public void setServerTypeNeedField(Map<Integer, Set<String>> serverTypeNeedField) {
        this.serverTypeNeedField = serverTypeNeedField;
    }

    public static void main(String[] args) {
        OrderStaticFieldConfigVO vo = new OrderStaticFieldConfigVO();
        vo.setAllNeedFiled(new HashSet<>(Arrays.asList("serverType", "businessCode", "venderId", "customerName", "customerPhone", "customerUserPin", "appointStartTime", "cardNo", "cardPassword")));
        Map<Integer, Set<String>> serverTypeNeedField = Maps.newHashMap();
        serverTypeNeedField.put(2, new HashSet<>(Arrays.asList("storeCode")));
        vo.setServerTypeNeedField(serverTypeNeedField);
        System.out.println(JSON.toJSONString(vo));
        String str = "{\"allNeedFiled\":[\"businessCode\",\"customerPhone\",\"appointStartTime\",\"customerUserPin\",\"cardPassword\",\"serverType\",\"venderId\",\"cardNo\",\"customerName\"],\"serverTypeNeedField\":{2:[\"storeCode\"]}}";
        System.out.print(JSON.parseObject(str, OrderStaticFieldConfigVO.class));
    }
}

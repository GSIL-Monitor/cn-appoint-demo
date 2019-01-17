package com.jd.appoint.vo;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yangyuan on 6/5/18.
 */
public class ControlItemData {

    /**
     * 静态数据
     */
    private List<Pair<Integer, String>> dataDetail;

    /**
     * 动态数据
     */
    private String url;

    public List<Pair<Integer, String>> getDataDetail() {
        return dataDetail;
    }

    public void setDataDetail(List<Pair<Integer, String>> dataDetail) {
        this.dataDetail = dataDetail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ControlItemData{" +
                "dataDetail=" + dataDetail +
                ", url='" + url + '\'' +
                '}';
    }

}

package com.jd.appoint.vo.order;

import com.google.common.collect.Maps;
import com.jd.appoint.vo.CommonRequest;
import com.jd.appoint.vo.page.Page;
import com.jd.appoint.vo.page.Sort;
import java.util.List;
import java.util.Map;

/**
 * 查询预约单列表
 * Created by gaoxiaoqing on 2018/7/9.
 */
public class AppointOrderListQuery extends CommonRequest {
    /**
     * 商家id
     */
    private Long venderId;
    /**
     * 门店Code
     */
    private String storeCode;

    /**
     * 服务类型： 到家(1),到店(2)
     */
    private Integer serverType;
    /**
     * 页码，页码从1开始
     */
    private Integer pageNumber;
    /**
     * 一页大小
     */
    private Integer pageSize;
    /**
     * AppointOrderDetailVO 对象的属性都支持筛选
     * AppointOrderDetailVO.formItems.keySet()都支持筛选-->这个跟表单提交项一致
     * 支持的操作
     * 1.EQ("等于")2.CONTAIN("包含")3.GTE("大于等于")4.GT("大于")5.LT("小于")6.LTE("小于等于")
     * e.<staffName.EQ,"梁技师"> 代表的意思是 技师名称必须等于梁技师
     * 多个筛选项之间是且的关系
     * 日期可以传yyyy-MM-dd'T'HH:mm:ss 或者yyyy-MM-dd俩种格式
     */
    private Map<String, String> searchMap;
    /**
     * 排序条件
     */
    private List<Sort> sortList;

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, String> getSearchMap() {
        return searchMap;
    }

    public void setSearchMap(Map<String, String> searchMap) {
        this.searchMap = searchMap;
    }

    public List<Sort> getSortList() {
        return sortList;
    }

    public void setSortList(List<Sort> sortList) {
        this.sortList = sortList;
    }

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Page<AppointOrderDetailVO> toPage() {
        Page<AppointOrderDetailVO> result = new Page<>();
        result.setPageNumber(this.pageNumber);
        result.setPageSize(this.pageSize);
        Map<String, String> searchMap = this.searchMap;
        if (searchMap == null) {
            searchMap = Maps.newHashMap();
        }
        if(this.venderId != null){
            searchMap.put("venderId.EQ", this.venderId.toString());
        }
        if(this.storeCode != null){
            searchMap.put("storeCode.EQ",this.storeCode);
        }
        if (this.serverType != null) {
            searchMap.put("serverType.EQ", this.serverType.toString());
        }
        searchMap.put("businessCode.EQ", this.businessCode);
        result.setSearchMap(searchMap);
        result.setSorts(this.sortList);
        return result;
    }
}

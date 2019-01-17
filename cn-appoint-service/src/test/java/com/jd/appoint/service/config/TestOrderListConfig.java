//package com.jd.appoint.service.config;
//
//import com.google.common.collect.Lists;
//import com.jd.appoint.dao.config.OrderDetailConfigDao;
//import com.jd.appoint.dao.config.OrderListConfigDao;
//import com.jd.appoint.dao.config.OrderOperateConfigDao;
//import com.jd.appoint.domain.config.OrderListConfigPO;
//import com.jd.appoint.domain.enums.*;
//import com.jd.appoint.vo.Pair;
//import com.jd.fastjson.JSON;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import webJunit.JUnit4SpringContextTests;
//import webJunit.fast.FastJUnit4Tests;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Created by shaohongsen on 2018/6/20.
// */
//public class TestOrderListConfig extends FastJUnit4Tests {
//    @Autowired
//    private OrderListConfigDao orderListConfigDao;
//    private static final String DAZHAXIE = "101";
//
//    @Test
//    public void testInsertStoreList() {
//        List<OrderListConfigPO> list = Lists.newArrayList();
//        OrderListConfigPO
//                orderListConfigPO = initStoreList();
//        orderListConfigPO.setLabel("预约单ID");
//        orderListConfigPO.setAlias("id");
//        orderListConfigPO.setInSearch(1);
//        orderListConfigPO.setInputType(InputTypeEnum.TEXT);
//        orderListConfigPO.setQueryType(QueryTypeEnum.EQ);
//        orderListConfigPO.setLineNo(1);
//        orderListConfigPO.setWidth(300);
//        list.add(orderListConfigPO);
//
//        orderListConfigPO = initStoreList();
//        orderListConfigPO.setLabel("商品名称");
//        orderListConfigPO.setAlias("skuName");
//        list.add(orderListConfigPO);
//
//        orderListConfigPO = initStoreList();
//        orderListConfigPO.setLabel("商品SKU");
//        orderListConfigPO.setAlias("skuId");
//        orderListConfigPO.setInSearch(1);
//        orderListConfigPO.setInputType(InputTypeEnum.TEXT);
//        orderListConfigPO.setQueryType(QueryTypeEnum.EQ);
//        orderListConfigPO.setLineNo(1);
//        orderListConfigPO.setWidth(300);
//        list.add(orderListConfigPO);
//
//
//        orderListConfigPO = initStoreList();
//        orderListConfigPO.setLabel("凭证卡号");
//        orderListConfigPO.setAlias("cardNo");
//        orderListConfigPO.setInSearch(1);
//        orderListConfigPO.setInputType(InputTypeEnum.TEXT);
//        orderListConfigPO.setQueryType(QueryTypeEnum.EQ);
//        orderListConfigPO.setLineNo(1);
//        orderListConfigPO.setWidth(300);
//        list.add(orderListConfigPO);
//
//        orderListConfigPO = initStoreList();
//        orderListConfigPO.setLabel("联系人");
//        orderListConfigPO.setAlias("customerName");
//        orderListConfigPO.setInSearch(1);
//        orderListConfigPO.setInputType(InputTypeEnum.TEXT);
//        orderListConfigPO.setQueryType(QueryTypeEnum.CONTAIN);
//        orderListConfigPO.setLineNo(2);
//        orderListConfigPO.setWidth(300);
//        list.add(orderListConfigPO);
//
//
//        orderListConfigPO = initStoreList();
//        orderListConfigPO.setLabel("联系方式");
//        orderListConfigPO.setAlias("customerPhone");
//        orderListConfigPO.setInSearch(1);
//        orderListConfigPO.setInputType(InputTypeEnum.TEXT);
//        orderListConfigPO.setQueryType(QueryTypeEnum.EQ);
//        orderListConfigPO.setLineNo(2);
//        orderListConfigPO.setWidth(300);
//        list.add(orderListConfigPO);
//
//
//        orderListConfigPO = initStoreList();
//        orderListConfigPO.setLabel("预约时间");
//        orderListConfigPO.setAlias("appointStartTime");
//        orderListConfigPO.setInSearch(1);
//        orderListConfigPO.setInputType(InputTypeEnum.DOUBLE_DATE);
//        orderListConfigPO.setLineNo(2);
//        orderListConfigPO.setWidth(300);
//        list.add(orderListConfigPO);
//
//        orderListConfigPO = initStoreList();
//        orderListConfigPO.setLabel("提交时间");
//        orderListConfigPO.setAlias("created");
//        list.add(orderListConfigPO);
//
//        orderListConfigPO = initStoreList();
//        orderListConfigPO.setLabel("预约单状态");
//        orderListConfigPO.setAlias("appointStatus");
//        orderListConfigPO.setInSearch(1);
//        orderListConfigPO.setInList(0);
//        orderListConfigPO.setInputType(InputTypeEnum.SELECT);
//        List<Pair<String, String>> data = Arrays.asList(
//                new Pair<>("", "全部"),
//                new Pair<>("2", "待审核"),
//                new Pair<>("3", "待自提"),
//                new Pair<>("8", "已完成"),
//                new Pair<>("9", "已取消"),
//                new Pair<>("5", "预约失败"));
//        orderListConfigPO.setItemData(JSON.toJSONString(data));
//        orderListConfigPO.setLineNo(3);
//        orderListConfigPO.setWidth(300);
//        list.add(orderListConfigPO);
//
//        orderListConfigPO = initStoreList();
//        orderListConfigPO.setLabel("预约单状态");
//        orderListConfigPO.setAlias("chAppointStatus");
//        list.add(orderListConfigPO);
//        int i = 1;
//        for (OrderListConfigPO o : list) {
//            o.setPriority(i++);
//            orderListConfigDao.insert(o);
//        }
//    }
//
//    private OrderListConfigPO initStoreList() {
//        OrderListConfigPO orderListConfigPO = new OrderListConfigPO();
//        orderListConfigPO.setInSearch(0);
//        orderListConfigPO.setInList(1);
//        orderListConfigPO.setBusinessCode(DAZHAXIE);
//        orderListConfigPO.setPlatform(PlatformEnum.STORE);
//        orderListConfigPO.setStatus(StatusEnum.ENABLE);
//        orderListConfigPO.setServerType(ServerTypeEnum.DAODIAN);
//        return orderListConfigPO;
//    }
//
//    @Test
//    public void testInsertShopList() {
//        List<OrderListConfigPO> list = Lists.newArrayList();
//        OrderListConfigPO orderListConfigPO = null;
//        orderListConfigPO = initShopList();
//        orderListConfigPO.setLabel("预约单ID");
//        orderListConfigPO.setAlias("id");
//        orderListConfigPO.setInSearch(1);
//        orderListConfigPO.setInputType(InputTypeEnum.TEXT);
//        orderListConfigPO.setQueryType(QueryTypeEnum.EQ);
//        orderListConfigPO.setLineNo(1);
//        orderListConfigPO.setWidth(300);
//        list.add(orderListConfigPO);
//
//        orderListConfigPO = initStoreList();
//        orderListConfigPO.setLabel("商品名称");
//        orderListConfigPO.setAlias("skuName");
//        list.add(orderListConfigPO);
//        ;
//        ;
//
//        orderListConfigPO = initShopList();
//        orderListConfigPO.setLabel("商品SKU");
//        orderListConfigPO.setAlias("skuId");
//        orderListConfigPO.setInSearch(1);
//        orderListConfigPO.setInputType(InputTypeEnum.TEXT);
//        orderListConfigPO.setQueryType(QueryTypeEnum.EQ);
//        orderListConfigPO.setLineNo(1);
//        orderListConfigPO.setWidth(300);
//        list.add(orderListConfigPO);
//
//
//        orderListConfigPO = initShopList();
//        orderListConfigPO.setLabel("凭证卡号");
//        orderListConfigPO.setAlias("cardNo");
//        orderListConfigPO.setInSearch(1);
//        orderListConfigPO.setInputType(InputTypeEnum.TEXT);
//        orderListConfigPO.setQueryType(QueryTypeEnum.EQ);
//        orderListConfigPO.setLineNo(1);
//        orderListConfigPO.setWidth(300);
//        list.add(orderListConfigPO);
//
//        orderListConfigPO = initShopList();
//        orderListConfigPO.setLabel("联系人");
//        orderListConfigPO.setAlias("customerName");
//        orderListConfigPO.setInSearch(1);
//        orderListConfigPO.setInputType(InputTypeEnum.TEXT);
//        orderListConfigPO.setQueryType(QueryTypeEnum.CONTAIN);
//        orderListConfigPO.setLineNo(2);
//        orderListConfigPO.setWidth(300);
//        list.add(orderListConfigPO);
//
//
//        orderListConfigPO = initShopList();
//        orderListConfigPO.setLabel("联系方式");
//        orderListConfigPO.setAlias("customerPhone");
//        orderListConfigPO.setInSearch(1);
//        orderListConfigPO.setInputType(InputTypeEnum.TEXT);
//        orderListConfigPO.setQueryType(QueryTypeEnum.EQ);
//        orderListConfigPO.setLineNo(2);
//        orderListConfigPO.setWidth(300);
//        list.add(orderListConfigPO);
//
//
//        orderListConfigPO = initShopList();
//        orderListConfigPO.setLabel("预约时间");
//        orderListConfigPO.setAlias("appointStartTime");
//        orderListConfigPO.setInSearch(1);
//        orderListConfigPO.setInputType(InputTypeEnum.DOUBLE_DATE);
//        orderListConfigPO.setLineNo(2);
//        orderListConfigPO.setWidth(300);
//        list.add(orderListConfigPO);
//
//        orderListConfigPO = initShopList();
//        orderListConfigPO.setLabel("物流单号");
//        orderListConfigPO.setAlias("logisticsNo");
//        list.add(orderListConfigPO);
//
//
//        orderListConfigPO = initShopList();
//        orderListConfigPO.setLabel("提交时间");
//        orderListConfigPO.setAlias("created");
//        list.add(orderListConfigPO);
//
//        orderListConfigPO = initShopList();
//        orderListConfigPO.setLabel("预约单状态");
//        orderListConfigPO.setAlias("appointStatus");
//        orderListConfigPO.setInSearch(1);
//        orderListConfigPO.setInList(0);
//        orderListConfigPO.setInputType(InputTypeEnum.SELECT);
//        List<Pair<String, String>> data = Arrays.asList(
//                new Pair<>("", "全部"),
//                new Pair<>("2", "待审核"),
//                new Pair<>("3", "待发货"),
//                new Pair<>("8", "已完成"),
//                new Pair<>("9", "已取消"),
//                new Pair<>("5", "预约失败"));
//        orderListConfigPO.setItemData(JSON.toJSONString(data));
//        orderListConfigPO.setLineNo(3);
//        orderListConfigPO.setWidth(300);
//        list.add(orderListConfigPO);
//
//        orderListConfigPO = initShopList();
//        orderListConfigPO.setLabel("预约单状态");
//        orderListConfigPO.setAlias("chAppointStatus");
//        list.add(orderListConfigPO);
//        int i = 0;
//        for (OrderListConfigPO o : list) {
//            o.setPriority(++i);
//            orderListConfigDao.insert(o);
//        }
//
//        i = 0;
//        for (OrderListConfigPO o : list) {
//            o.setPriority(++i);
//            o.setId(null);
//            o.setServerType(ServerTypeEnum.DAODIAN);
//            orderListConfigDao.insert(o);
//        }
//
//    }
//
//    private OrderListConfigPO initShopList() {
//        OrderListConfigPO orderListConfigPO = new OrderListConfigPO();
//        orderListConfigPO.setInSearch(0);
//        orderListConfigPO.setInList(1);
//        orderListConfigPO.setBusinessCode(DAZHAXIE);
//        orderListConfigPO.setPlatform(PlatformEnum.SHOP);
//        orderListConfigPO.setStatus(StatusEnum.ENABLE);
//        orderListConfigPO.setServerType(ServerTypeEnum.SHANGMEN);
//        return orderListConfigPO;
//    }
//
//    @Test
//    public void getFilterItem() {
//        orderListConfigDao.findByBusinessCodeAndServerTypeAndPlatform(DAZHAXIE, ServerTypeEnum.SHANGMEN.getIntValue(), PlatformEnum.SHOP.getIntValue());
//    }
//}

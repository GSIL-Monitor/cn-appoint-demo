//package com.jd.appoint.service.config;
//
//import com.google.common.collect.Lists;
//import com.jd.appoint.dao.config.OrderDetailConfigDao;
//import com.jd.appoint.domain.config.OrderDetailConfigPO;
//import com.jd.appoint.domain.enums.PlatformEnum;
//import com.jd.appoint.domain.enums.ServerTypeEnum;
//import com.jd.appoint.domain.enums.StatusEnum;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import webJunit.JUnit4SpringContextTests;
//
//import java.util.List;
//
///**
// * Created by shaohongsen on 2018/6/21.
// */
//public class TestOrderDetailConfig extends JUnit4SpringContextTests {
//    private static final String DAZHAXIE = "101";
//    @Autowired
//    private OrderDetailConfigDao orderDetailConfigDao;
//
//    @Test
//    public void testInsertShop() {
//        List<OrderDetailConfigPO> list = Lists.newArrayList();
//        OrderDetailConfigPO detail = null;
//        detail = initStoreDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("预约单ID");
//        detail.setAlias("id");
//        list.add(detail);
//
//        detail = initStoreDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("商品名称");
//        detail.setAlias("skuName");
//        list.add(detail);
//
//        detail = initStoreDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("商品SKU");
//        detail.setAlias("skuId");
//        list.add(detail);
//
//        detail = initStoreDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("门店名称");
//        detail.setAlias("storeName");
//        list.add(detail);
//
//        detail = initStoreDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("预约模式");
//        detail.setAlias("serverType");
//        list.add(detail);
//
//        detail = initStoreDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("凭证卡号");
//        detail.setAlias("cardNo");
//        list.add(detail);
//
//        detail = initStoreDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("提交时间");
//        detail.setAlias("created");
//        list.add(detail);
//
//        detail = initStoreDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("预约状态");
//        detail.setAlias("appointStatus");
//        list.add(detail);
//
//        detail = initStoreDetail();
//        detail.setGroupName("预约信息");
//        detail.setLabel("用户ID");
//        detail.setAlias("customerUserPin");
//        list.add(detail);
//
//        detail = initStoreDetail();
//        detail.setGroupName("预约信息");
//        detail.setLabel("预约时间");
//        detail.setAlias("appointStartTime");
//        list.add(detail);
//
//        detail = initStoreDetail();
//        detail.setGroupName("预约信息");
//        detail.setLabel("联系人");
//        detail.setAlias("customerName");
//        list.add(detail);
//
//        detail = initStoreDetail();
//        detail.setGroupName("预约信息");
//        detail.setLabel("联系方式");
//        detail.setAlias("customerPhone");
//        list.add(detail);
//
//        detail = initStoreDetail();
//        detail.setGroupName("预约信息");
//        detail.setLabel("到店地址");
//        detail.setAlias("storeAddress");
//        list.add(detail);
//
//        detail = initStoreDetail();
//        detail.setGroupName("预约信息");
//        detail.setLabel("用户备注");
//        detail.setAlias("remark");
//        list.add(detail);
//        int i = 0;
//        for (OrderDetailConfigPO detailConfigPO : list) {
//            detailConfigPO.setPriority(++i);
//            orderDetailConfigDao.insert(detailConfigPO);
//        }
//    }
//
//    @Test
//    public void insertShopTest() {
//
//        List<OrderDetailConfigPO> list = Lists.newArrayList();
//        OrderDetailConfigPO detail = null;
//        detail = initShopDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("预约单ID");
//        detail.setAlias("id");
//        list.add(detail);
//
//        detail = initShopDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("商品名称");
//        detail.setAlias("skuName");
//        list.add(detail);
//
//        detail = initShopDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("商品SKU");
//        detail.setAlias("skuId");
//        list.add(detail);
//
//        detail = initShopDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("物流单号");
//        detail.setAlias("logisticsNo");
//        list.add(detail);
//
//        detail = initShopDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("预约模式");
//        detail.setAlias("serverType");
//        list.add(detail);
//
//        detail = initShopDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("凭证卡号");
//        detail.setAlias("cardNo");
//        list.add(detail);
//
//        detail = initShopDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("提交时间");
//        detail.setAlias("created");
//        list.add(detail);
//
//        detail = initShopDetail();
//        detail.setGroupName("基本信息");
//        detail.setLabel("预约状态");
//        detail.setAlias("appointStatus");
//        list.add(detail);
//
//        detail = initShopDetail();
//        detail.setGroupName("预约信息");
//        detail.setLabel("用户ID");
//        detail.setAlias("customerUserPin");
//        list.add(detail);
//
//        detail = initShopDetail();
//        detail.setGroupName("预约信息");
//        detail.setLabel("预约时间");
//        detail.setAlias("appointStartTime");
//        list.add(detail);
//
//        detail = initShopDetail();
//        detail.setGroupName("预约信息");
//        detail.setLabel("联系人");
//        detail.setAlias("customerName");
//        list.add(detail);
//
//        detail = initShopDetail();
//        detail.setGroupName("预约信息");
//        detail.setLabel("联系方式");
//        detail.setAlias("customerPhone");
//        list.add(detail);
//
//        detail = initShopDetail();
//        detail.setGroupName("预约信息");
//        detail.setLabel("收货地址");
//        detail.setAlias("storeAddress");
//        list.add(detail);
//
//        detail = initShopDetail();
//        detail.setGroupName("预约信息");
//        detail.setLabel("用户备注");
//        detail.setAlias("memo");
//        list.add(detail);
//
//        detail = initShopDetail();
//        detail.setGroupName("预约信息");
//        detail.setLabel("商家备注备注");
//        detail.setAlias("venderMeMo");
//        list.add(detail);
//        int i = 0;
//        for (OrderDetailConfigPO detailConfigPO : list) {
//            detailConfigPO.setPriority(++i);
//            orderDetailConfigDao.insert(detailConfigPO);
//        }
//        i = 0;
//        for (OrderDetailConfigPO detailConfigPO : list) {
//            detailConfigPO.setId(null);
//            detailConfigPO.setPriority(++i);
//            detailConfigPO.setServerType(ServerTypeEnum.DAODIAN);
//            orderDetailConfigDao.insert(detailConfigPO);
//        }
//    }
//
//    private OrderDetailConfigPO initStoreDetail() {
//        OrderDetailConfigPO detailConfigPO = new OrderDetailConfigPO();
//        detailConfigPO.setBusinessCode(DAZHAXIE);
//        detailConfigPO.setServerType(ServerTypeEnum.DAODIAN);
//        detailConfigPO.setPlatform(PlatformEnum.STORE);
//        detailConfigPO.setStatus(StatusEnum.ENABLE);
//        return detailConfigPO;
//    }
//
//    private OrderDetailConfigPO initShopDetail() {
//        OrderDetailConfigPO detailConfigPO = new OrderDetailConfigPO();
//        detailConfigPO.setBusinessCode(DAZHAXIE);
//        detailConfigPO.setServerType(ServerTypeEnum.SHANGMEN);
//        detailConfigPO.setPlatform(PlatformEnum.SHOP);
//        detailConfigPO.setStatus(StatusEnum.ENABLE);
//        return detailConfigPO;
//    }
//}

//package com.jd.appoint.service.config;
//
//import com.google.common.collect.Lists;
//import com.jd.appoint.dao.config.OrderOperateConfigDao;
//import com.jd.appoint.domain.config.OrderOperateConfigPO;
//import com.jd.appoint.domain.enums.*;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import webJunit.JUnit4SpringContextTests;
//
//import java.util.List;
//
///**
// * Created by shaohongsen on 2018/6/20.
// */
//public class TestOperateConfig extends JUnit4SpringContextTests {
//    @Autowired
//    private OrderOperateConfigDao orderOperateConfigDao;
//    private static final String DAZHAXIE = "101";
//
//    @Test
//    public void testInsertC() {
////        OrderOperateConfigPO config;
////        List<OrderOperateConfigPO> list = Lists.newArrayList();
////
////        config = initC();
////        config.setAppointStatus(AppointStatusEnum.WAIT_ORDER);
////        config.setLabel("取消预约");
////        config.setOperateType(OperateTypeEnum.CANCEL);
////        list.add(config);
////        config = initC();
////        config.setLabel("修改预约");
////        config.setAppointStatus(AppointStatusEnum.WAIT_ORDER);
////        config.setOperateType(OperateTypeEnum.CHANGE_SCHEDULE_DATE);
////        list.add(config);
////        insert(list);
////
////        list = Lists.newArrayList();
////        config = initC();
////        config.setLabel("查看物流");
////        config.setAppointStatus(AppointStatusEnum.APPOINT_FINISH);
////        config.setServerType(ServerTypeEnum.SHANGMEN);
////        config.setOperateType(OperateTypeEnum.LOGISTICS);
////        list.add(config);
////        insert(list);
////
////        list = Lists.newArrayList();
////        config = initC();
////        config.setLabel("再次预约");
////        config.setAppointStatus(AppointStatusEnum.APPOINT_FAILURE);
////        config.setOperateType(OperateTypeEnum.APPOINT_AGAIN);
////        list.add(config);
////        insert(list);
//    }
//
//    @Test
//    public void testInsertShop() {
//        OrderOperateConfigPO config;
//        //待审核
//        List<OrderOperateConfigPO> list = Lists.newArrayList();
////        config = initShop();
////        config.setAppointStatus(AppointStatusEnum.WAIT_ORDER);
////        config.setLabel("取消预约");
////        config.setConfirmInfo("您确定取消预约吗？");
////        config.setOperateType(OperateTypeEnum.CANCEL);
////        list.add(config);
////
////        config = initShop();
////        config.setAppointStatus(AppointStatusEnum.WAIT_ORDER);
////        config.setLabel("预约改期");
////        config.setOperateType(OperateTypeEnum.CHANGE_SCHEDULE_DATE);
////        list.add(config);
////
////        config = initShop();
////        config.setAppointStatus(AppointStatusEnum.WAIT_ORDER);
////        config.setLabel("审核通过");
////        config.setOperateType(OperateTypeEnum.CUSTOM);
////        config.setConfirmInfo("您确定进行审核通过操作吗？");
////        config.setCustomType(CustomTypeEnum.AJAX_API);
////        list.add(config);
////
////        config = initShop();
////        config.setAppointStatus(AppointStatusEnum.WAIT_ORDER);
////        config.setLabel("商家备注");
////        config.setOperateType(OperateTypeEnum.REMARK);
////        list.add(config);
////        insert(list);
////        //待服务
////        list = Lists.newArrayList();
////        config = initShop();
////        config.setAppointStatus(AppointStatusEnum.WAIT_SERVICE);
////        config.setLabel("取消预约");
////        config.setConfirmInfo("您确定取消预约吗？");
////        config.setOperateType(OperateTypeEnum.CANCEL);
////        list.add(config);
////
////        config = initShop();
////        config.setAppointStatus(AppointStatusEnum.WAIT_SERVICE);
////        config.setLabel("预约改期");
////        config.setOperateType(OperateTypeEnum.CHANGE_SCHEDULE_DATE);
////        list.add(config);
////
////        config = initShop();
////        config.setAppointStatus(AppointStatusEnum.WAIT_SERVICE);
////        config.setLabel("商家备注");
////        config.setOperateType(OperateTypeEnum.REMARK);
////        list.add(config);
////
////        config = initShop();
////        config.setAppointStatus(AppointStatusEnum.WAIT_SERVICE);
////        config.setLabel("新增物流信息");
////        config.setOperateType(OperateTypeEnum.CUSTOM);
////        config.setCustomType(CustomTypeEnum.OPEN_DIV);
////        config.setServerType(ServerTypeEnum.SHANGMEN);
////        list.add(config);
////        insert(list);
////
////        list = Lists.newArrayList();
////        config = initShop();
////        config.setAppointStatus(AppointStatusEnum.APPOINT_FINISH);
////        config.setLabel("商家备注");
////        config.setOperateType(OperateTypeEnum.REMARK);
////        list.add(config);
////
////        config = initShop();
////        config.setAppointStatus(AppointStatusEnum.APPOINT_FINISH);
////        config.setLabel("编辑物流信息");
////        config.setOperateType(OperateTypeEnum.CUSTOM);
////        config.setCustomType(CustomTypeEnum.OPEN_DIV);
////        config.setServerType(ServerTypeEnum.SHANGMEN);
////        list.add(config);
////        insert(list);
////
////        list = Lists.newArrayList();
////        config = initShop();
////        config.setAppointStatus(AppointStatusEnum.APPOINT_CANCEL);
////        config.setLabel("商家备注");
////        config.setOperateType(OperateTypeEnum.REMARK);
////        list.add(config);
//
//        config = initShop();
//        config.setAppointStatus(AppointStatusEnum.WAIT_ORDER);
//        config.setLabel("批量审核");
//        config.setOperateType(OperateTypeEnum.CUSTOM);
//        config.setConfirmInfo("您确定进行批量审核操作吗？");
//        config.setCustomType(CustomTypeEnum.AJAX_API);
//        config.setIsBatch(1);
//        list.add(config);
//        insert(list);
//    }
//
//    private void insert(List<OrderOperateConfigPO> list) {
//        int i = 1;
//        for (OrderOperateConfigPO configPO : list) {
//            configPO.setPriority(i++);
//            orderOperateConfigDao.insert(configPO);
//        }
//    }
//
//    private OrderOperateConfigPO initC() {
//        OrderOperateConfigPO config = new OrderOperateConfigPO();
//        config.setStatus(StatusEnum.ENABLE);
//        config.setBusinessCode(DAZHAXIE);
//        config.setPlatform(PlatformEnum.TO_C);
//        config.setIsBatch(0);
//        return config;
//    }
//
//    private OrderOperateConfigPO initShop() {
//        OrderOperateConfigPO config = new OrderOperateConfigPO();
//        config.setStatus(StatusEnum.ENABLE);
//        config.setBusinessCode(DAZHAXIE);
//        config.setPlatform(PlatformEnum.SHOP);
//        config.setIsBatch(0);
//        return config;
//    }
//}

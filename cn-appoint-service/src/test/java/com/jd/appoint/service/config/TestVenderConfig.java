//package com.jd.appoint.service.config;
//
//import com.google.common.collect.Lists;
//import com.jd.appoint.api.vo.FinishButton;
//import com.jd.appoint.api.vo.ProcessConfigVO;
//import com.jd.appoint.dao.sys.VenderConfigDao;
//import com.jd.appoint.domain.sys.VenderConfigPO;
//import com.jd.appoint.inner.man.dto.VenderConfigDTO;
//import com.jd.fastjson.JSON;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import webJunit.JUnit4SpringContextTests;
//
//import java.util.List;
//
///**
// * Created by shaohongsen on 2018/6/13.
// */
//public class TestVenderConfig extends JUnit4SpringContextTests {
//    @Autowired
//    private VenderConfigDao venderConfigDao;
//    private static final String PREFIX = "https://yue.jd.com/";
//
//    @Test
//    public void test() {
//        VenderConfigPO venderConfigDTO = init();
//        List<ProcessConfigVO> processConfigVOList = Lists.newArrayList();
//        ProcessConfigVO configVO = new ProcessConfigVO();
//        configVO.setTitle("提交预约");
//        configVO.setButtonName("提交");
//        configVO.setSubmitUrl(PREFIX + "validate");
//        configVO.setCurrentPageNo("code");
//        processConfigVOList.add(configVO);
//
//        configVO = new ProcessConfigVO();
//        configVO.setTitle("填写预约信息");
//        configVO.setButtonName("下一步");
//        configVO.setSubmitUrl(PREFIX + "submitContacts");
//        configVO.setCurrentPageNo("contact");
//        processConfigVOList.add(configVO);
//
//        configVO = new ProcessConfigVO();
//        configVO.setTitle("请选择体检套餐");
//        configVO.setSubmitUrl(PREFIX + "submitParam");
//        configVO.setCurrentPageNo("packageList");
//        processConfigVOList.add(configVO);
//
//        configVO = new ProcessConfigVO();
//        configVO.setTitle("请选择门店");
//        configVO.setSubmitUrl(PREFIX + "submitParam");
//        configVO.setCurrentPageNo("storeList");
//        processConfigVOList.add(configVO);
//
//        configVO = new ProcessConfigVO();
//        configVO.setTitle("选择体检时间");
//        configVO.setSubmitUrl(PREFIX + "submitOrder");
//        configVO.setCurrentPageNo("dateScheduleList");
//        List<FinishButton> finishButtons = Lists.newArrayList();
//        FinishButton continueAppoint = new FinishButton();
//        continueAppoint.setButtonName("继续预约");
//        continueAppoint.setUrl("appoint.jd.com/code/");
//        finishButtons.add(continueAppoint);
//        FinishButton list = new FinishButton();
//        list.setButtonName("查看预约单");
//        list.setUrl("appoint.jd.com/list/");
//        finishButtons.add(list);
//        configVO.setFinishButtons(finishButtons);
//        processConfigVOList.add(configVO);
//        System.out.println(JSON.toJSONString(processConfigVOList));
////        venderConfigDTO.setValue(JSON.toJSONString(processConfigVOList));
////        venderConfigDao.insert(venderConfigDTO);
//
//    }
//
//    private VenderConfigPO init() {
//        VenderConfigPO venderConfigPO = new VenderConfigPO();
//        venderConfigPO.setBusinessCode("1002");
//        venderConfigPO.setCfgKey("processConfig");
//        venderConfigPO.setVenderId(-1l);
//        return venderConfigPO;
//    }
//
//}

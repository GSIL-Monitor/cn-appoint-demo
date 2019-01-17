package com.jd.appoint.shop.web;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.shop.service.BusinessCodeService;
import com.jd.appoint.shop.util.*;
import com.jd.appoint.shop.vo.AppointOrderQuery;
import com.jd.appoint.shopapi.ShopAppointOrderFacade;
import com.jd.appoint.shopapi.ShopAppointRecordFacade;
import com.jd.appoint.shopapi.ShopDynamicConfigFacade;
import com.jd.appoint.shopapi.ShopExpressFacade;
import com.jd.appoint.shopapi.vo.*;
import com.jd.appoint.vo.AppointCancel;
import com.jd.appoint.vo.AppointFinishVO;
import com.jd.appoint.vo.dynamic.FilterItemVO;
import com.jd.appoint.vo.dynamic.OperateItemVo;
import com.jd.appoint.vo.dynamic.ServerTypeRequest;
import com.jd.appoint.vo.dynamic.ServerTypeVO;
import com.jd.appoint.vo.dynamic.table.DynamicTable;
import com.jd.appoint.vo.express.ExpressCompanyVO;
import com.jd.appoint.vo.express.ExpressInfo;
import com.jd.appoint.vo.order.*;
import com.jd.appoint.vo.page.Page;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.virtual.appoint.StoreService;
import com.jd.virtual.appoint.common.CommonRequest;
import com.jd.virtual.appoint.common.CommonResponse;
import com.jd.virtual.appoint.common.PageRequest;
import com.jd.virtual.appoint.store.StoreItem;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jxl.Sheet;
import jxl.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by bjliuyong on 2018/5/23.
 */
@Controller
@RequestMapping("/api/appoint/order")
public class AppointOrderController {

    @Resource
    private ShopAppointOrderFacade shopAppointOrderFacade;

    @Resource
    private ShopAppointRecordFacade shopAppointRecordFacade;

    private static final Logger logger = LoggerFactory.getLogger(AppointOrderController.class);

    @Resource
    private ShopExpressFacade shopExpressFacade;

    @Resource
    private ShopDynamicConfigFacade shopDynamicConfigFacade;

    @Resource
    private StoreService storeService;

    @Resource
    private BusinessCodeService businessCodeService;


    @ApiOperation(value = "预约列表接口", httpMethod = "GET", response = AppointOrderDetailVO.class)
    @ApiImplicitParams({

            @ApiImplicitParam(name = "page", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", paramType = "query"),
            @ApiImplicitParam(name = "id", paramType = "query"),
            @ApiImplicitParam(name = "customerName", paramType = "query"),
            @ApiImplicitParam(name = "customerPhone", paramType = "query"),
            @ApiImplicitParam(name = "serverType", paramType = "query"),
            @ApiImplicitParam(name = "staffName", paramType = "query", value = "技师名称"),
            @ApiImplicitParam(name = "appointStartTime", paramType = "query"),
            @ApiImplicitParam(name = "appointEndTime", paramType = "query"),
            @ApiImplicitParam(name = "appointStatus", paramType = "query", value = "预约状态"),
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<Page<AppointOrderDetailVO>> list(AppointOrderQuery appointOrderQuery) {

        return Utils.call(() -> {
            SoaRequest<ShopAppointOrderListRequest> soaRequest = new SoaRequest<>();
            ShopAppointOrderListRequest shopAppointOrderListRequest = new ShopAppointOrderListRequest();
            shopAppointOrderListRequest.setVenderId(VenderIdGetter.get());
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            shopAppointOrderListRequest.setBusinessCode(businessCode);
            shopAppointOrderListRequest.setPageNumber(appointOrderQuery.getPage());
            shopAppointOrderListRequest.setPageSize(appointOrderQuery.getPageSize());

            Map<String, String> searchMap = new HashMap<>();
            builderSearchMap("id.EQ", appointOrderQuery.getId(), searchMap);
            builderSearchMap("customerName.EQ", appointOrderQuery.getCustomerName(), searchMap);
            builderSearchMap("customerPhone.EQ", appointOrderQuery.getCustomerPhone(), searchMap);
            builderSearchMap("serverType.EQ", appointOrderQuery.getServerType(), searchMap);
            builderSearchMap("staffName.EQ", appointOrderQuery.getStaffName(), searchMap);
            builderSearchMap("appointStatus.EQ", appointOrderQuery.getAppointStatus(), searchMap);

            String startTime = appointOrderQuery.getAppointStartTime();
            String endTime = appointOrderQuery.getAppointEndTime();

            builderSearchMap("appointStartTime.GTE", startTime, searchMap);
            builderSearchMap("appointStartTime.LTE", endTime, searchMap);

            shopAppointOrderListRequest.setSearchMap(searchMap);
            soaRequest.setData(shopAppointOrderListRequest);
            //soaRequest.setData(appointUpdateVO);
            logger.info("shopAppointOrderFacade.list 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopAppointOrderFacade.list(soaRequest);
        });
    }

    private void builderSearchMap(String key, Object value, Map<String, String> searchMap) {
        if (value != null)
            searchMap.put(key, value.toString());
    }

    @ApiOperation(value = "预约单详情接口", httpMethod = "GET", response = AppointOrderDetailVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appointOrderId", paramType = "query"),
    })
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<AppointOrderDetailVO> get(long appointOrderId) {
        SoaRequest<ShopAppointOrderQueryVO> soaRequest = new SoaRequest<>();
        ShopAppointOrderQueryVO shopAppointOrderQueryVO = new ShopAppointOrderQueryVO();
        shopAppointOrderQueryVO.setVenderId(VenderIdGetter.get());
        String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
        shopAppointOrderQueryVO.setBusinessCode(businessCode);
        shopAppointOrderQueryVO.setAppointOrderId(appointOrderId);
        soaRequest.setData(shopAppointOrderQueryVO);
        logger.info("shopAppointOrderFacade.getAppointOrderDetail 入参 ：【{}】", JSON.toJSONString(soaRequest));
        return Utils.call(() -> shopAppointOrderFacade.getAppointOrderDetail(soaRequest)
                , Constants.UMP_APPOINT_ORDER_DETAIL, soaRequest);
    }

    @ApiOperation(value = "预约单更新接口", httpMethod = "POST", response = SoaResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appointUpdateVO", dataType = "ShopAppointUpdateVO"),
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse edit(@RequestBody ShopAppointUpdateVO shopAppointUpdateVO) {
        return Utils.call(() -> {
            logger.info("更新预约单入参=" + JSON.toJSONString(shopAppointUpdateVO));
            SoaRequest<ShopAppointUpdateVO> soaRequest = new SoaRequest<>();
            shopAppointUpdateVO.setVenderId(shopAppointUpdateVO.getVenderId());
            shopAppointUpdateVO.setOperateUserPin(UserpinGetter.get());
            soaRequest.setData(shopAppointUpdateVO);
            logger.info("shopAppointOrderFacade.editAppointOrder 入参 ：【{}】", JSON.toJSONString(soaRequest));

            return shopAppointOrderFacade.editAppointOrder(soaRequest);
        });
    }


    @ApiOperation(value = "预约单取消接口", httpMethod = "POST", response = SoaResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appointCancel", dataType = "AppointCancel"),
    })
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse cancel(@RequestBody AppointCancel appointCancel) {
        return Utils.call(() -> {
            SoaRequest<AppointCancel> soaRequest = new SoaRequest<>();
            appointCancel.setUserPin(UserpinGetter.get());
            appointCancel.setVenderId(VenderIdGetter.get());
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            appointCancel.setBusinessCode(businessCode);
            soaRequest.setData(appointCancel);
            logger.info("shopAppointOrderFacade.cancel 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopAppointOrderFacade.cancel(soaRequest);
        });
    }


    @ApiOperation(value = "预约统计信息", httpMethod = "GET", response = OrderStatisticVO.class)
    @RequestMapping(value = "/statistic", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<OrderStatisticVO> statistic() {
        final LocalDate localDate = LocalDate.now();
        return Utils.call(() -> {
            SoaRequest<OrderStatisticQuery> soaRequest = new SoaRequest<>();
            OrderStatisticQuery orderStatisticQuery = new OrderStatisticQuery();
            orderStatisticQuery.setVenderId(VenderIdGetter.get());
            orderStatisticQuery.setDate(localDate);
            soaRequest.setData(orderStatisticQuery);
            logger.info("shopAppointOrderFacade.statisticByDate 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopAppointOrderFacade.statisticByDate(soaRequest);
        });
    }


    @ApiOperation(value = "预约记录", httpMethod = "GET", response = AppointRecordVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appointOrderId", paramType = "query"),
    })
    @RequestMapping(value = "/records", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<List<AppointRecordVO>> getAppointRecordInfo(long appointOrderId) {
        return Utils.call(() -> {
            SoaRequest<AppointRecordQueryVO> recordQueryVOSoaRequest = new SoaRequest<>();
            AppointRecordQueryVO appointRecordQueryVO = new AppointRecordQueryVO();
            appointRecordQueryVO.setAppointOrderId(appointOrderId + "");
            recordQueryVOSoaRequest.setData(appointRecordQueryVO);
            logger.info("shopAppointOrderFacade.getAppointRecordInfo 入参 ：【{}】", JSON.toJSONString(recordQueryVOSoaRequest));
            return shopAppointRecordFacade.getAppointRecordInfo(recordQueryVOSoaRequest);
        });
    }

    //================================二期===================================

    @ApiOperation(value = "获取服务类型", httpMethod = "GET", response = ServerTypeVO.class)
    @RequestMapping(value = "/serverTypeList", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<List<ServerTypeVO>> serverTypeList() {
        return Utils.call(() -> {
            SoaRequest<String> soaRequest = new SoaRequest<>();
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            soaRequest.setData(businessCode);

            logger.info("shopDynamicConfigFacade.serverTypeList 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopDynamicConfigFacade.serverTypeList(soaRequest);
        });
    }

    @ApiOperation(value = "获取筛选项", httpMethod = "GET", response = FilterItemVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serverType", paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "/filterItemList", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<List<FilterItemVO>> filterItemList(@RequestParam Integer serverType) {
        logger.info("入参serverType:" + serverType);
        return Utils.call(() -> {
            SoaRequest<ServerTypeRequest> soaRequest = new SoaRequest<>();
            ServerTypeRequest serverTypeRequest = new ServerTypeRequest();
            serverTypeRequest.setServerType(serverType);
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            serverTypeRequest.setBusinessCode(businessCode);
            soaRequest.setData(serverTypeRequest);

            logger.info("shopDynamicConfigFacade.filterItemList 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopDynamicConfigFacade.filterItemList(soaRequest);
        });
    }


    @ApiOperation(value = "获取批量操作", httpMethod = "GET", response = OperateItemVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "serverType", paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "/batchOperateList", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<List<OperateItemVo>> batchOperateList(@RequestParam Integer serverType) {
        logger.info("入参serverType:" + serverType);
        return Utils.call(() -> {
            SoaRequest<ServerTypeRequest> soaRequest = new SoaRequest<>();
            ServerTypeRequest serverTypeRequest = new ServerTypeRequest();
            serverTypeRequest.setServerType(serverType);
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            serverTypeRequest.setBusinessCode(businessCode);
            soaRequest.setData(serverTypeRequest);
            logger.info("shopDynamicConfigFacade.batchOperateList 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopDynamicConfigFacade.batchOperateList(soaRequest);
        });
    }


    @ApiOperation(value = "获取门店列表", httpMethod = "GET", response = StoreItem.class)
    @ApiImplicitParams({

            @ApiImplicitParam(name = "pageNumber", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", paramType = "query")
    })
    @RequestMapping(value = "/storeList", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<List<StoreItem>> storeList(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "200") int pageSize) {
        return Utils.call(() -> {
            CommonRequest commonRequest = new CommonRequest();
            PageRequest pageRequest = new PageRequest();
            pageRequest.setPageNumber(pageNumber);
            pageRequest.setPageSize(pageSize);
            Map<String, String> map = new HashMap<>();
            map.put("venderId", VenderIdGetter.get().toString());
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            map.put("businessCode", businessCode);
            logger.info("storeService.getStoreList 入参 ：pageRequest:【{}】,map:【{}】", JSON.toJSONString(pageRequest), JSON.toJSONString(map));
            CommonResponse<com.jd.virtual.appoint.common.Page<StoreItem>> commonResponse = storeService.getStoreList(commonRequest, map, pageRequest);
            logger.info("storeService.getStoreList 返回结果 ：commonResponse:【{}】", JSON.toJSONString(commonResponse));

            SoaResponse<List<StoreItem>> soaResponse = transformCR2SR(commonResponse);
            logger.info("storeService.getStoreList 最终返回结果 ：soaResponse:【{}】", JSON.toJSONString(soaResponse));
            return soaResponse;
        });

    }

    private SoaResponse<List<StoreItem>> transformCR2SR(CommonResponse<com.jd.virtual.appoint.common.Page<StoreItem>> commonResponse) {
        if ("200".equals(commonResponse.getCode())) {
            return new SoaResponse<>(commonResponse.getResult().getList());
        }
        return new SoaResponse<>(false, 500, commonResponse.getMsg());

    }

    @ApiOperation(value = "批量审核", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", dataType = "String", value = "选中的id以逗号隔开")
    })
    @RequestMapping(value = "/batchAudit", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse batchAudit(@RequestBody String ids) {
        logger.info("批量审核入参：ids:" + ids);
        return Utils.call(() -> {
            SoaRequest<CheckOrderVO> soaRequest = new SoaRequest();
            CheckOrderVO checkOrderVO = new CheckOrderVO();
            String[] idStrs = ids.split(",");
            List<Long> orderIds = new ArrayList<>();
            for (String str : idStrs) {
                orderIds.add(Long.parseLong(str));
            }
            logger.info("批量审核：idList:" + orderIds);
            checkOrderVO.setAppointOrderIds(orderIds);
            checkOrderVO.setVenderId(VenderIdGetter.get());
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            checkOrderVO.setBusinessCode(businessCode);
//            加入操作人
            checkOrderVO.setLoginUserPin(UserpinGetter.get());
            soaRequest.setData(checkOrderVO);
            logger.info("shopAppointOrderFacade.checkAppointOrder 入参 ：【{}】", JSON.toJSONString(soaRequest));

            return shopAppointOrderFacade.checkAppointOrder(soaRequest);
        });

    }

    @ApiOperation(value = "导入物流单号", httpMethod = "POST")
    @RequestMapping(value = "/importLogisticsNo", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse<Long> importLogisticsNo(@RequestParam("file") MultipartFile file) {
        long size = file.getSize();
        if (size > 2097152) {
            return new SoaResponse(false, 500, "您上传的文件大于2M");
        }
        return Utils.call(() -> {
            //获取所有物流公司与站点对应关系
            SoaResponse<List<ExpressCompanyVO>> soaResponse = shopExpressFacade.getAllExpressCompany();
            if (!soaResponse.isSuccess()) {
                logger.error("查询所有物流公司对应站点出现异常");
                return new SoaResponse(false, 500, "查询所有物流公司对应站点出现异常");
            }
            List<ExpressCompanyVO> expressCompanyVOList = soaResponse.getResult();

            //解析excel信息
            InputStream inputStream = file.getInputStream();
            Workbook rwb = Workbook.getWorkbook(inputStream);
            Sheet sheet = rwb.getSheet(0);
            int clos = sheet.getColumns();//得到所有的列
            int rows = sheet.getRows();//得到所有的行
            SoaRequest<LsnInputVO> soaRequest = new SoaRequest<>();
            LsnInputVO lsnInputVO = new LsnInputVO();
            lsnInputVO.setVenderId(VenderIdGetter.get());
            List<LsnVO> lsnVos = new ArrayList<>();

            lsnInputVO.setLsnVos(lsnVos);
            //加入操作人
            lsnInputVO.setOperateUser(UserpinGetter.get());
            soaRequest.setData(lsnInputVO);

            for (int i = 1; i < rows; i++) {

                //第一列预约单id，第二列物流公司名，第三列物流单号
                //第一个是列数，第二个是行数
                String appointOrderIdStr = sheet.getCell(0, i).getContents();//第一列是orderId
                if (StringUtils.isEmpty(appointOrderIdStr)) {
                    //预约单id为空为无效数据，换下一行
                    logger.info("第" + (i + 1) + "行的预约单id为空，略过");
                    continue;
                }
                long appointOrderId;
                try {
                    appointOrderId = Long.parseLong(appointOrderIdStr);
                } catch (Exception e) {
                    //如果转换异常，则同样视为无效，换下一行
                    logger.info("第" + (i + 1) + "行的预约单id" + appointOrderIdStr + "有非法字符,略过");
                    continue;
                }
                String logisticsName = sheet.getCell(1, i).getContents();//第二列是物流公司名称
                //有任一条物流公司名称无效则返回false不执行导入操作
                Integer siteId = getSiteId(logisticsName, expressCompanyVOList);
                if (siteId == null) {
                    logger.error("物流公司为空或者非法名称");
                    return new SoaResponse(false, 500, "物流公司名称不能为空，且必须从下拉框中选择");
                }


                String logisticsNo = sheet.getCell(2, i).getContents();//第三列是物流单号
                if (StringUtils.isEmpty(logisticsNo)) {
                    logger.error("物流单号必须填写");
                    return new SoaResponse(false, 500, "物流单号不能为空");
                }
                //构造jsf入参
                LsnVO lsnVO = new LsnVO();
                lsnVO.setAppointOrderId(appointOrderId);
                lsnVO.setLogisticsSource(logisticsName);
                lsnVO.setLogisticsSiteId(siteId);
                lsnVO.setLogisticsNo(logisticsNo);
                lsnVos.add(lsnVO);

                logger.info("第{}行数据：appointOrderId：{}，logisticsSource：{}，logisticsNo：{}", i + 1, appointOrderId, logisticsName, logisticsNo);

                //最多一次导入5000条数据
                int maxRow = 5000;
                if (lsnVos.size() == maxRow) {
                    logger.info("有效记录达到了{}条，后面的将全被忽略", maxRow);
                    break;
                }
            }
            logger.info("shopAppointOrderFacade.inputLsns 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopAppointOrderFacade.inputLsns(soaRequest);
        });

    }

    /**
     * 获取站点id，拿不到返回null
     *
     * @param logisticsName
     * @return
     */
    private Integer getSiteId(String logisticsName, List<ExpressCompanyVO> expressCompanyList) {
        //物流公司不为空且在列表内为有效
        if (StringUtils.isEmpty(logisticsName)) {
            return null;
        }
        for (ExpressCompanyVO expressCompanyVO : expressCompanyList) {
            if (expressCompanyVO.getName().equals(logisticsName)) {
                return expressCompanyVO.getThirdId();
            }
        }
        return null;
    }

    @ApiOperation(value = "预约列表接口", httpMethod = "GET", response = DynamicTable.class)
    @ApiImplicitParams({

            @ApiImplicitParam(name = "pageNumber", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", paramType = "query"),
            @ApiImplicitParam(name = "searchConditions", paramType = "query"),
            @ApiImplicitParam(name = "serverType", paramType = "query")
    })
    @RequestMapping(value = "/dynamic/list", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<DynamicTable> dynamicList(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "20") int pageSize, @RequestParam(defaultValue = "") String searchConditions, @RequestParam Integer serverType) {
        logger.info("searchConditions:" + searchConditions);
        return Utils.call(() -> {
            SoaRequest<ShopAppointOrderListRequest> soaRequest = new SoaRequest<>();
            ShopAppointOrderListRequest shopAppointOrderListRequest = new ShopAppointOrderListRequest();
            shopAppointOrderListRequest.setPageNumber(pageNumber);
            shopAppointOrderListRequest.setPageSize(pageSize);
            shopAppointOrderListRequest.setServerType(serverType);
            //转化json为map
            if (!StringUtils.isEmpty(searchConditions)) {
                Map<String, Object> jsonObject = JSON.parseObject(searchConditions);
                Map<String, String> searchMap = new HashMap<>();
                for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                    searchMap.put(entry.getKey(), (String) entry.getValue());
                }

                shopAppointOrderListRequest.setSearchMap(searchMap);
            }
            shopAppointOrderListRequest.setVenderId(VenderIdGetter.get());
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            shopAppointOrderListRequest.setBusinessCode(businessCode);
            soaRequest.setData(shopAppointOrderListRequest);
            logger.info("shopAppointOrderFacade.dynamicList 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopAppointOrderFacade.dynamicList(soaRequest);

        });
    }

    @ApiOperation(value = "导出预约列表接口", httpMethod = "GET", response = SoaResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchConditions", paramType = "query"),
            @ApiImplicitParam(name = "serverType", paramType = "query")
    })
    @RequestMapping(value = "/exportList", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse exportList(@RequestParam(defaultValue = "") String searchConditions, @RequestParam Integer serverType, HttpServletResponse response) {

        return Utils.call(() -> {
            List<LinkedHashMap<String, String>> list = new ArrayList<>();
            int pageNumber = 1;
            int pageSize = 1000;//每次请求1000条
            SoaRequest<ShopAppointOrderListRequest> soaRequest = new SoaRequest<>();
            ShopAppointOrderListRequest shopAppointOrderListRequest = new ShopAppointOrderListRequest();
            shopAppointOrderListRequest.setVenderId(VenderIdGetter.get());
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            shopAppointOrderListRequest.setBusinessCode(businessCode);
            shopAppointOrderListRequest.setServerType(serverType);

            shopAppointOrderListRequest.setPageSize(pageSize);
            if (!StringUtils.isEmpty(searchConditions)) {
                //转化json为map
                Map<String, Object> jsonObject = JSON.parseObject(searchConditions);
                Map<String, String> searchMap = new HashMap<>();
                for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                    if(entry.getValue() instanceof String) {
                        searchMap.put(entry.getKey(), (String) entry.getValue());
                    }else {
                        logger.info("query condition ignore :  {}", entry);
                    }
                }

                shopAppointOrderListRequest.setSearchMap(searchMap);
            }
            soaRequest.setData(shopAppointOrderListRequest);
            while(true) {
                if(pageNumber>10){//最多导出10000条
                    break;
                }
                shopAppointOrderListRequest.setPageNumber(pageNumber);

                logger.info("第{}次请求列表，shopAppointOrderFacade.exportAppointOrders 入参 ：【{}】", pageNumber, JSON.toJSONString(soaRequest));

                SoaResponse<List<LinkedHashMap<String, String>>> soaResponse = shopAppointOrderFacade.exportAppointOrders(soaRequest);
                List<LinkedHashMap<String, String>> result = soaResponse.getResult();

                if (!CollectionUtils.isEmpty(result)) {
                    list.addAll(result);
                    if(result.size() == pageSize){
                        pageNumber++;
                    }else {
                        break;
                    }
                }else{
                    break;
                }
            }

            boolean success = ExcelUtils.writeExcel(list, response);
            if (success) {
                return new SoaResponse();
            }
            return new SoaResponse(false, 500, "导出excel异常");
        });
    }

    @ApiOperation(value = "动态预约单详情接口", httpMethod = "GET", response = OrderDetailGroupVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "query"),
            @ApiImplicitParam(name = "serverType", paramType = "query")
    })
    @RequestMapping(value = "/dynamic/detail", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<DynamicOrderDetailVO> dynamicDetail(@RequestParam Long id, @RequestParam Integer serverType) {

        return Utils.call(() -> {

            SoaRequest<DynamicShopAppointOrderQuery> soaRequest = new SoaRequest<>();
            DynamicShopAppointOrderQuery query = new DynamicShopAppointOrderQuery();
            query.setVenderId(VenderIdGetter.get());
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            query.setBusinessCode(businessCode);
            query.setServerType(serverType);
            query.setAppointOrderId(id);
            soaRequest.setData(query);
            logger.info("shopAppointOrderFacade.dynamicGetAppointOrderDetail 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopAppointOrderFacade.dynamicGetAppointOrderDetail(soaRequest);
        });
    }

    @ApiOperation(value = "获取详情页操作列表", httpMethod = "GET", response = OperateItemVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "/detailOperateList", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<List<OperateItemVo>> detailOperateList(@RequestParam Long id) {
        logger.info("入参id:" + id);
        return Utils.call(() -> {
            SoaRequest<Long> soaRequest = new SoaRequest<>();
            soaRequest.setData(id);
            logger.info("shopDynamicConfigFacade.detailOperateList 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopDynamicConfigFacade.detailOperateList(soaRequest);
        });
    }

    //取消预约接口一期已经有了，是通用的接口
    /*@ApiOperation(value = "取消预约", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "query", dataType = "long")
    })
    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse cancel(@RequestParam Long id) {
        logger.info("入参id:" + id);
        return Utils.call(() -> {
            SoaRequest<AppointCancel> soaRequest = new SoaRequest();
            AppointCancel appointCancel = new AppointCancel();

            appointCancel.setAppointOrderId(id);
            appointCancel.setVenderId(VenderIdGetter.get());
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            appointCancel.setBusinessCode(businessCode);
            //加入操作人
            appointCancel.setOperateUser(UserpinGetter.get());
            soaRequest.setData(appointCancel);
            return shopAppointOrderFacade.cancel(soaRequest);
        });
    }*/

    @ApiOperation(value = "审核通过", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "String")
    })
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse audit(@RequestBody String id) {
        logger.info("入参id:" + id);
        return Utils.call(() -> {
            SoaRequest<CheckOrderVO> soaRequest = new SoaRequest();
            CheckOrderVO checkOrderVO = new CheckOrderVO();

            List<Long> orderIds = new ArrayList<>();
            orderIds.add(Long.parseLong(id));
            checkOrderVO.setAppointOrderIds(orderIds);
            checkOrderVO.setVenderId(VenderIdGetter.get());
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            checkOrderVO.setBusinessCode(businessCode);
            //加入操作人
            checkOrderVO.setLoginUserPin(UserpinGetter.get());
            soaRequest.setData(checkOrderVO);
            logger.info("shopAppointOrderFacade.checkAppointOrder 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopAppointOrderFacade.checkAppointOrder(soaRequest);
        });
    }

    @ApiOperation(value = "预约改期", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reschduleVO", dataType = "ReschduleVO")
    })
    @RequestMapping(value = "/changeSchedule", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse changeSchedule(@RequestBody ReschduleVO reschduleVO) {
        return Utils.call(() -> {
            SoaRequest<ReschduleVO> soaRequest = new SoaRequest();
            reschduleVO.setVenderId(VenderIdGetter.get());
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            reschduleVO.setBusinessCode(businessCode);
            //加入操作人
            reschduleVO.setLoginUserPin(UserpinGetter.get());
            soaRequest.setData(reschduleVO);
            logger.info("shopAppointOrderFacade.reschdule 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopAppointOrderFacade.reschdule(soaRequest);
        });
    }

    @ApiOperation(value = "编辑商家备注", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "venderMemo", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "id", paramType = "query", dataType = "long")
    })
    @RequestMapping(value = "/editVenderMemo", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse editVenderMemo(String venderMemo, Long id) {
        return Utils.call(() -> {
            SoaRequest<UpdateAppointVO> soaRequest = new SoaRequest();
            UpdateAppointVO updateAppointVO = new UpdateAppointVO();
            updateAppointVO.setAppointOrderId(id);
            updateAppointVO.setVenderMemo(venderMemo);
            updateAppointVO.setVenderId(VenderIdGetter.get());
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            updateAppointVO.setBusinessCode(businessCode);
            soaRequest.setData(updateAppointVO);
            logger.info("shopAppointOrderFacade.dynamicUpdateAppoint 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopAppointOrderFacade.dynamicUpdateAppoint(soaRequest);
        });
    }

    @ApiOperation(value = "新增物流信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appointFinishVO", dataType = "AppointFinishVO")
    })
    @RequestMapping(value = "/addLogisticsInfo", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse addLogisticsInfo(@RequestBody AppointFinishVO appointFinishVO) {
        logger.info("入参 appointFinishVO:{}", JSON.toJSONString(appointFinishVO));
        return Utils.call(() -> {
            SoaRequest<AppointFinishVO> soaRequest = new SoaRequest();
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            appointFinishVO.setBusinessCode(businessCode);
            //加入操作人
            appointFinishVO.setOperateUser(UserpinGetter.get());
            soaRequest.setData(appointFinishVO);
            logger.info("shopAppointOrderFacade.finished 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopAppointOrderFacade.finished(soaRequest);
        });
    }

    @ApiOperation(value = "编辑物流信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updateAttachVO", dataType = "UpdateAttachVO")
    })
    @RequestMapping(value = "/editLogistics", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse editLogistics(@RequestBody UpdateAttachVO updateAttachVO) {
        logger.info("入参 updateAttachVO:{}", JSON.toJSONString(updateAttachVO));
        return Utils.call(() -> {
            SoaRequest<UpdateAttachVO> soaRequest = new SoaRequest();
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            updateAttachVO.setBusinessCode(businessCode);
            updateAttachVO.setVenderId(VenderIdGetter.get());
            //加入操作人
            updateAttachVO.setLoginUserPin(UserpinGetter.get());
            soaRequest.setData(updateAttachVO);
            logger.info("shopAppointOrderFacade.updateAttachInfo 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return shopAppointOrderFacade.updateAttachInfo(soaRequest);
        });
    }

    @ApiOperation(value = "查询物流信息记录", httpMethod = "GET", response = ExpressInfo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/getLogisticsInfo", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<ExpressInfo> getLogisticsInfo(Long id) {
        return Utils.call(() -> {
            logger.info("shopExpressFacade.getExpressInfo 入参 ：【{}】", id);
            return shopExpressFacade.getExpressInfo(new SoaRequest<>(id));
        });
    }

    @ApiOperation(value = "查询所有物流公司", httpMethod = "GET", response = ExpressCompanyVO.class)
    @RequestMapping(value = "/getAllExpressCompany", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<List<ExpressCompanyVO>> getAllExpressCompany() {
        return Utils.call(() -> {
            return shopExpressFacade.getAllExpressCompany();
        });
    }

}

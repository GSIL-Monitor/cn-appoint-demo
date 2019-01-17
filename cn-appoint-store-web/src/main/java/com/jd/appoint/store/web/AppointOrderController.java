package com.jd.appoint.store.web;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.shopapi.ShopAppointOrderFacade;
import com.jd.appoint.shopapi.ShopAppointRecordFacade;
import com.jd.appoint.shopapi.ShopDynamicConfigFacade;
import com.jd.appoint.shopapi.ShopExpressFacade;
import com.jd.appoint.shopapi.vo.*;
import com.jd.appoint.store.service.BusinessCodeService;
import com.jd.appoint.store.util.Constants;
import com.jd.appoint.store.util.ExcelUtils;
import com.jd.appoint.store.util.LoginInfoGetter;
import com.jd.appoint.store.util.Utils;
import com.jd.appoint.store.vo.AppointOrderQuery;
import com.jd.appoint.storeapi.StoreAppointOrderFacade;
import com.jd.appoint.storeapi.StoreDynamicConfigFacade;
import com.jd.appoint.vo.AppointCancel;
import com.jd.appoint.vo.AppointFinishVO;
import com.jd.appoint.vo.dynamic.*;
import com.jd.appoint.vo.dynamic.FilterItemVO;
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
    private StoreAppointOrderFacade storeAppointOrderFacade;

    @Resource
    private ShopAppointRecordFacade shopAppointRecordFacade;

    private static final Logger logger = LoggerFactory.getLogger(AppointOrderController.class);

    @Resource
    private StoreDynamicConfigFacade storeDynamicConfigFacade;

    @Resource
    private BusinessCodeService businessCodeService;


    @ApiOperation(value = "预约单取消接口", httpMethod = "POST", response = SoaResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appointCancel", dataType = "AppointCancel"),
    })
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse cancel(@RequestBody AppointCancel appointCancel) {
        return Utils.call(() -> {
            SoaRequest<AppointCancel> soaRequest = new SoaRequest<>();
            appointCancel.setUserPin(LoginInfoGetter.getUserPin());
            appointCancel.setVenderId(LoginInfoGetter.getVenderId());
            appointCancel.setStoreCode(LoginInfoGetter.getStoreId() + "");
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            appointCancel.setBusinessCode(businessCode);
            soaRequest.setData(appointCancel);
            logger.info("storeAppointOrderFacade.cancel 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return storeAppointOrderFacade.cancel(soaRequest);
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
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            soaRequest.setData(businessCode);

            logger.info("storeDynamicConfigFacade.serverTypeList 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return storeDynamicConfigFacade.serverTypeList(soaRequest);
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
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            serverTypeRequest.setBusinessCode(businessCode);
            soaRequest.setData(serverTypeRequest);

            logger.info("storeDynamicConfigFacade.filterItemList 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return storeDynamicConfigFacade.filterItemList(soaRequest);
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
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            serverTypeRequest.setBusinessCode(businessCode);
            soaRequest.setData(serverTypeRequest);
            logger.info("storeDynamicConfigFacade.batchOperateList 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return storeDynamicConfigFacade.batchOperateList(soaRequest);
        });
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
            checkOrderVO.setVenderId(LoginInfoGetter.getVenderId());
            checkOrderVO.setStoreCode(LoginInfoGetter.getStoreId() + "");
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            checkOrderVO.setBusinessCode(businessCode);
//            加入操作人
            checkOrderVO.setLoginUserPin(LoginInfoGetter.getUserPin());
            soaRequest.setData(checkOrderVO);
            logger.info("storeAppointOrderFacade.checkAppointOrder 入参 ：【{}】", JSON.toJSONString(soaRequest));

            return storeAppointOrderFacade.checkAppointOrder(soaRequest);
        });

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

        return Utils.call(() -> {
            SoaRequest<StoreAppointOrderListRequest> soaRequest = new SoaRequest<>();
            StoreAppointOrderListRequest storeAppointOrderListRequest = new StoreAppointOrderListRequest();
            storeAppointOrderListRequest.setPageNumber(pageNumber);
            storeAppointOrderListRequest.setPageSize(pageSize);
            storeAppointOrderListRequest.setServerType(serverType);
            //转化json为map
            if (!StringUtils.isEmpty(searchConditions)) {
                Map<String, Object> jsonObject = JSON.parseObject(searchConditions);
                Map<String, String> searchMap = new HashMap<>();
                for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                    searchMap.put(entry.getKey(), (String) entry.getValue());
                }

                storeAppointOrderListRequest.setSearchMap(searchMap);
            }
            storeAppointOrderListRequest.setStoreCode(LoginInfoGetter.getStoreId() + "");
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            storeAppointOrderListRequest.setBusinessCode(businessCode);
            soaRequest.setData(storeAppointOrderListRequest);
            logger.info("shopAppointOrderFacade.dynamicList 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return storeAppointOrderFacade.dynamicList(soaRequest);

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
            int pageNumber = 1;
            int pageSize = 10000;//每次请求10000条
            SoaRequest<StoreAppointOrderListRequest> soaRequest = new SoaRequest<>();
            StoreAppointOrderListRequest storeAppointOrderListRequest = new StoreAppointOrderListRequest();
            storeAppointOrderListRequest.setStoreCode(LoginInfoGetter.getStoreId() + "");
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            storeAppointOrderListRequest.setBusinessCode(businessCode);
            storeAppointOrderListRequest.setServerType(serverType);

            storeAppointOrderListRequest.setPageSize(pageSize);
            storeAppointOrderListRequest.setPageNumber(pageNumber);

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

                storeAppointOrderListRequest.setSearchMap(searchMap);
            }
            soaRequest.setData(storeAppointOrderListRequest);

            logger.info("请求列表，storeAppointOrderFacade.exportAppointOrders 入参 ：【{}】", pageNumber, JSON.toJSONString(soaRequest));

            SoaResponse<List<LinkedHashMap<String, String>>> soaResponse = storeAppointOrderFacade.exportAppointOrders(soaRequest);
            List<LinkedHashMap<String, String>> list = soaResponse.getResult();

            if (!CollectionUtils.isEmpty(list)) {
                boolean success = ExcelUtils.writeExcel(list, response);
                if (success) {
                    return new SoaResponse();
                }
                return new SoaResponse(false, 500, "导出excel异常");
            }

            return new SoaResponse(false, 500, "没有符合条件的数据");


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

            SoaRequest<DynamicStoreAppointOrderQuery> soaRequest = new SoaRequest<>();
            DynamicStoreAppointOrderQuery query = new DynamicStoreAppointOrderQuery();
            query.setStoreId(LoginInfoGetter.getStoreId());
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            query.setBusinessCode(businessCode);
            query.setServerType(serverType);
            query.setAppointOrderId(id);
            soaRequest.setData(query);
            logger.info("shopAppointOrderFacade.dynamicGetAppointOrderDetail 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return storeAppointOrderFacade.dynamicGetAppointOrderDetail(soaRequest);
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
            logger.info("storeDynamicConfigFacade.detailOperateList 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return storeDynamicConfigFacade.detailOperateList(soaRequest);
        });
    }

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
            checkOrderVO.setVenderId(LoginInfoGetter.getVenderId());
            checkOrderVO.setStoreCode(LoginInfoGetter.getStoreId() + "");
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            checkOrderVO.setBusinessCode(businessCode);
            //加入操作人
            checkOrderVO.setLoginUserPin(LoginInfoGetter.getUserPin());
            soaRequest.setData(checkOrderVO);
            logger.info("shopAppointOrderFacade.checkAppointOrder 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return storeAppointOrderFacade.checkAppointOrder(soaRequest);
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
            reschduleVO.setVenderId(LoginInfoGetter.getVenderId());
            reschduleVO.setStoreCode(LoginInfoGetter.getStoreId() + "");
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            reschduleVO.setBusinessCode(businessCode);
            //加入操作人
            reschduleVO.setLoginUserPin(LoginInfoGetter.getUserPin());
            soaRequest.setData(reschduleVO);
            logger.info("storeAppointOrderFacade.reschdule 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return storeAppointOrderFacade.reschdule(soaRequest);
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
            updateAppointVO.setVenderId(LoginInfoGetter.getVenderId());
            updateAppointVO.setStoreCode(LoginInfoGetter.getStoreId() + "");
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            updateAppointVO.setBusinessCode(businessCode);
            soaRequest.setData(updateAppointVO);
            logger.info("storeAppointOrderFacade.dynamicUpdateAppoint 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return storeAppointOrderFacade.dynamicUpdateAppoint(soaRequest);
        });
    }

    @ApiOperation(value = "确认已提货", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "String")
    })
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse confirm(@RequestBody String id) {
        logger.info("入参 id:{}", id);
        return Utils.call(() -> {
            SoaRequest<AppointFinishVO> soaRequest = new SoaRequest();
            AppointFinishVO appointFinishVO = new AppointFinishVO();
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            appointFinishVO.setBusinessCode(businessCode);
            appointFinishVO.setAppointOrderId(Long.parseLong(id));
            //加入操作人
            appointFinishVO.setOperateUser(LoginInfoGetter.getUserPin());
            soaRequest.setData(appointFinishVO);
            logger.info("storeAppointOrderFacade.finished 入参 ：【{}】", JSON.toJSONString(soaRequest));
            return storeAppointOrderFacade.finished(soaRequest);
        });
    }

}

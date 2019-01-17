package com.jd.appoint.store.web;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.shopapi.ShopProductFacade;
import com.jd.appoint.shopapi.ShopStockFacade;
import com.jd.appoint.shopapi.ShopWorkTimeFacade;
import com.jd.appoint.shopapi.vo.*;
import com.jd.appoint.store.service.BusinessCodeService;
import com.jd.appoint.store.service.StockService;
import com.jd.appoint.store.service.WorkTimeService;
import com.jd.appoint.store.util.Constants;
import com.jd.appoint.store.util.LoginInfoGetter;
import com.jd.appoint.store.util.Utils;
import com.jd.appoint.store.vo.*;
import com.jd.appoint.storeapi.StoreProductFacade;
import com.jd.appoint.storeapi.StoreScheduleFacade;
import com.jd.appoint.vo.page.Page;
import com.jd.appoint.vo.schedule.ScheduleModel;
import com.jd.appoint.vo.schedule.ScheduleResult;
import com.jd.appoint.vo.schedule.ScheduleVO;
import com.jd.appoint.vo.stock.StockInfoVO;
import com.jd.appoint.vo.time.WorkTime;
import com.jd.appoint.vo.time.WorkTimeQuery;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by bjliuyong on 2018/5/23.
 */
@Controller
@RequestMapping("/api/product")
public class ProductController {

    @Resource
    private StoreProductFacade productFacade;

    @Resource
    private WorkTimeService workTimeService;
    @Resource
    private StockService stockService;
    @Resource
    private BusinessCodeService businessCodeService;
    @Resource
    private StoreScheduleFacade storeScheduleFacade;


    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @ApiOperation(value = "产品列表接口", httpMethod = "GET", response = Product.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", paramType = "query"),
            @ApiImplicitParam(name = "skuName", paramType = "query"),
            @ApiImplicitParam(name = "skuId", paramType = "query")
    })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<Page<Product>> list(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "20") int pageSize, String skuName, Long skuId) {

        return Utils.call(() -> {

            ProductQueryVO productQuery = new ProductQueryVO();
            productQuery.setPageSize(pageSize);
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            productQuery.setBusinessCode(businessCode);
            productQuery.setShopId(LoginInfoGetter.getStoreId());
            productQuery.setName(skuName);
            productQuery.setSkuId(skuId);
            productQuery.setPageNum(pageNumber);
            logger.info("productFacade.search 入参 ：【{}】", JSON.toJSONString(productQuery));

            SoaResponse<List<ProductVO>> searchResponse = productFacade.search(new SoaRequest<>(productQuery));
            logger.info("productFacade.search 返回结果 ：【{}】", JSON.toJSONString(searchResponse));
            if(!searchResponse.isSuccess()){
                return new SoaResponse<>(false,searchResponse.getCode(),searchResponse.getReason());
            }

            SoaResponse<Integer> countResponse = productFacade.totalCount(new SoaRequest<>(productQuery));
            logger.info("productFacade.totalCount 返回结果 ：【{}】", JSON.toJSONString(countResponse));

            Page<Product> page = new Page<>();
            page.setPageNumber(pageNumber);
            page.setPageSize(pageSize);
            page.setTotalCount(countResponse.getResult());
            page.setList(ProductConvert.convert2ProductList(searchResponse.getResult()));
            return new SoaResponse<>(page);
        });
    }

    @ApiOperation(value = "重新获取产品接口", httpMethod = "GET", response = SoaResponse.class)
    @RequestMapping(value = "/syncProduct", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse syncProduct() {

        return Utils.call(() -> {

            Long venderId = LoginInfoGetter.getVenderId();
            String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
            SyncSkuRequest syncSkuRequest = new SyncSkuRequest();

            syncSkuRequest.setBusinessType(Integer.parseInt(businessCode));
            syncSkuRequest.setVenderId(venderId);
            syncSkuRequest.setStoreId(LoginInfoGetter.getStoreId());
            logger.info("productFacade.syncProduct 入参 ：【{}】", JSON.toJSONString(syncSkuRequest));

            productFacade.syncStoreProduct(new SoaRequest<>(syncSkuRequest));
            return new SoaResponse();

        });
    }

    @ApiOperation(value = "删除产品接口", httpMethod = "GET", response = SoaResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "query")
    })
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse delete(Long id) {

        return Utils.call(() -> {


            SkuOperateRequest skuOperateRequest = new SkuOperateRequest();
            skuOperateRequest.setId(id);
            skuOperateRequest.setShopId( LoginInfoGetter.getStoreId());
            logger.info("productFacade.delete 入参 ：【{}】", JSON.toJSONString(skuOperateRequest));

            SoaResponse<Boolean> delete = productFacade.delete(new SoaRequest<>(skuOperateRequest));
            return delete;
        });
    }


    @ApiOperation(value = "更新产品状态接口", httpMethod = "POST", response = SoaResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product", paramType = "body", dataType = "Product")
    })
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse updateStatus(@RequestBody Product product) {

        return Utils.call(() -> {

            SkuOperateRequest skuOperateRequest = new SkuOperateRequest();
            skuOperateRequest.setId(product.getId());
            skuOperateRequest.setShopId(LoginInfoGetter.getStoreId());

            skuOperateRequest.setStatus(StatusEnum.getFromCode(product.getStatus()));
            logger.info("productFacade.updateStatus 入参 ：【{}】", JSON.toJSONString(skuOperateRequest));

            SoaResponse<Boolean> soaResponse = productFacade.updateStatus(new SoaRequest<>(skuOperateRequest));
            return soaResponse;
        });
    }

    @ApiOperation(value = "获取产品详情", httpMethod = "GET", response = Product.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path")
    })
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<Product> detail(@PathVariable Long id) {

        return Utils.call(() -> {


            SkuOperateRequest skuOperateRequest = new SkuOperateRequest();
            skuOperateRequest.setId(id);
            skuOperateRequest.setShopId(LoginInfoGetter.getStoreId());
            logger.info("productFacade.getOne 入参 ：【{}】", JSON.toJSONString(skuOperateRequest));

            SoaResponse<ProductVO> soaResponse = productFacade.getOne(new SoaRequest<>(skuOperateRequest));
            if (!soaResponse.isSuccess()) {
                return new SoaResponse<>(false, soaResponse.getCode(), soaResponse.getReason());
            }

            return new SoaResponse<>(ProductConvert.convert2Product(soaResponse.getResult()));
        });
    }

    @ApiOperation(value = "查询是否有sku维度的库存", httpMethod = "GET", response = Boolean.class)
    @RequestMapping(value = "/checkSkuStock", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<Boolean> ifExistsSkuStock() {

        return Utils.call(() -> {

            SoaResponse<ScheduleModel> model = storeScheduleFacade.searchScheduleModel(new SoaRequest<>(businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId())));
            if(model.isSuccess()){
                return new SoaResponse<>(model.getResult().getModel().equals(2));
            }
            return new SoaResponse<>(false,model.getCode(),model.getReason());

        });
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @ApiOperation(value = "获取产能日历接口", httpMethod = "GET", response = ScheduleResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "skuId", paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "startDate", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endDate", required = true, paramType = "query", dataType = "Date")
    })
    @RequestMapping(value = "/getStockSchedule", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<ScheduleResult> getStockSchedule(@RequestParam(defaultValue = "-1") Long skuId, @RequestParam Date startDate, @RequestParam Date endDate) {
        ScheduleVO scheduleVO = new ScheduleVO();
        scheduleVO.setSkuId(skuId);
        scheduleVO.setStartDate(startDate);
        scheduleVO.setEndDate(endDate);
        scheduleVO.setVenderId(LoginInfoGetter.getVenderId());
        scheduleVO.setStoreCode(LoginInfoGetter.getStoreId()+"");
        String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
        scheduleVO.setBusinessCode(businessCode);
        return stockService.getStockSchedule(scheduleVO);
    }

    @ApiOperation(value = "服务时间查询", httpMethod = "GET", response = WorkTime.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "skuId", required = true, paramType = "query", dataType = "long")
    })
    @RequestMapping(value = "/getWorkTime", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<WorkTime> getWorkTime(WorkTimeQuery workTimeQuery) {
        return workTimeService.getWorkTime(workTimeQuery);
    }


    @ApiOperation(value = "保存产品信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "product", required = true, dataType = "Product")
    })
    @RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse saveProduct(@RequestBody Product product) {
        logger.info("入参 product:{}", JSON.toJSONString(product));
        return Utils.call(() -> {
            //保存产品信息
            ProductVO productVO = new ProductVO();
            productVO.setId(product.getId());
            productVO.setName(product.getName());
            productVO.setSkuId(product.getSkuId());
            productVO.setStatus(StatusEnum.getFromCode(product.getStatus()));
            logger.info("productFacade.update 入参 ：【{}】", JSON.toJSONString(productVO));

            return productFacade.update(new SoaRequest<>(productVO));
        });
    }

    @ApiOperation(value = "修改零散库存", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updateStock", required = true, dataType = "UpdateStock")
    })
    @RequestMapping(value = "/updateStock", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse<List<String>> updateStock(@RequestBody UpdateStock updateStock) {
        logger.info("入参 updateStock:{}", JSON.toJSONString(updateStock));
        return stockService.updateStock(updateStock);

    }

    /**
     * 根据id判断是更新还是新增
     * @param workTime
     * @return
     */
    @ApiOperation(value = "修改服务时间", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "workTime", dataType = "WorkTime")
    })
    @RequestMapping(value = "/saveWorkTime", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse saveWorkTime(@RequestBody WorkTime workTime) {
        logger.info("入参 workTime:{}", JSON.toJSONString(workTime));
        return workTimeService.saveOrUpdate(workTime);

    }

    @ApiOperation(value = "批量更新库存", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stockInfoVO", required = true, dataType = "StockInfoVO")
    })
    @RequestMapping(value = "/batchUpdateStock", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse<List<String>> batchUpdateStock(@RequestBody StockInfoVO stockInfoVO) {
        logger.info("入参 stockInfoVO:{}", JSON.toJSONString(stockInfoVO));
        stockInfoVO.setVenderId(LoginInfoGetter.getVenderId());
        stockInfoVO.setStoreCode(LoginInfoGetter.getStoreId()+"");
        String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
        stockInfoVO.setBusinessCode(businessCode);

        return stockService.batchUpdateStock(stockInfoVO);

    }
}

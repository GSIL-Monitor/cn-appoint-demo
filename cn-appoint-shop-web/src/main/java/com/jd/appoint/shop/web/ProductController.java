package com.jd.appoint.shop.web;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.shop.service.BusinessCodeService;
import com.jd.appoint.shop.service.StockService;
import com.jd.appoint.shop.service.WorkTimeService;
import com.jd.appoint.shop.util.Constants;
import com.jd.appoint.shop.util.ExcelUtils;
import com.jd.appoint.shop.util.Utils;
import com.jd.appoint.shop.util.VenderIdGetter;
import com.jd.appoint.shop.vo.*;
import com.jd.appoint.shopapi.*;
import com.jd.appoint.shopapi.vo.*;
import com.jd.appoint.vo.AppointCancel;
import com.jd.appoint.vo.dynamic.*;
import com.jd.appoint.vo.dynamic.FilterItemVO;
import com.jd.appoint.vo.dynamic.table.DynamicTable;
import com.jd.appoint.vo.order.*;
import com.jd.appoint.vo.page.Page;
import com.jd.appoint.vo.schedule.ScheduleModel;
import com.jd.appoint.vo.schedule.ScheduleResult;
import com.jd.appoint.vo.schedule.ScheduleVO;
import com.jd.appoint.vo.stock.StockInfoVO;
import com.jd.appoint.vo.time.WorkTime;
import com.jd.appoint.vo.time.WorkTimeQuery;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.virtual.appoint.common.CommonResponse;
import com.jd.virtual.appoint.store.StoreItem;
import com.jd.virtual.appoint.store.StoreRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scala.collection.immutable.Stream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by bjliuyong on 2018/5/23.
 */
@Controller
@RequestMapping("/api/product")
public class ProductController {

    @Resource
    private ShopProductFacade productFacade;
    @Resource
    private ShopWorkTimeFacade shopWorkTimeFacade;

    @Resource
    private ShopStockFacade shopStockFacade;
    @Resource
    private WorkTimeService workTimeService;
    @Resource
    private StockService stockService;
    @Resource
    private BusinessCodeService businessCodeService;
    @Resource
    private  ShopScheduleFacade shopScheduleFacade;

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
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            productQuery.setBusinessCode(businessCode);
            productQuery.setShopId(VenderIdGetter.get());
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

            Long venderId = VenderIdGetter.get();
            String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
            SyncSkuRequest syncSkuRequest = new SyncSkuRequest();

            syncSkuRequest.setBusinessType(Integer.parseInt(businessCode));
            syncSkuRequest.setVenderId(venderId);
            logger.info("productFacade.syncProduct 入参 ：【{}】", JSON.toJSONString(syncSkuRequest));

            productFacade.syncProduct(new SoaRequest<>(syncSkuRequest));
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

            Long venderId = VenderIdGetter.get();
            SkuOperateRequest skuOperateRequest = new SkuOperateRequest();
            skuOperateRequest.setId(id);
            skuOperateRequest.setShopId(venderId);
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

            Long venderId = VenderIdGetter.get();
            SkuOperateRequest skuOperateRequest = new SkuOperateRequest();
            skuOperateRequest.setId(product.getId());
            skuOperateRequest.setShopId(venderId);

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

            Long venderId = VenderIdGetter.get();
            SkuOperateRequest skuOperateRequest = new SkuOperateRequest();
            skuOperateRequest.setId(id);
            skuOperateRequest.setShopId(venderId);
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

            SoaResponse<ScheduleModel> model = shopScheduleFacade.searchScheduleModel(new SoaRequest<>(businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get())));
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
        scheduleVO.setVenderId(VenderIdGetter.get());
        String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
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

    /*@ApiOperation(value = "保存产品及产能日历信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productSave", required = true, dataType = "ProductSave")
    })
    @RequestMapping(value = "/saveAll", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse saveAll(@RequestBody ProductSave productSave) {
        logger.info("入参 productSave:{}", JSON.toJSONString(productSave));
        return Utils.call(() -> {
            //保存产品信息
            Product product = productSave.getProduct();
            ProductVO productVO = new ProductVO();
            productVO.setId(product.getId());
            productVO.setName(product.getName());
            productVO.setSkuId(product.getSkuId());
            productVO.setStatus(StatusEnum.getFromCode(product.getStatus()));

            SoaResponse<Boolean> update = productFacade.update(new SoaRequest<>(productVO));
            String errMsg = "";
            String split = ",";
            if (!update.isSuccess() || !update.getResult()) {
                errMsg = "保存产品信息失败" + split;
            }


            //逐个更新库存
            List<SingleStockVO> singleStockVOList = productSave.getSingleStockVOList();
            for (SingleStockVO singleStockVO : singleStockVOList) {
                SoaRequest<StockInfoVO> soaRequest = new SoaRequest<>();
                StockInfoVO stockInfoVO = new StockInfoVO();
                stockInfoVO.setStartDate(singleStockVO.getDate());
                stockInfoVO.setEndDate(singleStockVO.getDate());
                stockInfoVO.setTotalQty(singleStockVO.getTotalQty());
                stockInfoVO.setVenderId(VenderIdGetter.get());
                stockInfoVO.setBusinessCode(Constants.BIZ_CODE);
                stockInfoVO.setSkuId(product.getSkuId());
                soaRequest.setData(stockInfoVO);

                SoaResponse updateStock = shopStockFacade.saveOrUpdateStock(soaRequest);
                if (!updateStock.isSuccess()) {
                    errMsg = "更新" + DateUtils.formatDate(singleStockVO.getDate(), "yyyy-MM-dd") + "的库存失败" + split;
                }
            }

            //更新服务时间
            SoaRequest<WorkTime> soaRequest = new SoaRequest<>();
            WorkTime workTime = productSave.getWorkTime();

            workTime.setVenderId(VenderIdGetter.get());
            workTime.setBusinessCode(Constants.BIZ_CODE);
            workTime.setTimeShowType(3);
            soaRequest.setData(workTime);
            SoaResponse editTime = shopWorkTimeFacade.editTime(soaRequest);
            if (!editTime.isSuccess()) {
                errMsg = "更新服务时间失败" + split;
            }
            if (!StringUtils.isEmpty(errMsg)) {
                errMsg = errMsg.substring(0, errMsg.length() - 1);
                return new SoaResponse(false, 500, errMsg);
            }
            return new SoaResponse();
        });

    }*/

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
        stockInfoVO.setVenderId(VenderIdGetter.get());
        String businessCode = businessCodeService.getBusinessCodeByVenderId(VenderIdGetter.get());
        stockInfoVO.setBusinessCode(businessCode);

        return stockService.batchUpdateStock(stockInfoVO);

    }
}

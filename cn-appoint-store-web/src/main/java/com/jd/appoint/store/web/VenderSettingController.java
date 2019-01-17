package com.jd.appoint.store.web;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.store.service.BusinessCodeService;
import com.jd.appoint.store.service.StockService;
import com.jd.appoint.store.service.WorkTimeService;
import com.jd.appoint.store.util.LoginInfoGetter;
import com.jd.appoint.store.vo.UpdateStock;
import com.jd.appoint.vo.schedule.ScheduleResult;
import com.jd.appoint.vo.schedule.ScheduleVO;
import com.jd.appoint.vo.stock.StockInfoVO;
import com.jd.appoint.vo.time.WorkTime;
import com.jd.appoint.vo.time.WorkTimeQuery;
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
 * Created by bjliuyong on 2018/5/17.
 */
@Controller
public class VenderSettingController {

    private static final Logger logger = LoggerFactory.getLogger(VenderSettingController.class);

    @Resource
    private WorkTimeService workTimeService;
    @Resource
    private StockService stockService;
    @Resource
    private BusinessCodeService businessCodeService;

    @ApiOperation(value = "服务时间查询", httpMethod = "GET", response = WorkTime.class)
    @RequestMapping(value = "/api/setting", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<WorkTime> setting(WorkTimeQuery workTimeQuery) {
        return workTimeService.getWorkTime(workTimeQuery);
    }

    @ApiOperation(value = "保存或者更新", httpMethod = "POST", response = SoaResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "workTime", required = true, dataType = "WorkTime")})
    @RequestMapping(value = "/api/update", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse updateOrEdit(@RequestBody WorkTime workTime) {
        return workTimeService.saveOrUpdate(workTime);

    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @ApiOperation(value = "获取产能日历接口", httpMethod = "GET", response = ScheduleResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", required = true, paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "endDate", required = true, paramType = "query", dataType = "Date")
    })
    @RequestMapping(value = "/api/setting/getStockSchedule", method = RequestMethod.GET)
    @ResponseBody
    public SoaResponse<ScheduleResult> getStockSchedule(@RequestParam Date startDate, @RequestParam Date endDate) {
        ScheduleVO scheduleVO = new ScheduleVO();
        scheduleVO.setStartDate(startDate);
        scheduleVO.setEndDate(endDate);
        //商家维度设置库存，skuId默认为-1
        scheduleVO.setSkuId(-1L);
        scheduleVO.setVenderId(LoginInfoGetter.getVenderId());
        scheduleVO.setStoreCode(LoginInfoGetter.getStoreId()+"");
        String businessCode = businessCodeService.getBusinessCodeByVenderId(LoginInfoGetter.getVenderId());
        scheduleVO.setBusinessCode(businessCode);

        return stockService.getStockSchedule(scheduleVO);
    }

    @ApiOperation(value = "修改零散库存", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updateStock", required = true, dataType = "UpdateStock")
    })
    @RequestMapping(value = "/api/setting/updateStock", method = RequestMethod.POST)
    @ResponseBody
    public SoaResponse<List<String>> updateStock(@RequestBody UpdateStock updateStock) {
        logger.info("入参 updateStock:{}", JSON.toJSONString(updateStock));
        return stockService.updateStock(updateStock);

    }

    @ApiOperation(value = "批量更新库存", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stockInfoVO", required = true, dataType = "StockInfoVO")
    })
    @RequestMapping(value = "/api/setting/batchUpdateStock", method = RequestMethod.POST)
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

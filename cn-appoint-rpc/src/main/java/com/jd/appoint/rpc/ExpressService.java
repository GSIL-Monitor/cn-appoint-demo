package com.jd.appoint.rpc;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Converter;
import com.google.common.collect.Lists;
import com.jd.etms.third.jsf.OrderShipsServiceJsf;
import com.jd.etms.third.service.dto.*;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * Created by yangyuan on 6/21/18.
 */
@Service(value = "expressService")
public class ExpressService {

    private static final String PREFIX = "YY";
    private static final String SUCCESS_CODE = "200";
    private static final Integer MAX_ROUTE_SUBSCRIBE_NUM = 200;
    Logger log = LoggerFactory.getLogger(ExpressService.class);
    @Autowired
    private OrderShipsServiceJsf orderShipsServiceJsf;
    @Value(value = "${express.validate.salt}")
    private String validateStr;

    /**
     * 获取所有快递公司
     *
     * @return
     */
    public List<ExpressCheckDto> getAllExpressList() {
        return getAllExpressList(null);
    }

    /**
     * 判断快递公司是否支持
     *
     * @param code 快递公司缩写
     * @return
     */
    public boolean serviceSupport(String code) {
        return getAllExpressList(code).size() > 0;
    }

    private List<ExpressCheckDto> getAllExpressList(String code) {
        WayBillCheckDto result = orderShipsServiceJsf.getAllExpressList(code);
        if (result == null || !SUCCESS_CODE.equals(result.getCode())) {
            log.error("rpc failure.result = {}", JSON.toJSONString(result));
            return Collections.EMPTY_LIST;
        }
        return result.getExpressList();
    }

    /**
     * 获取快递信息
     *
     * @param orderId
     * @return
     */
    public WayBillDto getExpressInfo(Long orderId) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        WayBillPackageDto wayBillPackageDto = orderShipsServiceJsf.getOrderShipsDto(orderId(orderId), encryptData(orderId));
        if (wayBillPackageDto == null || !SUCCESS_CODE.equals(wayBillPackageDto.getCode())) {
            log.info("rpc return failure. result = {}", JSON.toJSONString(wayBillPackageDto));
            return null;
        }
        List<WayBillDto> wayBillDtoList = wayBillPackageDto.getListWaybill();
        return CollectionUtils.isEmpty(wayBillDtoList) ? null : wayBillDtoList.get(0);
    }

    /**
     * 订阅路由信息
     *
     * @param orderId 订单号
     * @param shipId  快递单号
     * @param thirdId 站点ID
     * @return
     */
    public boolean routeSubscribe(Long orderId, String shipId, Integer thirdId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        List<OrderShipsReturnDto> orderShipsReturnDtoList = new ArrayList<>();
        orderShipsReturnDtoList.add(buildOrderShipReturnDto(orderId, shipId, thirdId));
        BaseResult result = orderShipsServiceJsf.insertOrderShips(orderShipsReturnDtoList, encryptData(orderId));
        if (result == null || result.getResultType() != 0) {//0 for success
            log.error("subscribe failure. result = {}", JSON.toJSONString(result));
            return false;
        }
        return true;
    }

    public boolean routeSubscribe(List<RouteDto> routeDtos) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (CollectionUtils.isEmpty(routeDtos)) {
            return true;
        }
        int flag = 0;
        do {
            BaseResult result = orderShipsServiceJsf.insertOrderShips(
                    routeDtos.subList(flag, Math.min(flag + MAX_ROUTE_SUBSCRIBE_NUM, routeDtos.size()))
                            .stream()
                            .map(t -> buildOrderShipReturnDto(t.getOrderId(), t.getShipId(), t.getThirdId()))
                            .collect(Collectors.toList()), encryptData(routeDtos.get(flag).getOrderId()));
            if (result == null || result.getResultType() != 0) {
                log.error("subscribe failure. result = {}", JSON.toJSONString(result));
                return false;
            }
            flag += MAX_ROUTE_SUBSCRIBE_NUM;
        } while (flag < routeDtos.size());
        return true;
    }

    private OrderShipsReturnDto buildOrderShipReturnDto(Long orderId, String shipId, Integer thirdId) {
        OrderShipsReturnDto orderShipsReturnDto = new OrderShipsReturnDto();
        orderShipsReturnDto.setOrderId(orderId(orderId));
        orderShipsReturnDto.setJdOrderType(0);//固定值
        orderShipsReturnDto.setClearOld(1);
        orderShipsReturnDto.setShipCarrierList(getCarrierInfo(orderId, shipId, thirdId));
        return orderShipsReturnDto;
    }


    /**
     * 获取承运人信息
     *
     * @param orderId
     * @param expressId
     * @param thirdId
     * @return
     */
    private List<ShipCarrierReturnDto> getCarrierInfo(Long orderId, String expressId, Integer thirdId) {
        List<ShipCarrierReturnDto> shipCarrierReturnDtoList = new ArrayList<>();
        ShipCarrierReturnDto shipCarrierReturnDto = new ShipCarrierReturnDto();
        shipCarrierReturnDto.setOrderId(orderId(orderId));
        shipCarrierReturnDto.setShipIds(Lists.newArrayList(expressId));
        shipCarrierReturnDto.setThirdId(thirdId);
        //shipCarrierReturnDto.setThirdName("");//文档为必填，可以不填
        shipCarrierReturnDto.setThirdType(16);//useless 固定值
        shipCarrierReturnDto.setFlagOrderType(41);//青龙系统分配
        shipCarrierReturnDtoList.add(shipCarrierReturnDto);
        return shipCarrierReturnDtoList;
    }


    /**
     * 生成青龙平台订单号
     *
     * @param orderId
     * @return
     */
    private String orderId(Long orderId) {
        return PREFIX + orderId;
    }

    /**
     * 获取访问token
     *
     * @param orderId
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private String encryptData(Long orderId) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return URLEncoder.encode(Base64.getEncoder()
                .encodeToString(MessageDigest.getInstance("md5").digest((orderId(orderId) + validateStr)
                        .getBytes("UTF-8"))), "UTF-8");
    }


    public String getExpressCompanyName(Integer thirdId) {
        return getAllExpressList()
                .stream()
                .filter(t -> t.getThirdIdList().contains(String.valueOf(thirdId)))
                .findFirst()
                .map(t -> t.getName())
                .orElse("");
    }
}

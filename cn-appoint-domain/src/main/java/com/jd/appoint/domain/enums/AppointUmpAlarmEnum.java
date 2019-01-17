package com.jd.appoint.domain.enums;

import com.jd.travel.monitor.alerts.UmpAlarmEnum;

/**
 * ump报警信息
 * Created by gaoxiaoqing on 2018/5/8.
 */
public enum AppointUmpAlarmEnum implements UmpAlarmEnum<AppointUmpAlarmEnum> {

    APPOINT_STATUS_MAPPING("appoint.status.mapping", "预约状态映射"),
    GET_FOOT_PRINT("get.foot.print", "获取足迹预约信息"),
    APPOINT_BURIED_POINT("appoint.buried.point", "预约信息埋点"),
    UPDATE_APPOINT_ORDER("update.appoint.order", "修改预约单"),
    APPOINT_ORDER_NOTICE_ES("appoint.order.notice.es", "预约单变更同步ES"),
    QUERY_SHOP_WORK_TIME_FROM_DB("query.shop.work.time.from.db", "从数据库查询服务时间"),
    QUERY_SHOP_WORK_TIME_ITEM_FROM_DB("query.shop.work.time.item.from.db", "从数据库查询服务时间项"),
    APPOINT_ORDER_SOURCE_TYPE("appoint.order.source.type", "预约单来源类型"),
    APPOINT_SERVER_INFO("appoint.server.info","获取服务信息"),
    APPOINT_EXPORT_SETTING("appoint.export.setting","列表导出配置"),

    //产能日历start
    VALIDATE_STOCK_STATUS("validate.stock.status", "校验库存状态"),
    STOCK_DECREASE("stock.decrease", "扣减库存"),
    STOCK_INCREASE("stock.increase", "回冲库存"),
    CHANGE_SCHEDULE("change.schedule", "改期"),
    SCHEDULE_MODEL("schedule.model", "产能日历模式"),
    //产能日历end
    //库存start
    STOCK_UPDATE("stock.update", "更新库存"),
    STOCK_INSERT("stock.insert", "更新库存"),
    STOCK_SET("stock.set", "库存设置"),
    STOCK_CACHE_LESS_THAN_ZERO("stock.cache.less.than.zero", "剩余库存为负数"),
    //库存end

    APPOINT_ORDER_RESCHULE("appoint.order.reschule", "预约单改期"),

    //外部商家接口
    RPC_POP_SHOP_INFO("rpc.pop.shop.info", "POP商家信息接口"),

    VENDER_ORDER_RETRY_TIME_OUT("vender.order.retry.time.out", "商家订单重试提交超时"),


    //短信发送失败的UMP监控
    SMS_ADD_TASK_ERROR("sms.add.task.error", "发送短信任务落地异常"),

    TIME_SHOW_TYPE("time.show.type" , "时间显示模式"),

    ROUTE_SUBSCRIBE_INFO("route.subscribe.info" , "订阅物流信息失败");

    private String key;
    private String message;

    AppointUmpAlarmEnum(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }
}

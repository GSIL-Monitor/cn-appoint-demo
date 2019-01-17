package com.jd.appoint.service.mq.constants;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/26 9:01
 */
public final class CoverColum {
    public final static String APPOINT_ID = "id";
    public final static String APPOINT_ORDER_ID = "appoint_order_id";
    public final static Set<String> APPOINT_TABLE_NAME = new HashSet<String>();
    public final static Set<String> APPOINT_INFO_TABLE_NAME = new HashSet<String>();

    static {
        APPOINT_TABLE_NAME.add("appoint_order");
        APPOINT_INFO_TABLE_NAME.add("appoint_order_form_item");
        APPOINT_INFO_TABLE_NAME.add("appoint_order_service_item");
    }
}
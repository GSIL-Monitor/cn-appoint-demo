package com.jd.appoint.service.mq.msg;

import com.jd.appoint.service.mq.constants.CoverColum;
import com.jd.binlog.client.WaveEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/25 14:49
 */
public class BlakeOrderMsg {
    private static Logger logger = LoggerFactory.getLogger(BlakeOrderMsg.class);
    WaveEntry.RowChange rowChange;
    WaveEntry.Header rowHeader;
    private WaveEntry.EventType eventType;
    private List<WaveEntry.RowData> rowDatas;
    private ColumnBean columnBean;

    public BlakeOrderMsg(WaveEntry.Header rowHeader, WaveEntry.RowChange rowChange) {
        this.rowHeader = rowHeader;
        this.rowChange = rowChange;
        eventType = this.rowChange.getEventType();
        rowDatas = this.rowChange.getRowDatasList();
        this.columnBean = new ColumnBean();
        this.columnBean.setEventType(eventType.name());
        if (rowDatas.size() != 0) {
            //子表操作
            if (CoverColum.APPOINT_INFO_TABLE_NAME.contains(rowHeader.getTableName())) {
                rowDatas.forEach(k -> {
                    k.getAfterColumnsList().
                            forEach(v -> {
                                if (CoverColum.APPOINT_ORDER_ID.equals(v.getName())) {
                                    this.columnBean.addAppointOrderId(v.getValue());
                                }
                            });
                });
            }
            //主表操作
            if (CoverColum.APPOINT_TABLE_NAME.contains(rowHeader.getTableName())) {
                rowDatas.forEach(k -> {
                    k.getAfterColumnsList().
                            forEach(v -> {
                                if (CoverColum.APPOINT_ID.equals(v.getName())) {
                                    this.columnBean.addAppointOrderId(v.getValue());
                                }
                            });
                });
            }

        } else {
            this.columnBean = null;
        }
    }


    /**
     * 获得对应的预约单号
     *
     * @return
     */
    public Set<String> getAppointOrderId() {
        return columnBean.getAppointOrderIdSet();
    }
}


/**
 * 解析到对应的处理类
 */
class ColumnBean {
    private String eventType;
    private Set<String> appointOrderIdSet = new HashSet<>();

    void addAppointOrderId(String appointOrderId) {
        appointOrderIdSet.add(appointOrderId);
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Set<String> getAppointOrderIdSet() {
        return appointOrderIdSet;
    }
}




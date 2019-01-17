package com.jd.appoint.vo.dynamic;

/**
 * Created by shaohongsen on 2018/6/17.
 */
public class OperateItemVo {
    /**
     * 前端展示文案
     */
    private String label;
    /**
     * 确认文案，没有不需要确认
     */
    private String confirmInfo;

    /**
     * CANCEL("取消", 1),
     * CHANGE_SCHEDULE_DATE("改期-日历", 2),
     * CHANGE_SCHEDULE_POINT("改期-时间点", 3),
     * REMARK("备注", 4),
     * CUSTOM("自定义", 0);
     */
    private int operateType;

    /**
     * 0不是批量操作 1:批量操作
     */
    private Integer isBatch;

    /**
     * 自定义操作对应的配置，打卡链接是url,div是divID
     */
    private String customData;
    /**
     * 自定义类型
     * OPEN_URL("打开链接", 0),
     * AJAX_API("调用接口", 1),
     * OPEN_DIV("打开DIV", 2);
     */
    private Integer customType;
    /**
     * 1上门，2到店
     */
    private Integer serverType;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getConfirmInfo() {
        return confirmInfo;
    }

    public void setConfirmInfo(String confirmInfo) {
        this.confirmInfo = confirmInfo;
    }

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public Integer getIsBatch() {
        return isBatch;
    }

    public void setIsBatch(Integer isBatch) {
        this.isBatch = isBatch;
    }

    public String getCustomData() {
        return customData;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }

    public Integer getCustomType() {
        return customType;
    }

    public void setCustomType(Integer customType) {
        this.customType = customType;
    }

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }
}

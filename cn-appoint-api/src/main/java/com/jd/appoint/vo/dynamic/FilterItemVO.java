package com.jd.appoint.vo.dynamic;

/**
 * Created by shaohongsen on 2018/6/17.
 * 筛选项
 */
public class FilterItemVO {
    /**
     * 前端展示文案
     */
    private String label;
    /**
     * 别名
     */
    private String alias;
    /**
     * 框体类型
     * TEXT("文本框", 1),
     * SELECT("下拉选择框", 2),
     * DATE("日期选择框", 3),
     * DOUBLE_DATE("日期双选框", 4),
     * DYNAMIC_SELECT("动态下拉框", 5);
     */
    private int inputType;
    /**
     * 控件内容json
     */
    private String itemData;
    /**
     * 行号
     */
    private int lineNo;
    /**
     * 宽度
     */
    private int width;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getItemData() {
        return itemData;
    }

    public void setItemData(String itemData) {
        this.itemData = itemData;
    }
}

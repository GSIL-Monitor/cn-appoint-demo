package com.jd.appoint.api.vo;

import java.util.List;

/**
 * Created by shaohongsen on 2018/6/11.
 */
public class ProcessConfigVO {
    /**
     * 页面title名称
     */
    private String title;
    /**
     * 提交接口地址
     * 此接口返回200接口通过，正常跳往下一页
     * 返回405前端提示字段校验失败
     * 提交时，当前页面的form表单内容全部提交
     */
    private String submitUrl;
    /**
     * 当前页按钮名称
     */
    private String buttonName;
    /**
     * 当前页面编号
     */
    private String currentPageNo;
    /**
     * 下一个页面编号
     */
    private String nextPageNo;
    /**
     * 分tab页表单控制项
     */
    private List<TabControlVO> tabControlVOList;

    private List<FinishButton> finishButtons;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubmitUrl() {
        return submitUrl;
    }

    public void setSubmitUrl(String submitUrl) {
        this.submitUrl = submitUrl;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(String currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public String getNextPageNo() {
        return nextPageNo;
    }

    public void setNextPageNo(String nextPageNo) {
        this.nextPageNo = nextPageNo;
    }

    public List<TabControlVO> getTabControlVOList() {
        return tabControlVOList;
    }

    public void setTabControlVOList(List<TabControlVO> tabControlVOList) {
        this.tabControlVOList = tabControlVOList;
    }

    public List<FinishButton> getFinishButtons() {
        return finishButtons;
    }

    public void setFinishButtons(List<FinishButton> finishButtons) {
        this.finishButtons = finishButtons;
    }
}

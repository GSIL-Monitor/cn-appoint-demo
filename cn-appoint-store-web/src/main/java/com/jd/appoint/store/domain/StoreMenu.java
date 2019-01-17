package com.jd.appoint.store.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 门店菜单对象
 * Created by wangshangyu on 2017/5/11.
 */
public class StoreMenu implements Serializable {

    private static final long serialVersionUID = -9091814576260806621L;
    /**
     * 菜单id
     */
    private Long menuId;
    /**
     * 菜单级别
     */
    private Integer menuLev;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单链接
     */
    private String menuUrl;
    /**
     * 菜单显示时排序号
     */
    private Integer menuOrder;
    /**
     * 父菜单id
     */
    private Long pMenuid;
    /**
     * 菜单权限码
     */
    private String permCode;
    /**
     * 代表是否展示new标，1：代表不展示new标，2代表展示new标
     */
    private Integer showNewMark;
    /**
     * 代表是否在新的tab打开，1：代表不在新的tab打开，2代表在新的tab打开
     */
    private Integer openInNewTab;
    /**
     * 菜单类型：1：左侧菜单，2：top菜单
     */
    private Integer menuType;
    /**
     * 埋点
     */
    private String clstag;
    /**
     * URL添加随机参数, 1:不加随机参数；2：加随机参数
     */
    private Integer urlAddRandom;

    private List<StoreMenu> subStoreMenu = new ArrayList<StoreMenu>();

    public void addSubStoreMenu(StoreMenu storeMenu) {
        subStoreMenu.add(storeMenu);
    }

    public StoreMenu() {
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Integer getMenuLev() {
        return menuLev;
    }

    public void setMenuLev(Integer menuLev) {
        this.menuLev = menuLev;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Long getpMenuid() {
        return pMenuid;
    }

    public void setpMenuid(Long pMenuid) {
        this.pMenuid = pMenuid;
    }

    public String getPermCode() {
        return permCode;
    }

    public void setPermCode(String permCode) {
        this.permCode = permCode;
    }

    public Integer getShowNewMark() {
        return showNewMark;
    }

    public void setShowNewMark(Integer showNewMark) {
        this.showNewMark = showNewMark;
    }

    public Integer getOpenInNewTab() {
        return openInNewTab;
    }

    public void setOpenInNewTab(Integer openInNewTab) {
        this.openInNewTab = openInNewTab;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public String getClstag() {
        return clstag;
    }

    public void setClstag(String clstag) {
        this.clstag = clstag;
    }

    public Integer getUrlAddRandom() {
        return urlAddRandom;
    }

    public void setUrlAddRandom(Integer urlAddRandom) {
        this.urlAddRandom = urlAddRandom;
    }

    public List<StoreMenu> getSubStoreMenu() {
        return subStoreMenu;
    }

    public void setSubStoreMenu(List<StoreMenu> subStoreMenu) {
        this.subStoreMenu = subStoreMenu;
    }


    @Override
    public String toString() {
        return "StoreMenu{" +
                "menuId=" + menuId +
                ", menuLev=" + menuLev +
                ", menuName='" + menuName + '\'' +
                ", menuUrl='" + menuUrl + '\'' +
                ", menuOrder=" + menuOrder +
                ", pMenuid=" + pMenuid +
                ", permCode='" + permCode + '\'' +
                ", showNewMark=" + showNewMark +
                ", openInNewTab=" + openInNewTab +
                ", menuType=" + menuType +
                ", clstag='" + clstag + '\'' +
                ", urlAddRandom=" + urlAddRandom +
                '}';
    }
}

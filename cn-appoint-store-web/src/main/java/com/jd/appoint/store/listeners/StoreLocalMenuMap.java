package com.jd.appoint.store.listeners;


import com.jd.appoint.store.domain.StoreMenu;

import java.util.List;

/**
 * Description: 本地菜单信息对象
 * Created by: yanghuai.
 * Created DateTime: 2017/5/17 12:34.
 * Project name: pop-o2o-stores.
 */
public class StoreLocalMenuMap {


    private static List<StoreMenu> menus = null;

    public static void setMenus(List<StoreMenu> menus) {
        StoreLocalMenuMap.menus = menus;
    }

    public static List<StoreMenu> getMenus() {
        return StoreLocalMenuMap.menus;
    }


}

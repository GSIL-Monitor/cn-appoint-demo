package com.jd.appoint.store.listeners;


import com.jd.appoint.store.domain.StoreMenu;
import com.jd.appoint.store.service.StoreManageService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * Description: 灾备加载菜单
 * Created by: yanghuai.
 * Created DateTime: 2017/5/17 11:16.
 * Project name: pop-o2o-stores.
 */
public class StoreMenuListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(StoreMenuListener.class);

    public static final String LOCAL_MENU_SERVICE_BEAN_NAME = "storeManageService";

    public static final String JIMDB_MENU_SERVICE_BEAN_NAME = "jimdbStoreMenuService";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        try {

            if (wac == null) {
                logger.error("WebApplicationContext wac 初始化失败");
                return;
            }

            StoreManageService storeManageService = (StoreManageService) wac.getBean(LOCAL_MENU_SERVICE_BEAN_NAME);

            if (storeManageService == null) {
                logger.error("localMenuServiceUsercenter   Bean尚未在spring中进行配置");
                return;
            }

            List<StoreMenu> listStoreMenu = storeManageService.queryAllStoreMenuBySystemType();

            if (CollectionUtils.isEmpty(listStoreMenu)) {
                logger.error("Mehtoud:contextInitialized-->没有加载到任何菜单!");
            } else {
                logger.warn("Mehtoud:contextInitialized-->加载备灾菜单数据成功,共加载了 " + listStoreMenu.size() + " 个菜单数据");
            }

            StoreLocalMenuMap.setMenus(listStoreMenu);

            JimdbStoreMenuService jimdbStoreMenuService = (JimdbStoreMenuService) wac.getBean(JIMDB_MENU_SERVICE_BEAN_NAME);
            if (jimdbStoreMenuService != null && CollectionUtils.isNotEmpty(listStoreMenu)) {
                jimdbStoreMenuService.writeStoreMenu(listStoreMenu);
            }
        } catch (BeansException e) {
            logger.error("Mehtoud:contextInitialized-->菜单初始化出错!:" + e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }


}

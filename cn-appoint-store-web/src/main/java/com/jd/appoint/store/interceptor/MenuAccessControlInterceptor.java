package com.jd.appoint.store.interceptor;

import com.jd.appoint.store.domain.StoreMenu;
import com.jd.appoint.store.listeners.JimdbStoreMenuService;
import com.jd.appoint.store.listeners.StoreLocalMenuMap;
import com.jd.appoint.store.service.StoreManageService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 菜单权限控制拦截器
 * Created by wangshangyu on 2017/2/22.
 */
public class MenuAccessControlInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(MenuAccessControlInterceptor.class);

    private static final String MENU_DATA_KEY = "menu_list";

    @Resource
    private StoreManageService storeManageService;

    @Resource
    private JimdbStoreMenuService jimdbStoreMenuService;


    /**
     * 在Controller方法调用之前进行拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return false-中断拦截，整个请求结束
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();
        String url = request.getContextPath();
        String pin = (String) request.getAttribute("pin");

        logger.info("RequestUri:{}, method:{}, url:{}, pin:{}", requestUri, method, url, pin);

        List<StoreMenu> storeMenuList = storeManageService.loadStoreMenus(pin);
        if(CollectionUtils.isEmpty(storeMenuList)) {
            storeMenuList = StoreLocalMenuMap.getMenus();
            if (CollectionUtils.isEmpty(storeMenuList)) {
                storeMenuList = jimdbStoreMenuService.getStoreMenu();
            }
        }
        request.setAttribute(MENU_DATA_KEY, storeMenuList);
        return true;
    }

    /**
     * “在Controller的方法调用之后” 且 “在DispatcherServlet进行视图的渲染之前” 拦截执行，可以对ModelAndView进行操作
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等;
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}

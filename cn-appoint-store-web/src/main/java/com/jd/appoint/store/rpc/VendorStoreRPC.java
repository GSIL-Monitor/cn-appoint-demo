package com.jd.appoint.store.rpc;

import com.jd.appoint.store.constant.UMPKeyConstants;
import com.jd.appoint.store.domain.StoreMenu;
import com.jd.appoint.store.domain.StoreUserInfo;
import com.jd.jmq.client.producer.MessageProducer;
import com.jd.pop.vender.center.api.domain.Menu;
import com.jd.pop.vender.center.api.domain.enums.MenuSystemTypeEnum;
import com.jd.pop.vender.center.api.service.BusinessCheckService;
import com.jd.pop.vender.center.api.service.MenuService;
import com.jd.pop.wxo2o.spi.lbs.LbsService;
import com.jd.pop.wxo2o.spi.lbs.to.StoreTO;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by wangshangyu on 2017/4/13.
 */
@Service("vendorStoreRpc")
public class VendorStoreRPC {

    private final static Logger logger = LoggerFactory.getLogger(VendorStoreRPC.class);
    @Resource
    private MenuService storeMenuService;
    @Resource
    private LbsService storeLbsService;

    /**
     * 查询门店菜单数据
     *
     * @param pin 门店登录账号
     * @return
     */
    public List<StoreMenu> findStoreMenusByPin(String pin) {
        CallerInfo caller = Profiler.registerInfo(UMPKeyConstants.RPC_VENDORSTORERPC_FINDSTOREMENUSBYPIN, UMPKeyConstants.UMP_APP_NAME, UMPKeyConstants.UMP_DISABLE_HEART, UMPKeyConstants.UMP_ENABLE_TP);

        List<StoreMenu> storeMenuList = new ArrayList<>();
        try {
            List<Menu> menuList = storeMenuService.getVenderMenusByPinAndSystemType(pin, MenuSystemTypeEnum.STORE);
            if (!CollectionUtils.isEmpty(menuList)) {
                for (int i = 0; i < menuList.size(); i++) {
                    storeMenuList.add(STORE_MENU_FUNCTION.apply(menuList.get(i)));
                }
            }
        } catch (Exception e) {
            logger.error("method_Exception RPC_VendorStoreRPC_findStoreMenusByPin(pin={}) throw Exception,cause:{}", pin, e);
            Profiler.functionError(caller);
        } finally {
            Profiler.registerInfoEnd(caller);
        }
        return storeMenuList;
    }

    public List<StoreUserInfo> findStoreInfoByIds(List<Long> storeIds) {
        CallerInfo caller = Profiler.registerInfo(UMPKeyConstants.RPC_VENDORSTORERPC_FINDSTOREINFOBYIDS,
                UMPKeyConstants.UMP_APP_NAME, UMPKeyConstants.UMP_DISABLE_HEART, UMPKeyConstants.UMP_ENABLE_TP);

        List<StoreUserInfo> storeUserInfoList = new ArrayList<>();
        try {
            List<String> strIds = storeIds.stream().map(String::valueOf).collect(Collectors.toList());
            List<StoreTO> storeTOList = storeLbsService.queryByMdIds(strIds);
            if (!CollectionUtils.isEmpty(storeTOList)) {
                storeUserInfoList.add(STORE_USER_INFO_FUNCTION.apply(storeTOList.get(0)));
            }
        } catch (Exception e) {
            logger.error("method_Exception RPC_VendorStoreRPC_findStoreInfoByIds(storeIs={}) throw Exception,cause:{}", storeIds, e);
            Profiler.functionError(caller);
        } finally {
            Profiler.registerInfoEnd(caller);
        }
        return storeUserInfoList;
    }

    private static final Function<Menu, StoreMenu> STORE_MENU_FUNCTION = menu -> {
        logger.info("原始菜单数据：{}", ReflectionToStringBuilder.toString(menu, ToStringStyle.SHORT_PREFIX_STYLE));
        StoreMenu storeMenu = new StoreMenu();
        storeMenu.setMenuId(menu.getMenuId());
        storeMenu.setMenuLev(menu.getLev());
        storeMenu.setMenuName(menu.getName());
        storeMenu.setMenuUrl(menu.getDurl());
        storeMenu.setMenuOrder(menu.getMorder());
        storeMenu.setpMenuid(menu.getPid());
        storeMenu.setPermCode(menu.getPremCode());
        storeMenu.setShowNewMark(menu.getShowNewMark());
        storeMenu.setOpenInNewTab(menu.getOpenInNewTab());
        storeMenu.setMenuType(menu.getMenuType());
        storeMenu.setClstag(menu.getClstag());
        storeMenu.setUrlAddRandom(menu.getUrlAddRandom());
        return storeMenu;
    };
    private static final Function<StoreTO, StoreUserInfo> STORE_USER_INFO_FUNCTION = storeTO -> {
        StoreUserInfo userInfo = new StoreUserInfo();
        userInfo.setStoreId(storeTO.getId());
        userInfo.setVenderId(storeTO.getVenderId());
        userInfo.setName(storeTO.getName());
        userInfo.setAddCode(storeTO.getAddCode());
        userInfo.setAddName(storeTO.getAddName());
        userInfo.setStatus(storeTO.getStatus());
        userInfo.setCreated(storeTO.getCreated());
        userInfo.setModified(storeTO.getModified());
        userInfo.setPhone(storeTO.getPhone());
        userInfo.setType(storeTO.getType());
        userInfo.setCoordinate(storeTO.getCoordinate());

        return userInfo;
    };

    public List<StoreMenu> queryAllStoreMenuBySystemType() {
        CallerInfo caller = Profiler.registerInfo(UMPKeyConstants.RPC_VENDORSTORERPC_QUERYALLSTOREMENUBYSYSTEMTYPE, UMPKeyConstants.UMP_APP_NAME, UMPKeyConstants.UMP_DISABLE_HEART, UMPKeyConstants.UMP_ENABLE_TP);
        List<StoreMenu> storeMenuList = new ArrayList<>();
        try {
            List<Menu> menuList = storeMenuService.getMenusBySystemType(MenuSystemTypeEnum.STORE);
            if (!CollectionUtils.isEmpty(menuList)) {
                for (int i = 0; i < menuList.size(); i++) {
                    storeMenuList.add(STORE_MENU_FUNCTION.apply(menuList.get(i)));
                }
            }
        } catch (Exception e) {
            logger.error("method_Exception RPC_queryAllStoreMenuBySystemType throw Exception,cause:{}", e);
            Profiler.functionError(caller);
        } finally {
            Profiler.registerInfoEnd(caller);
        }
        return storeMenuList;
    }
}

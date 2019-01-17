package com.jd.appoint.store.service;

import com.jd.appoint.store.constant.UMPKeyConstants;
import com.jd.appoint.store.domain.*;
import com.jd.appoint.store.rpc.VendorBasicInfoRPC;
import com.jd.appoint.store.rpc.VendorShopRPC;
import com.jd.appoint.store.rpc.VendorStoreRPC;
import com.jd.fastjson.JSON;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wangshangyu on 2017/3/21.
 */
@Service("storeManageService")
public class StoreManageService  {
    private final static Logger logger = LoggerFactory.getLogger(StoreManageService.class);

    @Resource
    private VendorShopRPC vendorShopRpc;
    @Resource
    private VendorBasicInfoRPC vendorBasicInfoRpc;
    @Resource
    private VendorStoreRPC vendorStoreRpc;
    /**
     * 根据门店登录账号获取门店信息
     *
     * @param vendorId 商家编号
     * @param pin      当前登录账号PIN
     * @return
     */
    public WebStore getStoreInformation(Long vendorId, Long storeId, String pin) {
        CallerInfo caller = Profiler.registerInfo(UMPKeyConstants.SERVICE_STOREMANAGESERVICE_GETSTOREINFORMATION,
                UMPKeyConstants.UMP_APP_NAME, UMPKeyConstants.UMP_DISABLE_HEART, UMPKeyConstants.UMP_ENABLE_TP);

        WebStore webStore = new WebStore();
        try {
            if (vendorId == null || StringUtils.isBlank(pin)) {
                logger.info("method_1 StoreManageService_getStoreInformation(args=[vendorId={},pin={}]) parameter is invalid", vendorId, pin);
                return webStore;
            }

            VendorShop vendorShop = vendorShopRpc.inquireAboutShopInfo(vendorId);
            if (vendorShop == null) {
                logger.info("method_2 StoreManageService_getStoreInformation(args=[vendorId={},pin={}])#vendorShopRpc.inquireAboutShopInfo(vendorId) vendorShop is NULL", vendorId, pin);
                return webStore;
            }

//            StoreUserInfo userInfo = storeUserInfoRCP.getStoreUserInfo(pin);
            StoreUserInfo userInfo = getStoreInfoById(storeId);
            if (userInfo == null) {
                logger.info("method_3 StoreManageService_getStoreInformation(args=[vendorId={},pin={}])#storeUserInfoRCP.getStoreUserInfo(pin) userInfo is NULL", vendorId, pin);
                return webStore;
            }

            VendorVender vender = vendorBasicInfoRpc.inquireAboutVenderInfo(vendorId);
            if (vender == null) {
                logger.info("method_4 StoreManageService_getStoreInformation(args=[vendorId={},pin={}])#vendorBasicInfoRpc.inquireAboutVenderInfo(vendorId) vender is NULL", vendorId, pin);
                return webStore;
            }
            webStore.setStoreId(storeId);
            webStore.setStoreName(userInfo.getName());
            webStore.setVenderId(vendorShop.getVendorId());

            webStore.setShopId(vendorShop.getShopId());
            webStore.setShopName(vendorShop.getShopName());
            webStore.setShopLogo(vendorShop.getShopFullLogoUri());
            Date ccTime = vender.getContractClosingTime();
            webStore.setVendorContractTime((ccTime == null) ? "" : DateFormatUtils.format(ccTime, "yyyy-MM-dd"));
        } catch (Exception e) {
            logger.info("method_6 StoreManageService_getStoreInformation(args=[vendorId={},pin={}]) throw Exception,cause:{}", vendorId, pin, e);
            Profiler.functionError(caller);
        } finally {
            Profiler.registerInfoEnd(caller);
        }
        return webStore;
    }

    /**
     * 加载门店菜单
     *
     * @param pin 当前登录账号PIN
     * @return
     */
    public List<StoreMenu> loadStoreMenus(String pin) {

        List<StoreMenu> storeMenuList = vendorStoreRpc.findStoreMenusByPin(pin);

        return convertMenuHierarchy(storeMenuList);
    }

    public List<StoreMenu> queryAllStoreMenuBySystemType() {

        List<StoreMenu> storeMenuList = vendorStoreRpc.queryAllStoreMenuBySystemType();
        logger.info("===========allStoreMenuList"+ JSON.toJSONString(storeMenuList));
        return convertMenuHierarchy(storeMenuList);
    }

    private List<StoreMenu> convertMenuHierarchy(List<StoreMenu> listStoreMenu) {

        if (CollectionUtils.isEmpty(listStoreMenu)) {
            return new ArrayList<StoreMenu>();
        }

        Map<Long, StoreMenu> menuMap = new HashMap<Long, StoreMenu>();
        List<StoreMenu> listParentMenu = new ArrayList<StoreMenu>();
        List<StoreMenu> listSonMenu = new ArrayList<StoreMenu>();

        for (StoreMenu menu : listStoreMenu) {
            menuMap.put(menu.getMenuId(), menu);
            if (menu.getpMenuid() == 0) {
                listParentMenu.add(menu);
            } else {
                listSonMenu.add(menu);
            }
        }

        if (CollectionUtils.isNotEmpty(listSonMenu)) {
            for (StoreMenu sonMenu : listSonMenu) {
                StoreMenu pStoreMenu = menuMap.get(sonMenu.getpMenuid());
                if (pStoreMenu != null) {
                    pStoreMenu.addSubStoreMenu(sonMenu);
                }
            }
        }
        return listParentMenu;
    }


    public StoreUserInfo getStoreInfoById(Long storeId) {
        List<Long> storeIds = Collections.singletonList(storeId);
        List<StoreUserInfo> infoList = vendorStoreRpc.findStoreInfoByIds(storeIds);
        return CollectionUtils.isEmpty(infoList) ? null : infoList.get(0);
    }

}

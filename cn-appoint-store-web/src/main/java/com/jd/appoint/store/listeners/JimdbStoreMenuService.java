package com.jd.appoint.store.listeners;

import com.alibaba.fastjson.JSONArray;
import com.jd.appoint.store.constant.UMPKeyConstants;
import com.jd.appoint.store.domain.StoreMenu;
import com.jd.jim.cli.Cluster;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Created by: yanghuai.
 * Created DateTime: 2017/5/17 17:17.
 * Project name: pop-o2o-stores.
 */
@Service("jimdbStoreMenuService")
public class JimdbStoreMenuService {

    private static Logger logger = LoggerFactory.getLogger(JimdbStoreMenuService.class);

    private final static String ALL_STORE_MENU_KEY = "all_store_menu_jimdb_key";

    @Resource
    private Cluster storeJimdbClient;

    public List<StoreMenu> getStoreMenu() {
        CallerInfo info = Profiler.registerInfo(UMPKeyConstants.JIMDB_JIMDBSTOREMENUSERVICEIMPL_WRITE, UMPKeyConstants.UMP_APP_NAME, UMPKeyConstants.UMP_DISABLE_HEART, UMPKeyConstants.UMP_ENABLE_TP);
        List<StoreMenu> listStoreMenu = new ArrayList<StoreMenu>();
        try {
            List<String> listMenu = storeJimdbClient.lRange(ALL_STORE_MENU_KEY, 0, -1);
            if (CollectionUtils.isNotEmpty(listMenu)) {
                for (String menu : listMenu) {
                    listStoreMenu.add(JSONArray.parseObject(menu, StoreMenu.class));
                }
            }
        } catch (Exception e) {
            Profiler.functionError(info);
            logger.error("JimdbStoreMenuServiceImpl.getStoreMenu error:{}", e);
        } finally {
            Profiler.registerInfoEnd(info);
        }
        return listStoreMenu;
    }

    public void writeStoreMenu(List<StoreMenu> listStoreMenu) {
        CallerInfo info = Profiler.registerInfo(UMPKeyConstants.JIMDB_JIMDBSTOREMENUSERVICEIMPL_READ, UMPKeyConstants.UMP_APP_NAME, UMPKeyConstants.UMP_DISABLE_HEART, UMPKeyConstants.UMP_ENABLE_TP);
        try {
            if (CollectionUtils.isNotEmpty(listStoreMenu)) {
                storeJimdbClient.del(ALL_STORE_MENU_KEY);
                for (StoreMenu storeMenu : listStoreMenu) {
                    storeJimdbClient.lPush(ALL_STORE_MENU_KEY, JSONArray.toJSONString(storeMenu));
                }
            }
        } catch (Exception e) {
            Profiler.functionError(info);
            logger.error("JimdbStoreMenuServiceImpl.writeStoreMenu data:{} error:{}", listStoreMenu, e);
        } finally {
            Profiler.registerInfoEnd(info);
        }
    }


}

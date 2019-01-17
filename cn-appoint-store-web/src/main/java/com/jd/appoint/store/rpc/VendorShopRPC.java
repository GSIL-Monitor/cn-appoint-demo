package com.jd.appoint.store.rpc;

import com.jd.appoint.store.constant.UMPKeyConstants;
import com.jd.appoint.store.domain.VendorShop;
import com.jd.pop.vender.center.service.shop.ShopSafService;
import com.jd.pop.vender.center.service.shop.dto.BasicShop;
import com.jd.pop.vender.center.service.shop.dto.BasicShopResult;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangshangyu on 2017/3/21.
 */
@Service("vendorShopRpc")
public class VendorShopRPC  {

    private final static Logger logger = LoggerFactory.getLogger(VendorShopRPC.class);

    private final static int SOURCE = 1;
    @Resource
    private ShopSafService shopSafService;

    /**
     * 根据商家编号查询店铺信息
     *
     * @param vendorId 商家编号
     * @return
     */
    public VendorShop inquireAboutShopInfo(Long vendorId) {
        CallerInfo caller = Profiler.registerInfo(UMPKeyConstants.RPC_VENDORSHOPRPC_INQUIREABOUTSHOPINFO,
                UMPKeyConstants.UMP_APP_NAME, UMPKeyConstants.UMP_DISABLE_HEART, UMPKeyConstants.UMP_ENABLE_TP);
        try {
            BasicShopResult basicShopResult = shopSafService.getBasicShopByVenderId(vendorId, null, SOURCE);

            if (basicShopResult == null) {
                logger.info("method_1 VendorShopRPC_inquireAboutShopInfo(args=[vendorId={}]) failed,basicShopResult is NULL", vendorId);
                return null;
            }

            if (basicShopResult.isSuccess()) {
                BasicShop basicShop = basicShopResult.getBasicShop();
                if (basicShop == null) {
                    logger.info("method_2 VendorShopRPC_inquireAboutShopInfo(args=[vendorId={}]) success,but basicShop is NULL", vendorId);
                    return null;
                }

                VendorShop vendorShop = new VendorShop();
                vendorShop.setVendorId(vendorId);
                vendorShop.setShopId(basicShop.getId());
                vendorShop.setShopName(basicShop.getTitle());
                vendorShop.setShopFullLogoUri(basicShop.getFullLogoUri());

                return vendorShop;
            } else {
                logger.info("method_3 VendorShopRPC_inquireAboutShopInfo(args=[vendorId={}]) failed,error message:{}", vendorId, basicShopResult.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("method_4 VendorShopRPC_inquireAboutShopInfo(args=[vendorId={}]) throw Exception,cause:{}", vendorId, e);
            Profiler.functionError(caller);
        } finally {
            Profiler.registerInfoEnd(caller);
        }
        return null;
    }
}

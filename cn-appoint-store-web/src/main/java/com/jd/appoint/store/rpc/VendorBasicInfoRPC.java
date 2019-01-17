package com.jd.appoint.store.rpc;

import com.jd.appoint.store.constant.UMPKeyConstants;
import com.jd.appoint.store.domain.VendorVender;
import com.jd.pop.vender.center.service.vbinfo.VenderBasicSafService;
import com.jd.pop.vender.center.service.vbinfo.dto.VenderBasicResult;
import com.jd.pop.vender.center.service.vbinfo.dto.VenderBasicVO;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wangshangyu on 2017/3/23.
 */
@Service("vendorBasicInfoRpc")
public class VendorBasicInfoRPC {

    private final static Logger logger = LoggerFactory.getLogger(VendorBasicInfoRPC.class);

    private final static int SOURCE = 1;
    @Resource
    private VenderBasicSafService venderBasicSafService;

    /**
     * 根据商家编号获取商家基本信息
     *
     * @param vendorId 商家编号
     * @return
     */
    public VendorVender inquireAboutVenderInfo(Long vendorId) {
        CallerInfo caller = Profiler.registerInfo(UMPKeyConstants.RPC_VENDORBASICINFORPC_INQUIREABOUTVENDERINFO,
                UMPKeyConstants.UMP_APP_NAME, UMPKeyConstants.UMP_DISABLE_HEART, UMPKeyConstants.UMP_ENABLE_TP);
        try {
            VenderBasicResult basicVenderResult = venderBasicSafService.getBasicVenderInfoByVenderId(vendorId, null, SOURCE);

            if (basicVenderResult == null) {
                logger.info("method_1 VendorBasicInfoRPC_inquireAboutVenderInfo(args=[vendorId={}]) failed,basicVenderResult is NULL", vendorId);
                return null;
            }

            if (basicVenderResult.isSuccess()) {
                VenderBasicVO venderBasicVO = basicVenderResult.getVenderBasicVO();
                if (venderBasicVO == null) {
                    logger.info("method_2 VendorBasicInfoRPC_inquireAboutVenderInfo(args=[vendorId={}]) success,but venderBasicVO is NULL", vendorId);
                    return null;
                }

                VendorVender vender = new VendorVender();
                vender.setVendorId(vendorId);
                vender.setCompanyId(venderBasicVO.getCompanyId());
                vender.setCompanyName(venderBasicVO.getCompanyName());
                vender.setContactEmail(venderBasicVO.getEmail());
                vender.setContractStartTime(venderBasicVO.getBeginTime());
                vender.setContractClosingTime(venderBasicVO.getEndTime());
                vender.setVenderBizType(venderBasicVO.getColType());
                vender.setVenderStatus(venderBasicVO.getStatus());
                vender.setShopId(venderBasicVO.getShopId());
                vender.setShopName(venderBasicVO.getShopName());
                vender.setFinancialPeriod(venderBasicVO.getFinancialPer());

                return vender;
            } else {
                logger.info("method_3 VendorBasicInfoRPC_inquireAboutVenderInfo(args=[vendorId={}]) failed,error message:{}", vendorId, basicVenderResult.getErrorMsg());
            }
        } catch (Exception e) {
            logger.info("method_4 VendorBasicInfoRPC_inquireAboutVenderInfo(args=[vendorId={}]) throw Exception,cause:{}", vendorId, e);
            Profiler.functionError(caller);
        } finally {
            Profiler.registerInfoEnd(caller);
        }
        return null;
    }

}

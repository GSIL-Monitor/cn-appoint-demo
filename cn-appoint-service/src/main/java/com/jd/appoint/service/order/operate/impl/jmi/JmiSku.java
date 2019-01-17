package com.jd.appoint.service.order.operate.impl.jmi;

import com.jd.appoint.dao.order.AppointOrderDao;
import com.jd.appoint.domain.order.AppointOrderPO;
import com.jd.appoint.rpc.card.dto.SkuInfoDTO;
import com.jd.appoint.rpc.jmi.sku.RpcJmiSkuService;
import com.jd.appoint.service.order.operate.BaseOperate;
import com.jd.appoint.service.order.operate.OperateResultEnum;
import com.jd.appoint.vo.order.AppointOrderDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JmiSku implements BaseOperate {
    @Autowired
    private AppointOrderDao appointOrderDao;
    @Autowired
    private RpcJmiSkuService rpcJmiSkuService;

    @Override
    public OperateResultEnum execute(AppointOrderDetailVO detailVO) {
        SkuInfoDTO sku = rpcJmiSkuService.getSku(detailVO.getSkuId());
        detailVO.setSkuName(sku.getSkuName());
        AppointOrderPO appointOrderPO = new AppointOrderPO();
        appointOrderPO.setId(detailVO.getId());
        appointOrderPO.setSkuName(sku.getSkuName());
        appointOrderDao.updateSelective(appointOrderPO);
        return OperateResultEnum.SUCCESS;
    }
}

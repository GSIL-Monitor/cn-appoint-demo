package com.jd.appoint.service.config.impl;

import com.jd.appoint.dao.config.OrderListConfigDao;
import com.jd.appoint.domain.config.OrderListConfigPO;
import com.jd.appoint.domain.enums.ServerTypeEnum;
import com.jd.appoint.domain.rpc.ServerTypeInfo;
import com.jd.appoint.service.config.OrderListConfigService;
import com.jd.appoint.service.order.impl.PopConfigServiceImpl;
import com.jd.appoint.vo.dynamic.ServerTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gaoxiaoqing on 2018/6/21.
 */
@Service(value = "orderListConfigService")
public class OrderListConfigServiceImpl implements OrderListConfigService {

    @Autowired
    private OrderListConfigDao orderListConfigDao;
    @Autowired
    private PopConfigServiceImpl popConfigServiceImpl;

    @Override
    public List<OrderListConfigPO> getListItems(String businessCode, Integer serverType, Integer platform) {
        if (platform == null) {
            throw new IllegalArgumentException("platform must not be null");
        }
        List<OrderListConfigPO> list = orderListConfigDao.findByBusinessCodeAndServerTypeAndPlatform(businessCode, serverType, platform);
        return list.stream().filter(orderListConfigPO -> orderListConfigPO.getInList() != null && orderListConfigPO.getInList() == 1)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderListConfigPO> getFilterItems(String businessCode, Integer serverType, Integer platform) {
        if (platform == null) {
            throw new IllegalArgumentException("platform must not be null");
        }
        List<OrderListConfigPO> list = orderListConfigDao.findByBusinessCodeAndServerTypeAndPlatform(businessCode, serverType, platform);
        return list.stream().filter(orderListConfigPO -> orderListConfigPO.getInSearch() != null && orderListConfigPO.getInSearch() == 1)
                .collect(Collectors.toList());
    }

    @Override
    public List<ServerTypeVO> getServerTypes(String businessCode, Integer platform) {
        List<Integer> serverTypes = orderListConfigDao.findDistinctServerType(businessCode, platform);
        ServerTypeInfo serverInfo = popConfigServiceImpl.getServerInfo(businessCode);
        List<ServerTypeVO> result = serverTypes.stream().map(serverType -> createServerTypeVO(serverType, serverInfo)).collect(Collectors.toList());
        return result;
    }

    private ServerTypeVO createServerTypeVO(Integer serverType, ServerTypeInfo serverInfo) {
        ServerTypeVO serverTypeVO = new ServerTypeVO();
        serverTypeVO.setServerType(serverType);
        if (serverInfo != null) {
            if (serverType == ServerTypeEnum.SHANGMEN.getIntValue()) {
                serverTypeVO.setChServerType(serverInfo.getToHomeName());
            } else {
                serverTypeVO.setChServerType(serverInfo.getToShopName());
            }
        } else {
            serverTypeVO.setChServerType(ServerTypeEnum.getFromCode(serverType).getAlias());
        }
        return serverTypeVO;
    }
}

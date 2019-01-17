package com.jd.appoint.inner.man;

import com.jd.appoint.inner.man.dto.ShopBusinessDTO;
import com.jd.appoint.inner.man.dto.ShopBusinessQueryDTO;
import com.jd.appoint.page.Page;

import java.util.List;

/**
 * Created by yangyuan on 5/16/18.
 */
public interface ManShopBusinessFacade {
    /**
     * 分页查询业务配置数据
     * @param request
     * @return
     */
    Page<ShopBusinessDTO> queryShopBusinessOnPage(ShopBusinessQueryDTO request);

    /**
     * 插入业务配置数据
     * @param shopBusinessDTO
     * @return
     */
    Boolean addShopBusiness(ShopBusinessDTO shopBusinessDTO);

    /**
     * 全量编辑业务配置
     * @param shopBusinessDTO
     * @return
     */
    Boolean editShopBusiness(ShopBusinessDTO shopBusinessDTO);

    /**
     * 获取业务配置详情
     * @param id
     * @return
     */
    ShopBusinessDTO getShopBusinessById(long id);

    /**
     * 获得所有状态为有效的业务类型
     * @return
     */
    List<ShopBusinessDTO> getAllAvailable();
}

package com.jd.appoint.service.product;

import com.jd.appoint.domain.product.ProductPO;
import com.jd.appoint.domain.product.ProductQuery;

import java.util.List;

/**
 * Created by yangyuan on 6/14/18.
 */
public interface ProductService {

    ProductPO queryById(Long id);

    ProductPO queryBySkuId(Long skuId);

    boolean update(ProductPO productPO);

    int insert(ProductPO productPO);

    List<ProductPO> queryConditional(ProductQuery productQuery);

    int totalCount(ProductQuery productQuery);

    /**
     * 根据id+ storeId 或 id+ venderId联合查询
     * @param id
     * @param storeId
     * @param venderId
     * @return
     */
    ProductPO queryChecked(Long id, Long storeId, Long venderId);

    boolean exist(Long venderId,Long storeId, Long skuId);

    ProductPO queryByShopIdAndSkuId(Long venderId, Long storeId, Long skuId);

    List<Long> getAllStoreIdBySkuId(Long skuId);

    List<Long> getStoreIdsBySkuIdOnPage(Long skuId, Integer offset, Integer pageSize);

    long getTotalCountBySkuId(Long skuId);

    List<Long> getStoreIdsByVenderIdOnPage(Long venderId, Integer offset, Integer pageSize);

    long getTotalCountByVenderId(Long venderId);

    List<ProductPO> queryByVenderStoreSkuId(Long venderId, Long storeId, Long skuId);
}

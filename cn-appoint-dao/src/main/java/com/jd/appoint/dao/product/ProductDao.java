package com.jd.appoint.dao.product;

import com.jd.appoint.dao.mybatis.MybatisDao;
import com.jd.appoint.dao.mybatis.annotation.MybatisRepository;
import com.jd.appoint.domain.product.ProductPO;
import com.jd.appoint.domain.product.ProductQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yangyuan on 6/14/18.
 */
@MybatisRepository
public interface ProductDao extends MybatisDao<ProductPO> {

    int totalCount(ProductQuery productQuery);

    List<ProductPO> queryConditional(ProductQuery productQuery);

    ProductPO queryChecked(@Param("id")Long id,
                           @Param("storeId")Long storeId,
                           @Param("venderId")Long venderId);

    int countProduct(@Param("venderId")Long venderId,
                     @Param("storeId")Long storeId,
                     @Param("skuId")Long skuId);

    ProductPO queryByShopIdAndSkuId(@Param("venderId")Long venderId,
                                    @Param("storeId")Long storeId,
                                    @Param("skuId")Long skuId);

    /**
     * 获取sku所有的门店id
     * @param skuId
     * @return
     */
    List<Long> getAllStoreIdBySkuId(Long skuId);

    /**
     * 根据skuId获取门店id列表
     * @param skuId
     * @param offset
     * @param pageSize
     * @return
     */
    List<Long> getStoreIdsBySkuIdOnPage(@Param("skuId")Long skuId,
                                        @Param("offset")Integer offset,
                                        @Param("pageSize")Integer pageSize);

    long getTotalCountBySkuId(Long skuId);

    List<Long> getStoreIdsByVenderIdOnPage(@Param("venderId")Long venderId,
                                           @Param("offset")Integer offset,
                                           @Param("pageSize")Integer pageSize);

    long getTotalCountByVenderId(Long venderId);

    ProductPO findBySkuId(Long skuId);

    List<ProductPO> queryByVenderStoreSkuId(@Param("venderId")Long venderId,
                                            @Param("storeId")Long storeId,
                                            @Param("skuId")Long skuId);
}

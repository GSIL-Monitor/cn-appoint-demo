package com.jd.appoint.service.product.impl;


import com.jd.appoint.common.utils.CacheUtils;
import com.jd.appoint.common.utils.RedisCache;
import com.jd.appoint.dao.product.ProductDao;
import com.jd.appoint.domain.product.ProductPO;
import com.jd.appoint.domain.product.ProductQuery;
import com.jd.appoint.service.product.ProductService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * Created by yangyuan on 6/14/18.
 */
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductDao productDao;

    @Resource
    private RedisCache redisCache;

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductPO queryById(Long id){
        return productDao.findById(id);
    }

    @Override
    public ProductPO queryBySkuId(Long skuId) {
        return productDao.findBySkuId(skuId);
    }

    public boolean update(ProductPO productPO){
        ProductPO fromDB = productDao.findById(productPO.getId());
        if(fromDB == null){
            log.info("data not found while update. id = {}", productPO.getId());
            return false;
        }
        clearCache(fromDB);
        return productDao.update(productPO) > 0;
    }

    private void clearCache(ProductPO productPo){
        redisCache.delete(CacheUtils.getProductKey(productPo.getVenderId(), productPo.getStoreId(), productPo.getSkuId()));
        redisCache.delete(CacheUtils.getProductIgnoreStatusKey(productPo.getVenderId(), productPo.getStoreId(), productPo.getSkuId()));
    }

    public int insert(ProductPO productPO){
        if(productPO.getStoreId() == null){
            productPO.setStoreId(-1l);
        }
        return productDao.insert(productPO);
    }

    public List<ProductPO> queryConditional(ProductQuery productQuery){
        Optional<List<ProductPO>> optional = Optional.ofNullable(productDao.queryConditional(productQuery));
        return optional.orElse(Collections.EMPTY_LIST);
    }

    public int totalCount(ProductQuery productQuery){
        return productDao.totalCount(productQuery);
    }

    public ProductPO queryChecked(Long id, Long storeId, Long venderId){
        return productDao.queryChecked(id, storeId, venderId);
    }

    /**
     * 通过sku相关信息获取数据
     * @param venderId
     * @param storeId
     * @param skuId
     * @return
     */
    public ProductPO queryByShopIdAndSkuId(Long venderId, Long storeId, Long skuId){
        ProductPO productPO = redisCache.get(CacheUtils.getProductKey(venderId, storeId, skuId),
                              ProductPO.class);
        if(productPO != null){
            return productPO;
        }
        productPO = productDao.queryByShopIdAndSkuId(venderId, storeId, skuId);
        tocache(productPO);
        return productPO;
    }

    public List<Long> getAllStoreIdBySkuId(Long skuId){
        return productDao.getAllStoreIdBySkuId(skuId);
    }

    public List<Long> getStoreIdsBySkuIdOnPage(Long skuId, Integer offset, Integer pageSize){
        return productDao.getStoreIdsBySkuIdOnPage(skuId, offset, pageSize);
    }

    public long getTotalCountBySkuId(Long skuId){
        return productDao.getTotalCountBySkuId(skuId);
    }

    public List<Long> getStoreIdsByVenderIdOnPage(Long venderId, Integer offset, Integer pageSize){
        return productDao.getStoreIdsByVenderIdOnPage(venderId, offset, pageSize);
    }

    public long getTotalCountByVenderId(Long venderId){
        return productDao.getTotalCountByVenderId(venderId);
    }

    private void tocache(ProductPO productPo){
        if(productPo != null){
            redisCache.setObject(CacheUtils.getProductKey(productPo.getVenderId(), productPo.getStoreId(),
                    productPo.getSkuId()), 10*60, productPo);
        }
    }

    public boolean exist(Long venderId, Long storeId, Long skuId){
        if(venderId == null && storeId == null){
            throw new IllegalArgumentException("both param is null.");
        }
        return productDao.countProduct(venderId, storeId, skuId) > 0;
    }

    @Override
    public List<ProductPO> queryByVenderStoreSkuId(Long venderId, Long storeId, Long skuId) {
        String key = CacheUtils.getProductIgnoreStatusKey(venderId, storeId, skuId);
        List<ProductPO> productPOs = redisCache.get(key , List.class);
        if(CollectionUtils.isNotEmpty(productPOs)){
            //return productPOs;
        }
        productPOs = productDao.queryByVenderStoreSkuId(venderId, storeId, skuId);
        if(CollectionUtils.isNotEmpty(productPOs)){
            redisCache.setObject(key, 10*60, productPOs);
        }
        return productPOs;
    }
}

package com.jd.appoint.service.shop.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jd.appoint.api.vo.FormControlQuery;
import com.jd.appoint.common.utils.CacheUtils;
import com.jd.appoint.common.utils.RedisCache;
import com.jd.appoint.dao.shop.ShopBusinessDao;
import com.jd.appoint.domain.enums.StatusEnum;
import com.jd.appoint.domain.shop.ShopBusinessPO;
import com.jd.appoint.domain.shop.ShopFormControlItemPO;
import com.jd.appoint.domain.shop.query.FormControlItemQuery;
import com.jd.appoint.domain.shop.query.ShopBusinessQuery;
import com.jd.appoint.service.shop.ShopBusinessService;
import com.jd.appoint.service.shop.ShopFormControlItemService;
import com.jd.jsf.gd.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by yangyuan on 5/14/18.
 */
@Service
public class ShopBusinessServiceImpl implements ShopBusinessService {

    private static final Logger log = LoggerFactory.getLogger(ShopBusinessServiceImpl.class);

    @Autowired
    private ShopBusinessDao shopBusinessDao;

    @Autowired
    private ShopFormControlItemService shopFormControlItemService;

    @Resource
    private RedisCache redisCache;

    @Override
    public ShopBusinessPO queryByCondition(FormControlItemQuery formControlItemQuery) {
        ShopBusinessPO sb = redisCache.get(CacheUtils.getFormControlKey(formControlItemQuery.getBusinessCode()), ShopBusinessPO.class);
        if (sb != null && CollectionUtils.isNotEmpty(sb.getItemList())) {
            sb.setItemList(filterControlItem(formControlItemQuery, sb.getItemList()));
            return sb;
        }
        sb = shopBusinessDao.queryByCode(formControlItemQuery.getBusinessCode());
        if (sb != null) {
            sb.setItemList(shopFormControlItemService.queryByBusinessCode(formControlItemQuery.getBusinessCode()));
            toCache(sb);
        } else {
            return null;
        }
        sb.setItemList(filterControlItem(formControlItemQuery, sb.getItemList()));//filter target item
        return sb;
    }

    /**
     * filter controlItems in memory
     *
     * @param formControlItemQuery
     * @param target
     * @return
     */
    private List<ShopFormControlItemPO> filterControlItem(FormControlItemQuery formControlItemQuery, List<ShopFormControlItemPO> target) {
        List<ShopFormControlItemPO> midList = target.stream().filter(t -> {
            if (StringUtils.isBlank(formControlItemQuery.getPageNo())) {
                return true;
            } else {
                return formControlItemQuery.getPageNo().equals(t.getPageNo());
            }
        }).filter(t -> {
            if (formControlItemQuery.getOnSiteDisplay() == null) {
                return true;
            } else {
                return formControlItemQuery.getOnSiteDisplay() == t.getOnSiteDisplay();
            }
        }).filter(t -> {
            if (formControlItemQuery.getToShopDisplay() == null) {
                return true;
            } else {
                return formControlItemQuery.getToShopDisplay() == t.getToShopDisplay();
            }
        }).collect(Collectors.toList());
        List<ShopFormControlItemPO> result = midList.stream().filter(t -> {
            if (formControlItemQuery.getVenderId() == null) {
                return true;
            } else {
                return formControlItemQuery.getVenderId().equals(t.getVenderId());
            }
        }).collect(Collectors.toList());
        return CollectionUtils.isNotEmpty(result) ? result : midList.stream().filter(t -> {
            if (t.getVenderId() == null || t.getVenderId() <= 0) {
                return true;//default item
            }
            return false;
        }).collect(Collectors.toList());
    }


    private void toCache(ShopBusinessPO shopBusinessPO) {
        if (shopBusinessPO != null) {
            redisCache.setObject(CacheUtils.getFormControlKey(shopBusinessPO.getCode()), 60 * 60, shopBusinessPO);
        }
    }

    @Override
    public ShopBusinessPO queryById(long id) {
        return shopBusinessDao.findById(id);
    }

    @Override
    @Transactional
    public int insert(ShopBusinessPO shopBusinessPO) {
        int id = shopBusinessDao.insert(shopBusinessPO);
        if (CollectionUtils.isNotEmpty(shopBusinessPO.getItemList())) {
            shopFormControlItemService.batchInsert(shopBusinessPO.getItemList());
        }
        return id;
    }

    @Override
    @Transactional
    public boolean edit(ShopBusinessPO shopBusinessPO) {
        if (shopBusinessPO == null) {
            return true;
        }
        shopBusinessDao.update(shopBusinessPO);
        updateItems(shopBusinessPO);
        deleteItems(shopBusinessPO);
        clearCache(shopBusinessPO.getCode());
        return true;
    }

    private void clearCache(String businessCode) {
        redisCache.delete(CacheUtils.getFormControlKey(businessCode));
    }

    /**
     * 更新item，存在更新，不存在新增
     *
     * @param shopBusinessPO
     */
    private void updateItems(ShopBusinessPO shopBusinessPO) {
        if (CollectionUtils.isNotEmpty(shopBusinessPO.getItemList())) {
            shopBusinessPO.getItemList().stream().forEach(s -> dealUpdateItem(s));
        }
    }

    /**
     * 更新ShopFormControlItem
     *
     * @param shopFormControlItemPO
     */
    private void dealUpdateItem(ShopFormControlItemPO shopFormControlItemPO) {
        Preconditions.checkNotNull(shopFormControlItemPO);
        if (shopFormControlItemPO.getId() == null) {//not exist then add
            shopFormControlItemService.insert(shopFormControlItemPO);
        } else {
            shopFormControlItemService.update(shopFormControlItemPO);
        }
    }

    /**
     * 删除数据库中存在而shopBusiness不存在的item
     *
     * @param shopBusinessPO
     */
    private void deleteItems(ShopBusinessPO shopBusinessPO) {
        List<ShopFormControlItemPO> itemPOList = shopFormControlItemService.queryByBusinessCode(shopBusinessPO.getCode());
        if (CollectionUtils.isNotEmpty(itemPOList)) {
            itemPOList.stream()
                    .filter(s -> !isExist(s, shopBusinessPO.getItemList()))
                    .forEach(t -> shopFormControlItemService.delete(t.getId()));
        }
    }

    /**
     * itemPO 是否存在itemPOList中
     *
     * @param itemPO
     * @param itemPOList
     * @return
     */
    private boolean isExist(ShopFormControlItemPO itemPO, List<ShopFormControlItemPO> itemPOList) {
        if (CollectionUtils.isEmpty(itemPOList)) {
            return false;
        }
        return itemPOList.stream().anyMatch(t -> {
            if (t.getId() != null && itemPO.getId() != null && t.getId().equals(itemPO.getId())) {
                return true;
            }
            return false;
        });
    }

    public List<ShopBusinessPO> queryOnPage(ShopBusinessQuery shopBusinessQuery) {
        List<ShopBusinessPO> list = shopBusinessDao.queryOnPage(shopBusinessQuery);
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    public List<ShopBusinessPO> getAllAvailable() {
        return shopBusinessDao.queryAll(StatusEnum.ENABLE);
    }

    public int totalCount(ShopBusinessQuery shopBusinessQuery) {
        return shopBusinessDao.totalCount(shopBusinessQuery);
    }
}


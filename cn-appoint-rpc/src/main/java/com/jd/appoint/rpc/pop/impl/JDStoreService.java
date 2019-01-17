package com.jd.appoint.rpc.pop.impl;

import com.google.common.collect.Lists;
import com.jd.fastjson.JSON;
import com.jd.pop.wxo2o.spi.Paginate;
import com.jd.pop.wxo2o.spi.lbs.LbsService;
import com.jd.pop.wxo2o.spi.lbs.to.StoreTO;
import com.jd.pop.wxo2o.spi.lbs.to.VenderStoreInfoQueryTO;
import com.jd.pop.wxo2o.spi.lbs.to.VenderStoreTO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by yangyuan on 6/28/18.
 */
@Service
public class JDStoreService {

    private final static Logger log = LoggerFactory.getLogger(JDStoreService.class);

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private LbsService lbsService;

    private static final int MAX_PAGE_SIZE = 30;


    /**
     * 分页获取商家店铺数据
     *
     * @param venderId
     * @param page
     * @param pageSize
     * @return
     */
    public Paginate<VenderStoreTO> getStoreOnPage(Long venderId, int page, int pageSize) {
        VenderStoreInfoQueryTO venderStoreInfoQueryTO = new VenderStoreInfoQueryTO();
        venderStoreInfoQueryTO.setVenderId(venderId);
        log.info("调用lbsService.queryStoreInfo接口，查询门店列表入参：venderStoreInfoQueryTO={}，page={}，pageSize={}", JSON.toJSONString(venderStoreInfoQueryTO),page,pageSize);
        return lbsService.queryStoreInfo(venderStoreInfoQueryTO, page, pageSize);
    }

    /**
     * 根据门店id列表获取门店详情数据
     * @param storeIds
     * @return
     */
    public List<StoreTO> getStoresInfo(List<String> storeIds) {
        try {
            return lbsService.queryByMdIds(storeIds);
        } catch (Exception exc) {
            log.info("rpc call error.", exc);
            return Collections.EMPTY_LIST;
        }
    }


    /**
     * 根据商家id获取所有门店数据
     *
     * @param venderId
     * @return
     */
    public List<VenderStoreTO> getAllStore(Long venderId) {
        VenderStoreInfoQueryTO venderStoreInfoQueryTO = new VenderStoreInfoQueryTO();
        venderStoreInfoQueryTO.setVenderId(venderId);
        Paginate<VenderStoreTO> firstPage = null;//lbsService.queryStoreInfo(venderStoreInfoQueryTO, 1, MAX_PAGE_SIZE);
        if (firstPage == null) {
            return Collections.emptyList();
        }
        List<VenderStoreTO> venderStoreTOList = new ArrayList();
        venderStoreTOList.addAll(firstPage.getItemList());
        if (firstPage.getTotalPage() > 1) {
            venderStoreTOList.addAll(getExtraStores(2, firstPage.getTotalPage()));
        }
        return venderStoreTOList;
    }

    /**
     * 获取第一页之后的额外数据
     *
     * @param beginPage
     * @param totalPage
     * @return
     */
    private List<VenderStoreTO> getExtraStores(int beginPage, int totalPage) {
        List<Future<List<VenderStoreTO>>> futures = Lists.newArrayList();
        do {
            futures.add(threadPoolTaskExecutor.submit(new DataTask(beginPage)));
        } while (++beginPage <= totalPage);
        List<VenderStoreTO> venderStoreTOList = new ArrayList();
        futures.stream().forEach(t -> {
            try {
                venderStoreTOList.addAll(t.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error("error.", e);
            }
        });
        return venderStoreTOList;
    }

    class DataTask implements Callable<List<VenderStoreTO>> {

        //请求页数
        private int page;

        public DataTask(int page) {
            this.page = page;
        }

        @Override
        public List<VenderStoreTO> call() throws Exception {
            VenderStoreInfoQueryTO venderStoreInfoQueryTO = new VenderStoreInfoQueryTO();
            Paginate<VenderStoreTO> storePage = lbsService.queryStoreInfo(venderStoreInfoQueryTO, page, MAX_PAGE_SIZE);
            if (storePage != null && CollectionUtils.isNotEmpty(storePage.getItemList())) {
                return storePage.getItemList();
            }
            return Collections.EMPTY_LIST;
        }
    }


}

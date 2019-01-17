package com.jd.appoint.service.product;

import com.jd.appoint.shopapi.ShopProductFacade;
import com.jd.appoint.shopapi.vo.ProductQueryVO;
import com.jd.appoint.shopapi.vo.ProductVO;
import com.jd.appoint.shopapi.vo.SkuOperateRequest;
import com.jd.appoint.shopapi.vo.SyncSkuRequest;
import com.jd.appoint.storeapi.StoreProductFacade;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.validate.ValidatorException;
import org.junit.Test;
import webJunit.JUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangyuan on 6/15/18.
 */
public class StoreProductFacadeTest extends JUnit4SpringContextTests {

    @Resource
    private ShopProductFacade shopProductFacade;

    @Resource
    private StoreProductFacade storeProductFacade;

    @Resource
    private ProductService productService;

    @Test
    public void testSyncStoreSku() throws InterruptedException {
        SoaRequest<SyncSkuRequest> requestSoaRequest = new SoaRequest<>();
        SyncSkuRequest syncSkuRequest = new SyncSkuRequest();
        syncSkuRequest.setVenderId(60461l);
        syncSkuRequest.setStoreId(129802l);
        syncSkuRequest.setBusinessType(101);
        syncSkuRequest.setClientIp("127.0.0.1");
        syncSkuRequest.setPort(8080);
        requestSoaRequest.setData(syncSkuRequest);
        requestSoaRequest.setTraceId("234542ertrerte33");
        storeProductFacade.syncStoreProduct(requestSoaRequest);
    }

    @Test
    public void testSyncVenderSku(){
        SoaRequest<SyncSkuRequest> requestSoaRequest = new SoaRequest<>();
        SyncSkuRequest syncSkuRequest = new SyncSkuRequest();
        syncSkuRequest.setVenderId(67344l);
        syncSkuRequest.setBusinessType(101);
        syncSkuRequest.setClientIp("127.0.0.1");
        syncSkuRequest.setPort(8080);
        //syncSkuRequest.setStoreId(3123l);
        requestSoaRequest.setData(syncSkuRequest);
        requestSoaRequest.setTraceId("234542ertrerte33");
        shopProductFacade.syncProduct(requestSoaRequest);
    }

    @Test
    public void testQueryOne(){
        SoaRequest<SkuOperateRequest> requestSoaRequest = new SoaRequest<>();
        SkuOperateRequest data = new SkuOperateRequest();
        data.setId(1l);
        data.setShopId(123l);
        System.out.println(shopProductFacade.getOne(requestSoaRequest));
    }

    @Test
    public void testQueryOne2(){
        SoaRequest<SkuOperateRequest> requestSoaRequest = new SoaRequest<>();
        SkuOperateRequest data = new SkuOperateRequest();
        data.setId(1l);
        data.setShopId(333l);
        System.out.println(storeProductFacade.getOne(requestSoaRequest));
    }

    @Test
    public void testSearch() throws ValidatorException {
        ProductQueryVO productQueryVO = new ProductQueryVO();
        productQueryVO.setBusinessCode("101");
        productQueryVO.setShopId(60461l);
        productQueryVO.setPageSize(10);
        productQueryVO.setPageNum(1);
       // productQueryVO.setSkuId(10000994139l);
        SoaRequest<ProductQueryVO> request = new SoaRequest<>();
        request.setData(productQueryVO);
        System.out.println(shopProductFacade.search(request));
    }

    @Test
    public void testSearch2() throws ValidatorException {
        ProductQueryVO productQueryVO = new ProductQueryVO();
        productQueryVO.setBusinessCode("1001");
        productQueryVO.setShopId(333l);
        productQueryVO.setSkuId(10000994139l);
        SoaRequest<ProductQueryVO> request = new SoaRequest<>();
        request.setData(productQueryVO);
        System.out.println(storeProductFacade.search(request));
    }

    @Test
    public void testUpdate(){
        SoaRequest<ProductVO> request = new SoaRequest<>();
        ProductVO productVO = new ProductVO();
        productVO.setId(297l);
        productVO.setSkuId(70090l);
        request.setData(productVO);
        shopProductFacade.update(request);

    }

    @Test
    public void  test11(){
        System.out.println(productService.getAllStoreIdBySkuId(10000994139l));
    }

    @Test
    public void testOnpage(){
        System.out.println(productService.getStoreIdsByVenderIdOnPage(20160613l, 0, 5));
    }

    @Test
    public void testOnpage2(){
        System.out.println(productService.getStoreIdsBySkuIdOnPage(2018061802l, 0, 5));
    }
}


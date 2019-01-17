package com.jd.appoint.service.innner.man;

import com.alibaba.fastjson.JSON;
import com.jd.appoint.inner.man.ManShopBusinessFacade;
import com.jd.appoint.inner.man.dto.FormControlItemDTO;
import com.jd.appoint.inner.man.dto.ShopBusinessDTO;
import com.jd.appoint.inner.man.dto.ShopBusinessQueryDTO;
import com.jd.travel.base.soa.SoaRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import webJunit.JUnit4SpringContextTests;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyuan on 5/16/18.
 */
public class InnerShopBusinessFacadeTest extends JUnit4SpringContextTests {

    @Autowired
    private ManShopBusinessFacade innerShopBusinessFacade;

    @Test
    public void testQuery(){
        SoaRequest<ShopBusinessQueryDTO> request = new SoaRequest();
        request.setData(new ShopBusinessQueryDTO());
        System.out.println(JSON.toJSONString(innerShopBusinessFacade.queryShopBusinessOnPage(new ShopBusinessQueryDTO())));
    }

    @Test
    public void testAdd(){
        ShopBusinessDTO shopBusinessDTO = new ShopBusinessDTO();

        shopBusinessDTO.setToShop(false);
        shopBusinessDTO.setName("体检");
        shopBusinessDTO.setCode("1111");
        shopBusinessDTO.setOnSite(false);
        shopBusinessDTO.setToShop(true);
        List<FormControlItemDTO> list = new ArrayList<>();
        FormControlItemDTO dto1 = new FormControlItemDTO();
        dto1.setName("姓名");
        dto1.setAlias("name");
        dto1.setBusinessCode(shopBusinessDTO.getCode());
        dto1.setNeedInput(true);
        dto1.setOnSiteDisplay(true);
        dto1.setEncryptType(1);
        dto1.setPriority(1);
        dto1.setItemType(1);
        dto1.setOrderField(true);
        list.add(dto1);
        FormControlItemDTO dto2 = new FormControlItemDTO();
        dto2.setName("性别");
        dto2.setAlias("gender_forbid");
        dto2.setBusinessCode(shopBusinessDTO.getCode());
        dto2.setNeedInput(true);
        dto2.setOnSiteDisplay(true);
        dto2.setEncryptType(1);
        dto2.setPriority(1);
        dto2.setItemType(2);
        dto2.setItemData("1:男,1:女");
        dto2.setOrderField(true);
        list.add(dto2);
        shopBusinessDTO.setControlItemDTOList(list);
        SoaRequest<ShopBusinessDTO> request = new SoaRequest<>();
        request.setData(shopBusinessDTO);
        System.out.println(innerShopBusinessFacade.addShopBusiness(shopBusinessDTO));
    }

    @Test
    public void testEdit(){
        ShopBusinessDTO shopBusinessDTO = new ShopBusinessDTO();
        shopBusinessDTO.setId(1l);
        shopBusinessDTO.setToShop(false);
        shopBusinessDTO.setName("服装");
        shopBusinessDTO.setCode("618618");
        shopBusinessDTO.setOnSite(false);
        List<FormControlItemDTO> list = new ArrayList<>();
        FormControlItemDTO dto1 = new FormControlItemDTO();//update
        dto1.setId(8l);
        dto1.setName("姓名");
        dto1.setAlias("name");
        dto1.setBusinessCode(shopBusinessDTO.getCode());
        dto1.setNeedInput(true);
        dto1.setOnSiteDisplay(true);
        dto1.setEncryptType(1);
        dto1.setPriority(1);
        list.add(dto1);
        FormControlItemDTO add = new FormControlItemDTO();//update
        add.setName("性别");
        add.setAlias("gender");
        add.setBusinessCode(shopBusinessDTO.getCode());
        add.setNeedInput(true);
        add.setOnSiteDisplay(true);
        add.setEncryptType(1);
        add.setPriority(1);
        list.add(add);
        shopBusinessDTO.setControlItemDTOList(list);
        SoaRequest<ShopBusinessDTO> request = new SoaRequest<>();
        request.setData(shopBusinessDTO);
        System.out.println(innerShopBusinessFacade.editShopBusiness(shopBusinessDTO));
    }

    @Test
    public void testDetail(){
        System.out.println(innerShopBusinessFacade.getShopBusinessById(24l));

    }
}

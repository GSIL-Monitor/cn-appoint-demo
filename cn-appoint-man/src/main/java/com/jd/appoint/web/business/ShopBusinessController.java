package com.jd.appoint.web.business;


import com.google.common.collect.Lists;
import com.jd.adminlte4j.model.UIModel;
import com.jd.adminlte4j.model.builder.TableBuilder;
import com.jd.appoint.inner.man.ManShopBusinessFacade;
import com.jd.appoint.inner.man.dto.FormControlItemDTO;
import com.jd.appoint.inner.man.dto.ShopBusinessDTO;
import com.jd.appoint.inner.man.dto.ShopBusinessQueryDTO;
import com.jd.appoint.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by yangyuan on 5/17/18.
 */
@Controller
@RequestMapping("/shop_biz")
public class ShopBusinessController {

    private static Logger log = LoggerFactory.getLogger(ShopBusinessController.class);

    @Autowired
    private ManShopBusinessFacade innerShopBusinessFacade;

    @RequestMapping(value = "/list")
    @ResponseBody
    public UIModel list(ShopBusinessQueryDTO shopBusinessQueryDTO) {
        try {
            Page page = innerShopBusinessFacade.queryShopBusinessOnPage(shopBusinessQueryDTO);
            return UIModel
                    .success()
                    .tableData(TableBuilder.newBuilder(ShopBusinessDTO.class)
                            .data(page.getList())
                            .pageSize(page.getPageSize())
                            .totalSize((int) page.getTotalCount()).build());
        } catch (Exception exc) {
            log.error("list error:", exc);
            return null;
        }
    }

    @RequestMapping(value = "/detail")
    @ResponseBody
    public UIModel detail(@RequestParam(value = "id", required = false) Integer id) {
        ShopBusinessDTO shopBusinessDTO;
        if (id == null) {//add
            shopBusinessDTO = new ShopBusinessDTO();
            shopBusinessDTO.setControlItemDTOList(getDefaultControlItems());
        } else {//edit
            shopBusinessDTO = innerShopBusinessFacade.getShopBusinessById(id);
        }
        return UIModel
                .success()
                .formData(shopBusinessDTO)
                .tableData(TableBuilder.newBuilder(FormControlItemDTO.class)
                        .data(shopBusinessDTO.getControlItemDTOList()));
    }

    /**
     * 固定配置
     * @return
     */
    private List<FormControlItemDTO> getDefaultControlItems(){
        List<FormControlItemDTO> defaultControlItems = Lists.newArrayList();
        defaultControlItems.add(new FormControlItemDTO.Builder("姓名","name")
                .priority(1)
                .forSelect(true)
                .needInput(true)
                .onSiteDisplay(true)
                .build());
        defaultControlItems.add(new FormControlItemDTO.Builder("手机号码","phone")
                .priority(2)
                .forSelect(true)
                .needInput(true)
                .onSiteDisplay(true)
                .build());
        return defaultControlItems;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public UIModel save(@RequestBody ShopBusinessDTO shopBusinessDTO) {
        Optional<ShopBusinessDTO> shopBusiness = Optional.ofNullable(shopBusinessDTO);
        boolean result = shopBusiness.map(s -> {
            if (s.getId() == null) {
                log.info("add shopbusiness");
                return innerShopBusinessFacade.addShopBusiness(s);
            } else {
                log.info("edit business ");
                return innerShopBusinessFacade.editShopBusiness(s);
            }
        }).orElse(false);
        if(result){
            return UIModel.success();
        }
        return UIModel.fail();
    }

}

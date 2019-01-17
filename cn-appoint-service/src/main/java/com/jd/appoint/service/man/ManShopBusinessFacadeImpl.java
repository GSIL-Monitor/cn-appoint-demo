package com.jd.appoint.service.man;

import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jd.appoint.domain.enums.EncryptTypeEnum;
import com.jd.appoint.domain.enums.ItemTypeEnum;
import com.jd.appoint.domain.shop.ShopBusinessPO;
import com.jd.appoint.domain.shop.ShopFormControlItemPO;
import com.jd.appoint.domain.shop.query.ShopBusinessQuery;
import com.jd.appoint.inner.man.ManShopBusinessFacade;
import com.jd.appoint.inner.man.dto.FormControlItemDTO;
import com.jd.appoint.inner.man.dto.ShopBusinessDTO;
import com.jd.appoint.inner.man.dto.ShopBusinessQueryDTO;
import com.jd.appoint.page.Page;
import com.jd.appoint.service.shop.ShopBusinessService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by yangyuan on 5/16/18.
 */
@Service
public class ManShopBusinessFacadeImpl implements ManShopBusinessFacade {

    private static final Logger log = LoggerFactory.getLogger(ManShopBusinessFacadeImpl.class);

    @Autowired
    private ShopBusinessService shopBusinessService;

    @Override
    public Page<ShopBusinessDTO> queryShopBusinessOnPage(ShopBusinessQueryDTO request) {
        checkNotNull(request);
        ShopBusinessQuery shopBusinessQuery = new ShopBusinessQueryDTOConvert().convert(request);
        List<ShopBusinessPO> shopBusinessPOList = shopBusinessService.queryOnPage(shopBusinessQuery);
        Page<ShopBusinessDTO> result = new Page<>();
        result.setList(new ArrayList<>(Lists.transform(shopBusinessPOList, s -> new ShopBusinessPOConvert()
                .convert(s))));
        result.setPageNumber(request.getPageNo());
        result.setPageSize(request.getPageSize());
        result.setTotalCount(shopBusinessService.totalCount(shopBusinessQuery));
        return result;
    }

    @Override
    public Boolean addShopBusiness(ShopBusinessDTO shopBusinessDTO){
        checkNotNull(shopBusinessDTO);
        try {
            setBusinessCodeForEachItem(shopBusinessDTO);
            return shopBusinessService.insert(new ShopBusinessPOConvert().reverse().convert(shopBusinessDTO)) > 0;
        }catch (Exception exc){
            log.error("add error.", exc);
            return false;
        }
    }

    public Boolean editShopBusiness(ShopBusinessDTO shopBusinessDTO){
        checkNotNull(shopBusinessDTO);
        try {
            setBusinessCodeForEachItem(shopBusinessDTO);
            return shopBusinessService.edit(new ShopBusinessPOConvert().reverse().convert(shopBusinessDTO));
        }catch (Exception exc){
            log.error("edit error.", exc);
            return false;
        }
    }

    private void setBusinessCodeForEachItem(ShopBusinessDTO shopBusinessDTO){
        if(CollectionUtils.isNotEmpty(shopBusinessDTO.getControlItemDTOList())){
            shopBusinessDTO.getControlItemDTOList().stream().forEach(s -> s.setBusinessCode(shopBusinessDTO.getCode()));
        }
    }

    public ShopBusinessDTO getShopBusinessById(long id){
        Optional<ShopBusinessPO> shopBusinessPo = Optional.ofNullable(shopBusinessService.queryById(id));
        return shopBusinessPo.map(s -> new ShopBusinessPOConvert().convert(s)).orElse(null);
    }


    private static class ShopBusinessQueryDTOConvert extends Converter<ShopBusinessQueryDTO, ShopBusinessQuery>{

        @Override
        protected ShopBusinessQuery doForward(ShopBusinessQueryDTO shopBusinessQueryDTO) {
            ShopBusinessQuery shopBusinessQuery = new ShopBusinessQuery();
            BeanUtils.copyProperties(shopBusinessQueryDTO, shopBusinessQuery);
            return shopBusinessQuery;
        }

        @Override
        protected ShopBusinessQueryDTO doBackward(ShopBusinessQuery shopBusinessQuery) {
            throw new RuntimeException("method not implement");
        }
    }

    private static class ShopBusinessPOConvert extends Converter<ShopBusinessPO, ShopBusinessDTO>{
        @Override
        protected ShopBusinessDTO doForward(ShopBusinessPO shopBusinessPO) {
            ShopBusinessDTO shopBusinessDTO = new ShopBusinessDTO();
            BeanUtils.copyProperties(shopBusinessPO, shopBusinessDTO);
            shopBusinessDTO.setOnSite(shopBusinessPO.isOnSite());
            shopBusinessDTO.setToShop(shopBusinessPO.isToShop());
            if(CollectionUtils.isNotEmpty(shopBusinessPO.getItemList())) {
                shopBusinessDTO.setControlItemDTOList(new ArrayList<>(Lists.transform(shopBusinessPO.getItemList(),
                        s -> new FormControlItemDTOConvert().reverse().convert(s))));
            }
            return shopBusinessDTO;
        }

        @Override
        protected ShopBusinessPO doBackward(ShopBusinessDTO shopBusinessDTO) {
            ShopBusinessPO shopBusinessPO = new ShopBusinessPO();
            BeanUtils.copyProperties(shopBusinessDTO,shopBusinessPO);
            shopBusinessPO.setOnSite(shopBusinessDTO.isOnSite());
            shopBusinessPO.setToShop(shopBusinessDTO.isToShop());
            if(CollectionUtils.isNotEmpty(shopBusinessDTO.getControlItemDTOList())) {
                shopBusinessPO.setItemList(new ArrayList<>(Lists.transform(shopBusinessDTO.getControlItemDTOList(),
                        s -> new FormControlItemDTOConvert().convert(s))));
            }
            return shopBusinessPO;
        }
    }

    private static class FormControlItemDTOConvert extends Converter<FormControlItemDTO, ShopFormControlItemPO>{
        @Override
        protected ShopFormControlItemPO doForward(FormControlItemDTO formControlItemDTO) {
            ShopFormControlItemPO shopFormControlItemPO = new ShopFormControlItemPO();
            BeanUtils.copyProperties(formControlItemDTO, shopFormControlItemPO);
            shopFormControlItemPO.setEncryptType(EncryptTypeEnum.ofValue(formControlItemDTO.getEncryptType()));
            shopFormControlItemPO.setItemType(ItemTypeEnum.ofValue(formControlItemDTO.getItemType()));
            return shopFormControlItemPO;
        }

        @Override
        protected FormControlItemDTO doBackward(ShopFormControlItemPO itemPO) {
            FormControlItemDTO formControlItemDTO = new FormControlItemDTO();
            BeanUtils.copyProperties(itemPO, formControlItemDTO);
            formControlItemDTO.setEncryptType(itemPO.getEncryptType().getIntValue());
            formControlItemDTO.setItemType(itemPO.getItemType().getIntValue());
            return formControlItemDTO;
        }
    }

    /**
     * 获得所有状态为有效的业务类型
     *
     * @return
     */
    @Override
    public List<ShopBusinessDTO> getAllAvailable() {
        List<ShopBusinessPO> allAvailable = shopBusinessService.getAllAvailable();
        List<ShopBusinessDTO> shopBusinessDTOs = new ArrayList<>();
        if(CollectionUtils.isEmpty(allAvailable)){
            return shopBusinessDTOs;
        }
        shopBusinessDTOs = new ArrayList<>(Lists.transform(allAvailable, s -> new ShopBusinessPOConvert().convert(s)));
        return shopBusinessDTOs;
    }
}


package com.jd.appoint.service.shop;

import com.jd.appoint.domain.enums.EncryptTypeEnum;
import com.jd.appoint.domain.order.AppointOrderFormItemPO;
import com.jd.appoint.domain.shop.ShopFormControlItemPO;
import com.jd.appoint.domain.shop.query.FormControlItemQuery;
import com.jd.appoint.service.order.vo.ValueFromControlItem;
import com.jd.appoint.vo.order.AppointOrderDetailVO;

import java.util.List;
import java.util.Map;

/**
 * Created by yangyuan on 5/15/18.
 */
public interface ShopFormControlItemService {

    boolean batchInsert(List<ShopFormControlItemPO> itemPOList);

    List<ShopFormControlItemPO> query(FormControlItemQuery formControlItemQuery);

    List<ShopFormControlItemPO> queryByBusinessCode(String businessCode);

    void insert(ShopFormControlItemPO shopFormControlItemPO);

    boolean update(ShopFormControlItemPO shopFormControlItemPO);

    void delete(Long id);

    /**
     * 根据表单控制项提交内容，拼接ValueFromControlItem
     *
     * @param appointOrderDetailVo
     * @return key=alias
     * @throws IllegalArgumentException 参数校验失败
     */
    Map<String, ValueFromControlItem> createValueMap(AppointOrderDetailVO appointOrderDetailVo) throws IllegalArgumentException;

    Map<String, EncryptTypeEnum> queryEncryptByBusinessCode(String businessCode);



    List<ShopFormControlItemPO> findByBusinessCodeAndPageNoAndVenderId(String businessCode, String pageNo, Long venderId);
}

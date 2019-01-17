package com.jd.appoint.api.vo;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author lijizhen1@jd.com
 * @date 2018/6/21 18:29
 */
public class InputCartVO implements Serializable {
    /**
     * 导入数据最多为5000条
     */
    @Valid
    @Size(min = 0, max = 5000)
    private List<CardVO> cardVOList;


    public List<CardVO> getCardVOList() {
        return cardVOList;
    }

    public void setCardVOList(List<CardVO> cardVOList) {
        this.cardVOList = cardVOList;
    }
}

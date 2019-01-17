package com.jd.appoint.domain.product;

import com.jd.appoint.domain.base.BaseEntity;
import com.jd.appoint.domain.enums.StatusEnum;


/**
 * Created by yangyuan on 6/14/18.
 */
public class ProductPO extends BaseEntity {


    /**
     * <pre>
     * 产品名称
     * 表字段 : product.name
     * </pre>
     */
    private String name;

    /**
     * <pre>
     * skuid
     * 表字段 : product.sku_id
     * </pre>
     * 
     */
    private Long skuId;

    /**
     * 业务编码
     */
    private String businessCode;
    /**
     * 商家编号
     */
    private Long venderId;

    /**
     * 门店编号
     */
    private Long storeId;

    /**
     * <pre>
     * 产品图片url
     * 表字段 : product.url
     * </pre>
     */
    private String url;

    /**
     * <pre>
     * 产品描述
     * 表字段 : product.description
     * </pre>
     */
    private String description;

    /**
     * <pre>
     * 1 上线，2 下线， 9 删除
     * 表字段 : product.status
     * </pre>
     */
    private StatusEnum status;


    /**
     * <pre>
     * 获取：产品名称
     * 表字段：product.name
     * </pre>
     *
     * @return product.name：产品名称
     */
    public String getName() {
        return name;
    }

    /**
     * <pre>
     * 获取：skuid
     * 表字段：product.sku_id
     * </pre>
     *
     * @return product.sku_id：skuid
     */
    public Long getSkuId() {
        return skuId;
    }


    /**
     * <pre>
     * 获取：产品图片url
     * 表字段：product.url
     * </pre>
     *
     * @return product.url：产品图片url
     */
    public String getUrl() {
        return url;
    }


    public String getBusinessCode() {
        return businessCode;
    }


    public StatusEnum getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class  Builder{

        private Long id;

        private String name;

        private Long skuId;

        private Long venderId;

        private Long storeId;

        private StatusEnum status;

        public Builder(){}

        public Builder(Long id){
            this.id = id;
        }

        public ProductPO build(){
            ProductPO productPO = new ProductPO();
            productPO.setId(id);
            productPO.setName(name);
            productPO.setSkuId(skuId);
            productPO.setStatus(status);
            return productPO;
        }

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder venderId(Long venderId){
            this.venderId  = venderId;
            return this;
        }

        public Builder storeId(Long storeId){
            this.storeId = storeId;
            return this;
        }

        public Builder skuId(Long skuId){
            this.skuId = skuId;
            return this;
        }

        public Builder status(StatusEnum status){
            this.status = status;
            return this;
        }


    }

    @Override
    public String toString() {
        return "ProductPO{" +
                "name='" + name + '\'' +
                ", skuId=" + skuId +
                ", businessCode='" + businessCode + '\'' +
                ", venderId=" + venderId +
                ", storeId=" + storeId +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                "} " + super.toString();
    }
}

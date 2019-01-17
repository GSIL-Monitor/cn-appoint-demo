package com.jd.appoint.store.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家基本信息
 * Created by wangshangyu on 2017/3/23.
 */
public class VendorVender implements Serializable {

    private static final long serialVersionUID = 1622546784677519817L;

    /**
     * 商家编号
     */
    private Long vendorId;
    /**
     * 公司编号
     */
    private Long companyId;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 联系人邮箱
     */
    private String contactEmail;
    /**
     * 商家合同开始时间
     */
    private Date contractStartTime;
    /**
     * 商家合同结束时间
     */
    private Date contractClosingTime;
    /**
     * 商家业务类型和合作方式：
     * SOP(0, "SOP"),
     * FBP(1, "FBP"),
     * LBP(2, "LBP"),
     * LBV(4, "LBV"),
     * SOPL( 5, "SOPL"),
     * VCARD(6, "虚卡"),
     * SHOUJICHONGZHI(7 , "手机充值"),
     * JIPIAO( 8, "机票"),
     * CAIPIAO(9, "彩票"),
     * TUANGOUKAIFANGPINGTAI(10, "团购开放平台"),
     * JIUDIAN(11, "酒店"),
     * HUOCHEPIAO(12, "火车票"),
     * DIANYINPIAO(13, "电影票"),
     * JINGDIAN(14, "景点"),
     * LVYOULUXIAN(15, "旅游线路"),
     * ZHUCHE(16, "租车"),
     * PIAOWU(17, "票务"),
     * YUMENG( 20, "域名"),
     * PAIMAI(21, "拍卖"),
     * YEYOU(22, "页面游戏"),
     * JIKAIXINGCAIPIAO(23, "即开型彩票"),
     * CHEXIANGSHANGJIA(24, "车险商家"),
     * FCHEXIANGSHANGJIA(25, "非车险商家"),
     * JINGDONGBANF(26, "京东帮"),
     * YEYOUJIFU(27, "网页游戏（技术服务）"),
     * JIAOFEI(28, "缴费"),
     * O2O(29, "O2O"),
     * TUANGOUSHANGJIA(30, "团购商家"),
     * EPT(31,"EPT商家"),
     * JPKFPT(32,"机票开放平台"),
     * QCP(33,"汽车票")
     */
    private Integer venderBizType;
    /**
     * 商家状态：
     * 1.待审核；
     * 2.财务收费审核；
     * 4.待启用;
     * 8.已启用;
     * 16.审核未通过；
     * 32.终止合作（预留）
     */
    private Integer venderStatus;
    /**
     * 店铺编号
     */
    private Long shopId;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 结账周期类型，结账周期类型,1:月结, 2:半月结 ,3:旬结，4:每天 5:每周 6：每三天 99：无结算周期 负数为t+1商家
     */
    private Integer financialPeriod;

    public VendorVender() {
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Date getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public Date getContractClosingTime() {
        return contractClosingTime;
    }

    public void setContractClosingTime(Date contractClosingTime) {
        this.contractClosingTime = contractClosingTime;
    }

    public Integer getVenderBizType() {
        return venderBizType;
    }

    public void setVenderBizType(Integer venderBizType) {
        this.venderBizType = venderBizType;
    }

    public Integer getVenderStatus() {
        return venderStatus;
    }

    public void setVenderStatus(Integer venderStatus) {
        this.venderStatus = venderStatus;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getFinancialPeriod() {
        return financialPeriod;
    }

    public void setFinancialPeriod(Integer financialPeriod) {
        this.financialPeriod = financialPeriod;
    }

    @Override
    public String toString() {
        return "VendorVender{" +
                "vendorId=" + vendorId +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contractStartTime=" + contractStartTime +
                ", contractClosingTime=" + contractClosingTime +
                ", venderBizType=" + venderBizType +
                ", venderStatus=" + venderStatus +
                ", shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", financialPeriod=" + financialPeriod +
                '}';
    }
}

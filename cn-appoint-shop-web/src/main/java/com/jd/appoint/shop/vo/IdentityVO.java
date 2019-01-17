package com.jd.appoint.shop.vo;

import com.jd.appoint.shop.util.Utils;
import com.jd.jmi.jicc.client.enums.JICCPapersTypeEnum;

/**
 * Created by bjliuyong on 2018/5/30.
 */
public class IdentityVO {

    /**
     * 户薄唯一ID
     */
    private String securityId ;

    /**
     * 姓名
     */
    private String name ;

    /**
     * 证件类型  1.身份证
     */
    private int type ;

    /**
     * 证件号码
     */
    private String no ;

    /**
     * 图像关联id
     */
    private String imgSid ;


    /**
     * 正面证件照
     */
    private String frontImg ;

    /**
     * 反面证件照
     */
    private String reverseImg ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void validate() {
        Utils.validateArgsBlank(name , "name is null or empty");
        Utils.validateArgsBlank(no , "no  is null or empty");
        Utils.validate(type == JICCPapersTypeEnum.IDENTITY.getCode() , "type is not 1");
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("IdentityVO{");
        sb.append("name='").append(name).append('\'');
        sb.append(", type=").append(type);
        sb.append(", no='").append(no).append('\'');
        sb.append(", imgSid='").append(imgSid).append('\'');
        sb.append(", frontImg='").append(frontImg).append('\'');
        sb.append(", reverseImg='").append(reverseImg).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getImgSid() {
        return imgSid;
    }

    public void setImgSid(String imgSid) {
        this.imgSid = imgSid;
    }


    public String getFrontImg() {
        return frontImg;
    }

    public void setFrontImg(String frontImg) {
        this.frontImg = frontImg;
    }

    public String getReverseImg() {
        return reverseImg;
    }

    public void setReverseImg(String reverseImg) {
        this.reverseImg = reverseImg;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }
}

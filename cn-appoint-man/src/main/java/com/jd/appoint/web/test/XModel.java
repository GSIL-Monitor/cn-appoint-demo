package com.jd.appoint.web.test;

import com.jd.adminlte4j.annotation.*;
import com.jd.adminlte4j.model.form.FormItemType;
import com.jd.adminlte4j.model.form.ValidateType;

import java.util.Date;

/**
 * Created by bjliuyong on 2018/3/30.
 */
@Form(span = 3 ,ignore = false , hidden = false)
public class XModel {

    /**
     * Id
     */
    @UIFormItem(hidden = true)
    private int id;

    @UIFormItem(span = 12 , label = "输入框" )
    @Validate
    private String inputName ;

    @Validate(type = ValidateType.NUMBER)
    private int age ;

    @UIFormItem(span = 3 ,label = "姓名")
    private String name ;

    @Validate(type = ValidateType.EMAIL)
    @UIFormItem(label = "联系方式")
    private String contact ;

    @com.alibaba.fastjson.annotation.JSONField(format="yyyy-MM-dd hh:mm:ss") //使用fastjson 格式化
    private Date birthDay ;

    @UIFormItem(type = FormItemType.CHECKBOX)
    @DictData({@DictEntry("写作") ,@DictEntry("阅读"),@DictEntry("听说")})
    private String love ;

    @UIFormItem(type = FormItemType.RADIO)
    @DictData(value = {@DictEntry(code = "1",value ="男") ,@DictEntry(code = "2", value = "女"),@DictEntry(code = "3",value = "中性")})
    private int gender ;

    @UIFormItem(type = FormItemType.SWITCH  , span = 12)
    @DictData({@DictEntry("是") ,@DictEntry("否")})
    private int isAdmin ;

    @UIFormItem(type = FormItemType.SELECT , label = "学历" )
    @DictData({@DictEntry(value = "小学") ,@DictEntry("初中") ,@DictEntry("高中"),@DictEntry("本科")})
    private int educatoin;

    @UIFormItem(type = FormItemType.ICON_SELECTOR , span = 12 , label = "选择图标")
    @Validate
    private String myIcon ;

    @UIFormItem(type = FormItemType.TEXTAREA , span = 12 , label = "座右铭")
    private String motto ;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }


    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public String getMyIcon() {
        return myIcon;
    }

    public void setMyIcon(String myIcon) {
        this.myIcon = myIcon;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getEducatoin() {
        return educatoin;
    }

    public void setEducatoin(int educatoin) {
        this.educatoin = educatoin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }
}

jQuery.extend(jQuery.validator.messages, {
    required: "请输入该字段",
    remote: "请修正该字段",
    email: "请输入正确格式的电子邮件",
    url: "请输入合法的网址",
    date: "请输入合法的日期",
    dateISO: "请输入合法的日期 (ISO).",
    number: "请输入合法的数字",
    digits: "只能输入整数",
    creditcard: "请输入合法的信用卡号",
    equalTo: "请再次输入相同的值",
    accept: "请输入拥有合法后缀名的字符串",
    maxlength: jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),
    minlength: jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),
    rangelength: jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
    range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
    max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
    min: jQuery.validator.format("请输入一个最小为 {0} 的值")
});


jQuery.validator.addMethod("isZipCode", function(value, element) {
    var pattern = /^[0-9]{6}$/;
    if(this.optional(element) || (pattern.test(value))) {
        $(element).attr("class","fe-text");
        return true;
    }else {
        $(element).attr("class","fe-text");
        return false;
    }
}, "请正确填写您的邮政编码");
//匹配由26个英文字母组成的字符串
jQuery.validator.addMethod("isLetter", function(value, element) {
    var pattern = new RegExp("^[A-Za-z]+$");
    if(this.optional(element) || (pattern.test(value))) {
        $(element).attr("class","fe-text");
        return true;
    }else {
        $(element).attr("class","fe-text");
        return false;
    }
},"请填写只由英文组成的字符串");
// 手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if (this.optional(element) || (length == 11 && mobile.test(value))) {
        $(element).attr("class","fe-text");
        return true;
    }else {
        $(element).attr("class","fe-text");
        return false;
    }
}, "请正确填写您的手机号码");
jQuery.validator.addMethod("isMobileNew", function (value, element) {
    var length = value.length;
    var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if (this.optional(element) || (length == 11 && mobile.test(value))) {
        return true;
    } else {
        return false;
    }
}, "请正确填写您的手机号码");
jQuery.validator.addMethod("isCode6", function (value, element) {
    var length = value.length;
    var code6 = /[0-9]{6}/;
    if (this.optional(element) || (length == 6 && code6.test(value))) {
        return true;
    } else {
        return false;
    }
}, "请填写正确的验证码");

// 电话号码验证  电话号码格式010-12345678
jQuery.validator.addMethod("isTel", function(value, element) {
    var tel = /^(\d{3,4}-)?\d{7,9}$/;
    if(this.optional(element) || (tel.test(value))) {
        $(element).attr("class","fe-text");
        return true;
    }else {
        $(element).attr("class","fe-text");
        return false;
    }
}, "请正确填写您的电话号码");
// 京东通用电话号码格式验证 
jQuery.validator.addMethod("isJdTel", function(value, element) {
    var tel = /^[0-9\-()（）]{7,18}$/;
    if(this.optional(element) || (tel.test(value))) {
        $(element).attr("class","fe-text");
        return true;
    }else {
        $(element).attr("class","fe-text");
        return false;
    }
}, "请正确填写您的电话号码");
// 联系电话(手机/电话皆可)验证
jQuery.validator.addMethod("isPhone", function(value,element) {
    var length = value.length;
    var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    var tel = /^(\d{3,4}-)?\d{7,9}$/;

    if(this.optional(element) || (tel.test(value) || (length == 11 && mobile.test(value)))) {
        $(element).attr("class","fe-text");
        return true;
    }else {
        $(element).attr("class","fe-text");
        return false;
    }

}, "请正确填写您的联系电话");
//验证括中文字、英文字母、数字和下划线
jQuery.validator.addMethod("stringCheck", function(value, element) {
    if(this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value)) {
        $(element).attr("class","fe-text");
        return true;
    }else {
        $(element).attr("class","fe-text");
        return false;
    }
}, "只能包括中文字、英文字母、数字和下划线");
jQuery.validator.addMethod("stringCheckNew", function (value, element) {
    if (this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value)) {
        return true;
    } else {
        return false;
    }
}, "只能包括中文字、英文字母、数字和下划线");
//验证金额 小数点后面可以跟0到2位
jQuery.validator.addMethod("moneyCheck", function(value, element) {
    if(this.optional(element) || /^[1-9][0-9]*(\.)?([0-9]){0,2}$|^[0-9](\.)?([0-9]){0,2}$/.test(value)) {
        $(element).attr("class","fe-text");
        return true;
    }else {
        $(element).attr("class","fe-text");
        return false;
    }
}, "金额格式错误");
//验证email
jQuery.validator.addMethod("isMail", function(value, element) {
    if(this.optional(element) || /^[A-Za-z0-9]+([-_.][A-Za-z0-9]+)*@([A-Za-z0-9]+[-.])+[A-Za-z0-9]{2,5}$/.test(value)) {
        $(element).attr("class","fe-text");
        return true;
    }else {
        $(element).attr("class","fe-text");
        return false;
    }
}, "只能包括英文字母、数字和下划线");
//是有效地整数
jQuery.validator.addMethod("isInt", function(value, element) {
    if(this.optional(element) || (/^([1-9][0-9]*)$/.test(value) && value*1 < 1000000000)) {
        $(element).attr("class","fe-text");
        return true;
    }else {
        $(element).attr("class","fe-text");
        return false;
    }
}, "请输入有效整数");
//验证整数
jQuery.validator.addMethod("checkPriceNoFj", function(value, element) {
    return this.optional(element) || /^([1-9][0-9]*)$|^0$/.test(value) || /^(?!0(\.0+)?$)([1-9][0-9]{0,9}|0)(\.[0]{1,3})?$/.test(value);
}, "金额只能是正整数");
//是有大于零的效地整数
jQuery.validator.addMethod("isMaxZeroInt", function(value, element) {
    return this.optional(element) || /^[1-9][0-9]*$/.test(value);
}, "请输入大于零整数");
//是有效地重量,小数点后三位 大于0
jQuery.validator.addMethod("isWeight", function(value, element) {
    return this.optional(element) || /^(?!0(\.0+)?$)([1-9][0-9]{0,9}|0)(\.[0-9]{1,3})?$/.test(value);
}, "请输入有效的重量");

jQuery.validator.addMethod("zh_CnLength",function(value,element,param){
    value = value.replace(/(^\s*)|(\s*$)/g, "");
    String.prototype.getBytes = function() {
        var cArr = this.match(/[^\x00-\xff]/ig);
        return this.length + (cArr == null ? 0 : cArr.length);
    }
    var length = value.getBytes();
    if(length>param){
        $(element).attr("class","fe-text");
        return false;
    }
    $(element).attr("class","fe-text");
    return true;
},"请输入一个长度最多是 {0} 的字符,一个中文算两个长度");

jQuery.validator.addMethod("zh_CnLengthNew", function (value, element, param) {
    value = value.replace(/(^\s*)|(\s*$)/g, "");
    String.prototype.getBytes = function () {
        var cArr = this.match(/[^\x00-\xff]/ig);
        return this.length + (cArr == null ? 0 : cArr.length);
    };
    var length = value.getBytes();
    if (length > param) {
        return false;
    }
    return true;
}, "请输入一个长度最多是 {0} 的字符,一个中文算两个长度");

jQuery.validator.addMethod("selectRequired", function(value, element) {
    var value = $(element).val() + '';
    if(value == '' || value == '0' || value == '-1' || value == '请选择') {
        return false;
    }
    return true;
}, "请选择选项");

jQuery.validator.addMethod("isImg", function(value, element) {

    if(value == '') {
        return true;
    }

    //为了避免转义反斜杠出问题，这里将对其进行转换
    var re = /(\\+)/g;
    var filename=value.replace(re,"#");
    //对路径字符串进行剪切截取
    var one=filename.split("#");
    //获取数组中最后一个，即文件名
    var two=one[one.length-1];
    //再对文件名进行截取，以取得后缀名
    var three=two.split(".");
    //获取截取的最后一个字符串，即为后缀名
    var last=three[three.length-1];

    //添加需要判断的后缀名类型
    var tp ="jpg,png,gif";
    //返回符合条件的后缀名在字符串中的位置
    var rs=tp.indexOf(last.toLowerCase());
    //如果返回的结果大于或等于0，说明包含允许上传的文件类型
    if(rs>=0){
        return true;
    }else{
        return false;
    }

}, "格式错误，请上传后缀为jpg,png,gif的图片文件");

jQuery.validator.addMethod("isExcel", function(value, element) {
    if(value == '') {
        return true;
    }

    //为了避免转义反斜杠出问题，这里将对其进行转换
    var re = /(\\+)/g;
    var filename=value.replace(re,"#");
    //对路径字符串进行剪切截取
    var one=filename.split("#");
    //获取数组中最后一个，即文件名
    var two=one[one.length-1];
    //再对文件名进行截取，以取得后缀名
    var three=two.split(".");
    //获取截取的最后一个字符串，即为后缀名
    var last=three[three.length-1];

    //添加需要判断的后缀名类型
    var tp ="xls,xlsx";
    //返回符合条件的后缀名在字符串中的位置
    var rs=tp.indexOf(last.toLowerCase());
    //如果返回的结果大于或等于0，说明包含允许上传的文件类型
    if(rs>=0){
        return true;
    }else{
        return false;
    }
}, "格式错误，请上传后缀为xls,xlsx的Excel文件");

function trim(str){  //删除左右两端的空格
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

jQuery.validator.addMethod("sellerRequired", function(value, element) {
    if(trim(value) == '') {
        $(element).attr("class","fe-text");
        return false;
    }
    $(element).attr("class","fe-text");
    return true;
}, "请输入");

//验证email
jQuery.validator.addMethod("isqq", function(value, element) {
    if(this.optional(element) || /^([1-9]{1})([0-9]{4,13})$/.test(value)) {
        $(element).attr("class","fe-text");
        return true;
    }else {
        $(element).attr("class","fe-text");
        return false;
    }
}, "QQ格式不对，请输入5-14的数字");
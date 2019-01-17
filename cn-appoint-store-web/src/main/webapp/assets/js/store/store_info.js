Vue.http.options.emulateJSON = true;
var vm = new Vue({
    el: 'body',
    data: {
        store: {},
        modal1: false,
        auth_tip_title: '  ',
        auth_tip_message: '  ',
        ebank: '',
        isSend: false,
        sendCode_btn: '获取验证码',
        disabled: false,
        isAgree: false,
        vCode: '',
        ruleValidate: {
            sendCode: [
                {
                    pattern: /^[a-zA-Z0-9]*$/,
                    message: '验证码输入不正确！'
                },{
                    required: true,
                    message: '验证码不能为空！'
                }
            ]
        }
    },
    methods:{
        getInfo: function () {
            // var _this = this;
            $(".pop-modal-mask").css("display","block");
            $(".pop-modal-wrap").css("display","block");
            this.$http.get('/store/info').then(function(response){
                if(response.status==200 && response.data){
                    this.store = response.data;
                    this.$http.get('/store/check-auth').then(function(response){
                        this.modal1 = !response.data;
                        if(this.modal1){
                            // 已打标，未授权，需要签署授权协议
                            this.$http.get('/store/ebank').then(function(response){
                                console.log(response);
                                if(response.status==200 && response.data){
                                    if (response.data.email) {
                                        this.ebank = response.data.email;
                                    } else {
                                        console.error(response.data);
                                        this.$Message.error(response.data.errCode + ":" +response.data.errMsg, 5);
                                        window.location.href = "/menu/error/50x?errCode=" + response.data.errCode;
                                    }
                                }
                            });
                        } else {
                            // 不需要签署授权协议
                            this.$http.get('/store/check-tag').then(function (res) {
                                console.log(res);
                                if (!res.data) {
                                    // 1.未打标
                                    this.auth_tip_title = '未打标';
                                    this.auth_tip_message = '结算至集团';
                                } else {
                                    // 2.已打标，已授权
                                    this.auth_tip_title = '已授权';
                                    this.auth_tip_message = '结算至本门店';
                                }
                            });
                        }
                    });
                }else{
                    this.$Message.error('页面加载失败！',5);
                }
            });
        },
        sendCode: function () {
            var _this = this;
            var second = 120;
            this.disabled = true;
            this.$http.get('/store/send-code').then(function(response){
                console.log(response);
                if(response.status==200 && response.data){
                    this.$Message.success('验证码发送成功！');
                }else{
                    this.$Message.error('验证码发送失败，请重新获取！');
                }
            });
            var count = setInterval(function() {
                second -= 1;
                _this.sendCode_btn = second + 's后可重新发送';
                if(second==0){
                    clearInterval(count);
                    _this.disabled = false;
                    _this.sendCode_btn = '获取验证码';
                }
            },1000);
        },
        submit_btn: function() {
            this.$http.post('/store/auth',{vCode:this.vCode,isAgree:this.isAgree}).then(function(response){
                if(response.status==200 && response.data.isAuthorized){
                    this.modal1 = false;
                    this.$Message.success('提交成功！');
                } else {
                    this.modal1 = true;
                    window.location.href = "/menu/error/50x?errCode=" + response.data.errCode;
                }
            });
        },
        handleSubmit: function() {
            this.$refs.query.validate(function(valid) {
                if (valid) {
                    this.submit_btn();
                }
            }.bind(this))
        }
    },
    ready: function () {
        this.getInfo();
    }
});
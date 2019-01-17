Vue.http.options.emulateJSON = true;
// Vue.http.options.emulateHTTP = true;

var vm = new Vue({
    el: '#loc_write_off',
    data: {
        tableShow: false,
        queryCondition: {
            card: '',
            codeNo: ''
        },
        querys: [],
        ruleValidate: {
            card: [
                {
                    pattern: /^[a-zA-Z0-9]*$/,
                    message: '卡号输入不正确请重新输入！'
                }
            ],
            codeNo: [
                {
                    required: true,
                    pattern: /^[a-zA-Z0-9]*$/,
                    message: '验证码输入不正确请重新输入！'
                }
            ]
        }
    },
    methods: {
        search: function(){
            //验证码核销查询
            this.$http.post('/loc/search',this.queryCondition).then(function(res){
                this.querys = [];
                if(res.data && res.status==200){
                    this.tableShow=true;
                    for(var i=0;i<res.data.length;i++){
                        this.querys.push(res.data[i]);
                    }
                }else{
                    this.tableShow=false;
                    this.$Message.warning('暂无符合条件的数据！',2);
                }
                console.log(this.querys);

            })
        },
        success: function() {
            this.$Message.config({
                top: 200,
                duration: 3
            });
            this.$Message.success('核销成功！',2);
        },
        writeoff: function(parameter){
            //按输入信息查询
            console.log(parameter);
            this.$http.post('/loc/write-off',parameter).then(function(res){
                console.log(res);
                if(res.status==200 && res.data.isWritten){
                    this.success();
                    window.location.href="/menu/loc/loc-view";
                }
            });
        },
        handleReset: function() {
            this.$refs.query.resetFields();
            this.queryCondition = {
                card: '',
                codeNo: ''
            };
        },
        validate: function () {
            var _this = this;
            this.$refs.query.validate(function(valid){
                if(valid){
                    _this.search();
                    _this.$Message.config({
                        top: 200
                    });
                    /*_this.$Message.success('查询成功！');*/
                }/*else{
                    _this.$Message.config({
                        top: 200,
                        duration: 2
                    });
                    _this.$Message.error('输入有误！');
                }*/
            });
        }
    }
});
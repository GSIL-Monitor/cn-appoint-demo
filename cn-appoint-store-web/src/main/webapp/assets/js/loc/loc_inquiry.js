var vm = new Vue({
    el: '#loc_inquiry',
    data: {
        key: '',
        tableShow: false,
        unusedTableShow: false,
        queryInfo: {
            code: '',
            phone: '',
            phone1: ''
        },
        querys: [],
        unusedQuerys: [],
        pageInfo: {
            page: 1,
            pageSize: 10,
            totalItem: 0,
            totalPage: 1
        },
        unusedPageInfo: {
            page: 1,
            pageSize: 10,
            totalItem: 0,
            totalPage: 1
        },
        isUsed: true,
        ruleValidate: {
            code: [
                {
                    pattern: /^[a-zA-Z0-9]*$/,
                    message: '核销码输入有误请重新输入！'
                }
            ],
            phone: [
                {
                    pattern: /^1[3|4|5|7|8][0-9]{9}$/,
                    message: '手机号不正确请重新输入！'
                }
            ]
        }
    },
    methods: {
        isTable: function (querys) {
            console.log(querys);
            this.tableShow = (querys.length == 0);
        },
        isUnusedTable: function (unusedQuerys) {
            console.log(unusedQuerys);
            this.unusedTableShow = (unusedQuerys.length == 0);
        },
        isActive: function (key) {
            if(key == 'used'){
                this.isUsed = true;
            }else{
                this.isUsed = false;
            }
        },
        inquiry: function(pageInfo){
            //验证码查询全部
            this.$http.get('/loc/inquiry?page='+pageInfo.page+'&pageSize='+pageInfo.pageSize).then(function(res){
                console.log(res.data);
                this.querys = res.data.itemList;
                this.pageInfo = {
                    page: res.data.page,
                    pageSize: res.data.pageSize,
                    totalItem: res.data.totalItem,
                    totalPage: res.data.totalPage
                };
                this.isTable(this.querys);
            })
        },
        inquiryUnused: function (unusedPageInfo) {
            this.$http.get('/loc/inquiry-unused?page='+unusedPageInfo.page+'&pageSize='+unusedPageInfo.pageSize).then(function (res) {
                this.unusedQuerys = res.data.itemList;
                this.unusedPageInfo = {
                    page: res.data.page,
                    pageSize: res.data.pageSize,
                    totalItem: res.data.totalItem,
                    totalPage: res.data.totalPage
                };
                this.isUnusedTable(this.unusedQuerys);
            })
        },
        criteriaInquiry: function(pageInfo){
            //按输入信息查询
            var type = 2; // UNUSED(1, "未消费"),CONSUMED(2, "已消费")
            var phone = '';
            var url = '/loc/criteria-inquiry';
            console.log(pageInfo);
            if(this.isUsed){
                type = 2;
                phone = this.queryInfo.phone;
            }else{
                type = 1;
                phone = this.queryInfo.phone1;
            }
            this.$http.get(url+'?phone='+phone+'&type='+type+'&page='+pageInfo.page+'&pageSize='+pageInfo.pageSize).then(function(res){
                console.log(res);
                if(type ==2){
                    this.tableShow = (res.data == '');
                    this.querys = res.data.itemList;
                    console.log(res.data);
                    this.pageInfo = {
                        page: res.data.page,
                        pageSize: res.data.pageSize,
                        totalItem: res.data.totalItem,
                        totalPage: res.data.totalPage
                    };
                    this.isTable(this.querys);
                }else if(type ==1){
                    this.unusedTableShow = (res.data == '');
                    this.unusedQuerys = res.data.itemList;
                    console.log(res.data);
                    this.unusedPageInfo = {
                        page: res.data.page,
                        pageSize: res.data.pageSize,
                        totalItem: res.data.totalItem,
                        totalPage: res.data.totalPage
                    };
                    this.isUnusedTable(this.unusedQuerys);
                }
            })
        },
        validates: function () {
            var _this = this;
            if (!_this.queryInfo.phone && this.isUsed) {
                _this.$Message.error('请输入手机号！');
                return;
            }else if(!_this.queryInfo.phone1 && !this.isUsed){
                _this.$Message.error('请输入手机号！');
                return;
            }
            var pageInfo = {
                page: 1,
                pageSize: 10,
                totalItem: 0,
                totalPage: 1
            };
            this.$refs.query.validate(function(valid){
                if(valid){
                    _this.$Message.config({
                        top: 200
                    });
                    /*_this.$Message.success('正在查询！');*/
                    _this.criteriaInquiry(pageInfo);
                }/*else{
                 _this.$Message.config({
                 top: 200
                 });
                 _this.$Message.error('输入有误！');
                 }*/
            });
        },
        pageChange: function (pageNum) {
            var pageInfo = {
                page: pageNum,
                pageSize: this.pageInfo.pageSize
            };
            /*console.log(pageInfo);
             this.inquiry(pageInfo);*/

            if(this.isUsed && this.queryInfo.phone == ''){
                this.inquiry(pageInfo);
            }else if(this.isUsed && this.queryInfo.phone != ''){
                this.criteriaInquiry(pageInfo);
            }else if(!this.isUsed && this.queryInfo.phone1 == ''){
                this.inquiryUnused(pageInfo);
            }else if(!this.isUsed && this.queryInfo.phone1 != ''){
                this.criteriaInquiry(pageInfo);
            }
        },
        pageSizeChange: function (pageSize) {
            var pageInfo = {
                page: 1,
                pageSize: pageSize
            };

            if(this.isUsed && this.queryInfo.phone == ''){
                this.inquiry(pageInfo);
            }else if(this.isUsed && this.queryInfo.phone != ''){
                this.criteriaInquiry(pageInfo);
            }else if(!this.isUsed && this.queryInfo.phone1 == ''){
                this.inquiryUnused(pageInfo);
            }else if(!this.isUsed && this.queryInfo.phone1 != ''){
                this.criteriaInquiry(pageInfo);
            }
        },
        handleReset: function() {
            this.$refs.query.resetFields();
            this.queryInfo = {
                code: '',
                phone: ''
            };
        }
    },
    ready: function () {
        this.inquiry(this.pageInfo);
        this.inquiryUnused(this.unusedPageInfo);
    }
});
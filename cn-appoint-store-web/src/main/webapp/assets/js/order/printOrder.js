var vm = new Vue({
    el: '#printOrder',
    data: {
        printOrder: {},
        query: {
            orderId: ''
        },
        orderCreateDateTime: [],
        pageInfo: {
            index: 1,
            pageSize: 10,
            totalItem: 0,
            totalPage: 1
        },
        isSearch: false,
        printOrderThead: [{
            title: '订单编号',
            key: 'orderId'
        }, {
            title: '下单时间',
            key: 'orderCreateTime'
        }, {
            title: '运单号',
            key: 'logicNo',
            render: function (row, column, index) {
                var str = '';
                var logicNo = row.logicNo;
                for(var i in logicNo){
                    str+= '<div class="mt_5 mb_5">'+logicNo[i]+'</div>'
                }
                return str;
            }
        }, {
            title: '快递公司',
            key: 'logiCoprName'
        }, {
            title: '操作',
            key: 'action',
            width: 150,
            align: 'center',
            render: function (row, column, index) {
                var isDisabled = row.isCheckPrint;
                return '<p-button type="ghost" size="small" @click="print('+row.orderId+','+row.logiCoprId+')" v-if="'+isDisabled+'">打印</p-button>'+
                    '<p-button type="ghost" size="small" disabled v-else>打印</p-button>';
            }
        }],
        tableDatas: [],
        rules: {
            orderId: [
                {
                    pattern: /^[0-9]*$/,
                    message: '该项只能输入数字！'
                }
            ]
        }
    },
    methods: {
        formatDate: tools.formatDate,
        errorMsg: tools.errorMsg,
        search: function(pageInfo){
            var query = {
                orderId: this.query.orderId,
                page: pageInfo.index,
                pageSize: pageInfo.pageSize
            };
            if(this.orderCreateDateTime.length>0){
                var orderCreateTimeStart = this.orderCreateDateTime[0];
                var orderCreateTimeEnd = this.orderCreateDateTime[1];
                query.orderCreateTimeEnd = this.formatDate(orderCreateTimeEnd);
                query.orderCreateTimeStart = this.formatDate(orderCreateTimeStart)
            }
            this.$http.post('/order/print/list',query).then(function(res){
                console.log(res.data);
                this.printOrder = res.data;
                this.tableData();
                this.checkPrint();
                this.isSearch = true;
            },function (error) {
                this.errorMsg(error);
            });
        },
        checkPrint:function () {
            for(var i in this.tableDatas){
                this.$http.get('/order/print/'+this.tableDatas[i].logiCoprId).then(function (response) {
                    this.tableDatas[i].isCheckPrint = response.data;
                });
            }
        },
        tableData: function () {
            this.tableDatas = [];
            for(var i = 0;i<this.printOrder.pageList.length;i++){
                for(var j = 0;j<this.printOrder.pageList[i].logicList.length;j++){
                    var tableData = {
                        orderId: this.printOrder.pageList[i].orderId,
                        orderCreateTime: this.printOrder.pageList[i].orderCreateTime,
                        logiCoprName: this.printOrder.pageList[i].logicList[j].logiCoprName,
                        logicNo: this.printOrder.pageList[i].logicList[j].logicNo,
                        logiCoprId: this.printOrder.pageList[i].logicList[j].logiCoprId,
                        isCheckPrint: true
                    };
                    this.tableDatas.push(tableData);
                }
            }
        },
        validates: function () {
            var _this = this;
            this.$refs.query.validate(function(valid){
                if(valid){
                    _this.$Message.config({
                        top: 200
                    });
                    _this.search(_this.pageInfo);
                }
            });
        },
        print: function (orderId,logicCoprId) {
            console.log(orderId);
            console.log(logicCoprId);
            this.$http.get('/order/print/printOrderDelivery?orderId='+orderId+'&logicCoprId='+logicCoprId).then(function (res) {
                console.log(res);
                window.open(res.body);
            },function (error) {
                this.errorMsg(error);
            });
            // window.open('/order/print?orderId=' + orderId + '&wuLiuId=' + wuLiuId + '&packNum=' + packageNum + '&preview=false');
        },
        pageChange: function (pageNum) {
            var pageInfo = {
                index: pageNum,
                pageSize: this.pageInfo.pageSize,
                totalItem: this.printOrder.totalItem,
                totalPage: this.printOrder.totalPage
            };
            this.search(pageInfo);
        },
        pageSizeChange: function (pageSize) {
            var pageInfo = {
                index: 1,
                pageSize: pageSize,
                totalItem: this.printOrder.totalItem,
                totalPage: this.printOrder.totalPage
            };
            this.search(pageInfo);
        },
        handleReset: function() {
            this.$refs.query.resetFields();
            this.query = {
                orderId: ''
            };
            this.orderCreateDateTime = [];
        }
    }
});
/**
 * Created by liyi on 2017/4/11.
 */
var tools = {
    formatDate: function (date) {
        var year = date.getFullYear();
        var month =(date.getMonth() + 1).toString();
        var day = (date.getDate()).toString();
        if (month.length == 1) {
            month = "0" + month;
        }
        if (day.length == 1) {
            day = "0" + day;
        }
        var dateTime = year + '-' + month + '-' + day;
        return dateTime;
    },
    handleSubmit: function () {
        var _this = this;
        this.$refs.query.validate(function(valid){
            console.log(valid);
            if(valid){
                _this.$Message.config({
                    top: 200
                });
                _this.$Message.success('正在查询！');
                _this.search(_this.pageInfo);
            }
        });
    },
    getRemarks: function (orderIds,isModal,isEdit) {
        this.$http.post('/order/remarks',orderIds).then(function(response){
            console.log(response.data);
            var pages = '';
            if(this.allOrder){
                pages = this.allOrder;
            }else if(this.orderInstore){
                pages = this.orderInstore;
            }
            if(response.data.length != 0 && isModal){
                this.orderRemarkVO.flagMark = response.data[0].flagMark;
                this.orderRemarkVO.remark = response.data[0].remark;
            }else if(response.data.length != 0 && isEdit){
                // for(var i=0;i<this.remarks.length;i++){
                //     if(this.remarks[i].orderId == response.data[0].orderId){
                //         this.remarks[i] = response.data[0];
                //     }
                // }
                for(var i=0;i<pages.pageList.length;i++){
                    pages.pageList[i].isRemark = false;
                    for(var j=0;j<response.data.length;j++){
                        if(pages.pageList[i].orderId == response.data[j].orderId){
                            pages.pageList[i].remark = response.data[j].remark;
                            pages.pageList[i].isRemark = true;
                        }
                    }
                }
            }else if(response.data.length == 0 && isModal) {
                this.orderRemarkVO.flagMark = 0;
                this.orderRemarkVO.remark = '';
            }
            // }else{
            //     for(var i=0;i<pages.pageList.length;i++){
            //         pages.pageList[i].isRemark = false;
            //         for(var j=0;j<response.data.length;j++){
            //             if(pages.pageList[i].orderId == response.data[j].orderId){
            //                 pages.pageList[i].remark = response.data[j].remark;
            //                 pages.pageList[i].isRemark = true;
            //             }
            //         }
            //     }
            //     this.remarks = pages;
            // }
        },function(error){
            this.modal = false;
            console.log(error.data);
            this.errorMsg(error);
        });
    },
    addRemark: function () {
        this.$http.post('/order/remark',this.orderRemarkVO).then(function(response){
            console.log(response);
            var orderIds = {
                orderIds: [this.orderRemarkVO.orderId]
            };
            this.getRemarks(orderIds,false,true);
        },function(error){
            var message = '';
            for(var i=0;i<error.data.fieldErrors.length;i++){
                message = message + error.data.fieldErrors[i].message;
            }
            this.$Modal.error({
                content: message
            });
        });
    },
    batchRemark: function () {
        if(this.all.length>0){
            this.modal2 = true;
            this.orderRemarkVO = {
                flagMark: 0,
                orderId: 0,
                orderProduceStatus: 1,
                remark: ''
            };
        }else{
            this.$Message.info('请选择订单！', 2);
        }
    },
    batchaddRemark: function () {
        for(var i = 0; i<this.all.length;i++){
            this.orderRemarkVO.orderId = this.all[i];
            this.$http.post('/order/remark',this.orderRemarkVO).then(function(response){
                console.log(response);
                // var orderIds = {
                //     orderIds: this.all
                // };
                // this.getRemarks(orderIds,true);
                this.getRemarks(orderIds,false,true);
            },function(error){
                var message = '';
                for(var i=0;i<error.data.fieldErrors.length;i++){
                    message = message + error.data.fieldErrors[i].message;
                }
                this.$Modal.error({
                    content: message
                });
            });
        }
        var orderIds = {
            orderIds: []
        };
        if(this.allOrder){
            for(var j=0;j<this.allOrder.pageList.length;j++){
                orderIds.orderIds.push(this.allOrder.pageList[j].orderId);
            }
        }else{
            for(var j=0;j<this.orderInstore.pageList.length;j++){
                orderIds.orderIds.push(this.orderInstore.pageList[j].orderId);
            }
        }
        this.all = [];
        this.select_all = false;
    },
    errorMsg: function (error) {
        var message = '';
        if(!error.data.fieldErrors.length){
            this.$Modal.error({
                content: error.data.msg
            });
        }else{
            for(var i=0;i<error.data.fieldErrors.length;i++){
                message = message + error.data.fieldErrors[i].message;
                this.$refs.query.setError(message.field,message.msg);
            }
        }
    },
    imageList: function (skuIds,isOut) {
        var skuId = skuIds.join(',');
        skuId = '[' + skuId + ']';
        this.$http.post('/order/image/list',skuId).then(function (res) {
            this.images = res.data;
            if(isOut){
                for(var i = 0;i<this.items.length;i++){
                    for(var j = 0;j<this.images.length;j++) {
                        if (this.images[j].skuId == this.items[i].skuId) {
                            this.items[i].image = this.images[j].url;
                        }
                    }
                }
            }
        },function (error) {
            // this.errorMsg(error);
        });
    },
    waresnapshot: function (orderId,skuId) {
        window.open('/order/waresnapshot?orderId='+orderId+'&skuId='+skuId);
        // this.$http.get('/order/waresnapshot?orderId='+orderId+'&skuId='+skuId).then(function (res) {
        //     console.log(res.data);
        //     if(res.data){
        //         window.open('/order/waresnapshot?orderId='+orderId+'&skuId='+skuId);
        //     }
        // },function (error) {
        //     this.errorMsg(error);
        // });
    },
    cancel:function () {
        
    },
    addDefault: function (pageList) {
        for(var i = 0;i<pageList.length;i++){
            this.deliveryCompanyVO.push({
                id: ''
            });
        }
    }
};

var datas = {
    urlName: {
        orderDetail: 'orderDetail',
        outbound: 'outbound',
        mergeOutbound: 'mergeOutbound',
        batchOutbound: 'batchOutbound'
    },
    paymentTypeList: [{
        value: '0',
        label: '请选择'
    }, {
        value: '1',
        label: '货到付款'
    }, {
        value: '2',
        label: '邮件汇款'
    }, {
        value: '3',
        label: '自提'
    }, {
        value: '4',
        label: '在线支付'
    }, {
        value: '5',
        label: '公司转账'
    }, {
        value: '6',
        label: '银行卡转账'
    }],
    waresThead: [{
        title: '商品',
        key: 'name',
        width: 500,
        render:function (row,col,index) {
            return '<div class="mt_10">'+
                        '<div class="fl">'+
                            '<img src="'+row.image+'" class="pic"/>'+
                        '</div>'+
                        '<div class="goods fl">'+
                            '<div class="goods_t">'+
                                row.name
                            +'</div>'+
                        '</div>'+
                        '<div class="goods_hand">'+
                            '<p-button type="ghost" @click="waresnapshot('+row.orderId+','+row.skuId+')">商品快照</p-button>'+
                        '</div>'+
                    '</div>'
        }
    }, {
        title: '商品编号',
        key: 'skuId'
    }, {
        title: '京东价',
        key: 'jingDongPrice',
        render:function (row,col,index) {
            return '￥'+row.jingDongPrice
        }
    }, {
        title: '商品数量',
        key: 'num'
    }, {
        title: '京豆',
        key: 'jingDou'
    }],
    outWares: [{
        title: ' ',
        key: 'image',
        render:function (row,col,index) {
            return '<div class="fl mt_5">'+
                '<img src="'+row.image+'" class="pic"/>'+
                '</div>'
        }
    }, {
        title: '商品编号',
        key: 'skuId'
    }, {
        title: '商品名称',
        key: 'name'
    }, {
        title: '数量',
        key: 'num',
        render:function (row,col,index) {
            return 'X'+row.num
        }
    }, {
        title: '京东价',
        key: 'jingDongPrice',
        render:function (row,col,index) {
            return '￥'+row.jingDongPrice
        }
    },{
        title: '赠送积分',
        key: 'point'
    },{
        title: '小计',
        key: 'sum',
        render:function (row,col,index) {
            return '￥'+row.sum
        }
    }],
    pickingItems: [{
        title: '货号',
        key: 'outerId'
    }, {
        title: '商品名称',
        key: 'name'
    }, {
        title: '数量',
        key: 'num',
        render:function (row,col,index) {
            return 'X'+row.num
        }
    }]
};
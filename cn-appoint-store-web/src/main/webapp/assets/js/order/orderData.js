/*数据处理*/
/*全部订单*/
var orderAllData = {
    data: function (){
        return {
            allOrder: {},
            images: [],
            orderRemarkVO: {
                flagMark: 0,
                orderId: 0,
                orderProduceStatus: 0,
                remark: ''
            },
            modal1: false,
            modal2: false,
            querys: {
                orderId: '',
                paymentType: '',
                consName: '',
                consMobilePhone: '',
                pin: '',
                skuId: '',
                skuName: '',
                logiNo: ''
            },
            isSearch: false,
            select_all: false,
            all: [],
            urlName: datas.urlName,
            pageInfo: {
                index: 1,
                pageSize: 10,
                totalItem: 0,
                totalPage: 1
            },
            orderCreateDateTime: [],
            paymentTypeList: datas.paymentTypeList,
            rules: {
                orderId: [
                    {
                        pattern: /^[0-9]*$/,
                        message: '该项只能输入数字！'
                    }
                ],
                consName:[
                    {
                        pattern: /^[a-zA-Z0-9\u4e00-\u9fa5]*$/,
                        message: '客户姓名输入有误！'
                    }
                ],
                consMobilePhone: [
                    {
                        pattern: /^1[3|4|5|7|8][0-9]{9}$/,
                        message: '请输入正确的手机号！'
                    }
                ],
                skuId:[
                    {
                        pattern: /^[0-9]*$/,
                        message: '该项只能输入数字！'
                    }
                ],
                skuName: [
                    {
                        pattern: /^[a-zA-Z0-9\u4e00-\u9fa5]*$/,
                        message: '本项不能输入符号！'
                    }
                ],
                logiNo:[
                    {
                        pattern: /^[a-zA-Z0-9]*$/,
                        message: '该项只能输入数字和字母！'
                    }
                ]
            }
        }
    },
    methods:{
        formatDate: tools.formatDate,
        handleSubmit:tools.handleSubmit,
        getRemarks: tools.getRemarks,
        addRemark: tools.addRemark,
        errorMsg: tools.errorMsg,
        batchRemark: tools.batchRemark,
        imageList: tools.imageList,
        waresnapshot: tools.waresnapshot,
        batchaddRemark: tools.batchaddRemark,
        cancel: tools.cancel,
        getData: function () {
            var orderIds = {
                orderIds: []
            };
            var skuIds = [];
            for(var i=0;i<this.allOrder.pageList.length;i++){
                orderIds.orderIds.push(this.allOrder.pageList[i].orderId);
                for(var j=0;j<this.allOrder.pageList[i].popOrderItemVoList.length;j++){
                    skuIds.push(this.allOrder.pageList[i].popOrderItemVoList[j].skuId);
                }
            }
            this.getRemarks(orderIds,false,true);
            this.imageList(skuIds);
        },
        getAllOrder: function (pageInfo) {
            this.$http.get('/order/allOrder?index='+pageInfo.index+'&pageSize='+pageInfo.pageSize).then(function(response){
                console.log(response.data);
                if(response.data.totalItem){
                    for(var i in response.data.pageList){
                        var list = response.data.pageList[i];
                        list.remark= '';
                        list.isRemark= '';
                    }
                    this.allOrder = response.data;
                    this.getData();
                }else{
                    this.$Modal.error({
                        content: '暂时没有数据！'
                    });
                }
            },function (error) {
                this.errorMsg(error);
            });
        },
        search: function(pageInfo){
            console.log(this.querys);
            var parameters = '';
            var time = '';

            if(this.orderCreateDateTime.length>0){
                var orderCreateDateStart = this.orderCreateDateTime[0];
                var orderCreateDateEnd = this.orderCreateDateTime[1];

                time = '&orderCreateDateStart=' + this.formatDate(orderCreateDateStart) + '&orderCreateDateEnd=' + this.formatDate(orderCreateDateEnd);;
            }
            for(var key in this.querys){
                if(this.querys[key]){
                    parameters = parameters + '&' + key + '=' + this.querys[key];
                }
            }
            this.$http.get('/order/allOrder?index='+pageInfo.index+'&pageSize='+pageInfo.pageSize+parameters+time).then(function(response){
                if(response.data.totalItem){
                    for(var i in response.data.pageList){
                        var list = response.data.pageList[i];
                        list.remark= '';
                        list.isRemark= '';
                    }
                    this.allOrder = response.data;
                    this.isSearch = true;
                    this.getData();
                }else{
                    this.allOrder = {};
                    this.$Modal.error({
                        content: '暂时没有数据！'
                    });
                }
            },function (error) {
                this.errorMsg(error);
            });
        },
        addBtn: function (orderId) {
            var orderIds = {
                orderIds: [orderId]
            };
            this.modal1 = true;
            this.getRemarks(orderIds,this.modal1);
            this.orderRemarkVO = {
                flagMark: this.orderRemarkVO.flagMark,
                orderId: orderId,
                orderProduceStatus: 1,
                remark: this.orderRemarkVO.remark
            };
            console.log(this.orderRemarkVO);
        },
        selectAll: function () {
            if(!this.select_all){
                for(var i=0;i<this.allOrder.pageList.length;i++){
                    this.all.push(this.allOrder.pageList[i].orderId);
                }
            }else{
                this.all = [];
            }
        },
        pageChange: function (pageNum) {
            var pageInfo = {
                index: pageNum,
                pageSize: this.pageInfo.pageSize,
                totalItem: this.allOrder.totalItem,
                totalPage: this.allOrder.totalPage
            };
            this.select_all = false;
            this.all = [];
            if(this.isSearch){
                this.search(pageInfo);
            }else{
                this.getAllOrder(pageInfo);
            }
        },
        pageSizeChange: function (pageSize) {
            var pageInfo = {
                index: 1,
                pageSize: pageSize,
                totalItem: this.allOrder.totalItem,
                totalPage: this.allOrder.totalPage
            };
            if(this.isSearch){
                this.search(pageInfo);
            }else{
                this.getAllOrder(pageInfo);
            }
        },
        handleReset: function() {
            this.$refs.query.resetFields();
            this.querys = {
                orderId: '',
                paymentType: '',
                consName: '',
                consMobilePhone: '',
                pin: '',
                skuId: '',
                skuName: '',
                logiNo: ''
            };
            this.orderCreateDateTime = [];
        }
    },
    watch: {
        'all': function (val,oldVal) {
            if(val.length == this.allOrder.pageList.length){
                this.select_all = true;
            }else{
                this.select_all = false;
            }
        }
    },
    ready: function () {
        this.getAllOrder(this.pageInfo);
    }
};

/*未付款*/
var orderUnpaidData = {
    data: function (){
        return {
            orderUnpaid: {},
            remarks: [],
            images: [],
            orderRemarkVO: {
                flagMark: 0,
                orderId: 0,
                orderProduceStatus: 0,
                remark: ''
            },
            modal2: false,
            querys: {
                orderId: ''
            },
            orderCreateDateTime: [],
            isSearch:false,
            pageInfo: {
                index: 1,
                pageSize: 10,
                totalItem: 0,
                totalPage: 1
            },
            urlName: datas.urlName,
            rules: {
                num: [
                    {
                        pattern: /^[a-zA-Z0-9]*$/,
                        message: '该项只能输入数字和字母！'
                    }
                ]
            }
        }
    },
    methods:{
        handleSubmit:tools.handleSubmit,
        getRemarks: tools.getRemarks,
        formatDate: tools.formatDate,
        addRemark: tools.addRemark,
        errorMsg: tools.errorMsg,
        imageList: tools.imageList,
        waresnapshot: tools.waresnapshot,
        getrderUnpaid: function (pageInfo) {
            this.$http.get('/order/notPayOrder?index='+pageInfo.index+'&pageSize='+pageInfo.pageSize).then(function(response){
                console.log(response.data);
                if(response.data.totalItem){
                    this.orderUnpaid = response.data;
                    var orderIds = {
                        orderIds: []
                    };
                    var skuIds = [];
                    for(var i=0;i<this.orderUnpaid.pageList.length;i++){
                        orderIds.orderIds.push(this.orderUnpaid.pageList[i].orderId);
                        for(var j=0;j<this.orderUnpaid.pageList[i].popOrderItemVoList.length;j++){
                            skuIds.push(this.orderUnpaid.pageList[i].popOrderItemVoList[j].skuId);
                        }
                    }
                    this.getRemarks(orderIds);
                    this.imageList(skuIds);
                }else{
                    this.$Modal.error({
                        content: '暂时没有数据！'
                    });
                }
            },function (error) {
                this.errorMsg(error);
            });
        },
        search: function(pageInfo){
            console.log(this.querys);
            var orderId = '';
            var time = '';

            if(this.orderCreateDateTime.length>0){
                var orderCreateDateStart = this.orderCreateDateTime[0];
                var orderCreateDateEnd = this.orderCreateDateTime[1];
                time = '&orderCreateDateStart=' + this.formatDate(orderCreateDateStart) + '&orderCreateDateEnd=' + this.formatDate(orderCreateDateEnd);;
            }
            if(this.querys.orderId){
                orderId = '&orderId='+this.querys.orderId;
            }

            this.$http.get('/order/notPayOrder?index='+pageInfo.index+'&pageSize='+pageInfo.pageSize+orderId+time).then(function(response){
                console.log(response.data);
                this.orderUnpaid = response.data;
                this.isSearch = true;
            },function (error) {
                this.errorMsg(error);
            });
        },
        addBtn: function (orderId) {
            var orderIds = {
                orderIds: [orderId]
            };
            this.modal2 = true;
            this.getRemarks(orderIds,this.modal2);
            this.orderRemarkVO = {
                flagMark: this.orderRemarkVO.flagMark,
                orderId: orderId,
                orderProduceStatus: 2,
                remark: this.orderRemarkVO.remark
            };
            console.log(this.orderRemarkVO);
        },
        pageChange: function (pageNum) {
            var pageInfo = {
                index: pageNum,
                pageSize: this.pageInfo.pageSize,
                totalItem: this.orderUnpaid.totalItem,
                totalPage: this.orderUnpaid.totalPage
            };

            if(this.isSearch){
                this.search(pageInfo);
            }else{
                this.getrderUnpaid(pageInfo);
            }
        },
        pageSizeChange: function (pageSize) {
            var pageInfo = {
                index: 1,
                pageSize: pageSize,
                totalItem: this.orderUnpaid.totalItem,
                totalPage: this.orderUnpaid.totalPage
            };

            if(this.isSearch){
                this.search(pageInfo);
            }else{
                this.getrderUnpaid(pageInfo);
            }
        },
        handleReset: function() {
            this.$refs.query.resetFields();
            this.querys = {
                orderId: ''
            };
            this.orderCreateDateTime = [];
        },
    },
    ready: function () {
        this.getrderUnpaid(this.pageInfo);
    }
};

/*等待出库*/
var orderInstoreData = {
    data: function (){
        return {
            orderInstore: {},
            deliveryCompanyVOList: [],
            deliveryCompanyVO: [],
            images: [],
            orderRemarkVO: {
                flagMark: 0,
                orderId: 0,
                orderProduceStatus: 0,
                remark: ''
            },
            querys: {
                orderId: '',
                paymentType: '',
                consName: '',
                consMobilePhone: '',
                pin: '',
                skuId: '',
                skuName: '',
                orderSumStart: 0,
                orderSumEnd: 0
            },
            isSearch:false,
            select_all: false,
            all: [],
            urlName: datas.urlName,
            pageInfo: {
                index: 1,
                pageSize: 10,
                totalItem: 0,
                totalPage: 1
            },
            modal1: false,
            modal2: false,
            orderCreateDateTime: [],
            paymentTypeList: datas.paymentTypeList,
            rules: {
                orderId: [
                    {
                        pattern: /^[0-9]*$/,
                        message: '该项只能输入数字！'
                    }
                ],
                consMobilePhone: [
                    {
                        pattern: /^1[3|4|5|7|8][0-9]{9}$/,
                        message: '请输入正确的手机号！'
                    }
                ],
                consName: [
                    {
                        pattern: /^[a-zA-Z0-9\u4e00-\u9fa5]*$/,
                        message: '本项不能输入符号！'
                    }
                ],
                skuId: [
                    {
                        pattern: /^[0-9]*$/,
                        message: '该项只能输入数字！'
                    }
                ],
                skuName: [
                    {
                        pattern: /^[a-zA-Z0-9\u4e00-\u9fa5]*$/,
                        message: '本项不能输入符号！'
                    }
                ]
            }
        }
    },
    methods:{
        formatDate: tools.formatDate,
        handleSubmit:tools.handleSubmit,
        getRemarks: tools.getRemarks,
        addRemark: tools.addRemark,
        errorMsg: tools.errorMsg,
        batchRemark: tools.batchRemark,
        imageList: tools.imageList,
        waresnapshot: tools.waresnapshot,
        batchaddRemark: tools.batchaddRemark,
        addDefault: tools.addDefault,
        cancel: tools.cancel,
        getData: function () {
            var orderIds = {
                orderIds: []
            };
            var skuIds = [];
            for(var i=0;i<this.orderInstore.pageList.length;i++){
                orderIds.orderIds.push(this.orderInstore.pageList[i].orderId);
                for(var j=0;j<this.orderInstore.pageList[i].popOrderItemVoList.length;j++){
                    skuIds.push(this.orderInstore.pageList[i].popOrderItemVoList[j].skuId);
                }
            }
            this.getRemarks(orderIds,false,true);
            this.imageList(skuIds);
        },
        getOrderInstore: function (pageInfo) {
            this.$http.get('/order/waitOutOrder?index='+pageInfo.index+'&pageSize='+pageInfo.pageSize).then(function(response){
                console.log(response.data.paginatedInfoVO);
                if(response.data.paginatedInfoVO.totalItem){
                    for(var i in response.data.paginatedInfoVO.pageList){
                        var list = response.data.paginatedInfoVO.pageList[i];
                        list.remark= '';
                        list.isRemark= '';
                    }
                    this.orderInstore = response.data.paginatedInfoVO;
                    this.deliveryCompanyVOList = response.data.deliveryCompanyVOList;
                    this.addDefault(response.data.paginatedInfoVO.pageList);
                    this.getData();
                }else{
                    this.$Modal.error({
                        content: '暂时没有数据！'
                    });
                }
            },function (error) {
                this.errorMsg(error);
            });
        },
        search: function(pageInfo){
            console.log(this.querys);
            var parameters = '';
            var time = '';

            if(this.orderCreateDateTime.length>0){
                var orderCreateDateStart = this.orderCreateDateTime[0];
                var orderCreateDateEnd = this.orderCreateDateTime[1];

                time = '&orderCreateDateStart=' + this.formatDate(orderCreateDateStart) + '&orderCreateDateEnd=' + this.formatDate(orderCreateDateEnd);;
            }
            for(var key in this.querys){
                if(this.querys[key]){
                    parameters = parameters + '&' + key + '=' + this.querys[key];
                }
            }
            this.$http.get('/order/waitOutOrder?index='+pageInfo.index+'&pageSize='+pageInfo.pageSize+parameters+time).then(function(response){
                console.log(response.data);
                if(response.data.paginatedInfoVO.totalItem){
                    for(var i in response.data.paginatedInfoVO.pageList){
                        var list = response.data.paginatedInfoVO.pageList[i];
                        list.remark= '';
                        list.isRemark= '';
                    }
                    this.orderInstore = response.data.paginatedInfoVO;
                    this.isSearch = true;
                    this.addDefault(response.data.paginatedInfoVO.pageList);
                    this.getData();
                }else{
                    this.orderInstore = {};
                    this.$Modal.error({
                        content: '没有查询到数据！'
                    });
                }
            },function (error) {
                this.errorMsg(error);
            });
        },
        selectAll: function () {
            if(!this.select_all){
                for(var i=0;i<this.orderInstore.pageList.length;i++){
                    this.all.push(this.orderInstore.pageList[i].orderId);
                }
            }else{
                this.all = [];
            }
        },
        addBtn: function (orderId) {
            var orderIds = {
                orderIds: [orderId]
            };
            this.modal1 = true;
            this.getRemarks(orderIds,this.modal1);
            this.orderRemarkVO = {
                flagMark: this.orderRemarkVO.flagMark,
                orderId: orderId,
                orderProduceStatus: 1,
                remark: this.orderRemarkVO.remark
            };
            console.log(this.orderRemarkVO);
        },
        handleReset: function() {
            this.$refs.query.resetFields();
            this.querys = {
                orderId: '',
                paymentType: '',
                consName: '',
                consMobilePhone: '',
                pin: '',
                skuId: '',
                skuName: '',
                orderSumStart: 0,
                orderSumEnd: 0
            };
        },
        pageChange: function (pageNum) {
            var pageInfo = {
                index: pageNum,
                pageSize: this.pageInfo.pageSize,
                totalItem: this.orderInstore.totalItem,
                totalPage: this.orderInstore.totalPage
            };
            this.select_all = false;
            this.all = [];
            if(this.isSearch){
                this.search(pageInfo);
            }else{
                this.getOrderInstore(pageInfo);
            }
        },
        pageSizeChange: function (pageSize) {
            this.pageInfo.pageSize = pageSize;
            var pageInfo = {
                index: 1,
                pageSize: pageSize,
                totalItem: this.orderInstore.totalItem,
                totalPage: this.orderInstore.totalPage
            };
            if(this.isSearch){
                this.search(pageInfo);
            }else{
                this.getOrderInstore(pageInfo);
            }
        }
    },
    watch: {
        'all': function (val,oldVal) {
            if(val.length == this.orderInstore.pageList.length){
                this.select_all = true;
            }else{
                this.select_all = false;
            }
        }
    },
    ready: function () {
        this.getOrderInstore(this.pageInfo);
    }
};

/*订单详情*/
var orderDetailData = {
    data: function () {
        return {
            detail: {},
            visible: {
                consMobilePhone: false,
                consPhone: false
            },
            isNull: true,
            orderStatu: '',
            trackList: [],
            consMobilePhone: '',
            consPhone: '',
            items:[],
            images: [],
            waresThead: datas.waresThead
        }
    },
    methods: {
        waresnapshot: tools.waresnapshot,
        imageList: tools.imageList,
        errorMsg: tools.errorMsg,
        crumbs: function () {
            $(".h-crumbs-list .crumbs-item:last-child").text("订单详情");
        },
        getDetail: function () {
            var orderId = $(".order_num").attr('orderId');
            this.crumbs();
            this.$http.get('/order/detail/'+orderId).then(function(response){
                console.log(response.data);
                for(var k in response.data.items){
                    var items = response.data.items[k];
                    items.image= '';
                }
                this.detail = response.data;
                this.items = this.detail.items;
                switch (this.detail.orderStatus){
                    case 0: this.orderStatu = '提交订单';
                    break;
                    case 1: this.orderStatu = '等待出库';
                        break;
                    case 2: this.orderStatu = '等待收货';
                            this.detail.orderStatus = 3;
                        break;
                    case 3: this.orderStatu = '完成';
                            this.detail.orderStatus = 4;
                        break;
                }
                for(var i = 0;i<this.items.length;i++){
                    this.items[i].orderId = Number(orderId);
                    this.imageList([this.items[i].skuId],true);
                }
                if(this.detail.orderId){
                    this.getTrackList(this.detail.orderId);
                }

            },function (error) {
                this.errorMsg(error);
            });
        },
        getConsPhone: function (phone) {
            var orderId = $(".order_num").attr('orderId');
            this.$http.get('/order/detail/'+orderId+'/'+phone).then(function(response){
                if(phone == 'CONS_MOBILE_PHONE'){
                    this.consMobilePhone = response.data;
                }else{
                    this.consPhone = response.data;
                }
            });
        },
        getTrackList: function (orderId) {
            this.$http.get('/order/track/list/'+orderId).then(function(response){
                console.log(response.data);
                this.trackList = response.data;
                if(response.data.length==0){
                    this.isNull = false;
                }else{
                    this.isNull = true;
                }
            });
        },
        close: function() {
            this.visible.consMobilePhone = false;
            this.visible.consPhone = false;
        }
    },
    ready: function () {
        this.getDetail();
    }
};

/*订单出库*/
var outboundData = {
    data: function () {
        return {
            outbound: {},
            items: [],
            images: [],
            logisticsNum: {
                items: [{
                    value: '',
                    isPlus: true
                }]
            },
            isJD: false,
            isOut: true,
            isBatch: false,
            isBatchlogiCor: true,
            isCheckPrint: false,
            deliverycompanyList: [],
            orderShopLogiInfos: [{
                logiCorId: '',
                logiNo: '',
                packageNum: 1
            }],
            rules: {
                logiNo: [
                    {
                        pattern: /^[a-zA-Z0-9]*$/,
                        message: '该项只能输入数字和字母！'
                    }
                ]
            },
            outWares: datas.outWares,
            pickingItems: datas.pickingItems
        }
    },
    methods: {
        imageList: tools.imageList,
        errorMsg: tools.errorMsg,
        crumbs: function () {
            $(".h-crumbs-list .crumbs-item:last-child").text("订单出库");
        },
        getOutbound: function () {
            var orderId = $("#out_orderId").attr('orderId');
            this.crumbs();
            this.$http.get('/order/out/'+orderId).then(function(response){
                console.log(response.data);
                this.outbound = response.data;
                var skuId = [];
                for(var i=0;i<response.data.items.length;i++){
                    skuId.push(response.data.items[i].skuId);
                    this.items.push({
                        skuId: response.data.items[i].skuId,
                        image: '',
                        itemId: response.data.items[i].itemId,
                        name: response.data.items[i].name,
                        num: response.data.items[i].num,
                        jingDongPrice: response.data.items[i].jingDongPrice,
                        point: response.data.items[i].point,
                        sum: response.data.items[i].sum
                    });
                }
                this.imageList(skuId,true);
                this.checkPrint($("#logistics").attr('logistics'),this.isBatchlogiCor);
            },function (error) {
                this.errorMsg(error);
            });
        },
        getdeliverycompany: function () {
            var logistics = $("#logistics").attr('logistics');
            this.$http.get('/deliverycompany/list').then(function(response){
                console.log(response.data);
                this.deliverycompanyList = response.data;
                this.orderShopLogiInfos[0].logiCorId = +logistics;
            });
        },
        jddelivery: function () {
            this.$http.get('/order/jddelivery/pre/'+this.outbound.orderId).then(function(response){
                console.log(response);
                this.orderShopLogiInfos[0].logiNo = response.body;
            },function (error) {
                this.$Message.info('运单号获取失败！', 2);
            });
        },
        addLogistics: function (index) {
            if(this.orderShopLogiInfos[index].logiCorId != 2087){
                console.log(this.orderShopLogiInfos[index].logiCorId);
                console.log(this.orderShopLogiInfos[index].logiNo);
                console.log(this.orderShopLogiInfos);
                if(this.orderShopLogiInfos.length>0){
                    this.isBatchlogiCor = false;
                }else{
                    this.isBatchlogiCor = true;
                }
                if(this.orderShopLogiInfos[index].logiCorId && this.orderShopLogiInfos[index].logiNo){
                    this.logisticsNum.items[index].isPlus = false;
                    this.isJD = true;
                    for(var i in this.deliverycompanyList){
                        if(this.deliverycompanyList[i].deliveryId == 2087){
                            this.deliverycompanyList.splice(i, 1);
                        }
                    }
                    this.logisticsNum.items.push({
                        value: '',
                        isPlus: true
                    });
                    this.orderShopLogiInfos.push({
                        logiCorId: '',
                        logiNo: '',
                        packageNum: 1
                    });
                }else{
                    this.$Message.info('请选择物流公司并填写运单号！', 2);
                }
            }
        },
        handleRemove: function(index) {
            if(this.orderShopLogiInfos.length==2){
                this.isBatchlogiCor = true;
                this.isJD = false;
                this.deliverycompanyList.push({
                    deliveryId: 2087,
                    deliveryName: "京东快递"
                });
            }
            this.logisticsNum.items.splice(index, 1);
            this.orderShopLogiInfos.splice(index,1);
        },
        print: function () {
            var wuLiuId = this.orderShopLogiInfos[0].logiCorId;
            console.log(this.isBatch);
            for(var i=0;i<this.orderShopLogiInfos.length;i++){
                for(var j=i+1;j<this.orderShopLogiInfos.length;j++){
                    if(this.orderShopLogiInfos[i].logiCorId != this.orderShopLogiInfos[j].logiCorId) {
                        this.isBatch = true;
                        return;
                    }
                }
            }
            if(this.isBatch){
                this.$Message.info('不支持多库打印，请前往"打印快递单"页面打印！', 2);
            }else{
                var orderId = this.outbound.orderId;
                window.open('/order/print?orderId=' + orderId + '&wuLiuId=' + wuLiuId + '&packNum=' + this.orderShopLogiInfos[0].packageNum + '&preview=false');
            }
        },
        outBtn: function () {
            var index = this.orderShopLogiInfos.length-1;

            if(index>0){
                if(this.orderShopLogiInfos[index].logiCorId == '' || this.orderShopLogiInfos[index].logiNo == ''){
                    this.orderShopLogiInfos.length=index;
                }
            }
            if(this.orderShopLogiInfos[0].logiCorId == '' || this.orderShopLogiInfos[0].logiNo == ''){
                this.$Message.info('请选择物流公司并填写运单号！', 2);
            }else{
                var orderShipVO = {
                    orderId: this.outbound.orderId,
                    orderShopLogiInfos: this.orderShopLogiInfos
                };

                console.log(orderShipVO);
                this.$http.post('/order/out/one',orderShipVO).then(function (response) {
                    console.log(response);
                    if(response.ok){
                        this.$Modal.success({
                            title: '订单出库',
                            content: '出库成功！'
                        });
                        this.isOut = false;
                    }
                }, function (error) {
                    this.errorMsg(error);
                });
            }
        },
        returnBtn: function () {
            $(".h-crumbs-list .crumbs-item:last-child").text("订单列表");
            window.location.href = '/menu/order/orderManage#!/orderInstore';
        },
        checkPrint:function (wuliuId,isBatchlogiCor) {
            if(wuliuId == 2087){
                this.isBatchlogiCor = true;
                // this.logisticsNum.items.splice(0, this.orderShopLogiInfos.length-1);
                // this.orderShopLogiInfos.splice(0, this.orderShopLogiInfos.length-1);
                this.jddelivery();
            }
            if(isBatchlogiCor){
                this.$http.get('/order/print/'+wuliuId).then(function (response) {
                    console.log(response.data);
                    this.isCheckPrint = response.data;
                });
            }
        }
    },
    ready: function () {
        this.getOutbound();
        this.getdeliverycompany();
    }
};

/*合并出库*/
var mergeOutboundData = {
    data: function () {
        return {
            detail: {},
            visible: {
                consMobilePhone: false,
                consPhone: false
            },
            trackList: [],
            consMobilePhone: '',
            consPhone: '',
            waresThead: datas.waresThead
        }
    },
    methods: {
        errorMsg: tools.errorMsg,
        getDetail: function () {
            var orderId = $(".order_num").attr('orderId');
            this.$http.get('/order/detail/'+orderId).then(function(response){
                console.log(response.data);
                this.detail = response.data;
                this.getTrackList(this.detail.orderId);
            },function (error) {
                this.errorMsg(error);
            });
        },
        getConsPhone: function (phone) {
            var orderId = $(".order_num").attr('orderId');
            this.$http.get('/order/detail/'+orderId+'/'+phone).then(function(response){
                console.log(response.data);
                if(phone == 'CONS_MOBILE_PHONE'){
                    this.consMobilePhone = response.data;
                }else{
                    this.consPhone = response.data;
                }
            });
        },
        getTrackList: function (orderId) {
            this.$http.get('/order/track/list/'+orderId).then(function(response){
                console.log(response.data);
                this.trackList = response.data;
            });
        },
        close: function() {
            this.visible.consMobilePhone = false;
            this.visible.consPhone = false;
        }
    },
    ready: function () {
        this.getDetail();
    }
};

/*批量出库*/
var batchOutboundData = {
    data: function () {
        return {
            detail: {},
            visible: {
                consMobilePhone: false,
                consPhone: false
            },
            trackList: [],
            consMobilePhone: '',
            consPhone: '',
            waresThead: datas.waresThead
        }
    },
    methods: {
        errorMsg: tools.errorMsg,
        getDetail: function () {
            var orderId = $(".order_num").attr('orderId');
            this.$http.get('/order/detail/'+orderId).then(function(response){
                console.log(response.data);
                this.detail = response.data;
                this.getTrackList(this.detail.orderId);
            },function (error) {
                this.errorMsg(error);
            });
        },
        getConsPhone: function (phone) {
            var orderId = $(".order_num").attr('orderId');
            this.$http.get('/order/detail/'+orderId+'/'+phone).then(function(response){
                console.log(response.data);
                if(phone == 'CONS_MOBILE_PHONE'){
                    this.consMobilePhone = response.data;
                }else{
                    this.consPhone = response.data;
                }
            });
        },
        getTrackList: function (orderId) {
            this.$http.get('/order/track/list/'+orderId).then(function(response){
                console.log(response.data);
                this.trackList = response.data;
            });
        },
        close: function() {
            this.visible.consMobilePhone = false;
            this.visible.consPhone = false;
        }
    },
    ready: function () {
        this.getDetail();
    }
};
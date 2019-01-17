var vm = new Vue({
    el: '#afs_refundment_list',
    mixins: [validate,methods],
    data: {
        tableData: [],
        page: {
            pageNo: 1,
            pageSize: 10,
            totalItem: 0
        },
        metadata: {
            statusList: null,
            popVenderOrderDomainName: null,
            containsFBPModel: null
        },
        query: {
            tabIndex: "0",
            refundmentApplyQuery: {
                orderId: "",
                applyTimeStartDate: "",
                applyTimeEndDate: "",
                buyerId: "",
                checkTimeStartDate: "",
                checkTimeEndDate: "",
                buyerName: "",
                status: ""
            }
        },
        isLoaded: false
    },
    ready: function(){
        this.initData();
        this.searchData(this.query);
    },
    methods: {
        initData: function () {
            var _this = this;
            var page = _this.page;
            var query = _this.query;
            var queryRoute = _this.getUrlParam();
            page.pageNo = queryRoute.pageNo ? parseInt(queryRoute.pageNo) : page.pageNo;
            page.pageSize = queryRoute.pageSize ? parseInt(queryRoute.pageSize) : page.pageSize;
            if (queryRoute["refundmentApplyQuery[orderId]"]) query.refundmentApplyQuery.orderId = queryRoute["refundmentApplyQuery[orderId]"];
            if (queryRoute["refundmentApplyQuery[applyTimeStartDate]"]) query.refundmentApplyQuery.applyTimeStartDate = _this.getDate(parseInt(queryRoute["refundmentApplyQuery[applyTimeStartDate]"]));
            if (queryRoute["refundmentApplyQuery[applyTimeEndDate]"]) query.refundmentApplyQuery.applyTimeEndDate = _this.getDate(parseInt(queryRoute["refundmentApplyQuery[applyTimeEndDate]"]));
            if (queryRoute["refundmentApplyQuery[buyerId]"]) query.refundmentApplyQuery.buyerId = queryRoute["refundmentApplyQuery[buyerId]"];
            if (queryRoute["refundmentApplyQuery[checkTimeStartDate]"]) query.refundmentApplyQuery.checkTimeStartDate = _this.getDate(parseInt(queryRoute["refundmentApplyQuery[checkTimeStartDate]"]));
            if (queryRoute["refundmentApplyQuery[checkTimeEndDate]"]) query.refundmentApplyQuery.checkTimeEndDate = _this.getDate(parseInt(queryRoute["refundmentApplyQuery[checkTimeEndDate]"]));
            if (queryRoute["refundmentApplyQuery[buyerName]"]) query.refundmentApplyQuery.buyerName = queryRoute["refundmentApplyQuery[buyerName]"];
            if (queryRoute["refundmentApplyQuery[status]"]) query.refundmentApplyQuery.status = parseInt(queryRoute["refundmentApplyQuery[status]"]);
            if (queryRoute.tabIndex) query.tabIndex = queryRoute.tabIndex;
        },
        goPage: function(val){
            this.query.tabIndex = val;
            this.query.refundmentApplyQuery.status = "";
            this.search();
        },
        search: function(){
            var _this = this;
            this.$refs.query.validate(function(valid){
                if(valid){
                    if (!_this.check()) return;
                    _this.page.pageNo = 1;
                    _this.pageChange();
                }else{
                    _this.$Message.error('验证未通过哦~');
                }
            });
        },
        reset: function(){
            // this.$refs.query.resetFields();
            this.query.refundmentApplyQuery.applyTimeStartDate = "";
            this.query.refundmentApplyQuery.applyTimeEndDate = "";
            this.query.refundmentApplyQuery.buyerId = "";
            this.query.refundmentApplyQuery.checkTimeStartDate = "";
            this.query.refundmentApplyQuery.checkTimeEndDate = "";
            this.query.refundmentApplyQuery.buyerName = "";
            this.query.refundmentApplyQuery.status = "";
        },
        getData: function(query){
            // 处理日期类型
            var param = {};
            Vue.util.extend(param, query);
            Vue.util.extend(param.refundmentApplyQuery = {}, query.refundmentApplyQuery);
            var refundmentApplyQuery = param.refundmentApplyQuery;
            for (prop in refundmentApplyQuery) {
                var val = refundmentApplyQuery[prop];
                if (val instanceof Date) {
                    refundmentApplyQuery[prop] = val.getTime();
                }
            }
            // console.log(param);
            var paramSerialize = decodeURIComponent(jQuery.param(param));
            // console.log(paramSerialize);

            var url = "/menu/afs/after/refundment/list?" + paramSerialize;
            // window.history.pushState(null, null, url);
            location.href = url;
        },
        searchData: function(query) {
            var _this = this;

            var queryTemp = {};
            Vue.util.extend(queryTemp, query);
            Vue.util.extend(queryTemp.refundmentApplyQuery = {}, query.refundmentApplyQuery);
            var refundmentApplyQuery = queryTemp.refundmentApplyQuery;
            for (prop in refundmentApplyQuery) {
                var val = refundmentApplyQuery[prop];
                if (val instanceof Date) {
                    refundmentApplyQuery[prop] = _this.formatDate(val);
                }
            }

            api.search(queryTemp,
                function(data){
                    // meatedata
                    _this.metadata.statusList = data.statusList;
                    // var statusList = data.statusList;
                    // if (statusList && statusList.length > 0) {
                    //     var metadata_statusList = new Array();
                    //     statusList.forEach(function (val) {
                    //         metadata_statusList.push({
                    //             key: val[0],
                    //             value: val[1]
                    //         });
                    //     });
                    //     _this.metadata.statusList = metadata_statusList;
                    // }
                    // else {
                    //     _this.metadata.statusList = null;
                    // }
                    _this.metadata.popVenderOrderDomainName = data.popVenderOrderDomainName;
                    _this.metadata.containsFBPModel = data.containsFBPModel;

                    var totalItem = 0;
                    var tableData = [];
                    if (data.refundment) {
                        totalItem = data.refundment.totalItem;
                        data.refundmentList.forEach(function(val){
                            if(val){
                                // 申请日期
                                val.applyTimeStr = _this.formatDateTime(val.applyTime);
                                // 审核日期
                                val.checkTimeStr = _this.formatDateTime(val.checkTime);
                                // 订单类型
                                switch (val.orderType) {
                                    case "21":
                                        val.orderTypeName = "FBP";
                                        break;
                                    case "22":
                                        val.orderTypeName = "SOP";
                                        break;
                                    default:
                                        val.orderTypeName = "其他";
                                }
                                // 审核状态
                                switch (val.status) {
                                    case "0":
                                        val.statusName = "待审核";
                                        break;
                                    case "1":
                                        val.statusName = "商家审核通过";
                                        break;
                                    case "2":
                                        val.statusName = "商家审核不通过";
                                        break;
                                    case "3":
                                        val.statusName = "退款成功";
                                        break;
                                    case "4":
                                        val.statusName = "退款失败";
                                        break;
                                    case "6":
                                        val.statusName = "商家审核通过并拦截";
                                        break;
                                    case "7":
                                        val.statusName = "京东拦截成功";
                                        break;
                                    case "8":
                                        val.statusName = "京东拦截失败";
                                        break;
                                    case "9":
                                        val.statusName = "强制关单";
                                        break;
                                }
                                // 超时自动审核时间
                                val.checkTimeMinutesStr = _this.formatDateTimeMinutes(val.checkTime);

                                tableData.push(val);
                            }
                        });
                    }
                    _this.page.totalItem = totalItem;
                    _this.tableData = tableData;
                    _this.isLoaded = true;
                },
                function(){
                    console.log('报错了')
                }
            )
        },
        check: function () {
            //申请开始时间
            var applyTimeStartDate = this.query.refundmentApplyQuery.applyTimeStartDate;
            //申请结束时间
            var applyTimeEndDate = this.query.refundmentApplyQuery.applyTimeEndDate;
            if(applyTimeStartDate && applyTimeEndDate){
                if(applyTimeStartDate > applyTimeEndDate){
                    alert("申请开始时间不能大于申请结束时间");
                    return false;
                }
            }

            //审核开始时间
            var checkTimeStartDate = this.query.refundmentApplyQuery.checkTimeStartDate;
            //审核结束时间
            var checkTimeEndDate = this.query.refundmentApplyQuery.checkTimeEndDate;
            if(checkTimeStartDate && checkTimeEndDate){
                if(checkTimeStartDate > checkTimeEndDate){
                    alert("审核开始时间不能大于审核结束时间");
                    return false;
                }
            }
            return true;
        }
    }
})
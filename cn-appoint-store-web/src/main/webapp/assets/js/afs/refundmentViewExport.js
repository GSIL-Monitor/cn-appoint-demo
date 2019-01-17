var vm = new Vue({
    el: '#afs_refundment_export_list',
    mixins: [validate,methods],
    data: {
        tableData: [],
        page: {
            pageNo: 1,
            pageSize: 10,
            totalItem: 0
        },
        metadata: {
            statusList: null
        },
        query: {
            exportQuery: {
                applyTimeStartDate: null,
                applyTimeEndDate: null,
                checkTimeStartDate: null,
                checkTimeEndDate: null,
                status: ""
            }
        },
        commitTimes: 0,
        storeHomeUrl: null,
        isLoaded: false
    },
    ready: function(){
        this.pageChange();
    },
    methods: {
        export: function () {
            var isNull = function (val) {
                return jQuery.trim(val).length == 0;
            }
            var _this = this;
            this.$refs.query.validate(function(valid){
                if(valid){
                    var exportQuery = _this.query.exportQuery;
                    var applyTimeStartDate = exportQuery.applyTimeStartDate;
                    var applyTimeEndDate = exportQuery.applyTimeEndDate;
                    var checkTimeStartDate = exportQuery.checkTimeStartDate;
                    var checkTimeEndDate = exportQuery.checkTimeEndDate;
                    if(!applyTimeStartDate && !applyTimeEndDate && !checkTimeStartDate && !checkTimeEndDate) {
                        alert("请至少输入申请日期或审核日期");
                        return false;
                    }
                    if(!isNull(applyTimeStartDate) && isNull(applyTimeEndDate)) {
                        alert("请输入申请结束日期");
                        return false;
                    }
                    if(isNull(applyTimeStartDate) && !isNull(applyTimeEndDate)) {
                        alert("请输入申请开始日期");
                        return false;
                    }
                    if(!isNull(checkTimeStartDate) && isNull(checkTimeEndDate)) {
                        alert("请输入审核结束日期");
                        return false;
                    }
                    if(isNull(checkTimeStartDate) && !isNull(checkTimeEndDate)) {
                        alert("请输入审核开始日期");
                        return false;
                    }

                    // 审核起始时间与结束时间都不为空
                    if(!isNull(checkTimeStartDate) && !isNull(checkTimeEndDate)){

                        var tempStartDate = checkTimeStartDate;
                        var tempEndDate = checkTimeEndDate;

                        if(tempStartDate > tempEndDate){
                            alert("审核开始日期不能大于结束日期!");
                            return false;
                        }

                        var days= tempEndDate.getTime() - tempStartDate.getTime();
                        var tempTime = parseInt(days/(3600*24*1000))+1;
                        if(tempTime > 31){
                            alert("审核开始日期与结束日期跨越时间不能超过31天!");
                            return false;
                        }
                    }

                    // 申请起始时间与结束时间都不为空
                    if(!isNull(applyTimeStartDate) && !isNull(applyTimeEndDate)){

                        var tempStartDate = applyTimeStartDate;
                        var tempEndDate = applyTimeEndDate;

                        if(tempStartDate > tempEndDate){
                            alert("申请开始日期不能大于结束日期!");
                            return false;
                        }

                        var days= tempEndDate.getTime() - tempStartDate.getTime();
                        var tempTime = parseInt(days/(3600*24*1000))+1;
                        if(tempTime > 31){
                            alert("申请开始日期与结束日期跨越时间不能超过31天!");
                            return false;
                        }
                    }

                    _this.commitTimes++;
                    if(_this.commitTimes==1){
                        var param = {};
                        Vue.util.extend(param.exportQuery = {}, _this.query.exportQuery);
                        var exportQuery = param.exportQuery;
                        for (prop in exportQuery) {
                            var val = exportQuery[prop];
                            if (val instanceof Date) {
                                exportQuery[prop] = _this.formatDate(val);
                            }
                        }
                        // console.log(param);
                        api.handleExport(param,
                            function(data){
                                var result = data.result;
                                try {
                                    var code = parseInt(result);
                                    if(code == 1){
                                        alert("没有符合的记录!");
                                        return;
                                    }else if(code == 2) {
                                        alert("系统已开始生成文件,请5分钟后查看下载!");
                                        return;
                                    }else if(code == 3) {
                                        alert("导出取消订单失败!");
                                        return;
                                    }else if(code == 4) {
                                        alert("申请开始日期不能大于结束日期!");
                                    }else if(code == 5){
                                        alert("申请开始日期与结束日期跨越时间不能超过31天!");
                                    }
                                } catch (e) {
                                    alert("导出取消订单失败!");
                                }
                            },
                            function(){
                                console.log('报错了')
                            },
                            true
                        );
                        _this.commitTimes=0;
                    } else{
                        alert("你已经提交2次，如果继续请刷新页面。");
                    }
                }else{
                    _this.$Message.error('验证未通过哦~');
                }
            });
        },
        search: function(pageNo){
            var _this = this;
            this.$refs.query.validate(function(valid){
                if(valid){
                    _this.query.pageNo = _this.page.pageNo = pageNo ? pageNo : 1;
                    _this.pageChange();
                }else{
                    _this.$Message.error('验证未通过哦~');
                }
            });
        },
        getData: function(query){
            var _this = this;

            var queryTemp = {};
            Vue.util.extend(queryTemp, query);
            Vue.util.extend(queryTemp.exportQuery = {}, query.exportQuery);
            var exportQuery = queryTemp.exportQuery;
            for (prop in exportQuery) {
                var val = exportQuery[prop];
                if (val instanceof Date) {
                    exportQuery[prop] = _this.formatDate(val);
                }
            }
            api.searchExport(queryTemp,
                function(data){
                    // meatedata
                    if (!_this.metadata.statusList) {
                        _this.metadata.statusList = data.statusList;
                    }
                    var dataList = data.dataList;
                    var tableData = [];
                    if (dataList) {
                        dataList.forEach(function(val){
                            if(val){
                                tableData.push(val);
                            }
                        });
                    }
                    _this.page.totalItem = data.totalItem;
                    _this.tableData = tableData;
                    _this.storeHomeUrl = data.storeHomeUrl;
                    _this.isLoaded = true;
                },
                function(){
                    console.log('报错了')
                }
            );
        },
        refresh: function () {
            this.search(this.page.pageNo);
        },
        back: function () {
            window.location.href = "/menu/afs/after/refundment/list";
        }
    }
})

new Vue({
    el: 'body',
    data: function() {
        return {
            headTitleArray: ['SKU编码', '商品编码', '货号', '商品名称', '库存总数', '已占用数'],
            selfDataList: [],   //当前页面列表数据
            selfDataListCopy: [],   //当前页面列表数据拷贝
            selfIdList: [],     //被选择的id集合
            selfObjList: [],    //被选择的obj集合
            allCheckStatus: false,
            query: {
                sku: null,
                wareName: null,
                skuStatus: 1
            },
            current : 1,
            pageSize: 10,
            total: null,
            tableData: []
        }
    },
    methods: {
        search: function(page) {
            var json = JSON.parse(JSON.stringify(this.query));
            page && (this.current = page);
            json.pageNo = this.current;
            json.pageSize = this.pageSize;
            api.fetchStoreStockInfoList(json, function (data) {
                    if (data.result && data.data) {
                        var disabledIds = data.data.skuList.map(function(item) {
                            return parseInt(item.skuId)
                        });
                        this.total = data.data.totalItem;
                        //翻页时，直接置空被选择的数组
                        this.selfIdList = [];
                        this.selfObjList = [];
                        this.allCheckStatus = false;

                        this.selfDataList = data.data.itemList;

                        //创建一个stockNumCopy属性，用于对比这个sku的库存是否被修改过
                        for (var i = 0, len = this.selfDataList.length; i < len; i++) {
                            this.selfDataList[i].stockNumCopy = this.selfDataList[i].stockNum;
                            Vue.set(this.selfDataList[i], "_message", "");
                            Vue.set(this.selfDataList[i], "_valid", true);
                            if (disabledIds.indexOf(parseInt(this.selfDataList[i].skuId)) > -1) {
                                Vue.set(this.selfDataList[i], "_disabled", true)
                            } else {
                                Vue.set(this.selfDataList[i], "_disabled", false)
                            }
                           
                        }
                        this.selfDataListCopy = JSON.parse(JSON.stringify(this.selfDataList));
                    }
                }.bind(this),
                function () {
                    _this.$Message.error('服务器错误');
                });

        },
        checkAll: function() {
            for (var i = 0, len = this.selfDataList.length; i < len; i++) {
                Vue.set(this.selfDataList[i], 'checked', this.allCheckStatus)
                this.setSelfIdList(this.selfDataList[i])
            }
        },
        check: function(item) {
            this.setSelfIdList(item);
            this.checkIsAllCheck();
        },
        checkIsAllCheck: function() {
            for (var i = 0, len = this.getDataList.length; i < len; i++) {
                if (this.getDataList[i].checked === false) {
                    return this.allCheckStatus = false;
                }
            }
            return this.allCheckStatus = true;
        },
        searchStore: function(page) {
            this.search(page)
        },
        setSelfIdList: function(item) {
            var index = this.selfIdList.indexOf(parseInt(item.skuId));

            if (item.checked) {
                if (!(index > -1)){
                    this.selfIdList.push(parseInt(item.skuId));
                    this.selfObjList.push(item);
                }
            } else {
                for (var i = 0, len = this.selfDataListCopy.length; i < len; i++) {
                    if (parseInt(item.skuId) === parseInt(this.selfDataListCopy[i].skuId)) {
                        item.stockNum = this.selfDataListCopy[i].stockNum;
                        this.NumChange(item);
                        break;
                    }
                }
                if (index > -1){
                    this.selfIdList.splice(index, 1);
                    this.selfObjList.splice(index, 1);
                }
            }
        },
        NumChange: function(item) {
            item.stockNum = item.stockNum && item.stockNum.toString().replace(/[^0-9]/g, '');
           /* item.stockNum = item.stockNum && item.stockNum.toString().replace(/^[0]/, '');*/
            var reg = /^[0-9]\d*$/;
            if (!reg.test(item.stockNum)) {
                item._message = '请输入有效数值';
                item._valid = false;

            } else if(item.stockNum > 999999999) {
                item._message = '不能大于999999999';
                item._valid = false;
            } else {
                item._message = "";
                item._valid = true;
            }

        },
        submitNum: function() {
            var _this = this;
            var valid = true;
            var putList = this.selfObjList.map(function(item) {
                if (item._valid === false) {
                    valid = false;
                }
                return {
                    skuId: item.skuId,
                    stockNum: parseInt(item.stockNum),
                    stockNumCopy: parseInt(item.stockNumCopy)
                }
            });

            if (!valid) {
                return;
            }

            putList = putList.filter(function(item) {
                return item.stockNumCopy !== item.stockNum;
            });

            if (putList.length) {
                api.putStoreStockNum(JSON.stringify(putList), function(data) {
                        if (data.success) {
                            var dataList = data.map.updateStockNumResult;
                            var allSuccess = true;
                            for (var i in dataList) {
                                if (dataList[i] === false) {
                                    allSuccess = false;
                                }
                            }
                            if (allSuccess) {
                                this.$Modal.success({
                                    title: '提示',
                                    content: '库存更新成功！'
                                });
                            } else {
                                this.$Modal.warning({
                                    title: '提示',
                                    content: '存在库存信息更新失败！'
                                });
                            }
                            this.search();

                        }
                    }.bind(this),
                    function(){
                        _this.$Message.error('服务器错误');
                    });
            } else {
                _this.$Message.error('您未做任何修改！');
            }

        }
    },
    computed: {
        getDataList: function() {
            var arr = [],
                thatId;
            for (var i = 0, len = this.selfDataList.length; i < len; i++) {
                arr[i] = this.selfDataList[i]
                thatId = parseInt(this.selfDataList[i].skuId)
                if (this.selfIdList.indexOf(thatId) >= 0) {
                    Vue.set(arr[i], 'checked', true)
                } else {
                    Vue.set(arr[i], 'checked', false)
                }
            }
            return arr;
        }
    },
    ready: function() {
        this.search();

    }
})
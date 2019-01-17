/**
 * Created by wanggenhua on 2017/3/23.
 */
var vm = new Vue({
    el: '.stock_list',
    mixins: [validate,methods],
    data: {
        tableTitle: thead.stockList,
        tableData: [],
        query: {
            wareId: null,
            wareName: null,
            wareStatus: '',
            sku: null,
            skuStatus: null,
            startStockNum: 0,
            endStockNum: 0
        },
        page: {
            pageNo: 1,
            total: 0,
            pageSize: 10
        },
        selectItem: [],
        searchData: {},
        stockList: [],
        isLoaded: false
    },
    ready: function(){
        this.isLoaded = true;
        var params = {
            wareId: null,
            wareName: null,
            skuStatus: null,
            sku: null,
            startStockNum: null,
            endStockNum: null,
            pageNo : 1,
            pageSize: 10
        };
        this.getData(params);
    },
    methods: {
        search: function(){
            var params = {
                wareId : this.query.wareId || null,
                wareName: this.query.wareName || null,
                skuStatus: +this.query.status || null,
                sku: this.query.sku || null,
                startStockNum: this.query.startStockNum || null,
                endStockNum: this.query.endStockNum || null
            };
            params.pageNo = 1;
            params.pageSize = this.page.pageSize;
            this.page.pageNo = 1;
            this.getData(params);
        },
        getData: function(query){
            var _this = this;
            api.stockList(
                query,
                function(data){
                    if(data.result && data.data){
                        _this.tableData = data.data.itemList;
                        _this.stockList = [];
                        _this.tableData.forEach(function(val){
                            var temp = {
                                oldSkuStock: val.oldSkuStock,
                                stockNum: val.stockNum,
                                skuId: val.skuId
                            }
                            val.stockNum = val.oldSkuStock;
                            _this.stockList.push(temp);
                        })
                        _this.page.total = data.data.totalItem;
                        _this.searchData = query;
                    }else{
                        _this.tableData = [];
                    }
                },
                function(){
                    _this.$Message.error('服务器错误');
                }
            )
        },
        saveSure: function(){
            var _this = this;
            var params = [];
            this.selectItem.forEach(function(val){
                _this.stockList.forEach(function(item){
                    if(val.skuId === item.skuId){
                        params.push(item);
                    }
                })
            })
            api.changeStock(
                JSON.stringify(params),
                function(data){
                    _this.$Message.success('保存成功');
                    setTimeout(function(){
                        _this.getData({});
                    },2000)
                },
                function(){
                    _this.$Message.error('保存失败');
                }
            )
        },
        changeStock: function(index){
            var _this = this;
            var index = index;
            setTimeout(function(){
                var val = $('.stock_num .shopweb-input-number-input').eq(index).val();
                _this.stockList[index].stockNum = +val;
            },30);
        },
        reset: function(){
            this.query = {
                wareId: null,
                wareName: null,
                wareStatus: '',
                sku: null,
                startStockNum: 0,
                endStockNum: 0
            }
        }
    }
})
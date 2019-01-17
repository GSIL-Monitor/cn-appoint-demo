/**
 * Created by wanggenhua on 2017/3/23.
 */
// Vue.http.options.emulateJSON = true;
console.log('替换成功');
var vm = new Vue({
    el: '.on_sale',
    mixins: [validate,methods],
    data: {
        tableTitle: thead.onSale,
        brands: [],
        tableData: [],
        page: {
            pageNo: 1,
            pageSize: 10,
            totalItem: 0
        },
        query: {
            onlineTime: [],
            wareId: null,
            wareName: null,
            classfiy: [],
            sku: '',
            startStockNum: 0,
            endStockNum: 0,
            brandId: '',
            startJdPrice: 0,
            endJdPrice: 0
        },
        classfiyData: [],
        searchable: true,
        searchData: {},
        modalStoreShow: false,
        modalStore:{
            stockNum: 0,
            classes: []
        },
        selectItem: [],
        editItem: [],
        isLoaded: false
    },
    ready: function(){
        this.getData({});
        this.getOthers();
        this.isLoaded = true;
    },
    methods: {
        search: function(){
            var _this = this;
            this.$refs.query.validate(function(valid){
                if(valid){
                    _this.page.pageNo = 1;
                    var param = {
                        pageNo: _this.page.pageNo,
                        pageSize: _this.page.pageSize,
                        wareId: _this.query.wareId || null,
                        brandId: _this.query.brandId || null,
                        wareName: _this.query.wareName,
                        categoryId: _this.query.classfiy[2] || null,
                        sku: _this.query.sku || null,
                        startStockNum: _this.query.startStockNum || null,
                        endStockNum: _this.query.endStockNum || null,
                        startJdPrice: _this.query.startJdPrice || null,
                        endJdPrice: _this.query.endJdPrice || null,
                        startOnlineTime:_this.query.onlineTime.length ?  _this.query.onlineTime[0] : null,
                        endOnlineTime:_this.query.onlineTime.length ? _this.query.onlineTime[1] : null
                    };
                    _this.getData(param);
                }else{
                    _this.$Message.error('验证未通过哦~');
                }
            });
        },
        edit: function(val){
            var _this = this;
            var id = this.tableData[val].wareId;
            _this.modalStore.stockNum = this.tableData[val].stockNum;
            api.getStock(id,function(data){
                if(data.result){
                    _this.editItem = [];
                    data.data.forEach(function(val){
                        var temp = {
                            stockNum: val.oldSkuStock || 0,
                            oldSkuStock: val.oldSkuStock,
                            skuId: val.skuId,
                            skuName: val.skuName,
                            pass: true
                        };
                        _this.editItem.push(temp);
                    });
                }
            })
            this.modalStoreShow = true;
        },
        editStore: function(){
            //库存编辑保存
            var query = [];
            var _this = this;
            var pass = true;
            this.editItem.forEach(function(val){
                var temp = {
                    oldSkuStock: val.oldSkuStock,
                    stockNum: val.stockNum,
                    skuId: val.skuId
                }
                if(!val.pass){
                    pass = false;
                    return;
                };
                query.push(temp);
            });
            if(!pass){
                this.modalStoreShow = true;
                return;
            }
            api.changeStock(JSON.stringify(query),
                function(data){
                    var error = [];
                    for(var i in data){
                        if(!data[i]){
                            _this.editItem.forEach(function(val){
                                if(val.skuId == i) error.push(val.skuName);
                            })
                        }
                    }
                    if(error.length){
                        _this.$Message.error('商品'+ error.join('，') +'库存修改失败',3);
                        if(error.length < _this.selectItem.length){
                            setTimeout(function(){
                                _this.getData({});
                            },2000)
                        };
                    }else{
                        _this.$Message.success('全部修改成功');
                        setTimeout(function(){
                            _this.getData({});
                        },2000)
                    }
                },
                function(){
                    _this.$Message.error('修改失败');
                }
            )
            this.modalStoreShow = false;
        },
        removeSure: function(){
            //批量删除
            var _this = this;
            if(this.selectItem.length){
                var temp = [];
                this.selectItem.forEach(function(val){
                    temp.push(val.wareId);
                })
                var error = [];
                api.removeOnSale(
                    JSON.stringify(temp),
                    function(data){
                        for(var i in data){
                            if(!data[i]) error.push(i);
                        };
                        if(error.length){
                            _this.$Message.error('商品编码为' + error.join('') + '的商品，删除失败',4);
                            if(error.length < _this.selectItem.length){
                                setTimeout(function(){
                                    _this.getData({});
                                },2000)
                            };
                        }else{
                            _this.$Message.success('删除成功',2);
                            setTimeout(function(){
                                _this.getData({});
                            },2000)
                        }
                    },
                    function(){
                        _this.$Message.error('服务器出异常',2);
                    }
                )
            }else{
                this.$Message.error('未选中任何商品',2);
            }
        },
        offLineSure: function(){
            //批量下架
            var _this = this;
            if(this.selectItem.length){
                var temp = [];
                var error = [];
                this.selectItem.forEach(function(val){
                    temp.push(val.wareId);
                });
                api.offLineOnsale(
                    JSON.stringify(temp),
                    function(data){
                        for(var i in data){
                            if(!data[i]) error.push(i);
                        };
                        if(error.length){
                            _this.$Message.error('商品编码为' + error.join('') + '的商品，下架失败',4);
                            if(error.length < _this.selectItem.length){
                                setTimeout(function(){
                                    _this.getData({});
                                },2000)
                            };
                        }else{
                            _this.$Message.success('下架成功',2);
                            setTimeout(function(){
                                _this.getData({});
                            },2000)
                        }
                    },
                    function(){
                        _this.$Message.error('服务器出异常',2);
                    }
                )
            }else{
                this.$Message.error('未选中任何商品',2);
            }
        },
        claimSure: function(){},
        reset: function(){
            this.query = {
                onlineTime: [],
                wareId: null,
                wareName: null,
                classfiy: [],
                sku: null,
                startStockNum: 0,
                endStockNum: 0,
                brandId: '',
                startJdPrice: 0,
                endJdPrice: 0
            }
        },
        getOthers: function(){
            var _this = this;
            //品牌获取
            api.getBrands(
                {},
                function(data){
                    if(data.result){
                        _this.brands = data.data;
                    }
                }
            )
            //获取类目信息
            api.getCategory(
                {},
                function(data){
                    if(data.result){
                        var arr = [];
                        var items = data.data;
                        var arrLevel1 = [];
                        var arrLevel2 = [];
                        items.forEach(function(val){
                            var level = val.lev;
                            switch(level){
                                case 1:
                                    arrLevel1.push(val);
                                    arr.push({
                                        value: val.id,
                                        label: val.name,
                                        children: []
                                    })
                                    break;
                                case 2:
                                    arrLevel2.push(val);
                                    break;
                                case 3:
                                    arrLevel2.push(val);
                                    break;
                            }
                        });
                        arr.forEach(function(val){
                            val.children = getChild(val.value,arrLevel2);
                        })
                        _this.classfiyData = arr;
                    }
                }
            );
        },
        getData: function(query){
            var _this = this;
            api.onSaleList(query,
                function(data){
                    if(data.result) {
                        var data = data.data;
                        var renderData = [];
                        data.itemList.forEach(function(val){
                            var temp = {};
                            if(val){
                                temp = {
                                    goodsName: val.title || '暂无',
                                    goodsUrl: val.logo,
                                    wareId: val.wareId,
                                    classify: val.categoryName || '暂无',
                                    stockNum: val.stockNum,
                                    jdPrice: val.jdPrice || '暂无',
                                    onlineTime: val.onlineTime,
                                    sku : val.sku,
                                    listsku: val.listsku
                                }
                                renderData.push(temp);
                            }
                        });
                        _this.tableData = renderData;
                        _this.page.totalItem = data.totalItem;
                        _this.searchData = query;
                    }else{
                        _this.page.pageNo = 1;
                        _this.tableData = [];
                        _this.page.totalItem = 0;
                    }
                },
                function(){
                    _this.$Notice.error({
                        desc: '服务器，暂时出问题了，请稍后再试！'
                    })
                }
            )
        },
        checkStock: function(item){
            var val = item.stockNum;
            item.pass = (val === parseInt(val));
        }
    }
});
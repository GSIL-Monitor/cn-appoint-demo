/**
 * Created by wanggenhua on 2017/3/23.
 */
var vm = new Vue({
    el: '.ware_basket',
    mixins: [validate,methods],
    data: {
        tableTitle: thead.wareBasket,
        tableData: [],
        query: {
            classfiy: [],
            wareId: null,
            wareName: null,
            sku: null,
            deleteTime: []
        },
        classfiyData: [],
        page: {
            pageNo: 1,
            total: 50,
            pageSize: 10
        },
        searchData: {},
        selectItem: [],
        isLoaded: false
    },
    ready: function(){
        this.getData({});
        this.getOthers();
        this.isLoaded = true;
    },
    methods:{
        getData: function(query){
            var _this = this;
            api.recoverList(
                query,
                function(data){
                    var renderData = [];
                    _this.page.total = data.data.totalItem;
                    if(data.result){
                        data.data.itemList.forEach(function(val){
                            var temp = {};
                            if(val){
                                temp = {
                                    goodsName: val.title,
                                    goodsUrl: val.logo,
                                    wareId: val.wareId,
                                    classify: val.categoryName,
                                    stockNum: val.stockNum,
                                    jdPrice: val.jdPrice,
                                    onlineTime: val.modifyTime,
                                    sku : val.sku,
                                    _checked: false,
                                    parentId: val.parentId
                                }
                                renderData.push(temp);
                            }
                        })
                        _this.tableData = renderData;
                        _this.searchData = _this.objCopy(_this.query);
                    }
                }
            )
        },
        getOthers: function(){
            var _this = this;
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
        search: function(){
            var _this = this;
            _this.page.pageNo = 1;
            this.$refs.query.validate(function(valid){
                if(valid){
                    var param = {
                        pageNo: _this.page.pageNo,
                        pageSize: _this.page.pageSize,
                        wareId: _this.query.wareId || null,
                        wareName: _this.query.wareName || null,
                        categoryId: _this.query.classfiy[2] || null,
                        sku: _this.query.sku || null,
                        startModifiedTime: _this.query.deleteTime[0] || null,
                        endModifiedTime: _this.query.deleteTime[1] || null
                    };
                    _this.getData(param);
                }else{
                    _this.$Message.error('验证未通过哦~');
                }
            });
        },
        recover: function(){
            var error3 = [];
            var error2 = [];
            var allPass = true;
            var _this = this;
            var params = [];
            this.selectItem.forEach(function(val){
                var temp = {
                    wareId : val.wareId,
                    parentId: val.parentId
                }
                params.push(temp);
            });
            api.recover(
                JSON.stringify(params),
                function(data){
                    if(data){
                        for(var i in data){
                            var code = data[i];
                            switch (code){
                                case '3':
                                    error3.push(i);
                                    allPass = false;
                                    break;
                                case '2':
                                    error2.push(i);
                                    allPass = false;
                                    break;
                            }
                        };
                        if(allPass){
                            _this.$Message.success('恢复成功',2);
                            _this.selectItem = [];
                            setTimeout(function(){
                                _this.getData({});
                            },2000)
                        }else{
                            var str = '';
                            if(error3.length && !error2.length){
                                str = '商品编码为' + error3.join('，') + '的商品，在商家端已经删除门店端无法直接恢复';
                            }
                            if(!error3.length && error2.length){
                                str = '商品编码为' + error3.join('，') + '的商品恢复失败'
                            }
                            if(error3.length && error2.length){
                                str = '商品编码为' + error3.join('，') + '的商品，在商家端已经删除门店端无法直接恢复;商品编码为' + error3.join('') + '的商品恢复失败。'
                            }
                            _this.$Message.error(str,4);
                            if(error3.length + error2.length < _this.selectItem.length){
                                setTimeout(function(){
                                    _this.getData({});
                                },2000)
                            };
                            _this.selectItem = [];
                            for(var i = 0,len = error2.length;i < len;i++){
                                var item = {
                                    wareId : error2[i]
                                }
                                _this.selectItem.push(item);
                            }
                            for(var i = 0,len = error3.length;i < len;i++){
                                var item = {
                                    wareId : error3[i]
                                }
                                _this.selectItem.push(item);
                            }
                            var errorList = error2.concat(error3);
                            _this.tableData.forEach(function(val){
                                val._checked = (errorList.indexOf(''+val.wareId) !== -1) ;
                            })
                        }
                    }
                }
            )
        },
        recoverOne: function(index){
            this.selectItem = [this.tableData[index]];
            this.recover();
        },
        reset: function(){
            console.log(1);
            this.query = {
                classfiy: [],
                wareId: null,
                wareName: null,
                sku: null,
                deleteTime: []
            }
        }
    }
})
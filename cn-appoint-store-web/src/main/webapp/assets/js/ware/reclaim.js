/**
 * Created by wanggenhua on 2017/3/23.
 */
var vm = new Vue({
    el: '.reclaim',
    mixins: [validate,methods],
    data: {
        tableTitle: thead.reclaim,
        tableData: [],
        query: {
            classfiy: [],
            wareName: null,
            claim: '-1'
        },
        page: {
            total: 0,
            pageNo: 1,
            pageSize: 10
        },
        selectItem: [],
        classfiyData: [],
        searchData: {},
        isLoaded: false
    },
    ready: function(){
        this.dataInit();
        this.getOthers();
        this.isLoaded = true;
    },
    methods:{
        getData: function(query){
            var _this = this;
            api.reclaimList(
                query,
                function(data){
                    if(data.result){
                        _this.tableData = [];
                        data.data.itemList.forEach(function(val,index){
                            val._checked = false;
                            val.claim = _this.query.claim == '-1' ? '未认领' : '已认领';
                            _this.tableData.push(val);
                        });
                        _this.page.total = data.data.totalItem;
                        _this.searchData = {
                            claim: _this.query.claim || null,
                            wareName: _this.query.wareName || null,
                            categoryId: _this.query.classfiy[2] || null
                        };

                    }else{
                        _this.tableData = [];
                        _this.page.total = 0;
                        _this.$Message.error(data.errorMessage);
                    }
                },
                function(){
                    _this.$Notice.error({
                        desc: '服务器，暂时出问题了，请稍后再试！'
                    })
                }
            )
        },
        dataInit: function(){
            this.getData({
                pageNo: this.page.pageNo,
                pageSize: this.page.pageSize,
                claim : -1
            });
        },
        getOthers: function(){
            var _this = this;
            //获取类目
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
            var params = {
                claim: +this.query.claim || null,
                wareName: this.query.wareName || null,
                categoryId: this.query.classfiy[2] || null
            };
            params.pageNo = 1;
            this.page.pageNo = 1;
            params.pageSize = this.page.pageSize;
            this.getData(params);
        },
        reclaimOne: function(id){
            var params = [id];
            this.reclaim(params);
        },
        reclaimMore: function(){
            var params = this.getWareIds();
            this.reclaim(params);
        },
        reclaim: function(params){
            var _this = this;
            var error1 = [];
            var error2 = [];
            api.reclaim(
                JSON.stringify(params),
                function(data){
                    console.log(data);
                    if(data){
                        for(var i in data){
                            var code = data[i];
                            switch (code){
                                case 1:
                                    error1.push(i);
                                    break;
                                case 2:
                                    error2.push(i);
                            }
                        }
                    }
                    if(error1.length || error2.length){
                        var str = '';
                        if(error1.length && !error2.length){
                            str = '商品编码为' + error1.join('') + '的商品，模板商品已经删除，门店无法认领';
                        }
                        if(!error1.length && error2.length){
                            str = '商品编码为' + error2.join('，') + '的商品，模板商品认领失败'
                        }
                        if(error1.length && error2.length){
                            str = '商品编码为' + error3.join('，') + '的商品，模板商品已经删除，门店无法认领;商品编码为' + error3.join('') + '的商品恢复失败。'
                        }
                        _this.$Message.error(str,4);
                        if(error1.length + error2.length < _this.selectItem.length) {
                            setTimeout(function(){
                                _this.dataInit({});
                            },2000);
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
                        var errorList = error2.concat(error1);
                        _this.tableData.forEach(function(val){
                            val._checked = (errorList.indexOf(''+val.wareId) !== -1) ;
                        })
                    }else{
                        _this.selectItem = [];
                        _this.$Message.success('认领成功',2);
                        setTimeout(function(){
                            _this.dataInit({});
                        },2000);
                    }
                },
                function(){
                    _this.$Message.error('服务器出异常',2);
                }
            )
        },
        getWareIds: function(){
            var arr = [];
            if(this.selectItem.length){
                this.selectItem.forEach(function(val){
                    arr.push(val.wareId)
                })
            }
            return arr;
        },
        reset: function(){
            this.query = {
                classfiy: [],
                wareName: null,
                claim: '-1'
            }
        },
        setStyle: function(index){
            $('#reclaim_table .shopweb-poptip-popper').eq(index).css({
                top: -47 + index * 65 + 'px'
            })
        }
    }
})
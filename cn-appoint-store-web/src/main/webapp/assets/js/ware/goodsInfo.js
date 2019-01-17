/**
 * Created by wanggenhua on 2017/3/24.
 */
var vm = new Vue({
    el : '.detail',
    data: {
        wareId: '',
        basic: {
            title: '',
            adWords: '',
            AdUrlWords: '',
            adWordsUrl: '',
            is7toReturn: ''
        },
        categoryAttrList: [],
        functionSettingVO: [],
        shopCatagoryVos: [],
        goodsInfo: {},
        attrTitle: [
            {title: '颜色',key: 'color'},
            {title: '尺寸',key: 'size'},
            {title: 'SKU编码',key: 'skuId'}
        ],
        attrData: [],
        imageUrlList: [],
        introduction:{
            type: 1,
            pc: '',
            m: ''
        },
        delivery: {
            address: '',
            fee: ''
        },
        others: {},
        isLoaded: false,
        isError: false,
        isPw: true
    },
    ready: function(){
        this.wareId = window.location.search.split('=')[1];
        this.getData();
        this.isLoaded = true;
    },
    methods: {
        getData: function(){
            var _this = this;
            api.getDetails(
                _this.wareId,
                function(data){
                    if(data.result){
                        console.log(data.data);
                        _this.isPw = data.data.isPw == 1 ? true : false;
                        //基本内容
                        _this.basic.title = data.data.title;
                        _this.basic.adWords = data.data.adWords;
                        _this.basic.AdUrlWords = data.data.AdUrlWords;
                        _this.basic.adWordsUrl = data.data.adWordsUrl;
                        _this.basic.is7toReturn = data.data.is7toReturn === 1 ? '支持' : '不支持';

                        //商品属性
                        _this.categoryAttrList = data.data.categoryAttrList || [];

                        //商品信息
                        _this.goodsInfo = {
                            jdPrice: data.data.jdPrice,
                            marketPrice: data.data.marketPrice,
                            weight: data.data.weight,
                            itemNum: data.data.itemNum,
                            length: data.data.length,
                            production: data.data.wareLocation,
                            discount: data.data.discount,
                            width: data.data.width,
                            barCode: data.data.barCode,
                            costPrice: data.data.costPrice,
                            height: data.data.height,
                            density: data.data.density
                        }
                        //销售属性
                        var colorArr = [];
                        if(data.data.skuPoList && data.data.skuPoList.length){
                            var attrList = data.data.skuPoList;
                            attrList.forEach(function(val){
                                var temp = {};
                                if(val.att){
                                    temp.color = val.att.color;
                                    temp.size = val.att.size;
                                    temp.skuId = val.skuId;
                                    var index = colorArr.indexOf(temp.color);
                                    if( index === -1){
                                        colorArr.push(temp.color);
                                        _this.attrData.push(temp);
                                    }else{
                                        _this.attrData.splice(index,0,temp);
                                    }
                                }
                            })
                        }
                        //图片管理
                        _this.imageUrlList = data.data.imageUrlList || [];

                        //功能设置
                        _this.functionSettingVO = data.data.functionSettingVO || [];
                        _this.functionSettingVO.forEach(function(val){
                            if(val.children){
                                val.children = _this.objToArr(val.children);
                                val.checked = val.value.split(',');
                            }
                        })
                        //商品描述
                        _this.introduction.pc = data.data.introduction;
                        _this.introduction.m = data.data.mobileDesc;
                        //商品物流信息
                        var delivery = data.data.addressMap;
                        var str = '';
                        for(var i in delivery){
                            str += delivery[i];
                        }
                        _this.delivery.address = str;
                        _this.delivery.fee = data.data.wareTemplate;
                        //其它商品信息
                        _this.others = {
                            afterSales : data.data.afterSales,
                            packListing: data.data.packListing
                        }
                        //店内分类
                        var level1 = [];
                        var level2 = [];
                        data.data.shopCatagoryVos.forEach(function(val){
                           val.parentId ? level2.push(val) : level1.push(val);
                        })
                        for(var i = 0,len = level2.length;i < len;i++){
                           var temp = _this.getParent(level2[i].parentId,level1);
                           if(temp.children){
                               temp.children.push(level2[i])
                           }else{
                               temp.children = [level2[i]]
                           };
                        }
                        _this.shopCatagoryVos = level1;
                    }
                }
            )
        },
        getParent: function(id,arr){
            var i = 0;
            var len = arr.length;
            for(;i < len;i ++){
                if(arr[i].id === id) return arr[i];
            }
        },
        save: function(){
            var params = {
                adWords: this.basic.adWords,
                wareId: +this.wareId
            }
            var _this = this;
            api.detailUpdate(
                JSON.stringify(params),
                function(data){
                    if(data.result){
                        _this.$Message.success('保存成功');
                    }else{
                        _this.$Message.error('保存失败');
                    }
                }
            )
        },
        objToArr: tools.objToArr
    },
    watch: {
        basic: {
            handler: function(val){
                var adWords = val.adWords;
                this.isError =  !/^[^%&',<;=?$\x22]*$/.test(adWords);
            },
            deep: true
        }
    }
})

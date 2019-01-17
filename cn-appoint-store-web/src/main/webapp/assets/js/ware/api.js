/**
 * Created by wanggenhua on 2017/3/23.
 */
var api = {};
api.basic = function(url,query,successFn,errorFn){
    Vue.http.post(url,query) 
        .then(function(res){
            var data = res.data;
            try {
                data = typeof data === "string" ? JSON.parse(data) : data;
            }catch (err){}
            successFn(data);
        },function(){
            errorFn();
        })
        .catch(function(){

        })
}
api.get = function(url,successFn,errorFn){
    Vue.http.get(url)
        .then(function(res){
            var data = res.data;
            try {
                data = typeof data === "string" ? JSON.parse(data) : data;
            }catch (err){}
            successFn(data);
        },function(){
            errorFn();
        })
        .catch(function(){

        })
}
//在售商品页面，还缺少认领新商品,库存价格修改接口,及类目接口.
//在售商品列表查询
api.onSaleList = function(query,successFn,errorFn){
    api.basic('/ware/onSaleList',query,successFn,errorFn)
};
//在售商品删除
api.removeOnSale = function(query,successFn,errorFn){
    api.basic('/ware/deleteOn',query,successFn,errorFn)
};
//在售商品下架
api.offLineOnsale = function(query,successFn,errorFn){
    api.basic('/ware/offLine',query,successFn,errorFn)
};

//待售商品
api.forSaleList = function(query,successFn,errorFn){
    api.basic('/ware/forSaleList',query,successFn,errorFn)
};
//待售商品上架
api.offLineForsale = function(query,successFn,errorFn){
    api.basic('/ware/onLineForSale',query,successFn,errorFn)
};
//待售商品删除
api.removeForsale = function(query,successFn,errorFn){
    api.basic('/ware/deleteFor',query,successFn,errorFn)
};
//在售待售页面，获取库存接口
api.getStock = function(query,successFn,errorFn){
    api.get('/ware/toSetSkuStock/'+ query,successFn,errorFn)
};
//在售待售页面，修改库存接口
api.changeStock = function(query,successFn,errorFn){
    api.basic('/ware/updateSkuStock',query,successFn,errorFn)
};

//类目获取接口
api.getCategory = function(query,successFn,errorFn){
    api.basic('/ware/category',query,successFn,errorFn)
};
//品牌获取接口
api.getBrands = function(query,successFn,errorFn){
    api.basic('/ware/brand',query,successFn,errorFn)
};

//商品回收站
//恢复    恢复返回值（3：表示该商品ID在商家端已经删除 门店端无法直接恢复；2：表示该商品恢复失败；1：表示该商品已经恢复成功）
api.recover = function(query,successFn,errorFn){
    api.basic('/ware/recover',query,successFn,errorFn)
};
//列表
api.recoverList = function(query,successFn,errorFn){
    api.basic('/ware/wasteBasketList',query,successFn,errorFn)
};

//商品认领列表
api.reclaimList = function(query,successFn,errorFn){
    api.basic('/ware/pwList',query,successFn,errorFn)
}
//商品认领
api.reclaim = function(query,successFn,errorFn){
    api.basic('/ware/reclaim',query,successFn,errorFn)
};

//商品详情
api.getDetails = function(query,successFn,errorFn){
    api.get('/ware/detail/' + query,successFn,errorFn);
}
api.detailUpdate = function(query,successFn,errorFn){
    api.basic('/ware/updateWare',query,successFn,errorFn)
};

//库存管理
api.stockList = function(query,successFn,errorFn){
    api.basic('/ware/skuList',query,successFn,errorFn)
};
//库存管理，修改
api.updateStock = function(query,successFn,errorFn){
    api.basic('/ware/updateSkuStock',query,successFn,errorFn);
};
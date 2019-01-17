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


api.fetchStoreStockInfoList = function(data,successFn,errorFn){
    api.basic('/StoreStock/queryStoreStockInfoList', data,successFn,errorFn)
};

api.putStoreStockNum = function(data,successFn,errorFn){
    api.basic('/StoreStock/updateStockNum', data,successFn,errorFn)
};
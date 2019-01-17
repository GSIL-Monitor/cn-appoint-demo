/**
 * Created by liyi on 2017/4/6.
 */
var api = {};
api.basic = function(url,data,successCallback,errorCallback){
    Vue.http.post(url,data)
        .then(function(res){
            successCallback(res.data);
        },function(){
            errorCallback();
        })
        .catch(function(){

        })
}

//验证码查询
api.inquiry = function(data,successCallback,errorCallback){
    api.basic('/loc/search',data,successCallback,errorCallback)
};
//验证码核销
api.writeOff = function(data,successCallback,errorCallback){
    api.basic('/loc/write-off',data,successCallback,errorCallback)
};

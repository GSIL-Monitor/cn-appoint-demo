var api = {};
api.basic = function(url,param,successFn,errorFn,async){
    // if (!async) {
    //     async = false;
    // }
    if (async==undefined || async==null) {
        async = true;
    }

    // Vue.http.post(url,param)
    //     .then(function(res){
    //         if (successFn) {
    //             console.log(res.data);
    //             successFn(res.data);
    //         }
    //     },function(){
    //         if (errorFn) {
    //             errorFn();
    //         }
    //     })
    //     .catch(function(){
    //
    //     });

    jQuery.ajax({
        url: url,
        async: async,
        cache: false,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(param),
        success: function(result){
            if (successFn) {
                // console.log(result);
                successFn(result);
            }
        },
        error: function(){
            if (errorFn) {
                errorFn();
            }
        }
    });
}
// 取消订单查询
api.search = function(query,successFn,errorFn){
    api.basic('/afs/after/refundment/api/search',query,successFn,errorFn);
};
// 批量导出查询
api.searchExport = function(query,successFn,errorFn){
    api.basic('/afs/after/refundment/api/viewExport',query,successFn,errorFn);
};
// 批量导出
api.handleExport = function(query,successFn,errorFn,async){
    api.basic('/afs/after/refundment/api/handleExport',query,successFn,errorFn,async);
};
// 退款详情
api.searchDetail = function(query,successFn,errorFn,async){
    api.basic('/afs/after/refundment/api/detail',query,successFn,errorFn,async);
};
// 检测该记录是否已经审核过，防止重复提交
api.refundmentCheckDupCommit = function(query,successFn,errorFn,async){
    api.basic('/afs/after/refundment/api/checkDupCommit?time=' + Math.random() * 1000 + 1,query,successFn,errorFn,async);
};
api.updateRefundment = function(query,successFn,errorFn,async){
    api.basic('/afs/after/refundment/api/updateRefundment',query,successFn,errorFn,async);
};


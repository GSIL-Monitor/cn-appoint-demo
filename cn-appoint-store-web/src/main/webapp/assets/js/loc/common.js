/**
 * Created by liyi on 2017/4/6.
 */
var validate  = {
    data: {
        ruleValidate: {
            code: [
                {
                    pattern: /^[0-9]*$/,
                    message: '输入不正确！'
                }
            ],
            phone: [
                {
                    pattern: /[^%&',<;=?$\x22]+/,
                    max: 11,
                    message: '手机号输入有误！'
                }
            ]
        }
    }
};

var tools = {
    objCopy : function(obj){
        var newObj = {};
        for(var val in obj){
            newObj[val] = obj[val];
        }
        return newObj;
    }
}

// var methods = {
//     methods: {
//         objCopy: tools.objCopy,
//         pageChange: function(){
//             var temp = this.objCopy(this.searchData);
//             temp.pageNo = this.page.pageNo;
//             this.getData(temp);
//         },
//         pageSizeChange: function(){
//             var temp = this.objCopy(this.searchData);
//             temp.pageSize = this.page.pageSize;
//             temp.pageNo = 1;
//             this.getData(temp);
//         },
//         goInfo: function(val){
//             console.log(val);
//             window.open('/ware/detail/' + val,'_blank');
//         },
//         goTab: function (val) {
//             if (!val) return;
//             routerInit.router.go(val);
//         }
//     }
// }
//
// var routerInit = function (pages) {
//     var App = Vue.extend();
//     routerInit.router = new VueRouter();
//     routerInit.router.map({
//         '/' : {
//             component : pages.used
//         },
//         'used' : {
//             component : pages.used
//         },
//         'unused' : {
//             component : pages.unused
//         }
//     });
//     routerInit.router.redirect({
//         '*' : '/'
//     });
//     routerInit.router.start(App, '#dataList');
// }
//
// var pages = {};
// pages.used = {
//     template : '#usedData',
//     methods : {
//
//     }
// };
// pages.unused = {
//     template : '#unusedData',
//     methods : {
//     }
// };
// routerInit(pages);
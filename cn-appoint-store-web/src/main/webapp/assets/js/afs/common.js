var validate  = {
    data: {
        rules: {
            num: [
                {
                    pattern: /^[0-9]*$/,
                    message: '该项只能输入数字哦！'
                }
            ],
            txt: [
                {
                    pattern: /[^%&',<;=?$\x22]+/,
                    message: '输入有误哦！'
                }
            ],
            numInt: [
                {
                    pattern: /^[0-9]*$/,
                    message: '必须是整数哦！'
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

var methods = {
    methods: {
        objCopy: tools.objCopy,
        pageChange: function(){
            var temp = this.objCopy(this.query);
            temp.pageNo = this.page.pageNo;
            this.getData(temp);
        },
        pageSizeChange: function(){
            var temp = this.objCopy(this.query);
            temp.pageSize = this.page.pageSize;
            temp.pageNo = 1;
            this.getData(temp);
        },
        goInfo: function(val){
            console.log(val);
            window.open('/ware/detail/' + val,'_blank');
        },
        formatDate: function (val) {
            if (val) {
                var date = this.getDate(val);
                val = date.getFullYear() + "-" + this.formatDateNumber(date.getMonth() + 1) + "-" + this.formatDateNumber(date.getDate());
            }
            return val;
        },
        formatDateTime: function (val) {
            if (val) {
                var date = this.getDate(val);
                val = date.getFullYear() + "-" + this.formatDateNumber(date.getMonth() + 1) + "-" + this.formatDateNumber(date.getDate()) + " " + this.formatDateNumber(date.getHours()) + ":" + this.formatDateNumber(date.getMinutes()) + ":" + this.formatDateNumber(date.getSeconds());
            }
            return val;
        },
        formatDateTimeMinutes: function (val) {
            if (val) {
                var date = this.getDate(val);
                val = date.getFullYear() + "-" + this.formatDateNumber(date.getMonth() + 1) + "-" + this.formatDateNumber(date.getDate()) + " " + this.formatDateNumber(date.getHours()) + ":" + this.formatDateNumber(date.getMinutes());
            }
            return val;
        },
        formatDateNumber: function (val) {
            return ("0" + val).slice(-2);
        },
        getDate: function (val) {
            if (val instanceof Date) {
                return val;
            }
            else if (typeof val == "number") {
                return new Date(val);
            }
            return val;
        },
        typeToString: function (val) {
            return val ? String(val) : "";
        },
        getUrlParam: function () {
            var url = location.search; //获取url中"?"符后的字串
            var theRequest = new Object();
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                strs = str.split("&");
                for(var i = 0; i < strs.length; i ++) {
                    theRequest[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
                }
            }
            return theRequest;
        },
        decodeHtml: function (val) {
            if (val) {
                var temp = document.createElement("div");
                temp.innerHTML = val;
                val = temp.innerText || temp.textContent;
            }
            return val;
        }
    }
}

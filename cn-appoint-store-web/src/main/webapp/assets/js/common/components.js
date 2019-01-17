/**
 * Created by nielingwei on 2018/1/26.
 */


Vue.component('pa-group-ctrl', {
    template: '<div class="group-ctrl">\n' +
    '    <div class="table-wrap">\n' +
    '        <ul class="table-row clearfix">\n' +
    '            <li class="table-item">' +
    '<input v-if="inputType == checkbox" class="table-checkbox" @change="checkAll()" v-model="allCheckStatus" type="checkbox" />' +
    '<span v-else>&nbsp;</span>'+
    '</li>\n' +
    '            <li class="table-item">{{headTitleArray[0]}}</li>\n' +
    '            <li class="table-item">{{headTitleArray[1]}}</li>\n' +
    '        </ul>\n' +
    '<div  v-if="inputType == checkbox">' +
    '        <ul class="table-row clearfix" v-for="item in getDataList">\n' +
    '            <li class="table-item"><input class="table-checkbox" @change="check(item)" v-model="item.checked" type="checkbox" /></li>\n' +
    '            <li class="table-item">{{item.id}}</li>\n' +
    '            <li class="table-item">{{item.name}}</li>\n' +
    '        </ul>\n' +
    '</div>'+
    '<div v-if="inputType == radio">' +
    '        <ul class="table-row clearfix" v-for="item in getDataList">\n' +
    '            <li class="table-item"><input name="storeRadio" class="table-checkbox" @click="radioClick(item)" :value="item.id" v-model="radioId" type="radio" /></li>\n' +
    '            <li class="table-item">{{item.id}}</li>\n' +
    '            <li class="table-item">{{item.name}}</li>\n' +
    '        </ul>\n' +
    '</div>'+
    '    </div>\n' +
    '\n' +
    '    <div class="page-wrap">\n' +
    '\n' +
    '        <p-row type="flex" justify="end">\n' +
    '            <p-page :current.sync="current" @on-change="pageChange" :total="totalItems" :show-sizer="false" :page-size="10"></p-page>\n' +
    '        </p-row>\n' +
    '\n' +
    '    </div>\n' +
    '</div>',
    data: function() {
        return {
            /*selfDataList: [],*/
            selfIdList: [],
            selfNameList: [],
            allCheckStatus: false,
            checkbox: 'checkbox',
            radio: 'radio',
        }
    },
    methods: {
        pageChange: function(page) {
            this.$dispatch('page-change', page)
        },
        radioClick: function(item) {
            this.selectJson = item;
        },
        checkAll: function() {
            for (var i = 0, len = this.selfDataList.length; i < len; i++) {
                Vue.set(this.selfDataList[i], 'checked', this.allCheckStatus)
                this.setSelfIdList(this.selfDataList[i], this.allCheckStatus)
            }
        },
        check: function(item) {
            this.setSelfIdList(item)
            this.checkIsAllCheck()
        },
        checkIsAllCheck: function() {
            var arr = [];
            for (var i = 0, len = this.getDataList.length; i < len; i++) {
                if (this.getDataList[i].checked === false) {
                    return this.allCheckStatus = false;
                }
            }
            return this.allCheckStatus = true;
        },
        setSelfIdList: function(item) {
            var index = this.selfIdList.indexOf(parseInt(item.id));
            if (item.checked) {
                if (!(index > -1)){
                    this.selfIdList.push(item.id);
                    this.selfNameList.push(item.name);
                }
            } else {
                if (index > -1){
                    this.selfIdList.splice(index, 1);
                    this.selfNameList.splice(index, 1);
                }

            }
        },
        setCheckObj: function(type) {
            if (!type) return;
            if (type === 'ok') {
                this.checkedIdList = this.selfIdList;
            }
            if (type === 'cancel') {
                this.selfIdList = this.checkedIdList;
            }
        }
    },
    computed: {
        getDataList: function() {
            var arr = [],
                thatId;
            for (var i = 0, len = this.selfDataList.length; i < len; i++) {
                arr[i] = this.selfDataList[i]
                thatId = parseInt(this.selfDataList[i].id)
                if (this.selfIdList.indexOf(thatId) >= 0) {
                    Vue.set(arr[i], 'checked', true)
                } else {
                    Vue.set(arr[i], 'checked', false)
                }
            }
            return arr;
        }
    },
    ready: function() {
        /*this.selfDataList = JSON.parse(JSON.stringify(this.dataList));*/
        /*this.selfIdList = JSON.parse(JSON.stringify(this.checkedIdList));*/
        /*this.checkIsAllCheck()*/
    },
    props: {
        totalItems: 'totalItems',
        pageSize: 'pageSize',
        selfDataList: {
            type: Array,
            default: []
        },
        current: 'current',
        getCheckObj: 'getCheckObj',
        checkedIdList: {
            type: Array,
            default: []
        },
        selectJson: {
            type: Object,
            default: {}
        },
        radioId: "",
        headTitleArray: {
            type: Array,
            default: ['id', '名称']
        },
        inputType: {
            type: String,
            default: 'checkbox'
        }
    },
    watch: {
        checkedIdList: function(val){
            this.selfIdList = JSON.parse(JSON.stringify(val));
            this.checkIsAllCheck();
        },
        selfDataList: function(){
            this.checkIsAllCheck();
        }
    }
})


Vue.component('pa-employee-ctrl', {
    template: '<div class="employee-ctrl">\n' +
    '    <div class="table-wrap">\n' +
    '        <ul class="table-row clearfix">\n' +
    '            <li class="table-item">&nbsp;</li>\n' +
    '            <li class="table-item">{{headTitleArray[0]}}</li>\n' +
    '            <li class="table-item">{{headTitleArray[1]}}</li>\n' +
    '        </ul>\n' +
    '<div v-if="type == checkbox">' +
    '        <ul class="table-row clearfix" v-for="item in getDataList">\n' +
    '            <li class="table-item"><input name="storeRadio" class="table-checkbox" @click="radioClick(item)" :value="item.id" v-model="selectId" type="radio" /></li>\n' +
    '            <li class="table-item">{{item.id}}</li>\n' +
    '            <li class="table-item">{{item.name}}</li>\n' +
    '        </ul>\n' +
    '</div>'+
    '<div v-if="type == radio">' +
    '        <ul class="table-row clearfix" v-for="item in getDataList">\n' +
    '            <li class="table-item"><input name="storeRadio" class="table-checkbox" @click="radioClick(item)" :value="item.id" v-model="radioId" type="radio" /></li>\n' +
    '            <li class="table-item">{{item.id}}</li>\n' +
    '            <li class="table-item">{{item.name}}</li>\n' +
    '        </ul>\n' +
    '</div>'+

    '    </div>\n' +
    '\n' +
    '    <div class="page-wrap">\n' +
    '\n' +
    '        <p-row type="flex" justify="end">\n' +
    '            <p-page @on-change="pageChange" :total="totalItems" :show-sizer="false" :page-size="10"></p-page>\n' +
    '        </p-row>\n' +
    '\n' +
    '    </div>\n' +
    '</div>',
    data: function() {
        return {
            /*selfDataList: [],*/
            selfIdList: [],
            selfNameList: [],
            allCheckStatus: false
        }
    },
    methods: {
        pageChange: function(page) {
            this.$dispatch('page-change', page)
        },
        radioClick: function(item) {
            this.selectJson = item;
        },
        checkIsAllCheck: function() {
            var arr = [];
            for (var i = 0, len = this.getDataList.length; i < len; i++) {
                if (this.getDataList[i].checked === false) {
                    return this.allCheckStatus = false;
                }
            }
            return this.allCheckStatus = true;
        },
        setSelfIdList: function(item) {
            var index = this.selfIdList.indexOf(parseInt(item.id));
            if (item.checked) {
                if (!(index > -1)){
                    this.selfIdList.push(item.id);
                    this.selfNameList.push(item.name);
                }
            } else {
                if (index > -1){
                    this.selfIdList.splice(index, 1);
                    this.selfNameList.splice(index, 1);
                }

            }
        },

        setCheckObj: function(type) {
            if (!type) return;
            if (type === 'ok') {
                this.checkedIdList = this.selfIdList;
            }
            if (type === 'cancel') {
                this.selfIdList = this.checkedIdList;
            }
        }
    },
    computed: {
        getDataList: function() {
            var arr = [],
                thatId;
            for (var i = 0, len = this.selfDataList.length; i < len; i++) {
                arr[i] = this.selfDataList[i]
                thatId = parseInt(this.selfDataList[i].id);
                if (this.selfIdList.indexOf(thatId) >= 0) {
                    Vue.set(arr[i], 'checked', true)
                } else {
                    Vue.set(arr[i], 'checked', false)
                }

            }
            return arr;
        }
    },
    ready: function() {
        /*this.selfDataList = JSON.parse(JSON.stringify(this.dataList));*/
        /*this.selfIdList = JSON.parse(JSON.stringify(this.checkedIdList));*/
        /*this.checkIsAllCheck()*/
    },
    props: {
        totalItems: 'totalItems',
        pageSize: 'pageSize',
        selfDataList: {
            type: Array,
            default: []
        },
        current: 'current',
        getCheckObj: 'getCheckObj',
        selectJson: {},
        headTitleArray: {
            type: Array,
            default: ['id', '名称']
        },
        type: {
            type: String,
            default: 'checkbox'
        }
    },
    watch: {
        checkedIdList: function(val){
            this.selfIdList = JSON.parse(JSON.stringify(val));
            this.checkIsAllCheck();
        },
        selfDataList: function(){
            this.checkIsAllCheck();
        }
    }
})

Vue.component('pa-cascader', {
    template:
    '<select :disabled="disabled" class="select-style-ctrl" @change="selectChange(1, valueIdList[0])" v-model="valueIdList[0]">'+
    '<option value="0">请选择</option>'+
    '<option v-for="item in selectDataLev0" :value="item.id">{{item.name}}</option>'+
    '</select>'+
    '<select :disabled="disabled" class="select-style-ctrl" @change="selectChange(2, valueIdList[1])" v-model="valueIdList[1]">'+
    '<option value="0">请选择</option>'+
    '<option v-for="item in selectDataLev1" :value="item.id">{{item.name}}</option>'+
    '</select>'+
    '<select :disabled="disabled" class="select-style-ctrl" @change="selectChange(3, valueIdList[2])" v-model="valueIdList[2]">'+
    '<option value="0">请选择</option>'+
    '<option v-for="item in selectDataLev2" :value="item.id">{{item.name}}</option>'+
    '</select>',
    data: function() {
        return {

        }
    },
    computed: {

    },
    methods: {
        selectChange: function(lev, id) {
            var value = this['selectDataLev' + (lev - 1)].find(function(item) {
                return item.id === id
            });

            this.valueList[lev - 1] = value;
            this.valueIdList[lev - 1] = id;
            this.$dispatch('select-emit', lev, value);

            if (lev === 1) {
                this.valueList[1] = {};
                this.valueList[2] = {};
                this.valueIdList[1] = 0
                this.valueIdList[2] = 0
                this.selectDataLev1 = []
                this.selectDataLev2 = []
            }
            if (lev === 2) {
                this.valueList[2] = {};
                this.valueIdList[2] = 0
                this.selectDataLev2 = []
            }

            console.log(this.valueList, this.valueIdList)
        }
    },
    props: {
        selectDataLev0: {
            type: Array,
            default: []
        },
        selectDataLev1: {
            type: Array,
            default: []
        },
        selectDataLev2: {
            type: Array,
            default: []
        },
        valueList: {
            type: Array,
            default: []
        },
        valueIdList: {
            type: Array,
            default: [0, 0, 0]
        },
        disabled: {
            type: Boolean,
            default: false
        }
    }
})

var mdTools = {
    getQueryString: function(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]); return null;
    },

    formatTime: function(date, format) {
        var o = {
            "M+" : date.getMonth()+1, //month
            "d+" : date.getDate(), //day
            "h+" : date.getHours(), //hour
            "m+" : date.getMinutes(), //minute
            "s+" : date.getSeconds(), //second
            "q+" : Math.floor((date.getMonth()+3)/3), //quarter
            "S" : date.getMilliseconds() //millisecond
        }
        if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
            (date.getFullYear()+"").substr(4- RegExp.$1.length));
        for(var k in o)if(new RegExp("("+ k +")").test(format))
            format = format.replace(RegExp.$1,
                RegExp.$1.length==1? o[k] :
                    ("00"+ o[k]).substr((""+ o[k]).length));
        return format;
    }
}

/**
 * 占位符处理方法
 * @returns {String}
 */
String.prototype.placeholderFormat = function(){
    if(arguments.length==0) return this;
    for(var s=this, i=0; i<arguments.length; i++)
        s=s.replace(new RegExp("\\{"+i+"\\}","g"), arguments[i]);
    return s;
};

window.validateObj = {
    /** 每个校验类型都需要添加一个对应的校验方法
     * @param rule 当前规则值{value:"", message:""}
     * @param validateRules 校验规则列表
     * @returns {*}
     */
    required:function (rule, validateRules) {/**必填选填校验**/
        if (rule) {
            var validateObj = {};
            if (rule.value == 1) {
                validateObj.required = true;
            } else {
                validateObj.required = false;
            }
            validateObj.message = rule.message.placeholderFormat(rule.placeholder0);
            validateRules.push(validateObj);
            return validateRules;
        }
    },
    fileminnum:function(rule, validateRules) {
        if (rule) {
            var validateObj = {};
            validateObj.min = parseInt(rule.value);
            validateObj.type = 'array';
            validateObj.message = rule.message.placeholderFormat(rule.placeholder0, rule.value);
            validateRules.push(validateObj);
            return validateRules;
        }
    },
    filemaxnum:function(rule, validateRules) {
        if (rule) {
            var validateObj = {};
            validateObj.max = parseInt(rule.value);
            validateObj.type = 'array';
            validateObj.message = rule.message.placeholderFormat(rule.placeholder0, rule.value);
            validateRules.push(validateObj);
            return validateRules;
        }
    }
}


if (!Array.prototype.filter)
    Array.prototype.filter = function(func, thisArg) {
        'use strict';
        if ( ! ((typeof func === 'Function' || typeof func === 'function') && this) )
            throw new TypeError();

        var len = this.length >>> 0,
            res = new Array(len), // preallocate array
            t = this, c = 0, i = -1;
        if (thisArg === undefined)
            while (++i !== len)
                // checks to see if the key was set
                if (i in this)
                    if (func(t[i], i, t))
                        res[c++] = t[i];
                    else
                        while (++i !== len)
                            // checks to see if the key was set
                            if (i in this)
                                if (func.call(thisArg, t[i], i, t))
                                    res[c++] = t[i];

        res.length = c; // shrink down array to proper size
        return res;
    };

// https://tc39.github.io/ecma262/#sec-array.prototype.find
if (!Array.prototype.find) {
    Object.defineProperty(Array.prototype, 'find', {
        value: function(predicate) {
            // 1. Let O be ? ToObject(this value).
            if (this == null) {
                throw new TypeError('"this" is null or not defined');
            }

            var o = Object(this);

            // 2. Let len be ? ToLength(? Get(O, "length")).
            var len = o.length >>> 0;

            // 3. If IsCallable(predicate) is false, throw a TypeError exception.
            if (typeof predicate !== 'function') {
                throw new TypeError('predicate must be a function');
            }

            // 4. If thisArg was supplied, let T be thisArg; else let T be undefined.
            var thisArg = arguments[1];

            // 5. Let k be 0.
            var k = 0;

            // 6. Repeat, while k < len
            while (k < len) {
                // a. Let Pk be ! ToString(k).
                // b. Let kValue be ? Get(O, Pk).
                // c. Let testResult be ToBoolean(? Call(predicate, T, « kValue, k, O »)).
                // d. If testResult is true, return kValue.
                var kValue = o[k];
                if (predicate.call(thisArg, kValue, k, o)) {
                    return kValue;
                }
                // e. Increase k by 1.
                k++;
            }

            // 7. Return undefined.
            return undefined;
        }
    });
}
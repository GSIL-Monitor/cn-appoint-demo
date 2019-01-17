/**
 * Created by wanggenhua on 2017/5/8.
 */
Vue.http.options.emulateJSON = true;
//数据请求
var api = {};
api.post = function(url,query,successFn,errorFn){
    Vue.http.post(url,query)
        .then(function(res){
            successFn(res.data);
        },function(){
            errorFn();
        })
        .catch(function(){
        })
}
api.get = function(url,successFn,errorFn){
    Vue.http.get(url)
        .then(function(res){
            successFn(res.data);
        },function(){
            errorFn();
        })
        .catch(function(){
        })
}
//list列表页数据
api.getListData = function(successFn,errorFn){
    api.post('/address/queryVenderStoreAddress',{},successFn,errorFn)
}
//list列表页删除
api.removeItem = function(query,successFn,errorFn){
    api.post('/address/deleteVenderStoreAddress',query,successFn,errorFn)
}

//edit页
api.addAddress = function(query,successFn,errorFn){
    api.post('/address/addVenderStoreAddress',query,successFn,errorFn)
}
api.updateAddress = function(query,successFn,errorFn){
    api.post('/address/updateVenderStoreAddress',query,successFn,errorFn)
}
api.getProvinceList = function(successFn,errorFn){
    api.post('/address/getProvinces',{},successFn,errorFn)
}
api.getCityList = function(query,successFn,errorFn){
    api.post('/address/getCities',query,successFn,errorFn)
}
api.getCountryList = function(query,successFn,errorFn){
    api.post('/address/getCountries',query,successFn,errorFn)
}
api.getTownList = function(query,successFn,errorFn){
    api.post('/address/getTowns',query,successFn,errorFn)
}


var pages = {};
pages.list = {
    template: '#list',
    data: function(){
        return {
            tableTitle: [
                {title: '所在地区',key: 'location',align: 'left',width: 230},
                {title: '详细地址',key: 'address',align:'left',width: 239},
                {title: '邮编',key: 'postcode',align:'center',width: 90},
                {title: '联系电话',key: 'phoneNum',align:'center',width: 120},
                {title: '发货人姓名',key: 'name',align:'center',width: 100},
                {title: '默认',key: 'isDefault',align:'center',width:70},
                {title: '操作',align: 'center',width: 150,
                    render: function(row,col,index){
                        var id = row.id;
                        var str = '<p-button type="text" @click="change('+ index +')">修改</p-button>'+
                        '<p-poptip confirm title="您确认删除吗？" @on-ok="remove('+ id +')" >'+
                            '<p-button type="text">删除</p-button>'+
                        '</p-poptip>';
                        return str;
                    }
                },
            ],
            tableData: []
        }
    },
    ready: function(){
        this.getData();
    },
    methods: {
        getData: function(){
            var _this = this;
            api.getListData(
                function(data){
                    _this.tableData = [];
                    if(data && data.length){
                        data.forEach(function(val){
                            var temp = {
                                location: val.catLev1Name + val.catLev2Name + val.catLev3Name + val.catLev4Name,
                                catLev1: val.catLev1,
                                catLev2: val.catLev2,
                                catLev3: val.catLev3,
                                catLev4: val.catLev4,
                                address: val.addr,
                                postcode: val.postcode,
                                phoneNum: val.storeTel,
                                name: val.consignor,
                                isDefault: val.defaultAddr ? '是' : '否',
                                id: val.id
                            }
                            _this.tableData.push(temp);
                        })
                    }
                }
            )
        },
        add: function(){
            router.go('/edit?type=1');
            pages.editData = null;
        },
        change: function(index){
            router.go('/edit?type=2');
            pages.editData = this.tableData[index];
        },
        remove: function(id){
            console.log(id);
            var _this = this;
            if(id){
                api.removeItem(
                    {id : id},
                    function(data){
                        if(data.success){
                            _this.$Message.success('删除成功');
                            _this.getData();
                        }else{
                            _this.$Message.error('删除失败');
                        }
                    },
                    function(){
                        _this.$Message.error('删除失败');
                    }
                )
            }
        }
    }
};
pages.edit = {
    template: '#edit',
    data: function(){
        return {
            title: '新增发货地址',
            flag: true,
            query: {
                provinceId: '',
                cityId: '',
                countryId: '',
                townId: '',
                addr: '',
                postcode: '',
                storeTel: '',
                consignor: '',
                defaultAddr: ''  //1默认，0不默认
            },
            names: {
                province: '',
                city: '',
                region: '',
                town: ''
            },
            provinceList: [],
            cityList: [],
            countryList: [],
            townList: [],
            rules: {
                required: [
                    {
                        required: true,
                        message: '该项必填'
                    }
                ],
                num: [
                    {
                        pattern: /^[0-9]*$/,
                        message: '该项只能输入数字！'
                    }
                ],
                numRequired: [
                    {
                        pattern: /^[0-9]*$/,
                        message: '该项只能输入数字！'
                    },
                    {
                        required: true,
                        message: '该项必填'
                    }
                ],
                txt: [
                    {
                        required: true,
                        message: '该项必填'
                    },
                    {
                        pattern: /^[^%&',<;=?$\x22]*$/,
                        message: '输入有误！'
                    }
                ],
                name: [
                    {
                        required: true,
                        message: '该项必填'
                    },
                    {
                        pattern: /^[\u4e00-\u9fa5]*$/,
                        message: '输入有误！'
                    }
                ]
            },
            canSubmit: true
        }
    },
    ready: function(){
        var _this = this;
        _this.flag = _this.$route.query.type === '1' ? true : false;
        _this.title = _this.flag ? '新增发货地址' : '修改发货地址';
        //获取省份信息
        api.getProvinceList(
            function(data){
                _this.provinceList = data ? data : [];
            }
        );
        if(pages.editData){
            _this.query.provinceId = pages.editData.catLev1;
            _this.query.cityId = pages.editData.catLev2;
            _this.query.countryId = pages.editData.catLev3;
            _this.query.townId = pages.editData.catLev4;
            _this.query.addr = pages.editData.address;
            _this.query.postcode = pages.editData.postcode;
            _this.query.storeTel = pages.editData.phoneNum;
            _this.query.consignor = pages.editData.name;
            _this.query.defaultAddr = pages.editData.isDefault === '是' ? 1 : 0;
        }
    },
    methods: {
        saveSure: function(){
            var _this = this;
            this.$refs.query.validate(function(valid){
                if(valid){
                    if(!_this.canSubmit){
                        _this.$Message.error('请稍后提交');
                        return;
                    }
                    _this.canSubmit = false;
                    var params = {
                        catLev1: _this.query.provinceId,
                        catLev2: _this.query.cityId,
                        catLev3: _this.query.countryId,
                        addr: _this.query.addr,
                        storeTel: ''+ _this.query.storeTel,
                        consignor: _this.query.consignor,
                        defaultAddr: +_this.query.defaultAddr
                    }
                    if(_this.query.townId) params.catLev4 = _this.query.townId;
                    if(_this.query.postcode) params.postcode = _this.query.postcode;
                    if(_this.flag){
                        api.addAddress(
                            params,
                            function(data){
                                setTimeout(function(){
                                    _this.canSubmit = true;
                                },1000);
                                if(data.success){
                                    _this.$Message.success('新增发货地址成功');
                                    setTimeout(function(){
                                        router.go('/');
                                    },500);
                                }else{
                                    _this.$Message.error(data.message);
                                }
                            },
                            function(){
                                setTimeout(function(){
                                    _this.canSubmit = true;
                                },1000);
                                _this.$Message.error('新增发货地址失败');
                            }
                        )
                    }else{
                        params.id =  pages.editData.id;
                        api.updateAddress(
                            params,
                            function(data){
                                setTimeout(function(){
                                    _this.canSubmit = true;
                                },1000);
                                if(data.success){
                                    _this.$Message.success('修改发货地址成功');
                                    setTimeout(function(){
                                        router.go('/');
                                    },500);
                                }else{
                                    _this.$Message.error(data.message);
                                }
                            },
                            function(){
                                setTimeout(function(){
                                    _this.canSubmit = true;
                                },1000);
                                _this.$Message.error('修改发货地址失败');
                            }
                        )
                    }
                }
            })
        },
        backToList: function(){
            history.back();
        },
        getName: function(id,arr){
            var name = '';
            arr.forEach(function(val){
                if(val.id === id){
                    name = val.name;
                    return;
                }
            });
            return name;
        }
    },
    watch: {
        query : {
            handler: function(val){
                var _this = this;
                //获取二级地址
                if(val.provinceId){
                    _this.names.province = _this.getName(val.provinceId,_this.provinceList);
                    api.getCityList(
                        {provinceId: val.provinceId},
                        function(data){
                            _this.cityList = data ? data : [];
                        }
                    )
                }else{
                    _this.cityList = [];
                    _this.names.province = '';
                    _this.names.city = '';
                    _this.names.region = '';
                    _this.names.town = '';
                    val.cityId = '';
                }
                //获取三级地址
                if(val.cityId){
                    _this.names.city = _this.getName(val.cityId,_this.cityList);
                    api.getCountryList(
                        {cityId: val.cityId},
                        function(data){
                            _this.countryList = data ? data : [];
                        }
                    )
                }else{
                    _this.countryList = [];
                    val.countryId = '';
                    _this.names.region = '';
                    _this.names.city = '';
                    _this.names.town = '';
                }
                //获取四级地址
                if(val.countryId){
                    _this.names.region = _this.getName(val.countryId,_this.countryList);
                    api.getTownList(
                        {countyId: val.countryId},
                        function(data){
                            _this.townList = data ? data : [];
                        }
                    )
                }else{
                    _this.townList = [];
                    val.townId = '';
                    _this.names.region = '';
                    _this.names.town = '';
                }

                if(val.townId){
                    _this.names.town = _this.getName(val.townId,_this.townList);
                }else{
                    _this.names.town = '';
                }
            },
            deep: true
        }
    }
}


var App = Vue.extend();
var router = new VueRouter();
router.map({
    '/': {
        component: pages.list
    },
    '/edit': {
        component: pages.edit
    }
})
router.redirect({
    '*' : '/'
})
router.start(App,'#address');
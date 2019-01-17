/**
 * Created by wanggenhua on 2017/3/23.
 */
var thead = {
    onSale: [
        { type: 'selection',width:60,align: 'center'},
        { title: '商品信息',width:439,align: 'left',
            render: function(row){
                var str = '';
                str = '<div class="goods_info clearfix">'+
                        '<div class="pic_wrap fl">'+
                            '<img src="'+ row.goodsUrl +'" alt="" class="pic"/>'+
                        '</div>'+
                         '<div class="txt fl">'+
                            '<div class="goods_name">'+ row.goodsName +'</div>'+
                            '<dl class="clearfix">'+
                                '<dt class="fl item_t">商品编码：</dt>'+
                                '<dd class="fl">'+ row.wareId +'</dd>'+
                             '</dl>'+
                             '<dl class="clearfix">'+
                                '<dt class="fl item_t">所属类目：</dt>'+
                                '<dd class="fl">'+ row.classify +'</dd>'+
                            '</dl>'+
                        '</div>'+
                    '</div>'
                return str;
            }
        },
        { title: '库存',key: 'stockNum',width:100,align: 'right',
            render: function(row,col,index){
                var str = '';
                str = row.stockNum + '<p-icon type="edit" class="edit_pen" @click="edit('+ index +')"></p-icon>'
                return str;
            }
        },
        { title: '上架时间',key: 'onlineTime',width:250,align: 'center'},
        { title: '操作',align: 'center',
            render: function(row){
                var val = row.wareId;
                return '<p-button type="text" @click="goInfo('+ val +')">查看详细信息</p-button>'
            },
            width: 150
        }
    ],
    forSale: [
        { type: 'selection',width:60,align: 'center'},
        { title: '商品信息',width:439,align: 'left',
            render: function(row){
                var str = '';
                str = '<div class="goods_info clearfix">'+
                    '<div class="pic_wrap fl">'+
                    '<img src="'+ row.goodsUrl +'" alt="" class="pic"/>'+
                    '</div>'+
                    '<div class="txt fl">'+
                    '<div class="goods_name">'+ row.goodsName +'</div>'+
                    '<dl class="clearfix">'+
                    '<dt class="fl item_t">商品编码：</dt>'+
                    '<dd class="fl">'+ row.wareId +'</dd>'+
                    '</dl>'+
                    '<dl class="clearfix">'+
                    '<dt class="fl item_t">所属类目：</dt>'+
                    '<dd class="fl">'+ row.classify +'</dd>'+
                    '</dl>'+
                    '</div>'+
                    '</div>'
                return str;
            }
        },
        { title: '库存',key: 'stockNum',width:100,align: 'right',
            render: function(row,col,index){
                var str = '';
                str = row.stockNum + '<p-icon type="edit" class="edit_pen" @click="edit('+ index +')"></p-icon>'
                return str;
            }
        },
        { title: '下架时间',key: 'onlineTime',width:250,align: 'center'},
        { title: '操作',align: 'center',
            render: function(row){
                var val = row.wareId;
                return '<p-button type="text" @click="goInfo('+ val +')">查看详细信息</p-button>'
            },
            width: 150
        }
    ],
    reclaim: [
        { type: 'selection',width:60},
        { title: '商品名称',key: 'title',align: 'left'},
        {title: '商品编码',key: 'wareId',align: 'center',width: 120},
        { title: '京东类目',key: 'categoryName',align: 'center',width: 200},
        { title: '创建时间',key: 'createTime',width: 170,align: 'center'},
        { title: '认领状态',key: 'claim',width: 85,align: 'center'},
        { title: '操作',align:'center',width: 150,
            render: function(row,col,index){
                var val = row.wareId;
                var str = '<p-button type="text" @click="goInfo('+ val +')">查看详细信息</p-button>' +
                    '<p-poptip confirm title="您确认认领该商品吗？" @on-ok="reclaimOne('+ val +')" v-show="query.claim == -1">'+
                        '<p-button type="text" @click="setStyle('+ index +')">认领</p-button>'+
                    '</p-poptip>';
                return str;
            }
        }
    ],
    stockList: [
        { type: 'selection',width:60},
        { title: '商品编码',key: 'wareId',align:'center'},
        { title: 'SKU编码',key: 'skuId',align:'center'},
        { title: '商品名称',key: 'wareName'},
        { title: '库存总数',align:'center',key: 'stockNum',width: 100,
            render: function(row,col,index){
                var val = row.stockNum;
                var str = '<p-input-number :value="'+ val +'" class="stock_num" @on-change="changeStock('+ index +')" :min="0"></p-input-number>';
                return str;
            }
        },
        { title: '已占用数',key: 'orderBookingNumSum',align:'center'}
    ],
    priceList: [
        { type: 'selection',width:60},
        { title: '商品编码',key: 'orderId',width:100},
        { title: 'SKU编码',key: 'venderId'},
        { title: '商品名称',key: 'duration'},
        { title: '京东价',key: 'classfiy'}
    ],
    wareBasket: [
        { type: 'selection',width:60},
        { title: '商品信息',align:'left',width:439,
            render: function(row){
                var str = '';
                str = '<div class="goods_info clearfix">'+
                    '<div class="pic_wrap fl">'+
                    '<img src="'+ row.goodsUrl +'" alt="" class="pic"/>'+
                    '</div>'+
                    '<div class="txt fl">'+
                    '<div class="goods_name">'+ row.goodsName +'</div>'+
                    '<dl class="clearfix">'+
                    '<dt class="fl item_t">商品编码：</dt>'+
                    '<dd class="fl">'+ row.wareId +'</dd>'+
                    '</dl>'+
                    '<dl class="clearfix">'+
                    '<dt class="fl item_t">所属类目：</dt>'+
                    '<dd class="fl">'+ row.classify +'</dd>'+
                    '</dl>'+
                    '</div>'+
                    '</div>'
                return str;
            }
        },
        { title: '总库存',key: 'stockNum',align:'center',width:100},
        { title: '价格',key: 'jdPrice',align:'center',width: 100},
        { title: '删除时间',key: 'onlineTime',align:'center',width:200},
        { title: '操作',align:'center',width: 100,
            render: function(row,col,index){
                var str = '<p-poptip confirm title="您确认恢复该商品吗？" @on-ok="recoverOne('+ index +')">'+
                                '<p-button type="text">恢复</p-button>'+
                          '</p-poptip>';
                return str;
            }
        }
    ]
};

var validate  = {
    data: {
        rules: {
            num: [
                {
                    pattern: /^[0-9]*$/,
                    message: '该项只能输入数字！'
                }
            ],
            txt: [
                {
                    pattern: /^[^%&',<;=?$\x22]*$/,
                    message: '输入有误！'
                }
            ],
            numInt: [
                {
                    pattern: /^[0-9]*$/,
                    message: '需为正整数'
                }
            ],
            sku: [
                {
                    pattern: /^(1\d{9})|([1-9]\d{10})|(20\d{10})$/,
                    message: '输入有误！'
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
    },
    objToArr : function(obj){
        if(!obj) return;
        var arr = [];
        for(var i in obj){
            var item = {
                name: obj[i],
                value: i
            };
            arr.push(item);
        }
        return arr;
    }
}

var methods = {
    methods: {
        objCopy: tools.objCopy,
        pageChange: function(){
            var temp = this.objCopy(this.searchData);
            temp.pageNo = this.page.pageNo;
            temp.pageSize = this.page.pageSize;
            this.getData(temp);
        },
        pageSizeChange: function(){
            var temp = this.objCopy(this.searchData);
            temp.pageSize = this.page.pageSize;
            temp.pageNo = 1;
            this.getData(temp);
        },
        selectAll: function(val){
            this.selectItem = val;
        },
        selectChange: function(val){
            this.selectItem = val;
        },
        goInfo: function(val){
            window.open('/menu/ware/toGoodsInfo?wareId='+ val,'_blank');
        }
    }
}

function getChild(id,data){
    var arr = [];
    data.forEach(function(val){
        if(val.fid === id){
            var temp = {
                value: val.id,
                label: val.name
            };
            if(getChild(val.id,data).length) temp.children = getChild(val.id,data);
            arr.push(temp);
        }
    })
    return arr;
}

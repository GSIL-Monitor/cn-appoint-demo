/**
 * Created by wanggenhua on 2017/3/23.
 */
var vm = new Vue({
    el: '.price_list',
    mixins: [validate,methods],
    data: {
        tableTitle: thead.priceList,
        query: {
            wareId: null,
            wareName: null,
            wareStatus: null,
            sku: null,
            startStockNum: 0,
            endStockNum: 0
        },
        page: {
            current: 1,
            total: 50,
            pageSize: 10
        },
        selectItem: [],
        searchData: {}
    },
    methods: {
        search: function(){},
        getData: function(){

        }
    }
})
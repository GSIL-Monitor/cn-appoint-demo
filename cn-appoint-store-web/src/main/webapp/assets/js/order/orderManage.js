seajs.use('/assets/js/order/pages/build/template', function(template) {
    routerInit = function(pages){
        var
            //创建路由根组件
            App = Vue.extend();
        //创建路由实例
        routerInit.router = new VueRouter();
        routerInit.router.map({
            '/orderAll' : {
                component: pages.orderAll
            },
            '/orderUnpaid': {
                component: pages.orderUnpaid
            },
            '/orderInstore': {
                component: pages.orderInstore
            },
            '/orderDetail/:orderId': {
                component: pages.orderDetail
            },
            '/outbound/:orderId/:logistics': {
                component: pages.outbound
            },
            '/mergeOutbound/:orderId': {
                component: pages.mergeOutbound
            },
            '/batchOutbound/:orderId': {
                component: pages.batchOutbound
            }
        });
        routerInit.router.redirect({
            '/' : '/orderAll'
        });
        //路由启动
        routerInit.router.start(App,'#orderManage');
    };

    var common = {
        methods: {
            goPage : function(val,data,logistics){
                if(!val) return;
                var url = '/' + val;
                if(val != 'outbound' && data && !logistics){
                    routerInit.router.go({ path: val+'/'+data });
                }else if(val == 'outbound' && data && logistics){
                    routerInit.router.go({ path: val+'/'+data+'/'+logistics});
                }else if(val == 'outbound' && data && !logistics){
                    // this.$Message.info('请选择物流公司！', 2);
                    routerInit.router.go({ path: val+'/'+data+'/'+0});
                }else{
                    routerInit.router.go(val);
                }
            }
        }
    };

    var pages = {};
    pages.orderAll = {
        template: template('orderAll')(),
        mixins: [common,orderAllData],
        methods: {
            batchExport: function(){
                console.log('导出');
            }
        }
    };
    pages.orderUnpaid = {
        template: template('orderUnpaid')(),
        mixins: [common,orderUnpaidData],
        methods: {
            batchExport: function(){
                console.log('导出');
            }
        }
    };
    pages.orderInstore = {
        template: template('orderInstore')(),
        mixins: [common,orderInstoreData],
        methods: {
            batchExport: function(){
                console.log('导出');
            }
        }
    };
    pages.orderDetail = {
        template: template('orderDetail')(),
        mixins: [common,orderDetailData],
        methods: {

        }
    };
    pages.outbound = {
        template: template('outbound')(),
        mixins: [common,outboundData],
        methods: {

        }
    };
    pages.mergeOutbound = {
        template: template('mergeOutbound')(),
        mixins: [common,mergeOutboundData],
        methods: {

        }
    };
    pages.batchOutbound = {
        template: template('batchOutbound')(),
        mixins: [common,batchOutboundData],
        methods: {

        }
    };

    Vue.mixin({
        watch: {
            isCopy: function(val) {
                if(val) {
                    this.$Message.info('已经复制到剪贴板', null, function() {
                        this.isCopy = false;
                    }.bind(this));
                }
            }
        },
        data: function() {
            return {
                isCopy: false
            }
        }
    });

    routerInit(pages);
});
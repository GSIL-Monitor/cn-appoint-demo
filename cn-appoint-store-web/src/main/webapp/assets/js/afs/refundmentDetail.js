var vm = new Vue({
    el: '#afs_refundment_detail',
    mixins: [validate,methods],
    data: {
        data: {
            refundmentApply: {}
        },
        metadata: {
            rejectTypeList: null,
            popVenderOrderDomainName: null,
            // 出库情况
            outOfDeptActualMetadata: [{
                code: 1,
                name: "已出库"
            }, {
                code: 2,
                name: "未出库"
            }]
        },
        query: {
            refundmentApplyQuery: {
                id: null
            }
        },
        buttonDisabled: {
            btnPassIntercept: true,
            btnPass: true,
            btnNotPass: true,
            btnForcePass: false
        },
        isLoaded: false
    },
    ready: function(){
        this.search();
        this.showButton4Loc();
    },
    methods: {
        search: function(){
            var _this = this;
            var param = {
                refundmentApplyQuery: {
                    id: refundmentId
                }
            };
            _this.getData(param);
        },
        getData: function(query){
            var _this = this;
            api.searchDetail(query,
                function(data){
                    // meatedata
                    if (!_this.metadata.rejectTypeList) {
                        _this.metadata.rejectTypeList = data.rejectTypeList;
                        _this.metadata.popVenderOrderDomainName = data.popVenderOrderDomainName;
                    }

                    // data
                    _this.data = data;
                    _this.isLoaded = true;
                },
                function(){
                    console.log('报错了')
                }
            );
        },
        getOutOfDeptActualName: function (code) {
            var name;
            this.metadata.outOfDeptActualMetadata.forEach(function(val){
                if(val.code == code){
                    name = val.name;
                }
            });
            return name;
        },
        onchange_OutOfDeptActual: function (val) {
            var data = this.data;
            var buttonDisabled = this.buttonDisabled;

            var isJdDelivery = false;
            var isManyWayBill = false;
            var extStatus = this.typeToString(data.refundmentApply.extStatus);
            var logiCopr = this.typeToString(data.logiCopr);
            if(logiCopr && logiCopr=='2087'){
                isJdDelivery = true;
            }
            var logiNo = this.typeToString(data.logiNo);
            logiNo = logiNo ? null : logiNo;
            if(logiNo && (logiNo.indexOf(",")>0 || logiNo.indexOf("|")>0)){
                isManyWayBill = true;
            }
            if(val==""){
                buttonDisabled.btnPass = true;
                buttonDisabled.btnPassIntercept = true;
            }else if(extStatus!='1'){
                //  订单来自老的退款系统 通过 一直可用
                buttonDisabled.btnPass = false;
                buttonDisabled.btnPassIntercept = true;
            }else if(extStatus=='1'&& val=="1" && isJdDelivery && !isManyWayBill){
                // 只有 在订单来自新的退款系统 JD快递 且非多运单的情况下 通过并京东拦截按钮 才可用
                buttonDisabled.btnPass = true;
                buttonDisabled.btnPassIntercept = false;
            }else if(val=="2" || val=="1"){
                buttonDisabled.btnPass = false;
                buttonDisabled.btnPassIntercept = true;
            }else{
                buttonDisabled.btnPass = true;
                buttonDisabled.btnPassIntercept = true;
            }

            this.showButton4Loc();
        },
        // 选择驳回原因时，驳回按钮可用
        onchange_RejectType: function (val) {
            var buttonDisabled = this.buttonDisabled;
            if(val!=""){
                buttonDisabled.btnNotPass = false;
            }else{
                buttonDisabled.btnNotPass = true;
            }
        },
        showButton4Loc: function () {
            var data = this.data;
            var buttonDisabled = this.buttonDisabled;

            var orderType= data.refundmentOrderType;
            // loc订单显示通过按钮
            if(orderType=="75"){
                buttonDisabled.btnPass = false;
                buttonDisabled.btnPassIntercept = true;
            }
        },
        //通过
        onclick_BtnPass: function () {
            var data = this.data;
            var refundmentApply = this.data.refundmentApply;

            if(this.isDupCommit(refundmentApply.status, refundmentApply.id)){
                alert("该申请单已经完成审核，请不要重复提交。");
                location.reload(true);
                return false;
            }
            var cr = jQuery.trim(refundmentApply.checkRemark);
            refundmentApply.rejectType = "";
            if (cr.length <= 0) {
                refundmentApply.checkRemark = "商家同意退款";
            }
            if (cr.length > 150) {
                alert("审核意见最多输入150个汉字");
                jQuery("#checkRemark").focus();
                return false;
            }
            var outOfDeptStr = "";
            if(refundmentApply.outOfDeptActual==1){
                outOfDeptStr="已出库";
            } else if(refundmentApply.outOfDeptActual==2){
                outOfDeptStr="未出库";
            }
            var payType = data.payType;
            if (confirm("请确认审核是否通过？\n\n出库情况："+outOfDeptStr+"\n支付方式："+payType)) {
                //如果为保税区订单，给予相应的提示信息确认
                if(data.orderBondedInfo){
                    if(confirm("此订单为保税区订单，已发送至海关处理，除非已确认海关无法放行或可以追回货物，否则建议不要审核通过，可能造成货款两失!")){
                        this.refundmentApplyForm_Submit({
                            id: refundmentApply.id,
                            status: 1,
                            oldStatus: refundmentApply.status
                        });
                    }
                } else {
                    this.refundmentApplyForm_Submit({
                        id: refundmentApply.id,
                        status: 1,
                        oldStatus: refundmentApply.status
                    });
                }
            }
        },
        //强制退款
        onclick_btnForcePass: function () {
            var refundmentApply = this.data.refundmentApply;

            if(this.isDupCommit('6', refundmentApply.id)){
                alert("该申请单已经不是拦截并退款状态，不能强制关单。");
                location.reload(true);
                return false;
            }
            if (confirm("强制退款后，订单款项会立即退给客户，请确认！")) {
                this.refundmentApplyForm_Submit({
                    id: refundmentApply.id,
                    status: 9,
                    oldStatus: refundmentApply.status
                });
            }
        },
        //拦截并通过
        onclick_btnPassIntercept: function () {
            var data = this.data;
            var refundmentApply = this.data.refundmentApply;

            var cr = jQuery.trim(refundmentApply.checkRemark);
            refundmentApply.rejectType = "";
            if (cr.length <= 0) {
                refundmentApply.checkRemark = "商家同意退款";
            }
            if (cr.length > 150) {
                alert("审核意见最多输入150个汉字");
                jQuery("#checkRemark").focus();
                return false;
            }
            var outOfDeptStr = "";
            if(refundmentApply.outOfDeptActual==1){
                outOfDeptStr="已出库";
            } else if(refundmentApply.outOfDeptActual==2){
                outOfDeptStr="未出库";
            }
            var payType = data.payType;
            if (confirm("请确认审核是否通过？\n\n出库情况："+outOfDeptStr+"\n支付方式："+payType)) {
                //如果为保税区订单，给予相应的提示信息确认
                if(data.orderBondedInfo){
                    if(confirm("此订单为保税区订单，已发送至海关处理，除非已确认海关无法放行或可以追回货物，否则建议不要审核通过，可能造成货款两失!")){
                        this.refundmentApplyForm_Submit({
                            id: refundmentApply.id,
                            status: 6,
                            oldStatus: refundmentApply.status
                        });
                    }
                } else {
                    this.refundmentApplyForm_Submit({
                        id: refundmentApply.id,
                        status: 6,
                        oldStatus: refundmentApply.status
                    });
                }
            }
        },
        //不通过
        onclick_btnNotPass: function () {
            var refundmentApply = this.data.refundmentApply;

            var rejectType= refundmentApply.rejectType;
            var cr = jQuery.trim(refundmentApply.checkRemark);
            if (rejectType==''||rejectType.length <= 0) {
                alert("请选择驳回原因!");
                // $("#rejectType").focus();
                return false;
            }
            if (cr.length <= 0) {
                alert("请填写审核意见!");
                jQuery("#checkRemark").focus();
                return false;
            }
            if (cr.length > 150) {
                alert("审核意见最多输入150个汉字");
                jQuery("#checkRemark").focus();
                return false;
            }
            if(this.isDupCommit(refundmentApply.status, refundmentApply.id)){
                alert("该申请单已经完成审核，请不要重复提交。");
                location.reload(true);
                return false;
            }
            if (confirm("请确认是否驳回？")) {
                this.refundmentApplyForm_Submit({
                    id: refundmentApply.id,
                    status: 2,
                    oldStatus: refundmentApply.status
                });
            }
        },
        isDupCommit: function (status, refundId) {
            var isDup = false;
            var param = {
                refundmentApplyQuery: {
                    id: refundId,
                    status: status
                }
            };
            api.refundmentCheckDupCommit(param,
                function(data){
                    var result = data.result;
                    if(result && parseInt(result) <= 0){
                        isDup = true;
                    }
                },
                function(e){
                    alert("error"+ e.message)
                    console.log(e.message);
                },
                false
            );
            return isDup;
        },
        refundmentApplyForm_Submit: function (refundmentApply) {
            var data = this.data;
            if(data.refundmentApply.status==0) {
                refundmentApply.outOfDeptActual = data.refundmentApply.outOfDeptActual;
                refundmentApply.rejectType = data.refundmentApply.rejectType;
            }
            if(data.refundmentApply.status==0 && ((data.refundmentApply.opOwner==30) || (data.refundmentApply.opOwner==33))) {
                refundmentApply.checkRemark = data.refundmentApply.checkRemark;
            }
            var param = {
                refer: data.refer,
                refundmentApply: refundmentApply
            };
            api.updateRefundment(param,
                function(data){
                    var stopmsg = data.stopmsg;
                    if (stopmsg) {
                        alert(stopmsg);
                        return;
                    }
                    if (data.refundId) {
                        location.reload(true);
                        return;
                    }
                    if (data.refer) {
                        location.href = data.refer;
                        return;
                    }
                },
                function(e){
                    alert("error: 出错了");
                    console.log(e);
                },
                false
            );
        }
    }
})

package com.jd.appoint.store.constant;

/**
 * UMP监控key常量，注意各层命名规则
 * Created by wangshangyu on 2017/2/23.
 */
public class UMPKeyConstants {

    public static final String UMP_APP_NAME = "pop-o2o-stos";

    /**
     * UMP-关闭心跳监控
     */
    public static final boolean UMP_DISABLE_HEART = false;
    /**
     * UMP-开启方法性能监控
     */
    public static final boolean UMP_ENABLE_TP = true;

    /**
     * Rpc层UMP监控点KEY创建规则： upperCase(RPC_${serviceName}_${methodName}) = RPC_${serviceName}_${methodName}
     */
    public static final String RPC_LOCVENDORCODEMODERPC_FINDCODEMODEBYVENDORID = "RPC_LocVendorCodeModeRPC_findCodeModeByVendorId";

    public static final String RPC_LOCCODEMANAGERPC_INQUIRELOCCODEINFO = "RPC_LocCodeManageRPC_inquireLocCodeInfo";
    public static final String RPC_LOCCODEMANAGERPC_INQUIRELOCORDERINFO = "RPC_LocCodeManageRPC_inquireLocOrderInfo";
    public static final String RPC_LOCCODEMANAGERPC_LOCCODEWRITEOFF = "RPC_LocCodeManageRPC_locCodeWriteOff";
    public static final String RPC_LOCCODEMANAGERPC_PAGEDINQUIRELOCCODEINFOLIST = "RPC_LocCodeManageRPC_pagedInquireLocCodeInfoList";
    public static final String RPC_LOCCODEMANAGERPC_INQUIRELOCORDERINFOLIST = "RPC_LocCodeManageRPC_inquireLocOrderInfoList";
    public static final String RPC_LOCCODEMANAGERPC_PAGINATEINQUIRELOCORDERINFOLIST = "RPC_LocCodeManageRPC_paginateInquireLocOrderInfoList";

    public static final String RPC_COMMONSKURPC_QUERYSKULISTBYID = "RPC_CommonSkuRPC_querySkuListById";
    public static final String RPC_COMMONSKURPC_QUERYSKUBYID = "RPC_CommonSkuRPC_querySkuById";
    public static final String RPC_COMMONSKURPC_SEARCHSKULISTBYQUERY = "RPC_CommonSkuRPC_searchSkuListByQuery";
    public static final String RPC_COMMONSKURPC_QUERYSKUSBYIDS = "RPC_CommonSkuRPC_querySkusByIds";

    public static final String RPC_VENDORCOMPANYRPC_INQUIREABOUTCOMPANYINFO = "RPC_VendorCompanyRPC_inquireAboutCompanyInfo";

    public static final String RPC_VENDORSHOPRPC_INQUIREABOUTSHOPINFO = "RPC_VendorShopRPC_inquireAboutShopInfo";
    public static final String RPC_VENDORBASICINFORPC_INQUIREABOUTVENDERINFO = "RPC_VendorBasicInfoRPC_inquireAboutVenderInfo";

    public static final String RPC_FINANCEACCOUNTRPC_FINDMERCHANTNOBYWALLETACCOUNTNAME = "RPC_FinanceAccountRPC_findMerchantNoByWalletAccountName";
    public static final String RPC_BIZ_FINANCEACCOUNTRPC_FINDFINANCEBYWALLETACCOUNTNAME = "RPC_BIZ_FinanceAccountRPC_findFinanceByWalletAccountName";
    public static final String RPC_FINANCEACCOUNTRPC_CREATEONLINEBANKACCOUNT = "RPC_FinanceAccountRPC_createOnlineBankAccount";
    public static final String RPC_BIZ_FinanceAccountRPC_createOnlineBankAccount = "RPC_BIZ_FinanceAccountRPC_createOnlineBankAccount";
    public static final String RPC_FINANCEACCOUNTRPC_CREATEBALANCEACCOUNT = "RPC_FinanceAccountRPC_createBalanceAccount";
    public static final String RPC_BIZ_FinanceAccountRPC_createBalanceAccount = "RPC_BIZ_FinanceAccountRPC_createBalanceAccount";

    public static final String RPC_SENDEMAILRPC_SENDVERIFICATIONCODE = "RPC_SendEmailRPC_sendVerificationCode";

    public static final String RPC_VENDORSTORERPC_FINDSTOREWALLETACCOUNT = "RPC_VendorStoreRPC_findStoreWalletAccount";
    public static final String RPC_VENDORSTORERPC_FINDSTOREAUTHORIZEDSTATUS = "RPC_VendorStoreRPC_findStoreAuthorizedStatus";
    public static final String RPC_VENDORSTORERPC_SAVESTOREAUTHORIZEDSTATUS = "RPC_VendorStoreRPC_saveStoreAuthorizedStatus";
    public static final String RPC_BIZ_VendorStoreRPC_saveStoreAuthorizedStatus = "RPC_BIZ_VendorStoreRPC_saveStoreAuthorizedStatus";
    public static final String RPC_VENDORSTORERPC_FINDSTOREMENUSBYPIN = "RPC_VendorStoreRPC_findStoreMenusByPin";
    public static final String RPC_VENDORSTORERPC_FINDSTOREINFOBYIDS = "RPC_VendorStoreRPC_findStoreInfoByIds";
    public static final String RPC_VENDORSTORERPC_QUERYALLSTOREMENUBYSYSTEMTYPE = "RPC_VendorStoreRPC_queryAllStoreMenuBySystemType";
    public static final String RPC_VENDORSTORERPC_UPDATEFINANCEACCOUNT = "RPC_VendorStoreRPC_updateFinanceAccount";

    public static final String RPC_BANKACCOUNTINFORPC_INSERTVENDERSTOREBANK = "RPC_BankAccountInfoRPC_insertVenderStoreBank";
    public static final String RPC_BANKACCOUNTINFORPC_UPDATEVENDERSTOREBANK = "RPC_BankAccountInfoRPC_updateVenderStoreBank";
    public static final String RPC_BANKACCOUNTINFORPC_QUERYVENDERSTOREBANKBYSTOREID = "RPC_BankAccountInfoRPC_queryVenderStoreBankByStoreId";

    public static final String RPC_BANKACCOUNTVERIFYRPC_INSERTSTOREBANKACCOUNT = "RPC_BankAccountVerifyRPC_insertStoreBankAccount";
    public static final String RPC_BANKACCOUNTVERIFYRPC_QUERYSTOREBANKACCOUNT = "RPC_BankAccountVerifyRPC_queryStoreBankAccount";

    public static final String RPC_BANKCNAPSSERVICERPC_GETALLBANKNAME = "RPC_BankCnapsServiceRPC_getAllBankName";
    public static final String RPC_BANKCNAPSSERVICERPC_GETALLCNAPS = "RPC_BankCnapsServiceRPC_getAllCnaps";
    public static final String RPC_BANKCNAPSSERVICERPC_GETALLPROVICES = "RPC_BankCnapsServiceRPC_getAllProvices";
    public static final String RPC_BANKCNAPSSERVICERPC_GETALLCITYSBYPROVICEID = "RPC_BankCnapsServiceRPC_getAllCitysByProviceId";


    /**
     * 商品管理ump
     */
    public static final String RPC_WARECODEMANAGERPC_ONOROFFLINE = "RPC_WareCodeManageRPC_wareCodeOnOroffLine";//商品上下或下架
    public static final String RPC_WARECODEMANAGERPC_DELONLINE = "RPC_WareCodeManageRPC_wareCodeDelOnLine";//删除在售商品
    public static final String RPC_WARECODEMANAGERPC_RECOVER = "RPC_WareCodeManageRPC_wareCodeRECOVER";//恢复已删除商品
    public static final String RPC_WARECODEMANAGERPC_SEARCHWARE = "PPP_WareCodeManageRPC_searchOnline";//查询查询商品
    public static final String RPC_WARECODEMANAGERPC_RECLAIM = "RPC_WareCodeManageRPC_wareCodeReclaim";//认领商品
    public static final String RPC_WARECODEMANAGERPC_UPDATEWARE = "RPC_WareCodeManageRPC_UpdateWare";//更新商品
    public static final String RPC_WARECODEMANAGERPC_DETAIL = "RPC_WareCodeManageRPC_wareCodeDetail";//商品详情
    public static final String RPC_WARECODEMANAGERPC_SOTOCKBOOKED = "RPC_WareCodeManageRPC_SotockBooked";//库存已占用数
    public static final String RPC_WARECODEMANAGERPC_WAREATTR = "RPC_WareCodeManageRpc_WareAttr";//商品属性
    public static final String RPC_WARECODEMANAGERPC_DELIVERY_AREA = "RPC_WareCodeManageRpc_Delivery";//商品发货地
    public static final String RPC_WARECODEMANAGERPC_FREIGHT = "RPC_WareCodeManageRpc_Fright";//运费
    public static final String RPC_WARECODEMANAGERPC_UPDATASTOCK = "RPC_WareCodeManageRPC_updataStock ";//修改商品库存
    public static final String RPC_WARECODEMANAGERPC_GETBRAND = "RPC_WareCodeManageRPC_getBrand";//获取品牌列表
    public static final String RPC_WARECODEMANAGERPC_GETCATEGORYS = "RPC_WareCodeManageRPC_GETCATEGORYS";//获取商品类目
    public static final String RPC_WARECODEMANAGERPC_GETSHOPCATEGORY = "RPC_WareCodeManageRPC_GETSHOPCATEGORY";//获取商品店内分类

    public static final String POP_STORES_RCP_STOREUSERINFOIMPL_GETSTOREUSERINFO = "pop.stores.rcp.StoreUserInfoImpl.getStoreUserInfo";
    public static final String POP_STORES_RPC_BIZ_StoreUserInfoRCP_getStoreUserInfo = "RPC_BIZ_StoreUserInfoRCP_getStoreUserInfo";

    /**
     * service层UMP监控点KEY创建规则： upperCase(SERVICE_${serviceName}_${methodName}) = Service_${serviceName}_${methodName}
     */
    public static final String SERVICE_LOCMANAGESERVICE_QUERYLOCORDERBYCODENUMBER = "Service_LocManageService_queryLocOrderByCodeNumber";
    public static final String SERVICE_LOCMANAGESERVICE_PAGEDQUERYLOCORDERLIST = "Service_LocManageService_pagedQueryLocOrderList";
    public static final String SERVICE_LOCMANAGESERVICE_PAGEDQUERYLOCORDERBYTELNUM = "Service_LocManageService_pagedQueryLocOrderByTelNum";
    public static final String SERVICE_LOCMANAGESERVICE_PAGEDQUERYLOCORDERBYCODENUM = "Service_LocManageService_pagedQueryLocOrderByCodeNum";

    public static final String SERVICE_STOREMANAGESERVICE_GETSTOREINFORMATION = "Service_StoreManageService_getStoreInformation";
    public static final String SERVICE_STOREMANAGESERVICE_HASAUTHORIZED = "Service_StoreManageService_hasAuthorized";

    public static final String SERVICE_BALANCEFINANCESERVICE_ACCREDITSETTLEMENT = "Service_BalanceFinanceService_accreditSettlement";

    public static final String SERVICE_SENDEMAILSERVICE_SENDEMAILVERIFICATIONCODE = "Service_SendEmailService_sendEmailVerificationCode";


    public static final String RPC_WECHATLOGINRPCIMPL_VERIFYLOGIN = "rpc_wechatloginrpcimpl_verifylogin";
    public static final String RPC_SHOP_CATAGORY = "rpc_shop_catagory";
    public static final String RPC_REMARK_GETVENDERIDBYORDERID = "rpc_remark_getvenderidbyorderid";
    public static final String RPC_REMARK_GETORDERDATANOTPAYBYQUERY = "rpc_remark_getOrderDataNotPayByQuery";
    public static final String RPC_REMARK_QUERYONEORDERREMARKRPC = "rpc_remark_queryOneOrderRemarkRPC";
    public static final String RPC_REMARK_QUERYONEORDERREMARK = "rpc_remark_queryOneOrderRemark";
    public static final String RPC_REMARK_UPDATEORDERREMARKRPC = "rpc_remark_updateOrderRemarkRPC";
    public static final String RPC_REMARK_QUERYORDERREMARKLIST = "rpc_remark_queryOrderRemarkList";
    public static final String RPC_REMARK_DETELEORDERREMARK = "rpc_remark_deteleOrderRemark";
    public static final String RPC_REMARK_INSERTORDERREMARK = "rpc_remark_insertOrderRemark";



    public static final String JIMDB_JIMDBSTOREMENUSERVICEIMPL_WRITE = "JIMDB_JimdbStoreMenuServiceImpl_write";

    public static final String JIMDB_JIMDBSTOREMENUSERVICEIMPL_READ = "JIMDB_JimdbStoreMenuServiceImpl_read";

    /*扫jpass码核销*/
    public static final String  RPC_JPASSCODEMANAGERPC_CANCELOFFLOCCOUPON = "rpc_JpassCodeManageServiceImpl_cancelOffLocCoupon";


}

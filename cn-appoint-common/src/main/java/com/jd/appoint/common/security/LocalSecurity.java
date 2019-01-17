//package com.jd.appoint.common.security;
//
//import com.jd.appoint.common.conf.PropertiesLoader;
//import com.jd.security.tdeclient.TDEClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * 对于京东提供的安全包进行的动态配置的包装
// */
//public class LocalSecurity {
//    private static Logger logger = LoggerFactory.getLogger(LocalSecurity.class);
//    private static final String SECURTY_TOKEN = "securty.token";
//    private static final String SECURTY_ISPROD = "securty.isprod";
//    private static final String SALT = "jd.cn_appoint.salt";
//    private static String token;
//    private static boolean isProd = false;
//    private static TDEClient tdeClient = null;
//
//    static {
//        PropertiesLoader propertiesLoader = new PropertiesLoader("config.properties");
//        token = propertiesLoader.getProperty(SECURTY_TOKEN);
//        isProd = Boolean.valueOf(propertiesLoader.getProperty(SECURTY_ISPROD));
//        tdeClient = getTDEClient();
//    }
//
//
//    /**
//     * 包装对应返回的客户端
//     *
//     * @return
//     */
//    public static TDEClient getTDEClient() {
//        try {
//            while (null == tdeClient) {
//                synchronized (LocalSecurity.class) {
//                    tdeClient = TDEClient.getInstance(token, isProd);
//                }
//            }
//            return tdeClient;
//        } catch (Exception e) {
//            logger.error("securty.token={},securty.isprod={}加解密工具类使用出现问题,具体的异常为e={}", SECURTY_TOKEN, SECURTY_ISPROD, e);
//        }
//        return tdeClient;
//    }
//
//    /**
//     * 加密
//     *
//     * @param str
//     * @return
//     */
//    public static String encrypt(String str) {
//        try {
//            return tdeClient.encryptString(str);
//        } catch (Exception e) {
//            logger.error("加密失败", e);
//            throw new SecurityException(e);
//        }
//    }
//
//    /**
//     * 解密
//     *
//     * @param str
//     * @return
//     */
//    public static String decrypt(String str) {
//        try {
//            return tdeClient.decryptString(str);
//        } catch (Exception e) {
//            logger.error("解密失败", e);
//            throw new SecurityException(e);
//        }
//    }
//
//    /**
//     * 获取索引列
//     *
//     * @param str
//     * @return
//     */
//    public static String getIndexStr(String str) {
//        try {
//            return TDEClient.calculateStringIndex(str.getBytes(), SALT.getBytes());
//        } catch (Exception e) {
//            logger.error("获取索引列失败", e);
//            throw new SecurityException(e);
//        }
//    }
//
//    public static void main(String[] args) {
//        System.out.println(LocalSecurity.encrypt("北京"));
//    }
//
//}

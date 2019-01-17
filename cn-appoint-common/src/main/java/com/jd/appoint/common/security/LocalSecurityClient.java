package com.jd.appoint.common.security;

import com.google.common.base.Strings;
import com.jd.security.tdeclient.TDEClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * Created by shaohongsen on 2018/5/29.
 */
public class LocalSecurityClient {
    private String token;
    private boolean isProd;
    private String salt;

    private TDEClient tdeClient;
    private Logger logger = LoggerFactory.getLogger(LocalSecurityClient.class);

    @PostConstruct
    public void init() {
        try {
            tdeClient = TDEClient.getInstance(token, isProd);
        } catch (Exception e) {
            throw new IllegalStateException("加密客户端初始化失败", e);
        }
    }

    /**
     * 加密
     *
     * @param str
     * @return
     */
    public String encrypt(String str) {
        try {
            return tdeClient.encryptString(str);
        } catch (Exception e) {
            logger.error("加密失败", e);
            throw new SecurityException(e);
        }
    }

    private boolean canDecrypt(String str) {
        TDEClient.CipherStatus decryptAble = this.tdeClient.isDecryptable(str);
        return decryptAble == TDEClient.CipherStatus.Decryptable;
    }

    /**
     * 解密
     *
     * @param str
     * @return
     */
    public String decrypt(String str) {
        if (Strings.isNullOrEmpty(str)) {
            return str;
        }
        try {
            if (!canDecrypt(str)) {
                return str;
            }
            return tdeClient.decryptString(str);
        } catch (Exception e) {
            logger.error("解密失败", e);
            throw new SecurityException(e);
        }
    }

    /**
     * 获取索引列
     *
     * @param str
     * @return
     */
    public String getIndexStr(String str) {
        try {
            return TDEClient.calculateStringIndex(str.getBytes(), salt.getBytes());
        } catch (Exception e) {
            logger.error("获取索引列失败", e);
            throw new SecurityException(e);
        }
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setProd(boolean prod) {
        isProd = prod;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}

package com.jd.appoint.common.hrm;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jd.common.cache.RedisClient;
import com.jd.common.hrm.UimHelper;
import com.jd.common.web.LoginContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * uim重写
 * Created by lijianzhen1 on 2017/11/21.
 */
public class HrmPrivilegeHelper {
    private final static String SUCCESS = "200";
    private static final Logger LOG = LoggerFactory.getLogger(HrmPrivilegeHelper.class);
    private String uimKey = "260b8d665e81494e85ff6d79af712291";
    private String uimToken = "cbd5559d1a7e4825b123ca5258040a9d";
    private String uimUrl = "http://idm.jd.com/opaauth/router/rest";
    private String cacheKeyPrefix = "uim.privilege.";
    private RedisClient redisClient;
    protected int cacheTime = 900;

    public HrmPrivilegeHelper() {
        LOG.info("Init HrmPrivilegeHelper");
    }

    public void setUimKey(String uimKey) {
        this.uimKey = uimKey;
    }

    public void setUimToken(String uimToken) {
        this.uimToken = uimToken;
    }

    public void setUimUrl(String uimUrl) {
        this.uimUrl = uimUrl;
    }

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    public static boolean checkResource(String[] my, Set<String> resources) {
        if ((resources != null) && (my != null)) {
            for (String string : my) {
                if (resources.contains(string)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Map<String, String> getAllPrivilege(String pin) {
        if (this.redisClient != null) {
            String key = getKey(pin);
            if (this.redisClient.exists(key).booleanValue()) {
                return this.redisClient.hMget(key);
            }
            String pStr = getUimResourceName(pin);
            if (StringUtils.isNotEmpty(pStr)) {
                String[] items = pStr.split(",");
                Map<String, String> hashes = new HashMap();
                for (String hashKey : items) {
                    hashes.put(hashKey, "");
                }
                this.redisClient.hMSet(key, hashes);
                this.redisClient.expire(key, this.cacheTime, TimeUnit.SECONDS);
                return hashes;
            }
        } else {
            String pStr = getUimResourceName(pin);
            if (LOG.isDebugEnabled()) {
                LOG.debug("从UIM取的{}的权限数据为{}", new Object[]{pin, pStr});
            }
            if (StringUtils.isNotEmpty(pStr)) {
                String[] items = pStr.split(",");
                Map<String, String> hashes = new HashMap();
                for (String hashKey : items) {
                    hashes.put(hashKey, "");
                }
                return hashes;
            }
        }
        return new HashMap();
    }

    public Set<String> getResources(String pin) {
        Set<String> set = getAllPrivilege(pin).keySet();
        return set;
    }

    private boolean checkRightByUim(String erp, String resCode) {
        boolean hasRight = false;
        try {
            String req_math = URLEncoder.encode(this.uimUrl, "utf-8");
            String format = "json";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateFormat.format(new Date());
            String random = new Random().nextInt() + "";
            String sign = UimHelper.generate(this.uimKey, this.uimToken, timestamp, random);

            Map<String, String> keyValueMap = new HashMap();
            keyValueMap.put("req_path", req_math);
            keyValueMap.put("format", format);
            keyValueMap.put("req_method", "POST");
            keyValueMap.put("sign", sign);
            keyValueMap.put("app_key", this.uimKey);
            keyValueMap.put("method", "uim.auth.res.checkUserAuth");
            keyValueMap.put("v", "2.0");
            keyValueMap.put("timestamp", timestamp);
            keyValueMap.put("random", random);
            keyValueMap.put("token", this.uimToken);
            keyValueMap.put("reqId", erp);
            keyValueMap.put("resCode", resCode);
            String json = UimHelper.doPost(this.uimUrl, keyValueMap);
            if (!StringUtils.isEmpty(json)) {
                Object content = JSON.parseObject(json);
                if (content != null) {
                    Map mContent = (Map) content;
                    Map mResp = (Map) mContent.get("authTicket.get.response");
                    if (mResp != null) {
                        String status = (String) mResp.get("resStatus");
                        if (SUCCESS.equals(status)) {
                            Map mAuthTicket = (Map) mResp.get("authTicket");
                            if (mAuthTicket != null) {
                                String code = (String) mAuthTicket.get("code");
                                if (SUCCESS.equals(code)) {
                                    hasRight = true;
                                }
                            }
                        }
                    } else {
                        LOG.error("调用UIM接口异常:" + json);
                    }
                }
            } else {
                LOG.error("调用UIM接口异常,接口返回空串");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasRight;
    }

    private String getUimResourceName(String erp) {
        StringBuffer buffer = new StringBuffer();
        try {
            String req_math = URLEncoder.encode(this.uimUrl, "utf-8");
            String format = "json";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateFormat.format(new Date());
            String random = new Random().nextInt() + "";
            String sign = UimHelper.generate(this.uimKey, this.uimToken, timestamp, random);

            Map<String, String> keyValueMap = new HashMap();
            keyValueMap.put("req_path", req_math);
            keyValueMap.put("format", format);
            keyValueMap.put("req_method", "POST");
            keyValueMap.put("sign", sign);
            keyValueMap.put("app_key", this.uimKey);
            keyValueMap.put("method", "uim.auth.res.getUserAuthTicket");
            keyValueMap.put("v", "2.0");
            keyValueMap.put("timestamp", timestamp);
            keyValueMap.put("random", random);
            keyValueMap.put("token", this.uimToken);
            keyValueMap.put("reqId", erp);
            String json = UimHelper.doPost(this.uimUrl, keyValueMap);
            if (!StringUtils.isEmpty(json)) {
                Object content = JSON.parseObject(json);
                if (content != null) {
                    Map mContent = (Map) content;
                    Map mResp = (Map) mContent.get("authTicket.get.response");
                    if (mResp != null) {
                        String status = (String) mResp.get("resStatus");
                        if (SUCCESS.equals(status)) {
                            Map mAuthTicket = (Map) mResp.get("authTicket");
                            if (mAuthTicket != null) {
                                String code = (String) mAuthTicket.get("code");
                                if (SUCCESS.equals(code)) {
                                    JSONArray auths = (JSONArray) mAuthTicket.get("authCodes");
                                    if ((auths != null) && (auths.size() > 0)) {
                                        for (int i = 0; i < auths.size(); i++) {
                                            buffer.append((String) auths.get(i)).append(",");
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        LOG.error("调用UIM接口异常:" + json);
                    }
                }
            } else {
                LOG.error("调用UIM接口异常,接口返回空串");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (buffer.length() > 0) {
            return buffer.substring(0, buffer.length() - 1);
        }
        return "";
    }

    public boolean hasHrmPrivilege(String pin, String resouce) {
        String[] strings = resouce.split(",");
        boolean ok = false;
        for (String item : strings) {
            ok = (ok) || (checkRightByUim(pin, item));
        }
        return ok;
    }

    public boolean hasHrmPrivilege(String resouce) {
        LoginContext context = LoginContext.getLoginContext();
        return (context != null) && (context.isLogin()) && (hasHrmPrivilege(context.getPin(), resouce));
    }

    private String getKey(String pin) {
        if (pin == null) {
            throw new NullPointerException("Pin is null");
        }
        return this.cacheKeyPrefix + pin;
    }

    public void setCacheTime(int cacheTime) {
        this.cacheTime = cacheTime;
    }
}

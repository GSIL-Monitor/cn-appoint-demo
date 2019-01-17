package com.jd.appoint.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author lijizhen1@jd.com
 * @date 2018/7/2 9:45
 */
public class IPUtil {

    static {
        String ip = null;
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
            InetAddress ipAddr[] = InetAddress.getAllByName(hostName);
            for (int i = 0; i < ipAddr.length; i++) {
                ip = ipAddr[i].getHostAddress();
            }
            if (ip == null) {
                ip = ipAddr[0].getHostAddress();
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        LOCAL_IP = ip;
        HOST_NAME = hostName;

    }

    /**
     * 系统的本地IP地址
     */
    public static final String LOCAL_IP;

    /**
     * 系统的本地服务器名
     */
    public static final String HOST_NAME;
}

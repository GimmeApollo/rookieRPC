package com.netty.rpc.util;

/**
 * @author hdz
 * @description TODO
 * @create 2021年08月21日 19:19
 */
public class ServiceUtil {

    public static final String SERVICE_CONCAT_TOKEN = "#";

    //例：com.app.test.service.HelloService#1.0或者com.app.test.service.HelloService
    public static String makeServiceKey(String interfaceName, String version) {
        String serviceKey = interfaceName;
        if (version != null && version.trim().length() > 0) {
            serviceKey += SERVICE_CONCAT_TOKEN.concat(version);
        }
        return serviceKey;
    }
}

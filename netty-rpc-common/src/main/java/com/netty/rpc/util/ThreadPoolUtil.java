package com.netty.rpc.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hdz
 * @description 线程池工具类
 * @create 2021年08月22日 14:49
 */
public class ThreadPoolUtil {
    public static ThreadPoolExecutor makeServerThreadPool(final String serviceName, int corePoolSize, int maxPoolSize) {
        ThreadPoolExecutor serverHandlerPool = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1000),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "netty-rpc-" + serviceName + "-" + r.hashCode());  //给线程加个名字
                    }
                },
                new ThreadPoolExecutor.AbortPolicy());      //TODO:合适不

        return serverHandlerPool;
    }
}
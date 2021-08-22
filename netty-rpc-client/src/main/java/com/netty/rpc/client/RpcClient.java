package com.netty.rpc.client;

import com.netty.rpc.client.connect.ConnectionManager;
import com.netty.rpc.client.discovery.ServiceDiscovery;
import com.netty.rpc.client.proxy.ObjectProxy;
import com.netty.rpc.client.proxy.RpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Proxy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hdz
 * @description RPC客户端
 * @create 2021年08月17日 10:08
 */
public class RpcClient implements DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    private ServiceDiscovery serviceDiscovery;  //TODO

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8, 16,
            600L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1000));

    public RpcClient(String address) {
        this.serviceDiscovery = new ServiceDiscovery(address);     //构造函数自带连接服务端
    }

    //使用线程池调用服务
    public static void submit(Runnable task) {
        threadPoolExecutor.submit(task);
    }

    // 创建动态代理对象,注意有非受检异常
    @SuppressWarnings("unchecked")
    public static <T> T createService(Class<T> interfaceClass, String version) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new ObjectProxy<T>(interfaceClass, version)
        );
    }

    //可以直接使用异步调用
    public static <T> RpcService createAsyncService(Class<T> interfaceClass, String version) {
        return new ObjectProxy<T>(interfaceClass, version);
    }

    //关闭客户端
    @Override
    public void destroy() throws Exception {
        threadPoolExecutor.shutdown();
        serviceDiscovery.stop();
        ConnectionManager.getInstance().stop();

    }
}

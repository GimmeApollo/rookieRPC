package com.netty.rpc.server;

import com.netty.rpc.server.service.HelloServiceImpl;
import com.netty.rpc.server.service.HelloServiceImpl2;
import com.netty.rpc.server.service.PersonServiceImpl;
import com.netty.rpc.service.HelloService;
import com.netty.rpc.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hdz
 * @description 分服务注册
 * @create 2021年08月23日 12:25
 */
public class RpcServerBootstrap2 {
    private static final Logger logger = LoggerFactory.getLogger(RpcServerBootstrap2.class);

    public static void main(String[] args) {
        String serverAddress = "127.0.0.1:18899";
        String registryAddress = "127.0.0.1:2181";
        RpcServer rpcServer = new RpcServer(serverAddress, registryAddress);
        HelloService helloService1 = new HelloServiceImpl();
        rpcServer.addService(HelloService.class.getName(), "1.0", helloService1);
        HelloService helloService2 = new HelloServiceImpl2();
        rpcServer.addService(HelloService.class.getName(), "2.0", helloService2);
//        PersonService personService = new PersonServiceImpl();
//        rpcServer.addService(PersonService.class.getName(), "", personService);
        try {
            rpcServer.start();
        } catch (Exception ex) {
            logger.error("Exception: {}", ex.toString());
        }
    }
}

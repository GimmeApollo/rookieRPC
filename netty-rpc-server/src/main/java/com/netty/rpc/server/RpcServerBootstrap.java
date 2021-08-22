package com.netty.rpc.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author hdz
 * @description 启动服务端
 * @create 2021年08月22日 16:58
 */
public class RpcServerBootstrap {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("server-spring.xml");
    }
}

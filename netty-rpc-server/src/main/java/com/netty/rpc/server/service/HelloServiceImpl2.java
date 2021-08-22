package com.netty.rpc.server.service;

import com.netty.rpc.annotation.NettyRpcService;
import com.netty.rpc.service.HelloService;
import com.netty.rpc.service.Person;

/**
 * @author hdz
 * @description HelloService实现类版本2
 * @create 2021年08月17日 14:38
 */
@NettyRpcService(value = HelloService.class, version = "2.0")
public class HelloServiceImpl2 implements HelloService {

    public HelloServiceImpl2() {

    }

    @Override
    public String hello(String name) {
        return "Hi " + name;
    }

    @Override
    public String hello(Person person) {
        return "Hi " + person.getFirstName() + " " + person.getLastName();
    }

    @Override
    public String hello(String name, Integer age) {
        return name + " is " + age + " years old";
    }
}

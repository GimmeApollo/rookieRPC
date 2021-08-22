package com.netty.rpc.server.service;

import com.netty.rpc.annotation.NettyRpcService;
import com.netty.rpc.service.HelloService;
import com.netty.rpc.service.Person;

/**
 * @author hdz
 * @description HelloService实现类版本1
 * @create 2021年08月17日 14:35
 */
@NettyRpcService(value = HelloService.class, version = "1.0")
public class HelloServiceImpl implements HelloService {

    public HelloServiceImpl() {

    }

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }

    @Override
    public String hello(Person person) {
        return "Hello " + person.getFirstName() + " " + person.getLastName();
    }

    @Override
    public String hello(String name, Integer age) {
        return name + " is " + age;
    }
}

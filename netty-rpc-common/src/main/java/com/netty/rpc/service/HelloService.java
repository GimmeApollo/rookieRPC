package com.netty.rpc.service;

/**
 * @author hdz
 * @description TODO
 * @create 2021年08月17日 14:24
 */
public interface HelloService {
    String hello(String name);

    String hello(Person person);

    String hello(String name, Integer age);
}


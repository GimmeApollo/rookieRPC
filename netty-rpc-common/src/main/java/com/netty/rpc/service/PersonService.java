package com.netty.rpc.service;

import java.util.List;

/**
 * @author hdz
 * @description TODO
 * @create 2021年08月17日 16:34
 */
public interface PersonService {
    List<Person> callPerson(String name, Integer num);
}
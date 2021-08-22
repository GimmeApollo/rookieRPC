package com.netty.rpc.server.service;

import com.netty.rpc.annotation.NettyRpcService;
import com.netty.rpc.service.Person;
import com.netty.rpc.service.PersonService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hdz
 * @description TODO
 * @create 2021年08月17日 16:35
 */
@NettyRpcService(PersonService.class)
public class PersonServiceImpl implements PersonService {

    @Override
    public List<Person> callPerson(String name, Integer num) {
        List<Person> persons = new ArrayList<>(num);
        for (int i = 0; i < num; ++i) {
            persons.add(new Person(Integer.toString(i), name));
        }
        return persons;
    }
}

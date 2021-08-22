package com.netty.rpc.protocol;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * @author hdz
 * @description TODO
 * @create 2021年08月19日 0:23
 */
public class RpcProtocolTest {

    @Test
    public void equals1() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(129);
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(129);
        list2.add(1);
        System.out.println(list.containsAll(list2));
        list2.add(4);
        System.out.println(list.containsAll(list2));
        list2.remove(2);
        list2.add(1);
        list2.add(1);
        list2.add(1);
        System.out.println(list.containsAll(list2));
        System.out.println(list.containsAll(list2));
    }
}
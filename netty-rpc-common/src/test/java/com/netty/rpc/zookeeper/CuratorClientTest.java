package com.netty.rpc.zookeeper;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author hdz
 * @description TODO
 * @create 2021年08月20日 16:19
 */
public class CuratorClientTest {

    @Test
    public void getData() throws Exception{
        CuratorClient client = new CuratorClient("127.0.0.1:2181");
//        System.out.println(client.getData("/registry/data"));
        System.out.println((client.getChildren("/registry")));
        System.out.println(new String(client.getData("/registry/data-13626436260000000498")));
    }

    @Test
    public void getChildren() {
    }
}
package com.netty.rpc.client;

import com.netty.rpc.client.handler.AsyncRPCCallback;
import com.netty.rpc.client.handler.RpcFuture;
import com.netty.rpc.client.proxy.RpcService;
import com.netty.rpc.service.Person;
import com.netty.rpc.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

/**
 * @author hdz
 * @description TODO
 * @create 2021年09月29日 0:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:client-spring.xml")
public class RpcCallbackTest {

    @Autowired
    private RpcClient rpcClient;


    @Test
    public void rpcCallbackTest(){
        Semaphore semaphore = new Semaphore(0);

        try {
            RpcService client = rpcClient.createAsyncService(PersonService.class, "");
            int num = -1;
            RpcFuture helloPersonFuture = client.call("callPerson", "Jerry", num);
            helloPersonFuture.addCallback(new AsyncRPCCallback() {
                @Override
                public void success(Object result) {
                    List<Person> persons = (List<Person>) result;
                    for (int i = 0; i < persons.size(); ++i) {
                        System.out.println(persons.get(i));
                    }
                    semaphore.release();
                }

                @Override
                public void fail(Exception e) {
                    System.out.println("回调函数打印异常"+e);
                    semaphore.release();
                }
            });

            semaphore.acquire();
            System.out.println("回调函数测试结束，结果为"+helloPersonFuture.get());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.netty.rpc.client;

import com.netty.rpc.client.handler.RpcFuture;
import com.netty.rpc.client.proxy.RpcService;
import com.netty.rpc.service.HelloService;
import com.netty.rpc.service.Person;
import com.netty.rpc.service.PersonService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * @author hdz
 * @description 客户端测试
 * @create 2021年08月22日 21:03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:client-spring.xml")
public class RpcClientTest {

    @Autowired
    private RpcClient rpcClient;

    //hello版本1测试
    @Test
    public void helloTest1() {
        HelloService helloService = rpcClient.createService(HelloService.class, "1.0");
        String result = helloService.hello("World");
        System.out.println(result);
    }

    //hello版本2测试
    @Test
    public void helloTest2() {
        HelloService helloService = rpcClient.createService(HelloService.class, "2.0");
        Person person = new Person("Yong", "Huang");
        String result = helloService.hello(person);
        System.out.println(result);
    }

    @Test
    public void helloFutureTest2() throws Exception {
        RpcService helloService = rpcClient.createAsyncService(HelloService.class, "1.0");
        Person person = new Person("Yong", "Huang");
        RpcFuture result = helloService.call("hello", person);
        Assert.assertEquals("Hello Yong Huang", result.get());
        System.out.println(result.get());
    }

    @Test
    public void helloPersonFutureTest1() throws Exception {
        RpcService helloPersonService = rpcClient.createAsyncService(PersonService.class, "");
        Integer num = 5;
        RpcFuture result = helloPersonService.call("callPerson", "jerry", num);
        List<Person> persons = (List<Person>) result.get();
        List<Person> expectedPersons = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            expectedPersons.add(new Person(Integer.toString(i), "jerry"));
        }
        assertThat(persons, equalTo(expectedPersons));

        for (int i = 0; i < num; ++i) {
            System.out.println(persons.get(i));
        }
    }

    //rpc异步调用+负载均衡测试
    @Test
    public void rpcAsyncTest() throws Exception{
//        final RpcClient rpcClient = new RpcClient("127.0.0.1:2181");

        int threadNum = 1;
        final int requestNum = 10;
        Thread[] threads = new Thread[threadNum];

        long startTime = System.currentTimeMillis();
        //benchmark for async call
        for (int i = 0; i < threadNum; ++i) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < requestNum; i++) {
                        try {
                            RpcService client = rpcClient.createAsyncService(HelloService.class, "2.0");
                            RpcFuture helloFuture = client.call("hello", Integer.toString(i));
                            String result = (String) helloFuture.get(3000, TimeUnit.MILLISECONDS);
                            if (!result.equals("Hi " + i)) {
                                System.out.println("error = " + result);
                            } else {
                                System.out.println("result = " + result);
                            }
                            try {
                                Thread.sleep(10 * 1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            System.out.println(e.toString());
                        }
                    }
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        long timeCost = (System.currentTimeMillis() - startTime);
        String msg = String.format("Async call total-time-cost:%sms, req/s=%s", timeCost, ((double) (requestNum * threadNum)) / timeCost * 1000);
        System.out.println(msg);

        rpcClient.stop();

    }


}
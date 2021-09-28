package com.netty.rpc.client.handler;

/**
 * @author hdz
 * @description 增加回调函数
 * @create 2021年09月29日 0:18
 */
public interface AsyncRPCCallback {

    void success(Object result);

    void fail(Exception e);
}

package com.netty.rpc.client.proxy;

import com.netty.rpc.client.handler.RpcFuture;

/**
 * @author hdz
 * @description 调用call方法，返回future对象
 * @create 2021年08月17日 18:32
 */
public interface RpcService {
    RpcFuture call(String funcName, Object... args) throws Exception;
}
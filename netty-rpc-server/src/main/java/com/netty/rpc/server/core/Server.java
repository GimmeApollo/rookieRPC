package com.netty.rpc.server.core;

/**
 * @author hdz
 * @description TODO
 * @create 2021年08月21日 22:35
 */
public abstract class Server {

    public abstract void start() throws Exception;

    public abstract void stop() throws Exception;
}

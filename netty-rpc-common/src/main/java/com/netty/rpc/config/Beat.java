package com.netty.rpc.config;

import com.netty.rpc.entity.RpcRequest;

/**
 * @author hdz
 * @description TODO
 * @create 2021年08月21日 21:04
 */
public final class Beat {

    public static final int BEAT_INTERVAL = 30;
    public static final int BEAT_TIMEOUT = 3 * BEAT_INTERVAL;
    public static final String BEAT_ID = "BEAT_PING_PONG";

    public static RpcRequest BEAT_PING;

    static {
        BEAT_PING = new RpcRequest() {};
        BEAT_PING.setRequestId(BEAT_ID);
    }

}
package com.netty.rpc.client.handler;

import com.netty.rpc.client.connect.ConnectionManager;
import com.netty.rpc.config.Beat;
import com.netty.rpc.entity.RpcRequest;
import com.netty.rpc.entity.RpcResponse;
import com.netty.rpc.protocol.RpcProtocol;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hdz
 * @description 客户端业务InboundHandler(channelRegistered->channelActive->channelInactive->channelUnregistered)
 * @create 2021年08月19日 0:39
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
    private static final Logger logger = LoggerFactory.getLogger(RpcClientHandler.class);

    //存放requestId和RpcFuture
    private ConcurrentHashMap<String, RpcFuture> pendingRPC = new ConcurrentHashMap<>();
    private volatile Channel channel;
    private SocketAddress remotePeer;
    private RpcProtocol rpcProtocol;

    //(2)channel处于活动状态，可以接收和发送数据
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("channelActive:"+channel.id());
        this.remotePeer = this.channel.remoteAddress();     //记录远程的SocketAddress
    }

    //(1)记录注册的channel
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    //(3)处理返回的RpcResponse
    @Override
    public void channelRead0(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
        String requestId = response.getRequestId();
        logger.debug("request {} Receive response.", requestId);
        RpcFuture rpcFuture = pendingRPC.get(requestId);
        if (rpcFuture != null) {
            pendingRPC.remove(requestId);
            rpcFuture.done(response);
            //TODO:这里可以增加回调函数
        } else {
            logger.warn("Can not get pending response for request id: " + requestId);
        }
    }

    //遇到异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Client caught exception: " + cause.getMessage());
        ctx.close();
    }

    //当 ChannelnboundHandler.fireUserEventTriggered()方法被调用时被调用
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //处理IdleStateEvent事件
        if (evt instanceof IdleStateEvent) {
            //TODO:Send ping
//            logger.info("IdleStateEvent事件:"+((IdleStateEvent) evt).state().toString());
            sendRequest(Beat.BEAT_PING);
            logger.debug("Client send beat-ping to " + remotePeer);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    public void setRpcProtocol(RpcProtocol rpcProtocol) {
        this.rpcProtocol = rpcProtocol;
    }

    //channel没有连接到远程结点
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("channelInactive:");
        ConnectionManager.getInstance().removeHandler(rpcProtocol);     //移除掉rpcProtocol对应的Handler
    }

    public RpcFuture sendRequest(RpcRequest request) {
        RpcFuture rpcFuture = new RpcFuture(request);
        pendingRPC.put(request.getRequestId(), rpcFuture);
        try {
            //每个出站操作都将返回一个ChannelFuture，ChannelFutureListener将在操作完成时被通知该操作是成功了还是出错了。
            logger.info("Send request {}",request.getRequestId());
            ChannelFuture channelFuture = channel.writeAndFlush(request).sync();
            //TODO：这里可以改为ChannelFutureListener()
            if (!channelFuture.isSuccess()) {
                logger.error("Send request {} error", request.getRequestId());
            }
        } catch (InterruptedException e) {
            logger.error("Send request exception: " + e.getMessage());
        }

        return rpcFuture;
    }

    public void close() {
        //将目前暂存于ChannelOutboundBuffer中的消息在下一次flush或者writeAndFlush的时候冲刷到远程并关闭这个channel
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

}
package com.yuan.Client.rpcClient.impl;

import com.yuan.Client.rpcClient.RpcClient;
import com.yuan.common.Message.RpcRequest;
import com.yuan.common.Message.RpcResponse;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

public class NettyRpcClient implements RpcClient {

    private String host;
    private int port;
    private static final Bootstrap bootstrap;
    private static final EventLoopGroup eventLoopGroup;
    public NettyRpcClient(String host,int port){
        this.host=host;
        this.port=port;
    }

    static {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                //NettyClientInitializer这里 配置netty对消息的处理机制
                .handler(new NettyClientInitializer());
    }


    @Override
    public RpcResponse sendRequest(RpcRequest request) {
        return null;
    }
}

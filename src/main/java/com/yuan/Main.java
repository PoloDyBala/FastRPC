package com.yuan;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        // 1 处，创建 NioEventLoopGroup，可以简单理解为 线程池 + Selector 后面会详细展开
        // 2 处，选择服务 Scoket 实现类，其中 NioServerSocketChannel 表示基于 NIO 的服务器端实现，其它实现
        new ServerBootstrap() // 服务器端的启动器(组装netty组件)
                .group(new NioEventLoopGroup()) // 1
                .channel(NioServerSocketChannel.class) // 2
                .childHandler(new ChannelInitializer<NioSocketChannel>() { // 3
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new StringDecoder()); // 5
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() { // 6
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(8080); // 4
    }
}
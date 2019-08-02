package com.web.demo.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @CLassName MyClient
 * @Author xuzhu
 * @Date 2019/8/2 上午11:07
 * @Version 1.0
 * @Description
 **/
public class MyClient {

    public static void main(String[] args) throws Exception {
        EventLoopGroup eventExecutorGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutorGroup).channel(NioSocketChannel.class)
                    .handler(new ClientChannelInit());
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8889).sync();
            channelFuture.sync().channel().closeFuture().sync();
        } finally {
            eventExecutorGroup.shutdownGracefully();
        }

    }
}

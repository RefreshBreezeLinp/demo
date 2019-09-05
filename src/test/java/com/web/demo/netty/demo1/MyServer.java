package com.web.demo.netty.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @CLassName MyServer
 * @Author xuzhu
 * @Date 2019/8/4 下午5:51
 * @Version 1.0
 * @Description
 **/
public class MyServer {

    public static void main(String[] args) throws Exception {

        EventLoopGroup bootGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bootGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new MyServerChannelInit());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();

            channelFuture.channel().closeFuture().sync();
        } finally {
            bootGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }



    }

}

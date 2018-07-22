package com.web.demo.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {

    public static void main(String[] args) {

        EventLoopGroup eventLoopGroup =  new NioEventLoopGroup();
        EventLoopGroup workLoopGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootStrap = new ServerBootstrap();
            serverBootStrap.group(eventLoopGroup,workLoopGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInit());

            ChannelFuture channelFuture = serverBootStrap.bind(8889).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            System.out.println("error:"+e.getMessage());
        } finally {
            eventLoopGroup.shutdownGracefully();
            workLoopGroup.shutdownGracefully();
        }

    }

}

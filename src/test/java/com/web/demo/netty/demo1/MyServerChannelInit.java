package com.web.demo.netty.demo1;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @CLassName MyServerChannelInit
 * @Author xuzhu
 * @Date 2019/8/4 下午6:06
 * @Version 1.0
 * @Description
 **/
public class MyServerChannelInit extends ChannelInitializer<ServerSocketChannel> {

    @Override
    protected void initChannel(ServerSocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast(new IdleStateHandler(5, 7, 9, TimeUnit.SECONDS));
        channelPipeline.addLast(new MyServerChannelHandler());
    }
}

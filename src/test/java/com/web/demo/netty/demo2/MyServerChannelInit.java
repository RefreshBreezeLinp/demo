package com.web.demo.netty.demo2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http2.Http2Codec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @CLassName MyServerChannelInit
 * @Author xuzhu
 * @Date 2019/8/19 下午8:59
 * @Version 1.0
 * @Description
 **/
public class MyServerChannelInit extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast(new HttpServerCodec());
        channelPipeline.addLast(new ChunkedWriteHandler());
        channelPipeline.addLast(new HttpObjectAggregator(8192));
        channelPipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        channelPipeline.addLast(new MyServerChannelHandler());
    }
}

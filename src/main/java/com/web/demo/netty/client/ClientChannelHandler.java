package com.web.demo.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @CLassName ClientChannelInit
 * @Author xuzhu
 * @Date 2019/8/2 下午1:29
 * @Version 1.0
 * @Description
 **/
public class ClientChannelHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("client msg: " + msg);
        ctx.channel().writeAndFlush("client msg");
    }
}

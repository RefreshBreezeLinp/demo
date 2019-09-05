package com.web.demo.netty.demo1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @CLassName MyServerChannelHandler
 * @Author xuzhu
 * @Date 2019/8/4 下午6:10
 * @Version 1.0
 * @Description
 **/
public class MyServerChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
        switch (idleStateEvent.state()) {
            case READER_IDLE:
                System.out.println(ctx.channel().remoteAddress() + ": 读事件");
                break;
            case WRITER_IDLE:
                System.out.println(ctx.channel().remoteAddress() + ": 写事件");
                break;
            case ALL_IDLE:
                System.out.println(ctx.channel().remoteAddress() + ": 读写事件");
        }
        ctx.channel().close();
    }
}

package com.web.demo.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;


public class TestHttpServerHandler  extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String httpObject) throws Exception {
        /*if (httpObject  instanceof  HttpRequest){
            HttpRequest request = (HttpRequest) httpObject;
            System.out.println(request.method().name());
        }
        ByteBuf  content = Unpooled.copiedBuffer("hello world",CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
*/
        System.out.println("remote address: " + channelHandlerContext.channel().remoteAddress() + "msg:" + httpObject);
        channelHandlerContext.channel().writeAndFlush("httpObject");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        System.out.println("channel register");
        super.channelRegistered(channelHandlerContext);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active");
        ctx.channel().writeAndFlush("channel active");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unRegistered");
        super.channelUnregistered(ctx);
    }

}

package com.web.demo.nio;

import ch.qos.logback.core.net.server.Client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.security.Key;
import java.util.List;

public class Dealer {

    public static SocketChannel accept(Selector selector, ServerSocketChannel serverSocketChannel) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector,SelectionKey.OP_READ);
            socketChannel.write(Util.charset.encode("please input your name"));
        } catch (Exception e){
            System.out.println("Error while configure socket channel :"+e.getMessage());
            if (socketChannel!=null){
                try {
                    socketChannel.close();
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        }
        return socketChannel;
    }
    public static void read(Selector selector, SelectionKey selectionKey, List<SocketChannel> clientChannels) throws Exception {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(6 * 1024);
        StringBuilder content = new StringBuilder();
        int num = 0;
        try {
            while (socketChannel.read(byteBuffer)>0){
                byteBuffer.flip();
                content.append(Util.charset.decode(byteBuffer));
            }
            System.out.println("num : "+num);
            System.out.println("server is listening from client :"+socketChannel.getRemoteAddress()+" data rev is:"+content);
        } catch  (Exception e) {
            //如果出现异常，则将num改为-1，用于关闭sockChannel
            num = -1;
        }
        if (num>=0){
            if (content.length()>0){
                String[] arrayContent = content.toString().split(Util.USER_CONTENT_SPLITE);
                if (arrayContent!=null&&arrayContent.length == 1){
                    String name = arrayContent[0];
                    if (Util.users.contains(name)) {
                        socketChannel.write(Util.charset.encode(Util.USER_EXIST));
                    }
                    else {
                        Util.users.add(name);
                        int onlineNum = clientChannels.size();
                        String message = "welcome " +name+
                                " to chat room! Online numbers: "+onlineNum;
                        //todo 广播
                        BroadCast2(clientChannels,null,message);
                    }
                }
                //注册完，发送消息
                else if (arrayContent!=null&&arrayContent.length>1){
                    String name = arrayContent[0];
                    String message = content.substring(name.length() + Util.USER_CONTENT_SPLITE.length());
                    message = name+" say:"+message;
                    if (Util.users.contains(name)){
                        //不回发给发送此内容的客户端
                        // TODO: 2018/6/20
                        BroadCast2(clientChannels,socketChannel,message);
                    }
                }
            }
        }
        else {
            /**
             * 如果没有读到数据，对方关闭了socketChannel所以服务器这边也要关闭
             */
            socketChannel.close();
            int length = clientChannels.size();
            for (int i = 0;i<length;i++){
                if (clientChannels.get(i).equals(socketChannel)){
                    clientChannels.remove(i);
                }
            }
        }
    }

    public static int onlineNum(Selector selector) {
        int res = 0;
        for (SelectionKey selectionKey:selector.keys()){
            Channel channel = selectionKey.channel();
            if (channel instanceof SocketChannel) {
                res++;
            }
        }
        return res;
    }

    public void BroadCast(Selector selector,SocketChannel socketChannel, String content) throws IOException  {
        for (SelectionKey selectionKey:selector.keys()){
            Channel channel = selectionKey.channel();
            if (channel instanceof SocketChannel && socketChannel != channel) {
                SocketChannel socketChannel1 = (SocketChannel) channel;
                socketChannel.write(Util.charset.encode(content));
            }
        }
    }

    public static void BroadCast2(List<SocketChannel> socketChannels,SocketChannel socketChannel,String content) {
        socketChannels.forEach(socketChannel1 -> {
            if (!socketChannel.equals(socketChannel1)){
                //除了自己，其他通道都通知
                try {
                    socketChannel.write(Util.charset.encode(content));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

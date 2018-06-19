package com.web.demo.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
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
                
            }
        }
    }
}

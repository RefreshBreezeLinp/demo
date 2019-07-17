package com.web.demo.nio.client;

import com.web.demo.nio.server.Util;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Dealer {

    public  static void read(SocketChannel socketChannel) throws Exception {

        ByteBuffer byteBuffer = ByteBuffer.allocate(6 * 1024);
        int num = 0;
        StringBuilder content = new StringBuilder();
        while ((num = socketChannel.read(byteBuffer))>0) {
            byteBuffer.flip();
            content.append(Util.charset.decode(byteBuffer));
        }
        if (Util.USER_EXIST.equals(content)){
            System.out.println("name has exist");
        }
        System.out.println(content.toString());
    }
}

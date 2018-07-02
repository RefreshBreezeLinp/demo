package com.web.demo.nio.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Deamon implements Runnable {

    //选择器用于监听注册在server中的socketChannel的状态
    private Selector selector = null;
    //SocketChannel用于接收和发送客户端数据的通道
    private SocketChannel socketChannel = null;
    //运行标志，在线程里此标志为false的时候推出线程
    private Boolean flag = true;

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Deamon(String address,int port) {
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(address, port));
            socketChannel.configureBlocking(false);
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void channelToWrite(ByteBuffer byteBuffer){
        try {
            socketChannel.write(byteBuffer);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("client run,,,");
        while (this.flag) {
            int num = 0;
            try {
                num = this.selector.select();
            } catch (Exception e) {
                break;
            }
            if (num > 0) {
                Iterator<SelectionKey> keys  = selector.keys().iterator();
                while (keys .hasNext()) {
                    SelectionKey key  = keys .next();
                    System.out.println("keys:"+key);
                    keys .remove();
                    if (key.isReadable()){
                        System.out.println("client isReadable");
                        try {
                            //todo 读操作
                            Dealer.read((SocketChannel) key.channel());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (key.isWritable()) {
                        //todo 写操作
                    }
                }
            }
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //关闭连接
        if (socketChannel!=null&&socketChannel.isOpen()){
            try {
                this.socketChannel.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //关闭连接
        if (selector!=null&&selector.isOpen()) {
            try {
                selector.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

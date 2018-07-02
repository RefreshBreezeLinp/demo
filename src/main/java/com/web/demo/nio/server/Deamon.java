package com.web.demo.nio.server;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Deamon implements Runnable {

    private Boolean flag = true;

    private ServerSocketChannel serverSocketChannel = null;

    private Selector selector = null;
    //记录所有客户端
    private List<SocketChannel> clientChannel = null;

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Deamon(Integer port) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
            this.clientChannel = new ArrayList<>();
            System.out.println("server is listening now ...");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (this.flag){
            int num = 0;
            try {
                num = selector.select();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            if (num>0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()){
                        //监听到新的注册加入到读操作
                        //todo
                        this.clientChannel.add(Dealer.accept(selector,serverSocketChannel));
                    }
                    else if (key.isReadable()) {
                        //监听到读操作
                        try {
                            //// TODO: 2018/6/20
                            Dealer.read(selector,key,clientChannel);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("server to close");
        if (this.serverSocketChannel!=null&&this.serverSocketChannel.isOpen()){
            try {
                this.serverSocketChannel.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        if (this.selector!=null&&this.selector.isOpen()){
            try {
                this.selector.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

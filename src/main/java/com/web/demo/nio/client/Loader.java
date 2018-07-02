package com.web.demo.nio.client;

import com.web.demo.nio.server.Util;

import java.util.Scanner;

public class Loader {

    public static void main(String[] args) {
        String name = "";
        Deamon deamon = new Deamon("127.0.0.1",8999);
        new Thread(deamon).start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if ("".equals(line)) {
                continue; //不允许发空消息
            }
            if ("".equals(name)) {
                name = line ;
                line = name +Util.USER_CONTENT_SPLITE;
            }
            else {
                line = name + Util.USER_CONTENT_SPLITE;
            }
            deamon.channelToWrite(Util.charset.encode(line)); //sc既能读，也能写
        }
    }

}

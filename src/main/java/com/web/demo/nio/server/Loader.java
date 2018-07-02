package com.web.demo.nio.server;

import sun.applet.Main;

public class Loader {

    public static void main(String[] args) {
        Deamon deamon = new Deamon(8999);
        new Thread(deamon).start();
    }

}

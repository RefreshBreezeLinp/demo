package com.web.demo.nio;

import java.nio.charset.Charset;
import java.util.HashSet;

public class Util {

    public static Charset charset = Charset.forName("UTF-8");
    //自定义协议
    public static String USER_CONTENT_SPLITE = "#@#";
    //用于记录用户
    public static HashSet<String> users = new HashSet<String>();

    public static String USER_EXIST = "MESSAGE: user exist, please change a name !";

}
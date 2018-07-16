package com.web.demo.threadpool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadHandle implements Runnable {

    private String index;

    ThreadHandle(String index){
        this.index = index;
    }

    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sdf.format(new Date());
       // System.out.println(Thread.currentThread().getName()+"Start Time:"+date);

        try {
            Thread.sleep(4000);
        } catch (Exception e){
            e.printStackTrace();
        }

        String end = sdf.format(new Date());
        System.out.println(Thread.currentThread().getName()+"End Time:"+end);
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}

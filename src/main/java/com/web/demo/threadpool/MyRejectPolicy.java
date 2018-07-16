package com.web.demo.threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class MyRejectPolicy implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (r instanceof ThreadHandle) {
            ThreadHandle t = (ThreadHandle) r;
            System.out.println("exception:"+t.getIndex());
        }
    }
}

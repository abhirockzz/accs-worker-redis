package com.oracle.cloud.worker.redis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bootstrap {
    public static void main(String[] args) throws Exception {
        ExecutorService workerThreadPool = Executors.newSingleThreadExecutor();
        workerThreadPool.submit(new Worker());
        //new Thread(new Worker()).start();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                workerThreadPool.shutdownNow();
            }
        }));
        System.out.println("Shutdown hook added..");
    }
 
}

    

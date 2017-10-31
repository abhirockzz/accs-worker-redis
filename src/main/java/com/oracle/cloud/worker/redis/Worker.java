package com.oracle.cloud.worker.redis;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import redis.clients.jedis.Jedis;

public class Worker implements Runnable {

    private final String redisHost;
    private final String redisPort;
    private static final String QUEUE_NAME = "workQ";
    private static final String NOTIFICATION_CHANNEL = "workstatus";
    private final Jedis jedis;
    private final String workerInstance;

    public Worker() {
        redisHost = System.getenv().getOrDefault("REDIS_HOST", "192.168.99.100");
        redisPort = System.getenv().getOrDefault("REDIS_PORT", "6379");
        jedis = new Jedis(redisHost, Integer.valueOf(redisPort), 10000);
        workerInstance = System.getenv().getOrDefault("ORA_INSTANCE_NAME", "worker");
        
        System.out.println("Worker instance '"+ workerInstance+"' Connected to Redis");
    }

    @Override
    public void run() {
        String notification = null;
        while (true) {
            System.out.println("Waiting for messages....");
            String msg = jedis.brpop(0, QUEUE_NAME).get(1); //second element consists of the popped value
            System.out.println("processing message " + msg);
            try {
                Thread.sleep(1500); //simualting processing
            } catch (InterruptedException ex) {
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("PROCESSED message " + msg);
            
            notification = "message '"+ msg + "' processed by worker instance '"+ workerInstance + "' on " + new Date();
            jedis.publish(NOTIFICATION_CHANNEL, notification);
            System.out.println("Notification sent to work status channel");
        }

    }
}

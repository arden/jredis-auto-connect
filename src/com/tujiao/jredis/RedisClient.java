/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tujiao.jredis;

import com.tujiao.jredis.pool.RedisPoolableObjectFactory;
import org.jredis.ri.alphazero.connection.DefaultConnectionSpec;
import org.jredis.connector.ConnectionSpec;
import org.jredis.JRedis;
import org.jredis.ri.alphazero.JRedisClient;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.SoftReferenceObjectPool;

/**
 *
 * @author arden
 */
public class RedisClient implements Runnable {
    private static String host = "localhost";
    private static int port = 6379;
    private static JRedis jredis = null;
    private static final int retryConnectTimes = 3;
    private static PoolableObjectFactory factory = new RedisPoolableObjectFactory();
    public static SoftReferenceObjectPool pool = new SoftReferenceObjectPool(factory);

    static {
//        try {
//            // 启动自动重连功能
//            RedisReconnect.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static JRedis getRedis() {
        Object o;
        try {
            o = pool.borrowObject();
            if (o instanceof JRedis) {
                return (JRedis)o;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static JRedis getRedisClient() {
        if (jredis == null) {
            connect();
        }
        return jredis;
    }

    private static JRedis connect() {
        try {
            ConnectionSpec spec = DefaultConnectionSpec.newSpec(host, port, 0, null);
            spec.setReconnectCnt(retryConnectTimes);
            jredis = new JRedisClient(spec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jredis;
    }

    @Override
    public void run() {
        for (;;) {
            try {                
                Thread.sleep(1000);
                jredis.ping();                
            } catch (Exception e) {
                try {
                    jredis.quit();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                jredis = connect();
                e.printStackTrace();
            }
        }
    }
}

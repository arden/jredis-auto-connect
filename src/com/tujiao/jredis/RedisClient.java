/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tujiao.jredis;

import com.jrails.commons.utils.PropertiesUtils;
import com.jrails.modules.spring.ServiceLocator;
import org.jredis.ri.alphazero.connection.DefaultConnectionSpec;
import org.jredis.connector.ConnectionSpec;
import org.jredis.JRedis;
import org.jredis.ri.alphazero.JRedisClient;

/**
 *
 * @author arden
 */
public class RedisClient implements Runnable {
    private static String host = "121.11.69.53";
    private static int port = 6379;
    private static JRedis jredis = null;
    private static final int retryConnectTimes = 3;

    static {
        try {
            // 启动自动重连功能
            RedisReconnect.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

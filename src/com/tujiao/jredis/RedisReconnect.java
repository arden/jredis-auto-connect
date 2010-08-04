/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tujiao.jredis;

/**
 *
 * @author arden
 */
public class RedisReconnect {
    public static void start() {
        RedisClient client = new RedisClient();
        Thread t = new Thread(client);
        t.setDaemon(true);
        t.start();
    }
}

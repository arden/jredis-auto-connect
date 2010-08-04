/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tujiao.jredis;

import java.util.List;
import org.jredis.JRedis;
import static org.jredis.ri.alphazero.support.DefaultCodec.*;

/**
 *
 * @author arden
 */
public class RedisTest {
    
    public static void main(String... args) {
        try {
            
            ClientThread clientThread = new ClientThread();
            new Thread(clientThread).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   static class ClientThread implements Runnable {

        @Override
        public void run() {
            String key = "name";
            //for (;;) {
                try {
                    JRedis jredis = RedisClient.getRedisClient();
                    //jredis.flushall();
                    //jredis.set(key, "arden");
                    //jredis.expire(key, 60);
//                    if (!jredis.exists(key)) {
//                        System.out.println("----------111");
//                        //jredis.expireat(key, 5 * 60);
//                        //jredis.expire(key, 2 * 60 * 1000);
//                    }
//                    jredis.expire(key, 2 * 60);
//                    System.out.println("ttl:" + jredis.ttl(key));
                    //jredis.rpush(key, "tujiao");
                    jredis.rpush(key, "arden");
                    
                    //if (!jredis.exists(key)) {
                        //jredis.expire(key, 60);
                    //}
                    //String value = toStr(jredis.get(key));
                    List<byte[]> values = jredis.lrange(key, 0, -1);
                    for (byte[] value : values) {
                        System.out.format("%s\n", toStr(value));
                        System.out.println("-----------------");
                        //System.out.format("%s\n", toStr(jredis.lpop(key)));
                    }
                    
//                    byte[] value = jredis.rpop(key);
//                    System.out.format("%s\n", toStr(value));
//                    value = jredis.rpop(key);
//                    System.out.format("%s\n", toStr(value));
//                    value = jredis.rpop(key);
//                    System.out.format("%s\n", toStr(value));
//                    value = jredis.rpop(key);
//                    System.out.format("%s\n", toStr(value));
                    Thread.sleep(1 * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            //}
        }
    }
}

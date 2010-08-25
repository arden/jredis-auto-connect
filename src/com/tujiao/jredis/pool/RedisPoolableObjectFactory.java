/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tujiao.jredis.pool;

import com.paojiao.redis.RedisClient;
import org.apache.commons.pool.PoolableObjectFactory;
import org.jredis.JRedis;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 *
 * @author arden
 */
public class RedisPoolableObjectFactory implements PoolableObjectFactory  {
    private static Logger logger = LoggerFactory.getLogger(RedisPoolableObjectFactory.class);

    @Override
    public void activateObject(Object o) throws Exception {
        logger.debug("Activating Object " + o);
    }

    @Override
    public void destroyObject(Object o) throws Exception {
        logger.debug("DestroyObject Object " + o);
    }

    @Override
    public Object makeObject() throws Exception {
        JRedis jredis = RedisClient.connect();
        logger.debug("正在创建JRedis对象:" + jredis);
        return jredis;
    }

    @Override
    public void passivateObject(Object o) throws Exception {
        logger.debug("Passivating Object " + o);
    }

    @Override
    public boolean validateObject(Object o) {
        JRedis jredis = null;
        boolean result = true;
        try {
            if (o instanceof JRedis) {
                jredis = (JRedis)o;
                jredis.ping();
                logger.debug("----:jredis:" + jredis);
                //RedisClient.pool.returnObject(jredis);
            }
        } catch (Exception e) {
            result = false;
            try {
                if (jredis != null) {
                    jredis.quit();
                }
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
            //e.printStackTrace();
        }
        logger.debug("Validating Object " + o + " : " + result);
        return result;
    }
    
}

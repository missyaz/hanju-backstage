package com.teleplay.hanju.spider.webmagic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * @author f
 * @desc
 * @create 2022-02-11 13:04
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis-host}")
    private String host;

    @Value("${spring.redis-port}")
    private int port;

    @Bean
    public JedisPool redisPoolFactory(){
        return new JedisPool(host,port);
    }

}

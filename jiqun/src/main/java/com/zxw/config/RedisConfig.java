package com.zxw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author zxw
 * @date 2019/8/27 20:08
 */
@Configuration
public class RedisConfig {
    @Autowired
    JedisConnectionFactory jedisConnectionFactory;
    @Bean(name="longRedisTemplate")
    public RedisTemplate<String,Long> redisTemplate(){
        RedisTemplate<String,Long> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericToStringSerializer<>(Long.class));
        template.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        return template;
    }
}

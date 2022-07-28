package com.athan;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.athan.serializer.JsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * @aurhor hanyadong
 * @description redis 配置类  设置redis参数的模板
 *
 * @date 2022/7/27 13:48
 */
@Configuration
public class RedisConfig {
    @Resource
    private RedisConnectionFactory factory;  //线程连接工厂

    @Bean
    public RedisTemplate<String,Object> redisTemplate(){
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());//设置key 的值的参数
        redisTemplate.setValueSerializer(new FastJsonRedisSerializer<>(Object.class));//设置值的参数
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JsonRedisSerializer<>(Object.class));
        redisTemplate.setConnectionFactory(factory);//设置连接工厂
        return redisTemplate;

    }

}

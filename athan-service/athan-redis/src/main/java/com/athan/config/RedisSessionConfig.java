package com.athan.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @aurhor yyh
 * @description
 * @date 2022/7/27 14:27
 */
@Data
@Configuration
public class RedisSessionConfig {
    @Value("${spring.redis.host}")
    private String HOST;//地址

    @Value("${spring.redis.port}")
    private String PORT;//端口

    @Value("${spring.redis.password}")
    private String PASSWORD;//密码

    @Bean
    public RedissonClient redisClient() {
        Config config = new Config();//创建配置类
        SingleServerConfig server = config.useSingleServer();//初始化服务器配置
        server.setAddress("redis://" + HOST + ":" + PORT);//设置端口地址
        server.setPassword(PASSWORD);//设置密码
        return Redisson.create(config);//提供配置

    }
}

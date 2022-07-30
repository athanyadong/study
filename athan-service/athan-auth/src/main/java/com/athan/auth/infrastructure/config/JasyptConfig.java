package com.athan.auth.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @aurhor athan
 * @description  加密配置
 * @date 2022/7/29 14:02
 */

@Configuration
@Slf4j
public class JasyptConfig {
    @Value("${jasypt.encryptor.password}")
    private String passwordEncode;

    @Bean //所有接收字符串消息并返回字符串结果的加密器的通用接口。
    public StringEncryptor stringEncryptor() {
        log.info("秘钥：{}",passwordEncode);
        //加密
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        //应用配置
        config.setPassword(passwordEncode);
        encryptor.setConfig(config);
        return encryptor;
    }
}

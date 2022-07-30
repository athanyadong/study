package com.athan.auth.infrastructure.component;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 身份验证处理
 * @aurhor athan
 * @description
 *
 * @date 2022/7/29 10:54
 */
@Component
@ConfigurationProperties(prefix = "auth")
@Slf4j
@Data
@RefreshScope
public class AuthHandler {

    private List<Map<String,String>> uris;  //获取他的地址信息


    @Resource
    private org.springframework.cloud.context.scope.refresh.RefreshScope refreshScope;  //刷新范围

    @ApolloConfigChangeListener
    private void changeHandler(ConfigChangeEvent event) {
        refreshScope.refreshAll();//全部刷新
        log.info("apollo动态拉取配置...");
    }


}

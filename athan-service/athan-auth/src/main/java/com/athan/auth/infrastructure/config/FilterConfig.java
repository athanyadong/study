package com.athan.auth.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * Filter配置
 * @aurhor athan
 * @description
 * @date 2022/7/29 13:54
 */
@Configuration
@Slf4j
public class FilterConfig {

    @Bean  //配置shiro过滤器
    public FilterRegistrationBean shiroFilterRegistration(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContrainer管理
        registrationBean.addInitParameter("targetFilterLifecycle","true");
        registrationBean.setEnabled(true);
        registrationBean.setOrder(Integer.MAX_VALUE - 1);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}

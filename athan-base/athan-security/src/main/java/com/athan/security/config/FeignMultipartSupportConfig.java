package com.athan.security.config;

import com.athan.constant.Constant;
import com.athan.utils.HttpContextUtil;
import feign.Contract;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * @aurhor
 * @description  多部分远程调用信息   配置
 * @date 2022/7/28 18:13
 */
@Configuration
public class FeignMultipartSupportConfig {
    /**
     * fegin日志管理
     * @return
     */
    @Bean
    public feign.Logger.Level multipartLoggerLevel() {
        return feign.Logger.Level.FULL;
    }

    /**
     * 获取请求信息
     * @return
     */
    @Bean
    public RequestInterceptor getRequestInterceptor(){
        return requestTemplate->{
            HttpServletRequest httpServletRequest = HttpContextUtil.getHttpServletRequest();
            requestTemplate.header(Constant.AUTHORIZATION_HEADER,httpServletRequest.getHeader(Constant.AUTHORIZATION_HEADER));
        };
    }

}

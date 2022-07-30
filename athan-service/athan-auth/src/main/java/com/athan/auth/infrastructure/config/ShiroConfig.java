package com.athan.auth.infrastructure.config;

import com.athan.auth.infrastructure.common.oauth2.Oauth2Filter;
import com.athan.auth.infrastructure.common.oauth2.Oauth2Realm;
import com.athan.auth.infrastructure.component.AuthHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.CollectionUtils;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @aurhor athan
 * @description  Shiro的配置文件
 * @date 2022/7/29 14:06
 */
@Configuration
@Slf4j
public class ShiroConfig {

    @Autowired
    private AuthHandler authHandler;

    @Bean("sessionManager") //谁知session会话 设置会话验证计划程序   设置会话 ID 网址重写
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    @Bean("securityManager")  //引用的权限认证的bean，同时引用设置session的bean
    public SecurityManager securityManager(Oauth2Realm oauth2Realm, SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();//创建默认的安全管理器
        securityManager.setRealm(oauth2Realm); //设置领域
        securityManager.setRememberMeManager(null);
        securityManager.setSessionManager(sessionManager);//设置会话管理器
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        //创建一个shiro过期工厂
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        //设置安全过滤
        shiroFilter.setSecurityManager(securityManager);
        //oauth过滤
        Map<String, Filter> filter = new HashMap<>();
        filter.put("oauth2", new Oauth2Filter());
        shiroFilter.setFilters(filter);
        Map<String, String> filterMap = new LinkedHashMap<>();
        List<Map<String, String>> auth = authHandler.getUris();//具体的uris可以在配置文件中查看
        log.info("map:{}",auth);
        if (!CollectionUtils.isEmpty(auth)) {
            auth.forEach(item -> {
                String url = item.get("url");
                String permission = item.get("permission");
                log.info("{} > {}",url,permission);
                filterMap.put(url,permission);
            });
        }
        filterMap.put("/**","oauth2");
        //未授权界面返回JSON
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")  //声明周期bean管理器
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean //引用安全管理器
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 下面的代码是添加注解支持
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
}

package com.athan.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @aurhor yyh
 * @description  上下文工具类
 * @date 2022/7/27 17:56
 */
@Component    //在访问接口前调用                                     在摧毁bean中之前调用
public class SpringContextUtil implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext=applicationContext;
    }
    @Override
    public void destroy() throws Exception {

    }

    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    /**
     * 参数：
     * requiredType – bean 必须匹配的类型；可以是接口或超类
     * @param requiredType
     * @param <T>
     * @return
     */
    public static  <T> T getBean(Class<T> requiredType){
        return applicationContext.getBean(requiredType);
    }

    /**
     * 是否有符合名称的bean
     * @param name
     * @return
     */
    public static boolean containsBean(String name){
        return applicationContext.containsBean(name);
    }

    /**
     * 这个bean是否对应一个单体实例
     * @param name
     * @return
     */
    public static boolean isSingleton(String name){
        return applicationContext.isSingleton(name);
    }

    /**
     *
     * @param name
     * @return   返回bean 的类型，如果不可确定，则返回null
     */
    public static Class<? extends Object> getType(String name){
        return applicationContext.getType(name);
    }

    public static void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }
}

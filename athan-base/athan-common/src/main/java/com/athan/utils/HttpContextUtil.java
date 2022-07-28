package com.athan.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @aurhor yyh
 * @description http 上下文工具类
 * @date 2022/7/28 17:37
 */
public class HttpContextUtil {

    public static HttpServletRequest getHttpServletRequest() {
        //获取当前的请求属性
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        //强转获取请求
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    //获取参数映射
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, String> params = new HashMap<>();
        //测试此枚举是否包含更多元素。
        while (parameterNames.hasMoreElements()) {
            //获取key
            String s = parameterNames.nextElement();
            //获取value
            String parameter = request.getParameter(s);
            if (StringUtils.isNotBlank(parameter)) {
                params.put(s, parameter);
            }
        }
        return params;
    }


    public static String getDomain(){
        HttpServletRequest request = getHttpServletRequest();
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(),url.length()).toString();
    }

    public static String getOrigin(){
        HttpServletRequest request = getHttpServletRequest();
        return request.getHeader(HttpHeaders.ORIGIN);
    }

    public static String getLanguage(){
        //默认语言
        String defaultLanguage = "z-CN";
        //request
        HttpServletRequest request = getHttpServletRequest();
        if (null == request){
            return defaultLanguage;
        }
        //请求语言
        defaultLanguage = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        return defaultLanguage;
    }
}

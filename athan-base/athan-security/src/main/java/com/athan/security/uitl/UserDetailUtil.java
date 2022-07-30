package com.athan.security.uitl;

import com.athan.constant.Constant;
import com.athan.exception.CustomException;
import com.athan.security.feign.auth.AuthApiFeignClient;
import com.athan.user.UserDetail;
import com.athan.utils.HttpContextUtil;
import com.athan.utils.HttpResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @aurhor yyh
 * @description 用户详细信息实用程序
 * @date 2022/7/28 17:10
 */
@Component
public class UserDetailUtil {

    @Autowired
    private AuthApiFeignClient authApiFeignClient;

        //获取用户详细信息
    public UserDetail getUserDetail(HttpServletRequest request) {
        //获取token
        String token = getAuthorization(request);
        String language = HttpContextUtil.getLanguage();
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        HttpResultUtil<UserDetail> result = authApiFeignClient.resource(language, token, requestURI, method);
        if (!result.success()) {
            throw new CustomException(result.getCode(),result.getMsg());
        }
        return result.getData();
    }

    /**
     * 获取请求的token
     */
    private String getAuthorization(HttpServletRequest request) {
        //从header中取出token
        String token = request.getHeader(Constant.AUTHORIZATION_HEADER);
        //如果header中不存在Authorization，则从参数中获取Authorization
        if (StringUtils.isBlank(token)){
            token = request.getParameter(Constant.AUTHORIZATION_HEADER);
        }
        return token;

    }

}

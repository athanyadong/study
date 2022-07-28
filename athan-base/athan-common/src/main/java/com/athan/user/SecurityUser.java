package com.athan.user;


import com.athan.constant.Constant;
import com.athan.exception.CustomException;
import com.athan.exception.ErrorCode;
import com.athan.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
public class SecurityUser {

    public static Long getUserId(HttpServletRequest request) {
        String userIdHeader = request.getHeader(Constant.USER_KEY_HEAD);
        if (StringUtils.isBlank(userIdHeader)) {
            String authHeader = getAuthorization(request);
            if (StringUtils.isBlank(authHeader)) {
                throw new CustomException(ErrorCode.UNAUTHORIZED);
            }
            if (TokenUtil.isExpiration(authHeader)) {
                throw new CustomException(ErrorCode.AUTHORIZATION_INVALID);
            }
            return TokenUtil.getUserId(authHeader);
        }
        return Long.valueOf(userIdHeader);
    }

    public static String getUsername(HttpServletRequest request) {
        String userNameHeader = request.getHeader(Constant.USERNAME_HEAD);
        if (StringUtils.isBlank(userNameHeader)) {
            String authHeader = getAuthorization(request);
            if (StringUtils.isBlank(authHeader)) {
                throw new CustomException(ErrorCode.UNAUTHORIZED);
            }
            if (TokenUtil.isExpiration(authHeader)) {
                throw new CustomException(ErrorCode.AUTHORIZATION_INVALID);
            }
            return TokenUtil.getUsername(authHeader);
        }
        return userNameHeader;
    }

    /**
     * 获取请求的token
     */
    private static String getAuthorization(HttpServletRequest request){
        //从header中获取token
        String Authorization = request.getHeader(Constant.AUTHORIZATION_HEADER);
        //如果header中不存在Authorization，则从参数中获取Authorization
        if(org.apache.commons.lang3.StringUtils.isBlank(Authorization)){
            Authorization = request.getParameter(Constant.AUTHORIZATION_HEADER);
        }
        return Authorization;
    }

}

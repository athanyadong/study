package com.athan.auth.infrastructure.common.oauth2;

import com.alibaba.fastjson.JSON;
import com.athan.auth.application.service.SysAuthApplicationService;
import com.athan.exception.ErrorCode;
import com.athan.user.UserDetail;
import com.athan.utils.MessageUtil;
import com.athan.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @aurhor athan
 * @description  认证授权
 * @date 2022/7/29 11:30
 */
@Slf4j
@Component
public class Oauth2Realm  extends AuthorizingRealm {


    @Autowired
    SysAuthApplicationService sysAuthApplicationService;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取用户信息
        UserDetail primaryPrincipal = (UserDetail) principals.getPrimaryPrincipal();
        //获取用户权限信息
        Set<String> permsSet =new HashSet<>(primaryPrincipal.getPermissionsList());
        log.info("获取权限标识:{}", JSON.toJSONString(permsSet));
        //创建权限容器
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //将去权限设置进去
        info.setStringPermissions(permsSet);
        return info;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Oauth2Token;
    }


    /**
     * addStringPermissions
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
       String accessToken= (String)token.getPrincipal();
        //查看token是否失效   失效报出异常
        if (TokenUtil.isExpiration(accessToken)) {
            throw new IncorrectCredentialsException(MessageUtil.getMessage(ErrorCode.AUTHORIZATION_INVALID));
        }
        //查看用户信息
        Long userId = TokenUtil.getUserId(accessToken);
        UserDetail userDetail = sysAuthApplicationService.getUserDetail(userId);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDetail, accessToken, getName());
        return info;
    }
}

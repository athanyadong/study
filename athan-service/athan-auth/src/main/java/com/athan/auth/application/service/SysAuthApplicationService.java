package com.athan.auth.application.service;

import com.athan.auth.interfaces.dto.LoginDTO;
import com.athan.auth.interfaces.vo.LoginVO;
import com.athan.auth.interfaces.vo.UserInfoVO;
import com.athan.user.UserDetail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @aurhor athan
 * @description * auth服务
 * @date 2022/7/29 11:47
 */
public interface SysAuthApplicationService {
    /**
     * 登录
     * @param loginDTO
     * @return
     * @throws Exception
     */
    LoginVO login(LoginDTO loginDTO) throws Exception;

    /**
     * 退出
     * @param userId
     * @return
     */
    Boolean logout(Long userId);

    /**
     * 生成验证码
     * @param uuid
     * @param response
     * @throws IOException
     */
    void captcha(String uuid, HttpServletResponse response) throws IOException;

    /**
     * 访问资源权限
     * @param Authorization
     * @param uri
     * @param method
     * @return
     */
    UserDetail resource(String Authorization, String uri, String method);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    UserInfoVO userInfo(Long userId);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    UserDetail getUserDetail(Long userId);

    /**
     * 支付宝登录
     * @param request
     * @param response
     * @throws IOException
     */
    void zfbLogin(HttpServletRequest request, HttpServletResponse response) throws IOException;

}

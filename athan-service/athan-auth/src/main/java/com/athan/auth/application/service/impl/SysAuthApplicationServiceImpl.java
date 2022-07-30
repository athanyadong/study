package com.athan.auth.application.service.impl;

import com.athan.auth.application.service.SysAuthApplicationService;
import com.athan.auth.interfaces.dto.LoginDTO;
import com.athan.auth.interfaces.vo.LoginVO;
import com.athan.auth.interfaces.vo.UserInfoVO;
import com.athan.user.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @aurhor athan
 * @description
 * @date 2022/7/29 11:48
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
public class SysAuthApplicationServiceImpl implements SysAuthApplicationService {
    @Override
    public LoginVO login(LoginDTO loginDTO) throws Exception {
        return null;
    }

    @Override
    public Boolean logout(Long userId) {
        return null;
    }

    @Override
    public void captcha(String uuid, HttpServletResponse response) throws IOException {

    }

    @Override
    public UserDetail resource(String Authorization, String uri, String method) {
        return null;
    }

    @Override
    public UserInfoVO userInfo(Long userId) {
        return null;
    }

    @Override
    public UserDetail getUserDetail(Long userId) {
        return null;
    }

    @Override
    public void zfbLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}

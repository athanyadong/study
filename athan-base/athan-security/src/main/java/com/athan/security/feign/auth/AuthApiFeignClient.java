package com.athan.security.feign.auth;

import com.athan.constant.Constant;
import com.athan.constant.ServiceConstant;
import com.athan.security.feign.auth.fallback.AuthApiFeignClientFallBack;
import com.athan.user.UserDetail;
import com.athan.utils.HttpResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(name = ServiceConstant.ATHAN_AUTH,fallbackFactory = AuthApiFeignClientFallBack.class)
public interface AuthApiFeignClient {
    /**
     * 根据token获取用户信息
     * @param Authorization
     * @param uri
     * @param method
     * @param language
     * @return
     */
    @GetMapping("/sys/auth/api/resource")
    HttpResultUtil<UserDetail> resource(
            @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language,
            @RequestParam(Constant.AUTHORIZATION_HEADER) String Authorization,
            @RequestParam(Constant.URI)String uri,
            @RequestParam(Constant.METHOD)String method);
}

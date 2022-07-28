package com.athan.feign.auth.fallback;

import com.athan.feign.auth.AuthApiFeignClient;
import com.athan.feign.auth.factory.AuthApiFeignClientFallbackFactory;
import com.athan.user.UserDetail;
import com.athan.utils.HttpResultUtil;
import feign.hystrix.FallbackFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @aurhor yyh
 * @description  服务降级
 * @date 2022/7/28 17:18
 */
@Slf4j
@AllArgsConstructor
public class AuthApiFeignClientFallBack implements AuthApiFeignClient {

    private final Throwable throwable;

    @Override
    public HttpResultUtil<UserDetail> resource(String language, String Authorization, String uri, String method) {
        log.error("服务调用失败，报错原因：{}",throwable.getMessage());
        return new HttpResultUtil<UserDetail>().error("服务调用失败，请联系管理员");
    }
}

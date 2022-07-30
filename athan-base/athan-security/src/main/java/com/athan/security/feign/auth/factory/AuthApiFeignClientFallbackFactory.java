package com.athan.security.feign.auth.factory;


import com.athan.security.feign.auth.fallback.AuthApiFeignClientFallBack;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @aurhor yyh
 * @description  回调工厂
 * @date 2022/7/28 17:21
 */
@Component
public class AuthApiFeignClientFallbackFactory   implements FallbackFactory<AuthApiFeignClientFallBack> {


    @Override
    public AuthApiFeignClientFallBack create(Throwable throwable) {
        return new AuthApiFeignClientFallBack(throwable);
    }
}

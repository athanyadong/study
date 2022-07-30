package com.athan.security.annotation;

import java.lang.annotation.*;

/**
 * @aurhor athan
 * @description   授权注解
 * @date 2022/7/29 9:13
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PreAuthorize {

        String value() ;
}

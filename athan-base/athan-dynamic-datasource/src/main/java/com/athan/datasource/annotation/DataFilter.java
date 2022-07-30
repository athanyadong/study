package com.athan.datasource.annotation;

import java.lang.annotation.*;

/**
 * 数据过滤
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFilter {
    /**
     * 表别名
     * @return
     */
    String tableAlias();

    /**
     * 用户ID
     */
    String userId() default "creator";

    /**
     * 部门ID
     */
    String deptId() default "dept_id";
}

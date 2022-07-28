package com.athan.dto;

import lombok.Data;

/**
 * @aurhor yyh
 * @description 操作日志
 * @date 2022/7/27 16:06
 */
@Data
public class OperateLogDTO {


    /**
     * 模块名称，如：系统菜单
     */
    private String module;

    /**
     * 操作名称
     */
    private String operation;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 浏览器版本
     */
    private String userAgent;

    /**
     * IP地址
     */
    private String requestIp;

    /**
     * 归属地
     */
    private String requestAddress;

    /**
     * 状态  0：成功   1：失败
     */
    private Integer requestStatus;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 方法名称
     */
    private String methodName;

}

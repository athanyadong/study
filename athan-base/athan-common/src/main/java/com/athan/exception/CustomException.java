package com.athan.exception;

import com.athan.utils.MessageUtil;
import lombok.Data;

/**
 * @aurhor yyh
 * @description 自定义异常类
 * @date 2022/7/28 10:36
 */
@Data
public class CustomException extends RuntimeException {

    private int code; //错误码
    private String msg;//错误信息

    public CustomException(int code) {
        this.code = code;
        this.msg = MessageUtil.getMessage(code);
    }
    public CustomException(int code,String...params){
        this.code=code;
        this.msg=MessageUtil.getMessage(code,params);
    }

    public CustomException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CustomException(int code, Throwable e) {
        super(e);
        this.code = code;
        this.msg = MessageUtil.getMessage(code);
    }

    public CustomException(int code, Throwable e, String... params) {
        super(e);
        this.code = code;
        this.msg = MessageUtil.getMessage(code, params);
    }

    public CustomException(String msg) {
        super(msg);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
    }

    public CustomException(String msg, Throwable e) {
        super(msg, e);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
    }
}

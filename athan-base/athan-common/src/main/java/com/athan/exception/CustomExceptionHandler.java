package com.athan.exception;

import com.athan.utils.HttpResultUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @aurhor yyh
 * @description  custom异常处理器
 * @date 2022/7/28 11:36
 */
@RestControllerAdvice  //对请求响应处理程序的建议
@ResponseBody
@Component
public class CustomExceptionHandler {

    //处理自定义异常
    @ExceptionHandler({CustomException.class})
    public HttpResultUtil<Boolean>handleRenException(CustomException e){
        return new HttpResultUtil<Boolean>().error(e.getCode(),e.getMsg());
    }
}

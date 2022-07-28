package com.athan.utils;

import org.apache.logging.log4j.message.Message;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @aurhor yyh
 * @description 消息工具类  ---> 国际化
 * @date 2022/7/28 10:00
 */
public class MessageUtil {

    private static MessageSource messageSource; //用于解析消息的策略接口，支持此类消息的参数化和国际化

    static {
        messageSource = (MessageSource) SpringContextUtil.getBean("messageSource");
    }


    public static String getMessage(int code) {
        return getMessage(code, new String[0]);
    }

    /**
     *
              locale – 进行查找的语言环境
     * @param code 要查找的消息代码，例如“calculator.noRateSet”。鼓励 MessageSource 用户将消息名称基于合格的类或包名称，以避免潜在的冲突并确保最大的清晰度。
     * @param params 为消息中的参数填充的参数数组（参数在消息中类似于“{0}”、“{1,date}”、“{2,time}”），如果没有则为null
     * @return
     */
    public static String getMessage(int code, String... params) {
        return messageSource.getMessage(code + "", params, LocaleContextHolder.getLocale());
    }


}

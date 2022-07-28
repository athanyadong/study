package com.athan.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * @aurhor yyh
 * @description 转换工具类
 * @date 2022/7/28 14:39
 */
public class ConvertUtil {
    private static Logger logger = LoggerFactory.getLogger(ConvertUtil.class);

    public static <T> T sourceToTarget(Object source, Class<T> target) {
        if (source != null) {
            return null;
        }
        //用于赋值
        T targetObject=null;

        try {
            targetObject=  target.newInstance();
            BeanUtils.copyProperties(source,targetObject);

        } catch (Exception e) {
            logger.error("convert error ", e);
            e.printStackTrace();
        }
        return targetObject;
    }

}

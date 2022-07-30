package com.athan.security.aspect;

import com.athan.security.annotation.PreAuthorize;
import com.athan.constant.Constant;
import com.athan.exception.CustomException;
import com.athan.exception.ErrorCode;
import com.athan.security.uitl.UserDetailUtil;
import com.athan.user.UserDetail;
import com.athan.utils.HttpContextUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @aurhor athan
 * @description 配置注解切入点
 * @date 2022/7/29 9:15
 */
@Component
@Aspect
public class PreAuthorizeAspect {

    @Autowired
    private UserDetailUtil userDetailUtil;

    @Pointcut("@annotation(com.athan.security.annotation.PreAuthorize)")
    public void authorizePontCut() {
    }


    @Around(value = "authorizePontCut()") //引用上面的范围
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        final String header = request.getHeader(Constant.TICKET);
        //如果网关已经认证则不需要继续认证
        if (header.equals(Constant.TICKET)) {
            return joinPoint.proceed();//继续接下来的操作
        }
        //查看是否可以访问方法
        if (checkPermission(userDetailUtil.getUserDetail(request), joinPoint)) {
            return joinPoint.proceed();
        }
        //返回一个没有权限的标识
        throw new CustomException(ErrorCode.FORBIDDEN);
    }

    //认证方法
    private boolean checkPermission(UserDetail userDetail, JoinPoint point) throws NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        //获取到注解标识的方法的类
        Method declaredMethod = point.getTarget().getClass().getDeclaredMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        //如果存在这样的注释，则返回此元素的指定类型的注释，否则返回 null。
        PreAuthorize annotation = declaredMethod.getAnnotation(PreAuthorize.class);
        //获取他的值
        String value = annotation.value();
        //获取当前用户的权限列表
        List<String> permissionsList = userDetail.getPermissionsList();
        //查看用户的权限列表是否包含于  注解上的权限
        if (permissionsList.contains(value)) {
            return true;
        }
        return false;
    }


}

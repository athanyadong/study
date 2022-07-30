package com.athan.datasource.aspect;

import com.athan.datasource.annotation.DataFilter;
import com.athan.entity.BasePage;
import com.athan.enums.SuperAdminEnum;
import com.athan.exception.CustomException;
import com.athan.security.uitl.UserDetailUtil;
import com.athan.user.UserDetail;
import com.athan.utils.HttpContextUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @aurhor athan
 * @description  定义数据过滤切入点
 * @date 2022/7/29 10:28
 */
public class DataFilterAspect {
    @Autowired
    private UserDetailUtil userDetailUtil;

    @Pointcut("@annotation(com.athan.datasource.annotation.DataFilter)")
    public void dataFilterPointCut() {}

    @Before("dataFilterPointCut()")
    public void dataFilterPoint(JoinPoint point) {
        Object params = point.getArgs()[0];
        if (params != null && params instanceof BasePage) {
            HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
            UserDetail userDetail = userDetailUtil.getUserDetail(request);
            //如果是超级管理员，不进行数据过滤
            if (userDetail.getSuperAdmin() == SuperAdminEnum.YES.ordinal()) {
                return;
            }
            try {
                //否则进行数据过滤
                BasePage page = (BasePage)params;
                String sqlFilter = getSqlFilter(userDetail, point);
                page.setSqlFilter(sqlFilter);
            }catch (Exception e){}
            return;
        }
        throw new CustomException("服务正在维护，请联系管理员");
    }

    /**
     * 获取数据过滤的SQL
     */
    private String getSqlFilter(UserDetail userDetail, JoinPoint point) throws Exception {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        DataFilter dataFilter = method.getAnnotation(DataFilter.class);
        //获取表的别名
        String tableAlias = dataFilter.tableAlias();
        if(StringUtils.isNotBlank(tableAlias)){
            tableAlias +=  ".";
        }
        StringBuilder sqlFilter = new StringBuilder();
        //用户列表
        if (CollectionUtils.isNotEmpty(userDetail.getDepts())) {
            List<Long> deptIds = userDetail.getDepts().stream().map(item -> item.getId()).collect(Collectors.toList());
            sqlFilter.append(" find_in_set(").append(tableAlias).append(dataFilter.deptId()).append(" , ").append("'").append(StringUtils.join(deptIds,",")).append("'").append(") or ");
        }
        sqlFilter.append(tableAlias).append(dataFilter.userId()).append(" = ").append("'").append(userDetail.getId()).append("' ");
        return sqlFilter.toString();
    }
}

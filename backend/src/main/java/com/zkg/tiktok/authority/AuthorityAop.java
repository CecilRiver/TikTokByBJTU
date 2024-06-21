package com.zkg.tiktok.authority;

import com.zkg.tiktok.exception.AuthorityException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 权限校验
 * @Version: 1.0
 */


@Aspect
@Component
public class AuthorityAop {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ApplicationContext applicationContext; // 注入ApplicationContext来获取bean

    /**
     * 自定义校验aop
     * @param joinPoint
     * @param authority
     * @return
     * @throws Throwable
     */
    @Around("@annotation(authority)")
    public Object authority(ProceedingJoinPoint joinPoint, Authority authority) throws Throwable {
        if (!AuthorityUtils.getPostAuthority()) {
            return joinPoint.proceed(); // 如果全局权限校验未启用，直接继续执行
        }

        // 全局校验类
        Object verifyObject = applicationContext.getBean(AuthorityUtils.getGlobalVerify());
        Method method = verifyObject.getClass().getMethod("authorityVerify", HttpServletRequest.class, String[].class);
        boolean result = (Boolean) method.invoke(verifyObject, request, authority.value());

        if (!result) {
            String requiredPermissions = String.join(", ", authority.value());
            throw new AuthorityException("权限不足。需要以下权限: " + requiredPermissions);
        }

        return joinPoint.proceed();
    }
}

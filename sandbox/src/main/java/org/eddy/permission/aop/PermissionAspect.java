package org.eddy.permission.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.eddy.permission.InvokePermission;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class PermissionAspect {

    @Pointcut("@annotation(org.eddy.permission.annotation.Permission)")
    public void permission(){}

    @Before("permission()")
    public void permissionCheck(JoinPoint joinPoint) {
        Optional.ofNullable(System.getSecurityManager()).ifPresent(securityManager -> securityManager.checkPermission(new InvokePermission()));
    }
}

package org.eddy.permission.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.eddy.permission.InvokePermission;
import org.eddy.permission.annotation.Permission;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class PermissionAspect {

    @Pointcut("(execution(* org.eddy..*(..)) && (@annotation(org.eddy.permission.annotation.Permission)))")
    public void permission(){}

    @Before("permission()")
    public void permissionCheck(JoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Permission permission = methodSignature.getMethod().getAnnotation(Permission.class);
        Optional.ofNullable(System.getSecurityManager()).ifPresent(securityManager -> securityManager.checkPermission(new InvokePermission(permission.value())));
    }
}

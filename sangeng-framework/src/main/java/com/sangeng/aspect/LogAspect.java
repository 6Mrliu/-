package com.sangeng.aspect;

import com.alibaba.fastjson.JSON;
import com.sangeng.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.sangeng.annotation.SystemLog)")
    public void pt() {
    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

        Object ret;// 执行方法
        try {
            log.info("==============Start============");
            handleBefore(joinPoint);// 前置
            ret = joinPoint.proceed();// 执行方法
            handleAfter(ret);// 后置
        } finally {
            //结束后换行
            log.info("==============End===============" + System.lineSeparator());
        }

        return ret;
    }

    private void handleAfter(Object ret) {
        // 获取返回值
        log.info("Response        : {}", JSON.toJSONString(ret));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        // 获取请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);
        // 打印请求 URL
        log.info("URL : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName        : {}",systemLog.businessName() );
        // 打印 Http method
        log.info("HTTP Method        : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method        : {}.{}", joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP        : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args        : {}", JSON.toJSONString(joinPoint.getArgs()));

    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SystemLog annotation = methodSignature.getMethod().getAnnotation(SystemLog.class);
        return annotation;
    }
}

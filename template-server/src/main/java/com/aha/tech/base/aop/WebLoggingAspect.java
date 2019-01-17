package com.aha.tech.base.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @Author: luweihong
 * @Date: 2018/8/8
 */
@Aspect
@Component
public class WebLoggingAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(WebLoggingAspect.class);

    @Resource
    private ThreadPoolTaskExecutor nonCoreThreadPool;

    /**
     * 第一个* 表示 任何返回值
     * com.aha.tech.controller.* 表示包名
     * 第二个* 代表类名,*表示所有类名
     * .*(..) 表示任何方法名,括号里的是参数,二个点表示任何参数
     */
    @Pointcut("execution(* com.aha.tech.controller.*.*(..))")
    public void controllerMethodPointcut() {
    }

    @Before("controllerMethodPointcut()")
    public void deBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        printRequestInfo(request.getRemoteAddr(), request.getRequestURL().toString(), Arrays.toString(joinPoint.getArgs()), LocalDateTime.now());
    }

    /**
     * 输出request info
     * @param ip
     * @param url
     * @param params
     * @param now
     */
    public void printRequestInfo(String ip, String url, String params, LocalDateTime now) {
        nonCoreThreadPool.submit(() -> LOGGER.info("Received from ip : {} request url : {} with params : {} at {}", ip, url, params, now));
    }

    /**
     * 环绕增强
     * 仅仅计算时间
     * @param pjp
     * @return
     */
//    @Around("controllerMethodPointcut()")
//    public void around(ProceedingJoinPoint pjp) throws Throwable {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        pjp.proceed(pjp.getArgs());
//        stopWatch.stop();
//        LOGGER.info("cost {} ms", stopWatch.getTotalTimeMillis());
//    }

}

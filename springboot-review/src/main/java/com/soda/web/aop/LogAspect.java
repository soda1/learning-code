package com.soda.web.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author soda
 * @date 2021/5/11
 */
@Aspect
@Component
public class LogAspect {

    /**
     * 声明一个切面
     * execution(* com.soda.web.controller.*.*(..)) 配置要使用切面的方法
     */
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* com.soda.web.controller.*.*(..))")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void begin() {
        logger.info("method begin");
    }

    @After("pointCut()")
    public void after() {
        logger.info("method after");
    }

    @AfterThrowing("pointCut()")
    public void afterThrow() {
        logger.info("method failure");
    }


    /**
     * 环绕通知
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("around...");
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        logger.info(request.getRequestURL().toString());
        logger.info(request.getRequestURI());
        Object[] args = joinPoint.getArgs();
        //获取参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((key,val) ->{
            System.out.println(key + ":" + val[0]);
        });

        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        //方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        System.out.println(joinPoint.getSignature().getName());
        System.out.println(joinPoint.getSignature().getDeclaringTypeName());
        System.out.println(joinPoint.getSignature().getDeclaringType());
        return joinPoint.proceed();







    }

}

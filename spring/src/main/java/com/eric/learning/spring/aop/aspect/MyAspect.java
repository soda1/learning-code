package com.eric.learning.spring.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author eric
 * @date 4/7/2023
 * <p>reference：<href>https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/aop.html#aop-introduction</href>
 * <p>定义Aspect类</p>
 */
@Component
@Aspect
public class MyAspect {
    /**
     * 通过注解表示通知类型，execution表达式定义切入点，表示要执行此通知的方法
     * @param jp
     */
    @Before("execution(* com.eric.learning.spring.aop.service.*.*(..))")
    public void before(JoinPoint jp){
        System.out.println("before the method");
    }

    @After("execution(* com.eric.learning.spring.aop.service.*.*(..))")
    public void After(JoinPoint jp) {
        System.out.println("after the method invoke");
    }

    @AfterReturning(pointcut = "execution(* com.eric.learning.spring.aop.service.*.*(..))", returning = "ret")
    public void afterReturning(JoinPoint jp, Object ret) {
        System.out.println("after the returning: " + ret);
    }

    @AfterThrowing(pointcut = "execution(* com.eric.learning.spring.aop.service.*.*(..))", throwing = "ex")
    public void afterThrowing(JoinPoint jp, Exception ex){
        System.out.println("catch method invoke exception: " + ex.getMessage());
    }

}

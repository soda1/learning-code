package com.soda.learn.config;

import com.soda.learn.javabean.Account;
import com.soda.learn.mapper.AccountMapper;
import com.soda.learn.util.TransactionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author eric
 * @date 6/4/2023
 */
@Component
@Aspect
@Order(100)
public class AccountInterceptor {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    AfterProcess afterProcess;

    @Pointcut("execution(public * com.soda.learn.mapper.AccountMapper.updateAccount(..))")
    public void method() {

    }

    @Before("method()")
    public void process(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Account account = (Account) args[0];
        Account byName = accountMapper.findByName(account.getName());
        System.out.println("before:" + byName.getMoney());
        System.out.println(Thread.currentThread().getName());
        //throw new RuntimeException();

    }

    @AfterReturning("method()")
    public void afterReturn(JoinPoint joinPoint) throws InterruptedException {
        Object[] args = joinPoint.getArgs();
        Account account = (Account) args[0];
        TransactionUtils.afterCommitSyncExecute(() ->{
            try {
                afterProcess.queryAccount(account.getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }



}

@Component
class AfterProcess {

    @Autowired
    AccountMapper accountMapper;
    @Async
    public void queryAccount(String name) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        Account byName = accountMapper.findByName(name);
        System.out.println("updated: " + byName.getMoney());
        System.out.println(Thread.currentThread().getName());
    }
}

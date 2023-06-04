package com.soda.learn.service;

import com.soda.learn.javabean.Account;
import com.soda.learn.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class AccountService {

    @Autowired
    AccountMapper accountMapper;

    @Cacheable(cacheNames = "emp")
    public Account findById(Integer id) {
        System.out.println("查询" + id + "号账户");
        return accountMapper.findById(id);
    }

    public Account insertAccount(Account account) {
        accountMapper.insertAccount(account);
        return account;
    }

    @Transactional(rollbackFor = Exception.class)
    public void testAopOrder(String name) throws Exception {
        Account account = new Account();
        account.setName(name);
        account.setMoney((float) (Math.random() * 10000));
        accountMapper.updateAccount(account);
        System.out.println("finish");
        TimeUnit.SECONDS.sleep(10);
        //throw new Exception("21321");
    }
}

package com.soda.service;

import com.soda.javabean.Account;
import com.soda.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
}

package com.soda.learn.service;

import com.soda.learn.javabean.Account;
import com.soda.learn.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountMapper accountMapper;

    @Cacheable(cacheNames = "emp", key = "#id")
    public Account findById(Integer id) {
        System.out.println("查询" + id + "号账户");
        return accountMapper.findById(id);
    }

    public Account insertAccount(Account account) {
        accountMapper.insertAccount(account);
        return account;
    }

    //Cacheable 不能使用result.id ，因为是先查询再到目标方法的
    @CachePut(value = "emp", key = "#result.id")
    public Account updateAccount(Float money, Integer id) {
        accountMapper.updateAccount(money, id);
        return accountMapper.findById(id);

    }

    @Caching(
            cacheable = {@Cacheable(value = "emp", key = "#name")},
            put = {@CachePut(cacheNames = "emp", key = "#result.id")}
    )
    public Account findByName(String name) {
        System.out.println("查询名字为" + name + "的账户");
        return accountMapper.findByName(name);
    }

    public List<Account> findAll() {
        return accountMapper.findAll();
    }
}

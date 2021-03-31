package com.soda.controller;

import com.soda.javabean.Account;
import com.soda.mapper.AccountMapper;
import com.soda.service.AccountService;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

   @Autowired
    AccountService accountService;

    @RequestMapping("{id}")
    public Account findById(@PathVariable("id")Integer id) {

        return accountService.findById(id);
    }

    @PostMapping
    public Account insertAccount(Account account) {

        return accountService.insertAccount(account);
    }



}

package com.soda.learn.controller;

import com.soda.learn.javabean.Account;
import com.soda.learn.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/testAopOrder")
    public Account aopOrder(String name) throws Exception {
        accountService.testAopOrder(name);
        return null;
    }



}

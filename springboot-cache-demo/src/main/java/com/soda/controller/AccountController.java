package com.soda.controller;

import com.soda.javabean.Account;
import com.soda.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping
    public Account updateAccount(Float money, Integer id) {
        return accountService.updateAccount(money, id);
    }

    @GetMapping()
    public List<Account> findAll() {
        return accountService.findAll();
    }
    @GetMapping("/name/{name}")
    public Account findByName(@PathVariable("name") String name) {
        return accountService.findByName(name);
    }



}

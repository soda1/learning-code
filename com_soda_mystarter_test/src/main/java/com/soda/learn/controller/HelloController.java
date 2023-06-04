package com.soda.learn.controller;

import com.soda.learn.autoconfigure.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping("/hello/game/{do}")
    public String doSomething(@PathVariable("do")String todo ) {
        return helloService.playGame(todo);
    }
}

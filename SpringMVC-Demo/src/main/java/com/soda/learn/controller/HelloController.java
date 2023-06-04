package com.soda.learn.controller;


import com.soda.learn.autoconfigure.SodaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    SodaProperties sodaProperties;


    @RequestMapping("/hello")
    public String hello() {
        System.out.println(sodaProperties.getName() + sodaProperties.getAge());
        return "hello world";
    }
}

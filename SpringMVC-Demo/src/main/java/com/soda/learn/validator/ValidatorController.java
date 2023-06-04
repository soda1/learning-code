package com.soda.learn.validator;


import com.soda.learn.autoconfigure.SodaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController()
@RequestMapping("/valid")

public class ValidatorController {

    @Autowired
    SodaProperties sodaProperties;


    @GetMapping("/test")
    public String hello(@Validated Person person) {
        System.out.println(sodaProperties.getName() + sodaProperties.getAge());
        return "hello world";
    }
    @GetMapping("/test2")
    public String hello2(@NotNull(message="name cant empty") String name) {
        System.out.println(sodaProperties.getName() + sodaProperties.getAge());
        return "hello world";
    }
}

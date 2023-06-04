package com.eric.learning.spring.aop.service;

import org.springframework.stereotype.Component;

/**
 * @author eric
 * @date 4/7/2023
 */

@Component
public class MyService {

    public String testAspect(String arg) {
        System.out.println("method run: " + arg);
        return arg;
    }

}

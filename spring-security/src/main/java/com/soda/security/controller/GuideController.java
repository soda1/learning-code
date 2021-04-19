package com.soda.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author soda
 * @date 2021/4/15
 */

@RestController
@RequestMapping("/guide")
public class GuideController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello guide";
    }
}

package com.soda.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author soda
 * @date 2021/4/18
 */
@Controller
public class LoginController {

    @RequestMapping("/login.html")
    public String login() {
        return "login.html";
    }
}

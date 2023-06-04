package com.soda.learn;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

@SpringBootApplication
public class SpringDemo01 {

    public static void main(String[] args) {
        SpringApplication.run(SpringDemo01.class);
    }

    @Bean
    public ViewResolver myResolver() {
        return new MyResolver();
    }

    public static class MyResolver implements ViewResolver {

        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
            return null;
        }
    }
}

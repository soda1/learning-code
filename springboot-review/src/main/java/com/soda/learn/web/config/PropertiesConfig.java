package com.soda.learn.web.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(Properties.class)
public class PropertiesConfig {

    public Properties properties;

    public void getProperties() {
        System.out.println(properties.age);;
        System.out.println(properties.name);;
    }
}

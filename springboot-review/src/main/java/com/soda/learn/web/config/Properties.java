package com.soda.learn.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "soda")
public class Properties {

    private final String DefaultName = "soda";

    private final int DefaultAge = 18;


    public String name = DefaultName;
    public int age = DefaultAge;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

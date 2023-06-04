package com.soda.learn.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 学习SpringBoot的属性注入
 */
@ConfigurationProperties(prefix = "soda")
public class SodaProperties {

    String name;

    Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

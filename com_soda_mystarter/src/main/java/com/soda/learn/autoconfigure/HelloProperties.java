package com.soda.learn.autoconfigure;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "soda")
public class HelloProperties {
    public final static String DEFAULT_NAME = "Soda";
    public final static String WORD_TO_SAY = "Hello";

    private String name = DEFAULT_NAME;
    private String say = WORD_TO_SAY;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSay() {
        return say;
    }

    public void setSay(String say) {
        this.say = say;
    }
}

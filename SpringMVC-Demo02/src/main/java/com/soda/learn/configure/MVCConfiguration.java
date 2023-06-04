package com.soda.learn.configure;

import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;

@Configuration
public class MVCConfiguration {

    @Bean
    public ViewResolver getResolver() {
        return new MyResolver();
    }

    //定制化Tomcat
    @Bean
    public WebServerFactoryCustomizer myWebServerFactoryCustomizer() {
        return (WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory>) factory -> factory.setPort(8083);
    }

}


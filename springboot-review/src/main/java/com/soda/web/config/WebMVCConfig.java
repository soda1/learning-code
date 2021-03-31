package com.soda.web.config;

import com.soda.web.Interceptor.Interceptor1;
import com.soda.web.Interceptor.Interceptor2;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
      //  registry.addInterceptor(new Interceptor1()).order(1).addPathPatterns("/**");
        //registry.addInterceptor(new Interceptor2()).order(2).addPathPatterns("/**");
/*
        registry.addInterceptor(new Interceptor1()).addPathPatterns("/**");
        registry.addInterceptor(new Interceptor2()).addPathPatterns("/**");
*/

    }
}

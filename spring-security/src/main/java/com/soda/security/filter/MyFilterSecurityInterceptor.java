package com.soda.security.filter;

import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author soda
 * @date 2021/4/15
 */
public class MyFilterSecurityInterceptor extends FilterSecurityInterceptor {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("自定义MyFilterSecurityInterceptor过滤器正在执行");
        chain.doFilter(request, response);
    }
}

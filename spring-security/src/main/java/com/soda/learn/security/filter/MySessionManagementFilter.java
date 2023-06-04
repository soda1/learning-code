package com.soda.learn.security.filter;

import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.session.SessionManagementFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author soda
 * @date 2021/4/15
 */
public class MySessionManagementFilter extends SessionManagementFilter {

    public MySessionManagementFilter(SecurityContextRepository securityContextRepository) {
        super(securityContextRepository);
    }

    public MySessionManagementFilter(SecurityContextRepository securityContextRepository, SessionAuthenticationStrategy sessionStrategy) {
        super(securityContextRepository, sessionStrategy);
    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("自定义MySessionManagementFilter正在执行");
        chain.doFilter(req, res);
    }
}

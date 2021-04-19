package com.soda.security.config;

import com.soda.security.filter.MyFilterSecurityInterceptor;
import com.soda.security.filter.MySessionManagementFilter;
import com.soda.security.service.UserDetailService;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author soda
 * @date 2021/4/14
 */
@EnableWebSecurity
public class WebSecurityConfig {
    /**
     * spring自带的userDetailService，用于对用户进行验证，
     * 可以自己自定义一个，主要是用户密码验证及角色权限
     *
     * @return
     */
/*
    @Bean
    public UserDetailsService userDetailsService() {

        //从内存中读取用户
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withDefaultPasswordEncoder()
                .username("admin")
                .password("123")
                .roles("admin")
                .build());
        return inMemoryUserDetailsManager;
    }
*/


    /**
     * 配置密码加解密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 自定义WebSecurityConfigurerAdapter
     */
    @Configuration
    @Order(1)// 声明优先使用
    class Config2 extends WebSecurityConfigurerAdapter {



        @Override
        public void configure(WebSecurity web) throws Exception {

            web.ignoring().mvcMatchers("/favicon.ico");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    // 添加过滤器
                    .addFilterAfter(new MySessionManagementFilter(new HttpSessionSecurityContextRepository(), new ConcurrentSessionControlAuthenticationStrategy(new SessionRegistryImpl())), SessionManagementFilter.class)
                    // 只对/admin/*进行处理
//                    .antMatcher("/admin/*")
                    //对所有url进行过滤处理，不会再给下一个filterChain进行处理
                    .antMatcher("/**")
                    // 授权认证
                    // 1、对antMatcher pattern下的请求进行授权认证
                    .authorizeRequests()
                    // 2、认证所有请求y
                    .anyRequest()
                    // 3、认证所需角色
                    .hasRole("admin")
                    .and()
                    //表单登录
                    .formLogin()
                    //get请求为登录页，Post请求为用户验证，配置后默认登录页就会失效
                    .loginPage("/login.html")
                    //验证成功后跳转路径,因为不是重定向，所以路径要为post请求
                    .successForwardUrl("/admin/hello")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    //允许所有用户访问该路径,spring security会将路径存进metasource，并设置属性为permitAll
                    //FilterSecurityInterceptor先进行预处理（pre invocation)来判断是否需要进行授权
                    .permitAll()
                    .and()
                    .exceptionHandling()
                    //处理accessDeniedException,没有访问权限/csrf token校验错误会调用该请求
                    .accessDeniedHandler((req, rep, exception) -> {
                        rep.setStatus(404);
                        rep.setContentType("text/html; charset=utf8");
                        rep.getWriter().write("没有权限，请联系管理员");
                    })
                    //验证处理端口，当未验证/或者验证失败时会调用并响应请求
                    .authenticationEntryPoint((request, response, authException) -> {
                        //设置response编码格式
                        response.setContentType("text/html; charset=utf8");
                        response.setStatus(401);
                        response.getWriter().write("请登录");
                        response.sendRedirect("/login.html");
                    })
                    .and();
                    //关闭csrf验证，这个安全验证有点蛋疼，请求过来需要带上上次返回给页面的csrf token,
                    //此示例代码使用了thymeleaf来配置登录页
/*
                    .csrf()
                    .disable();

*/

        }
    }


    /**
     * 再次自定义WebSecurityConfigurerAdapter，测试不同url pattern匹配的SecurityFilterChain。
     */
    @Configuration
    class Config1 extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.addFilterBefore(new MyFilterSecurityInterceptor(), FilterSecurityInterceptor.class)
                    .antMatcher("/guide/**");
        }
    }


}

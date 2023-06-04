package com.soda.learn.security.service;

import com.soda.learn.security.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户验证服务
 * @author soda
 * @date 2021/4/19
 */
@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    /**
     * 将此类暴露在容器后，spring security会将此服务注册进DaoAuthenticationProvider
     * 进行用户验证时需要此方法来获取用户信息，
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.queryUser(username);
    }
}

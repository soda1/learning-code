package com.soda.security.mapper;

import com.soda.security.domain.User;

/**
 * @author soda
 * @date 2021/4/19
 */

public interface UserDao {

    User queryUser(String username);
}

package com.soda.learn.mapper;

import org.apache.ibatis.annotations.Insert;

public interface UserMapper {


    @Insert("insert into user values(null, #{username})")
    int insertUser(String username);
}

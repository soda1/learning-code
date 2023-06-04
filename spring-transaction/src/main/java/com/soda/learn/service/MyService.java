package com.soda.learn.service;


import com.soda.learn.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyService {

    @Autowired
    UserMapper userMapper;

    @Transactional
    public void insertUser(String username1, String username2) {

        userMapper.insertUser(username1);
        //产生异常
        int i = 10/0;
        userMapper.insertUser(username2);
        System.out.println("insert success");
    }

    /**
     * 测试在类的其他方法中调用事务方法，看是事务是否可以起作用
     */
    public void invokeInsertUser() {
        insertUser("hhh","yyy");
    }

}

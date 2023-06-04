package com.soda.learn.mapper;

import com.soda.learn.javabean.Account;

import java.util.List;

public interface AccountMapper {

//    @Select("select * from account where id = #{id}")
    Account findById(Integer id);

    List<Account> findAll();

    int insertAccount(Account account);


     void updateAccount(Account account);

    void deleteAccount(String name);

    Account findByName(String name);

}

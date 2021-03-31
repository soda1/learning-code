package com.soda.mapper;

import com.soda.javabean.Account;

import java.util.List;

public interface AccountMapper {

//    @Select("select * from account where id = #{id}")
    Account findById(Integer id);

    List<Account> findAll();

    int insertAccount(Account account);

    int updateAccount(Float money, Integer id);


    Account findByName(String name);
}

package com.soda.learn.javabean;

import org.springframework.stereotype.Service;

import java.io.Serializable;

public class Account implements Serializable {

    private Integer id;

    private String name;

    private Float money;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }
}

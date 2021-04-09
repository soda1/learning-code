package com.soda.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 学习springboot循环依赖解决
 */
@Component
public class ComponentB {


    @Autowired
    private ComponentA componentA;


/*
    public ComponentB(ComponentA componentA) {
        this.componentA = componentA;
    }
*/
}

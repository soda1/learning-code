package com.soda.web.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 学习springboot循环依赖解决
 */
@Component
public class ComponentA {


    @Autowired
    private ComponentB componentB;

    /**
     * spring注册bean时有多个构造器如何选用
     * 1、优先使用无参构造器
     * 2、如果某个构造器使用@Autowire标注，会使用该构造器
     * 3、如果有多个构造器使用@Autowire标注，配置参数require=false时（true会报错），会根据IOC容器中的bean进行参数匹配
     */
/*
    public ComponentA() {

    }
*/

/*
    public ComponentA( ComponentB componentB) {
        this.componentB = componentB;
    }
*/

}

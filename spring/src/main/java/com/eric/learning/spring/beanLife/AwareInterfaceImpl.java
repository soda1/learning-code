package com.eric.learning.spring.beanLife;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;

/**
 *
 * @author eric
 * @date 2/20/2023
 *
 * 实现aware接口可以让bean知道自己的名字或由哪个工厂类生成的
 */
public class AwareInterfaceImpl  implements BeanNameAware, BeanFactoryAware {
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("beanInstance is singleton : " + beanFactory.isSingleton("beanInstance"));
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("bean name is " + name);

    }
}

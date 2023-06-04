package com.eric.learning.spring.beanLife;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author eric
 * @date 2/20/2023
 * 测试BeanPostProcessor流程
 */

@Component
public class BeanPostProcessorImpl implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if( bean instanceof ProductBean){
            System.out.println("Post Process Before Initialization method is called : Bean Name " + beanName);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof ProductBean){
            System.out.println("Post Process After Initialization method is called : Bean Name " + beanName);
        }

        return bean;
    }

}

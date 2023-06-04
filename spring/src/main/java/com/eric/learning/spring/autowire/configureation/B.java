package com.eric.learning.spring.autowire.configureation;

import org.springframework.stereotype.Component;

/**
 * @author eric
 * @date 4/6/2023
 */
@Component
public class B implements AutowireBean {
    @Override
    public void test() {
        System.out.println("B");
    }
}

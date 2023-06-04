package com.eric.learning.spring.beanLife;

import com.eric.learning.spring.beanLife.AwareInterfaceImpl;
import org.springframework.context.annotation.Bean;

/**
 * @author eric
 * @date 2/20/2023
 */
@org.springframework.context.annotation.Configuration
public class Configuration {

    //@Bean(name = "beanInstance")
    public AwareInterfaceImpl getAware(){
        return new AwareInterfaceImpl();
    }
}

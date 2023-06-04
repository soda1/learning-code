package com.eric.learning.spring.beanLife;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.stereotype.Component;

/**
 * @author eric
 * @date 2/20/2023
 *
 * aware接口可以让bean知道自己的名字或由哪个工厂类生成的
 *
 * InitializingBean: The InitializingBean interface includes the afterPropertiesSet() method to write the initialization logic.
 * The container calls this method after the properties are set.
 *
 * DisposableBean: It contains the destroy() method. Works after Spring Container releases the bean.
 * But it is tough coupled, recommend use annotation init method: @PostConstruct destroy method: @PreDestroy
 */
@Component("productBean")
public class ProductBean implements InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware {
    private String productName;

    public ProductBean() {
        System.out.println("ProductBean constructor called.");
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "productName='" + productName + '\'' +
                '}';
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("BeanInstance.destroy() method called");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("ProductBean.afterPropertiesSet() method");

    }
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("beanInstance is singleton : " + beanFactory.isSingleton("productBean"));
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("bean name is " + name);

    }
}

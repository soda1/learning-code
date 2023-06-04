package com.eric.learning.spring.autowire.configureation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author eric
 * @date 4/6/2023
 */
@Component
public class AutoWireTest {

    /**
     * 如果查询结果刚好为一个，就将该bean装配给@Autowired指定的数据；
     *
     * 如果查询的结果不止一个，那么@Autowired会根据名称来查找；
     *
     * 如果上述查找的结果为空，那么会抛出异常。解决方法时，使用required=false。
     */
    @Autowired
    private AutowireBean b1;


}

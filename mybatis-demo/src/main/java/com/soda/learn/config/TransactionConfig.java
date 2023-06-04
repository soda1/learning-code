package com.soda.learn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author eric
 * @date 6/4/2023
 */
@Configuration
@EnableTransactionManagement(order = 300)
public class TransactionConfig {

}

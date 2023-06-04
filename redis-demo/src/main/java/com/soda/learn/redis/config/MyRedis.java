package com.soda.learn.redis.config;


import com.soda.learn.redis.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class MyRedis {

    /**
     * 自定义LettuceConnectionFactory
     * @return
     */
    //@Bean
    //public LettuceConnectionFactory myLettuce() {
    //    LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
    //    lettuceConnectionFactory.setDatabase(1);
    //    return lettuceConnectionFactory;
    //}




    @Bean("redisTemplate")
    public RedisTemplate getRedis(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        //构造器参数：反序列化时的对象信息
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //设置hash类型键值对序列化器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        //System.out.println(redisTemplate);
        return redisTemplate;
    }


}

package com.soda.learn.redis.controller;


import com.soda.learn.redis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
public class redisController {

    @Autowired
    private RedisTemplate redisTemplate;

    //数据库对象
    private User user;

    /**
     * 测试缓存一致性 cache aside pattern
     * @param user
     * @return
     */
    @PutMapping("/cache")
    public String updateData( User user) throws InterruptedException {

        String key = "cache";
        //模拟更新数据库
        TimeUnit.SECONDS.sleep(4);
        this.user = user;
        System.out.println("数据库已更新");
        //缓存失效
        redisTemplate.opsForValue().getOperations().delete(key);

        return "更新成功";
    }


    @GetMapping("/cache")
    public User getData() {

        String key = "cache";
        User  user = (User) redisTemplate.opsForValue().get(key);
        //如果缓存中不存在，从数据库中获取
        if (user == null) {
            System.out.println("从数据库中获取");
            user = this.user;
        }
        return user;
    }


    @GetMapping("/add")
    public ResponseEntity addKey(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, 5000, TimeUnit.SECONDS);
        return ResponseEntity.ok("success");
    }



}

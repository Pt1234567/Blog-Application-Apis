package com.blogApplication.blogapis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testSendEmail(){
        redisTemplate.opsForValue().set("email","prashant@gmail.com");
        //Object email=redisTemplate.opsForValue().get("email");
        int a=1;
    }

}

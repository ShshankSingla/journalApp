package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    void test(){
        redisTemplate.opsForValue().set("test_key","test_value");
        //Object email = redisTemplate.opsForValue().get("email");



            Object value = redisTemplate.opsForValue().get("salary");

            System.out.println("================================");
            System.out.println("SALARY = " + value);
            System.out.println("================================");

    }
}

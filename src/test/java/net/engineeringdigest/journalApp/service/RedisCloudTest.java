package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisCloudTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void testRedis() {

        redisTemplate.opsForValue().set(
                "redis_cloud_test",
                "Hello Redis Cloud"
        );

        Object value = redisTemplate.opsForValue()
                .get("redis_cloud_test");

        System.out.println("Redis value = " + value);
    }
}
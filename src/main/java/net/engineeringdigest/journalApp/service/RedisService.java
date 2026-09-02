package net.engineeringdigest.journalApp.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    // generic method
    // get object from redis
    public <T>T get(String key, Class<T> entityClass){

        try {
            Object o = redisTemplate.opsForValue().get(key);
            if(o== null){
                return null;
            }
            // delete
//            redisTemplate.delete("" );
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(o.toString(), entityClass);
        }catch(Exception e){
            log.error("Exception ",e);
            return null;
        }
    }

    // store obj in redis wiht ttl
    // har ek object ko hm set karwate hai corresponding to a key for a particular time
    public void set(String key, Object o, Long ttl ){ // time to live

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.SECONDS);
//            redisTemplate.opsForValue().set(key,o.toString(),ttl, TimeUnit.SECONDS);

        }catch(Exception e){
            log.error("Exception ",e);

        }
    }


}

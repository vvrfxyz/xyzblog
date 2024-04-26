package xyz.vvrf.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;

@Component
@Slf4j
public class RedisLockUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final Long LOCK_REDIS_TIMEOUT = 10L;

    public Boolean getLock(String key,String value){
        return redisTemplate.opsForValue().setIfAbsent(key,value, Duration.ofSeconds(LOCK_REDIS_TIMEOUT));
    }

    public Boolean getLock(String key,String value,Long expireTimeOfSeconds){
        return redisTemplate.opsForValue().setIfAbsent(key,value, Duration.ofSeconds(expireTimeOfSeconds));
    }

    public Boolean releaseLock(String key, String value) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                "redis.call('del', KEYS[1]) " +
                "return true " +
                "else return false end";

        Boolean result = (Boolean)redisTemplate.execute(new DefaultRedisScript<Boolean>(script, Boolean.class),
                Collections.singletonList(key), value);
        return result;
    }
}

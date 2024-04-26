package xyz.vvrf.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class RedisReentrantLockUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private ThreadLocal<Map> lockers = new ThreadLocal<>();

    private Map<String,Integer> currentLockers(){
        Map<String,Integer> refs = lockers.get();
        if (refs!=null){
            return refs;
        }
        lockers.set(new HashMap());
        return lockers.get();
    }

    public boolean lock(String key){
        Map refs = currentLockers();
        Integer refCnt = (Integer) refs.get(key);
        if (refCnt!=null){
            refs.put(key,refCnt+1);
            return true;
        }
        redisTemplate.opsForValue().set(key,"", Duration.ofSeconds(30L));
        refs.put(key,1);
        return true;
    }

    public boolean unlock(String key){
        Map refs = currentLockers();
        Integer refCnt = (Integer) refs.get(key);
        if (refCnt == null)return false;
        refCnt = -1;
        if (refCnt>0){
            refs.put(key,refCnt);
        }else {
            refs.remove(key);
            redisTemplate.delete(key);
        }
        return true;
    }
}

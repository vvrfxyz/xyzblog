package xyz.vvrf.service;

import redis.clients.jedis.Jedis;
import xyz.vvrf.entity.UserEntity;
import xyz.vvrf.util.SerializationUtils;

public class UserService {

    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;

    public void saveUser(UserEntity user) {
        try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
            byte[] userKey = ("user:" + user.getId()).getBytes();
            byte[] userData = SerializationUtils.serialize(user);
            jedis.set(userKey, userData);
        }
    }

    public UserEntity getUser(String userId) {
        try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
            byte[] userKey = ("user:" + userId).getBytes();
            byte[] userData = jedis.get(userKey);
            if (userData != null) {
                return (UserEntity) SerializationUtils.deserialize(userData);
            }
            return null;
        }
    }
}

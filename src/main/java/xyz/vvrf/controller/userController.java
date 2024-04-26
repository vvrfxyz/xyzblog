package xyz.vvrf.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.vvrf.entity.UserEntity;
import xyz.vvrf.service.UserService;
import xyz.vvrf.util.RedisLockUtils;
import xyz.vvrf.util.RedisReentrantLockUtils;

@RestController
@RequestMapping(value = "/api/setUser")
public class userController {
    @Autowired
    RedisLockUtils redisLockUtils;
    @Autowired
    RedisReentrantLockUtils redisReentrantLockUtils;
    @GetMapping("/setuser")
    public void set(){
        System.out.println(Thread.currentThread().getName());
        redisLockUtils.getLock("111",Thread.currentThread().getName(),100L);
        redisLockUtils.getLock("111",Thread.currentThread().getName(),100L);
    }

    @GetMapping("/test")
    public void get(){
        System.out.println(redisReentrantLockUtils.lock("codehole"));
        System.out.println(redisReentrantLockUtils.lock("codehole"));
        System.out.println(redisReentrantLockUtils.unlock("codehole"));
        System.out.println(redisReentrantLockUtils.unlock("codehole"));
    }
}

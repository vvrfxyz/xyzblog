package xyz.vvrf.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.vvrf.entity.UserEntity;
import xyz.vvrf.service.UserService;

@RestController
@RequestMapping(value = "/api/setUser")
public class userController {
    @GetMapping("/setuser")
    public void set(){
        UserService userService = new UserService();
        UserEntity user = new UserEntity();
        user.setId("1");
        user.setName("Alice");
        user.setAge(30);

        userService.saveUser(user); // 将用户保存到Redis
        UserEntity retrievedUser = userService.getUser("1"); // 从Redis读取用户

        System.out.println(retrievedUser.getName()); // 应该打印出"Alice"
    }
}

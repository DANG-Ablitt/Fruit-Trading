package platform.controller;

import platform.annotation.Login;
import platform.annotation.LoginUser;
import platform.entity.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.Result;

/**
 * 测试接口
 */
@RestController
@RequestMapping("test")
public class ApiTestController {

    /**
     * 获取用户信息
     */
    @Login
    @GetMapping("userInfo")
    public Result<UserEntity> userInfo(@LoginUser UserEntity user){
        return new Result<UserEntity>().ok(user);
    }

    /**
     * 获取用户ID
     */
    @Login
    @GetMapping("userId")
    public Result<Long> userInfo(@RequestAttribute("userId") Long userId){
        return new Result<Long>().ok(userId);
    }

    /**
     * 忽略Token验证测试
     */
    @GetMapping("notToken")
    public Result<String> notToken(){
        return new Result<String>().ok("无需token也能访问。。。");
    }

}
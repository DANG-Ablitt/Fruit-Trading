package platform.controller;


import exception.ErrorCode;
import platform.annotation.Login;
import platform.dto.LoginDTO;
import platform.feign.MessageFeignClient;
import platform.service.TokenService;
import platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.Result;
import validator.AssertUtils;
import validator.ValidatorUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 登录接口
 */
@RestController
@RequestMapping("auth")
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private MessageFeignClient messageFeignClient;


    /**
     * 登录(如果是新用户自动注册)
     */
    @PostMapping("login")
    public Result<Map<String, Object>> login(@RequestBody LoginDTO dto){
        //表单校验
        ValidatorUtils.validateEntity(dto);
        //用户登录
        Map<String, Object> map = userService.login(dto);
        return new Result().ok(map);
    }

    /**
     * 请求验证码
     */
    @PostMapping("captcha")
    public Result captcha(@RequestBody Map<String,String> mobileparams){
        //与消息模块通信发送短信验证码
        messageFeignClient.captcha(mobileparams);
        return new Result();
    }

    /**
     * 退出
     */
    @Login
    @PostMapping("logout")
    public Result logout(@RequestAttribute("userId") Long userId,@RequestAttribute("id") Long id){
        tokenService.expireToken(userId,id);
        return new Result();
    }
}
package platform.controller;


import platform.annotation.Login;
import platform.dto.LoginDTO;
import platform.service.TokenService;
import platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.Result;
import validator.ValidatorUtils;

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


    /**
     * 登录
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
     * 退出
     */
    @Login
    @PostMapping("logout")
    public Result logout(@RequestAttribute("userId") Long userId){
        tokenService.expireToken(userId);
        return new Result();
    }

}
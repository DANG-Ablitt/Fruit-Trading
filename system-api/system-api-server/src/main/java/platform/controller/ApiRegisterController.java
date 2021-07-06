package platform.controller;

import platform.entity.UserEntity;
import platform.dto.RegisterDTO;
import platform.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.Result;
import validator.ValidatorUtils;

import java.util.Date;

/**
 * 注册接口
 */
@RestController
@RequestMapping("register")
public class ApiRegisterController {
    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param dto
     * @return
     */
    @PostMapping
    public Result register(@RequestBody RegisterDTO dto){
        //表单校验
        ValidatorUtils.validateEntity(dto);
        UserEntity user = new UserEntity();
        user.setMobile(dto.getMobile());
        user.setUsername(dto.getMobile());
        user.setPassword(DigestUtils.sha256Hex(dto.getPassword()));
        user.setCreateDate(new Date());
        userService.insert(user);
        return new Result();
    }
}
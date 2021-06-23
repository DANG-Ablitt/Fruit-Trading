package authentication.controller;

import authentication.dao.user.RegisterDao;
import authentication.dto.RegisterDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.user.UserDetail;
import utils.Result;
import validator.ValidatorUtils;

import java.util.Date;

/**
 * 注册接口
 */
@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private RegisterDao registerDao;

    @PostMapping
    public Result register(@RequestBody RegisterDTO dto){
        //表单校验
        ValidatorUtils.validateEntity(dto);
        UserDetail user = new UserDetail();
        user.setMobile(dto.getMobile());
        user.setPassword(DigestUtils.sha256Hex(dto.getPassword()));
        //将数据写入数据库中
        registerDao.insert(user);
        return new Result();
    }
}
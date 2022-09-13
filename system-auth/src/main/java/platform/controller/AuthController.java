package platform.controller;

import constant.Constant;
import exception.ErrorCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import platform.dto.AuthorizationDTO;
import platform.dto.LoginDTO;
import platform.service.AuthService;
import platform.service.CaptchaService;
import platform.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import security.user.StaffDetail;
import utils.Result;
import validator.AssertUtils;
import validator.ValidatorUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 授权管理
 */
@RestController
public class AuthController {
    private static final Logger LOGGER = LogManager.getLogger();
    @Autowired
    private AuthService authService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private CaptchaService captchaService;

    @GetMapping("captcha")
    public void captcha(HttpServletResponse response, String uuid)throws IOException {
        LOGGER.info("日志输出======================================"+uuid);
        //uuid不能为空
        AssertUtils.isBlank(uuid, ErrorCode.IDENTIFIER_NOT_NULL);
        //生成图片验证码
        BufferedImage image = captchaService.create(uuid);
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.close();
    }

    @PostMapping(value = "login")
    public Result<AuthorizationDTO> login(@RequestBody LoginDTO login){
        //效验数据
        ValidatorUtils.validateEntity(login);
        //验证码是否正确
        boolean flag = captchaService.validate(login.getUuid(), login.getCaptcha());
        if(!flag){
            return new Result<AuthorizationDTO>().error(ErrorCode.CAPTCHA_ERROR);
        }
        //获取登录授权信息
        AuthorizationDTO authorization = authService.login(login);
        return new Result<AuthorizationDTO>().ok(authorization);
    }

    @PostMapping(value = "logout")
    public Result logout(HttpServletRequest request){
        String userId = request.getHeader(Constant.USER_KEY);
        authService.logout(Long.parseLong(userId));
        return new Result();
    }

    /**
     * 是否有资源访问权限
     * @param token   token
     * @param url     资源URL
     * @param method  请求方式
     * @return 有访问权限，则返回用户信息
     */
    @PostMapping("resource")
    public Result<StaffDetail> resource(@RequestParam(value = "token", required = false) String token,
                                        @RequestParam("url") String url, @RequestParam("method") String method){
        StaffDetail data = resourceService.resource(token, url, method);
        return new Result<StaffDetail>().ok(data);
    }
}
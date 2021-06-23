package authentication.controller;

import authentication.service.CaptchaService;
import exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import validator.AssertUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 获取验证码
 */
@Controller
public class CaptchaController {
    @Autowired
    private CaptchaService captchaService;

    /**
     * 获取图形验证码
     */
    @GetMapping("image_captcha")
    public void image_captcha(HttpServletResponse response, String uuid)throws IOException {
        //uuid不能为空
        AssertUtils.isBlank(uuid, ErrorCode.IDENTIFIER_NOT_NULL);
        //生成图片验证码
        BufferedImage image = captchaService.image_create(uuid);
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.close();
    }

    /**
     * 获取短信验证码
     */
}

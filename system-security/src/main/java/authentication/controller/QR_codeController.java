package authentication.controller;

import authentication.service.QR_codeService;
import exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import validator.AssertUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * 获取二维码
 */
@Controller
public class QR_codeController {

    @Autowired
    private QR_codeService qr_codeService;
    @GetMapping("QR_code")
    public void QR_code(HttpServletResponse response, String id) throws Exception {
        //uuid不能为空
        AssertUtils.isBlank(id, ErrorCode.IDENTIFIER_NOT_NULL);
        //生成二维码图片
        BufferedImage image = qr_codeService.qr_code_create(id);
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.close();
    }
}

package platform.controller;

import com.alibaba.fastjson.JSON;
import exception.ErrorCode;
import page.PageData;
import platform.dto.SysSmsDTO;
import platform.enums.PlatformEnum;
import platform.remote.ParamsRemoteService;
import platform.service.SysSmsService;
import platform.service.impl.CaptchaService;
import platform.sms.SmsConfig;
import platform.utils.ModuleConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.Result;
import validator.AssertUtils;
import validator.ValidatorUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * 短信服务
 */
@RestController
@RequestMapping("sms")
public class SmsController {
	@Autowired
	private SysSmsService sysSmsService;
    @Autowired
    private ParamsRemoteService paramsRemoteService;
    @Autowired
	private CaptchaService captchaService;

    private final static String KEY = ModuleConstant.SMS_CONFIG_KEY;

	@GetMapping("page")
	public Result<PageData<SysSmsDTO>> page(@RequestParam Map<String, Object> params){
		PageData<SysSmsDTO> page = sysSmsService.page(params);
		return new Result<PageData<SysSmsDTO>>().ok(page);
	}

    @GetMapping("config")
    public Result<SmsConfig> config(){
		SmsConfig config = paramsRemoteService.getValueObject(KEY, SmsConfig.class);
        return new Result<SmsConfig>().ok(config);
    }

	@PostMapping("saveConfig")
	public Result saveConfig(@RequestBody SmsConfig config){
		//校验类型
		ValidatorUtils.validateEntity(config);
		if(config.getPlatform() == PlatformEnum.ALIYUN.value()){
			//校验阿里云数据
			ValidatorUtils.validateEntity(config);
		}else if(config.getPlatform() == PlatformEnum.QCLOUD.value()){
			//校验腾讯云数据
			ValidatorUtils.validateEntity(config);
		}
		paramsRemoteService.updateValueByCode(KEY, JSON.toJSONString(config));
		return new Result();
	}

    @PostMapping("send")
    public Result send(String mobile, String params){
        sysSmsService.send(mobile, params);
        return new Result();
    }

	@DeleteMapping
	public Result delete(@RequestBody Long[] ids){
		sysSmsService.deleteBatchIds(Arrays.asList(ids));
		return new Result();
	}

	/**
	 * 请求短信验证码（用于移动端登录）
	 */
	@GetMapping("captcha")
	public void captcha(HttpServletResponse response,String mobile) throws IOException {
		//uuid不能为空
		AssertUtils.isBlank(mobile, ErrorCode.IDENTIFIER_NOT_NULL);
		//生成验证码
		String captcha=captchaService.create(mobile,"SMS_CAPTCHA_XX");
		//响应返回
		response.getWriter().write(captcha);
	}

}
package platform.controller;

import com.alibaba.fastjson.JSON;
import page.PageData;
import platform.dto.SysSmsDTO;
import platform.enums.PlatformEnum;
import platform.service.SysSmsService;
import platform.sms.SmsConfig;
import platform.utils.ModuleConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import utils.Result;
import validator.ValidatorUtils;

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
    //@Autowired
    //private ParamsRemoteService paramsRemoteService;

    private final static String KEY = ModuleConstant.SMS_CONFIG_KEY;

	@GetMapping("page")
	public Result<PageData<SysSmsDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
		PageData<SysSmsDTO> page = sysSmsService.page(params);

		return new Result<PageData<SysSmsDTO>>().ok(page);
	}

    @GetMapping("config")
    public Result<SmsConfig> config(){
		//SmsConfig config = paramsRemoteService.getValueObject(KEY, SmsConfig.class);
		SmsConfig config=null;
        return new Result<SmsConfig>().ok(config);
    }

	@PostMapping("saveConfig")
	public Result saveConfig(@RequestBody SmsConfig config){
		//校验类型
		ValidatorUtils.validateEntity(config);

		if(config.getPlatform() == PlatformEnum.ALIYUN.value()){
			//校验阿里云数据
			//ValidatorUtils.validateEntity(config, AliyunGroup.class);
		}else if(config.getPlatform() == PlatformEnum.QCLOUD.value()){
			//校验腾讯云数据
			//ValidatorUtils.validateEntity(config, QcloudGroup.class);
		}

		//paramsRemoteService.updateValueByCode(KEY, JSON.toJSONString(config));

		return new Result();
	}

    @PostMapping("send")

    public Result send(String mobile, String params){
        sysSmsService.send(mobile, params);

        return new Result();
    }

	@DeleteMapping
	@ApiOperation("删除")
	public Result delete(@RequestBody Long[] ids){
		sysSmsService.deleteBatchIds(Arrays.asList(ids));

		return new Result();
	}

}
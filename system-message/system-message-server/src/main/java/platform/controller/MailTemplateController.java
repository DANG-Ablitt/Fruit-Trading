package platform.controller;

import com.alibaba.fastjson.JSON;
import page.PageData;
import platform.dto.SysMailTemplateDTO;
import platform.email.EmailConfig;
import platform.remote.ParamsRemoteService;
import platform.service.SysMailTemplateService;
import platform.utils.ModuleConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utils.Result;
import validator.ValidatorUtils;
import java.util.Arrays;
import java.util.Map;

/**
 * 邮件模板
 */
@RestController
@RequestMapping("mailtemplate")
public class MailTemplateController {
    @Autowired
    private SysMailTemplateService sysMailTemplateService;
    @Autowired
    private ParamsRemoteService paramsRemoteService;

    private final static String KEY = ModuleConstant.MAIL_CONFIG_KEY;

    @GetMapping("page")
    public Result<PageData<SysMailTemplateDTO>> page(@RequestParam Map<String, Object> params){
        PageData<SysMailTemplateDTO> page = sysMailTemplateService.page(params);
        return new Result<PageData<SysMailTemplateDTO>>().ok(page);
    }

    @GetMapping("config")
    public Result<EmailConfig> config(){
        EmailConfig config = paramsRemoteService.getValueObject(KEY, EmailConfig.class);
        return new Result<EmailConfig>().ok(config);
    }

    @PostMapping("saveConfig")
    public Result saveConfig(@RequestBody EmailConfig config){
        //校验数据
        ValidatorUtils.validateEntity(config);
        paramsRemoteService.updateValueByCode(KEY, JSON.toJSONString(config));
        return new Result();
    }

    @GetMapping("{id}")
    public Result<SysMailTemplateDTO> info(@PathVariable("id") Long id){
        SysMailTemplateDTO sysMailTemplate = sysMailTemplateService.get(id);
        return new Result<SysMailTemplateDTO>().ok(sysMailTemplate);
    }

    @PostMapping
    public Result save(SysMailTemplateDTO dto){
        //校验类型
        ValidatorUtils.validateEntity(dto);
        sysMailTemplateService.save(dto);
        return new Result();
    }

    @PutMapping
    public Result update(SysMailTemplateDTO dto){
        //校验类型
        ValidatorUtils.validateEntity(dto);
        sysMailTemplateService.update(dto);
        return new Result();
    }

    @DeleteMapping
    public Result delete(@RequestBody Long[] ids){
        sysMailTemplateService.deleteBatchIds(Arrays.asList(ids));
        return new Result();
    }

    @PostMapping("send")
    public Result send(Long id, String mailTo, String mailCc, String params) throws Exception{
        boolean flag = sysMailTemplateService.sendMail(id, mailTo, mailCc, params);
        if(flag){
            return new Result();
        }
        return new Result().error("邮件发送失败");
    }
}
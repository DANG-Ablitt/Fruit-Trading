package platform.sms;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import platform.enums.PlatformEnum;
import platform.remote.ParamsRemoteService;
import platform.service.SysSmsService;
import platform.service.impl.SysSmsServiceImpl;
import platform.utils.ModuleConstant;
import utils.SpringContextUtils;

import java.util.Map;

/**
 * 短信Factory
 */
public class SmsFactory {
    private static ParamsRemoteService paramsRemoteService;

    private static SysSmsService sysSmsService;

    static {
        SmsFactory.paramsRemoteService = SpringContextUtils.getBean(ParamsRemoteService.class);
        SmsFactory.sysSmsService = SpringContextUtils.getBean(SysSmsServiceImpl.class);
    }

    public static AbstractSmsService build(String template){
        //获取短信配置信息
        SmsConfig config = paramsRemoteService.getValueObject(ModuleConstant.SMS_CONFIG_KEY, SmsConfig.class);
        //根据配置信息选择不同的短信工厂
        //阿里云
        if(config.getPlatform() == PlatformEnum.ALIYUN.value()){
            //从数据库获取阿里云账号和密码
            String name=sysSmsService.namepass("SMS_ALIYUN");
            //将获得的数据绑定到config中
            Map mapTypes = JSON.parseObject(name);
            config.setName(mapTypes.get("aliyunkey").toString());
            config.setPassword(mapTypes.get("aliyunpasswork").toString());
            //从数据库获取短信宝签名和模板
            String name1=sysSmsService.moban123(template);
            //将获得的数据绑定到config中
            Map mapTypes1 = JSON.parseObject(name1);
            config.setSignName(mapTypes1.get("SignName").toString());
            config.setTemplate(mapTypes1.get("Template").toString());
            return new AliyunSmsService(config);
            //腾讯云
        }else if(config.getPlatform() == PlatformEnum.QCLOUD.value()){
            return new QcloudSmsService(config);
            //短信宝
        }else if(config.getPlatform() == PlatformEnum.DUANXINBAO.value()) {
            //从数据库获取短信宝账号和密码
            String name=sysSmsService.namepass("SMS_DUANXINBAO");
            //将获得的数据绑定到config中
            Map mapTypes = JSON.parseObject(name);
            config.setName(mapTypes.get("duanxinbaoName").toString());
            config.setPassword(mapTypes.get("duanxinbaoPassword").toString());
            //从数据库获取短信宝签名和模板
            String name1=sysSmsService.moban123(template);
            //将获得的数据绑定到config中
            Map mapTypes1 = JSON.parseObject(name1);
            config.setSignName(mapTypes1.get("SignName").toString());
            config.setTemplate(mapTypes1.get("Template").toString());
            return new DuanxinbaoSmsService(config);
        }
        return null;
    }
}
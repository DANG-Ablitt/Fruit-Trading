package platform.sms;

import platform.enums.PlatformEnum;
import platform.utils.ModuleConstant;
import utils.SpringContextUtils;

/**
 * 短信Factory
 *
 * @author Mark sunlightcs@gmail.com
 */
public class SmsFactory {
    //private static ParamsRemoteService paramsRemoteService;

    static {
        //SmsFactory.paramsRemoteService = SpringContextUtils.getBean(ParamsRemoteService.class);
    }

    public static AbstractSmsService build(){
        //获取短信配置信息
        SmsConfig config=null;
        //SmsConfig config = paramsRemoteService.getValueObject(ModuleConstant.SMS_CONFIG_KEY, SmsConfig.class);
        //根据配置信息选择不同的短信工厂
        //阿里云
        if(config.getPlatform() == PlatformEnum.ALIYUN.value()){
            return new AliyunSmsService(config);
            //腾讯云
        }else if(config.getPlatform() == PlatformEnum.QCLOUD.value()){
            return new QcloudSmsService(config);
            //短信宝
        }else if(config.getPlatform() == PlatformEnum.DUANXINBAO.value()) {
            return new DuanxinbaoSmsService(config);
        }
        return null;
    }
}
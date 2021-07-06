package platform.service;

import mybatis_plus.service.BaseService;
import page.PageData;
import platform.dto.SysSmsDTO;
import platform.entity.SysSmsEntity;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 短信
 */
public interface SysSmsService extends BaseService<SysSmsEntity> {

    PageData<SysSmsDTO> page(Map<String, Object> params);

    /**
     * 发送短信
     * @param mobile   手机号
     * @param params   短信参数
     */
    void send(String mobile, String params);

    /**
     * 保存短信发送记录
     * @param platform   平台
     * @param mobile   手机号
     * @param params   短信参数
     * @param status   发送状态
     */
    void save(Integer platform, String mobile, LinkedHashMap<String, String> params, Integer status);

    /**
     * 获取短信平台连接信息（账号和密码）
     */
    String namepass(String name);

    /**
     * 获取短信模板信息（签名和模板）
     */
    String moban123(String name);
}


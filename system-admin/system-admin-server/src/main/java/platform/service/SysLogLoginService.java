package platform.service;

import mybatis_plus.service.BaseService;
import page.PageData;
import platform.dto.SysLogLoginDTO;
import platform.entity.SysLogLoginEntity;
import java.util.List;
import java.util.Map;

/**
 * 登录日志
 */
public interface SysLogLoginService extends BaseService<SysLogLoginEntity> {

    PageData<SysLogLoginDTO> page(Map<String, Object> params);

    List<SysLogLoginDTO> list(Map<String, Object> params);

    void save(SysLogLoginEntity entity);
}
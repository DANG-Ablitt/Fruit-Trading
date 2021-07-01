package platform.service;

import mybatis_plus.service.BaseService;
import page.PageData;
import platform.dto.SysLogErrorDTO;
import platform.entity.SysLogErrorEntity;
import java.util.List;
import java.util.Map;

/**
 * 异常日志
 */
public interface SysLogErrorService extends BaseService<SysLogErrorEntity> {

    PageData<SysLogErrorDTO> page(Map<String, Object> params);

    List<SysLogErrorDTO> list(Map<String, Object> params);

    void save(SysLogErrorEntity entity);

}
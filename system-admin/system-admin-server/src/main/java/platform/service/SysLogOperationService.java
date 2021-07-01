package platform.service;

import mybatis_plus.service.BaseService;
import page.PageData;
import platform.dto.SysLogOperationDTO;
import platform.entity.SysLogOperationEntity;
import java.util.List;
import java.util.Map;

/**
 * 操作日志
 */
public interface SysLogOperationService extends BaseService<SysLogOperationEntity> {

    PageData<SysLogOperationDTO> page(Map<String, Object> params);

    List<SysLogOperationDTO> list(Map<String, Object> params);

    void save(SysLogOperationEntity entity);
}
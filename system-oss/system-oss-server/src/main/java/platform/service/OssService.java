package platform.service;

import mybatis_plus.service.BaseService;
import page.PageData;
import platform.entity.OssEntity;
import java.util.Map;

/**
 * 文件上传
 */
public interface OssService extends BaseService<OssEntity> {

	PageData<OssEntity> page(Map<String, Object> params);
}

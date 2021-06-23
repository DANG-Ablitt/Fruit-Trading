package authentication.service;

import authentication.dao.staff.SysResourceDao;
import authentication.entity.SysResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import security.bo.ResourceBO;
import utils.ConvertUtils;
import java.util.List;

/**
 * 资源管理
 */
@Service
public class SysResourceService {

    @Autowired
    private SysResourceDao sysResourceDao;

    public List<ResourceBO> getUserResourceList(Long staffId) throws Exception {
        List<SysResourceEntity> entityList = sysResourceDao.getUserResourceList(staffId);
        return ConvertUtils.sourceToTarget(entityList, ResourceBO.class);
    }

}
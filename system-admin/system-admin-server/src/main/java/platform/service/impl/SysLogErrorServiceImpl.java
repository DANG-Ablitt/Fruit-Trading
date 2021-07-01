package platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import constant.Constant;
import mybatis_plus.service.impl.BaseServiceImpl;
import page.PageData;
import platform.dao.SysLogErrorDao;
import platform.dto.SysLogErrorDTO;
import platform.entity.SysLogErrorEntity;
import platform.service.SysLogErrorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.ConvertUtils;

import java.util.List;
import java.util.Map;

/**
 * 异常日志
 */
@Service
public class SysLogErrorServiceImpl extends BaseServiceImpl<SysLogErrorDao, SysLogErrorEntity> implements SysLogErrorService {

    @Override
    public PageData<SysLogErrorDTO> page(Map<String, Object> params) {
        IPage<SysLogErrorEntity> page = baseDao.selectPage(
            getPage(params, Constant.CREATE_DATE, false),
            getWrapper(params)
        );

        return getPageData(page, SysLogErrorDTO.class);
    }

    @Override
    public List<SysLogErrorDTO> list(Map<String, Object> params) {
        List<SysLogErrorEntity> entityList = baseDao.selectList(getWrapper(params));

        return ConvertUtils.sourceToTarget(entityList, SysLogErrorDTO.class);
    }

    private QueryWrapper<SysLogErrorEntity> getWrapper(Map<String, Object> params){
        String module = (String) params.get("module");

        QueryWrapper<SysLogErrorEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(module), "module", module);
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysLogErrorEntity entity) {
        insert(entity);
    }

}
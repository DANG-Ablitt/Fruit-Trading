package platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import constant.Constant;
import mybatis_plus.service.impl.BaseServiceImpl;
import page.PageData;
import platform.dao.OssDao;
import platform.entity.OssEntity;
import platform.service.OssService;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class OssServiceImpl extends BaseServiceImpl<OssDao, OssEntity> implements OssService {

	@Override
	public PageData<OssEntity> page(Map<String, Object> params) {
		IPage<OssEntity> page = baseDao.selectPage(
			getPage(params, Constant.CREATE_DATE, false),
			new QueryWrapper<>()
		);
		return getPageData(page, OssEntity.class);
	}
}

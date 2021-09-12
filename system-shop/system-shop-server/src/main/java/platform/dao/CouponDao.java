package platform.dao;

import mybatis_plus.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import platform.entity.CouponEntity;

/**
 * 优惠卷管理
 */
@Mapper
public interface CouponDao extends BaseDao<CouponEntity> {

}

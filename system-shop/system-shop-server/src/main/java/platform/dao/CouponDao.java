package platform.dao;

import mybatis_plus.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import platform.entity.CouponEntity;

/**
 * 优惠卷管理
 */
@Mapper
public interface CouponDao extends BaseDao<CouponEntity> {

    CouponEntity getShopByShopId(Long shopId);

    CouponEntity getById(@Param("id") Long id);

}

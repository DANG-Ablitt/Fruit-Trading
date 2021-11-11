package platform.dao;

import mybatis_plus.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import platform.entity.OrderEntity;

@Mapper
public interface OrderDao extends BaseDao<OrderEntity> {
}

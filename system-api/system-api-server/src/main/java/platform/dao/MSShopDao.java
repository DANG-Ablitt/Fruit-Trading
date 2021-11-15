package platform.dao;

import mybatis_plus.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import platform.dto.ShopInfoDTO;
import platform.entity.MSShopEntity;

import java.util.List;

@Mapper
public interface MSShopDao extends BaseDao<MSShopEntity> {

    /**
     *  获取秒杀商品列表
     */
    List<MSShopEntity> getShopById();
    /**
     * 获取商品详细信息
     */
    ShopInfoDTO getShopByInfo(Long shop_id);
}

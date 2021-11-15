package platform.service;

import mybatis_plus.service.BaseService;
import platform.dto.ShopInfoDTO;
import platform.entity.MSShopEntity;
import platform.entity.UserEntity;

import java.util.List;

public interface MSShopService extends BaseService<MSShopEntity> {

    /**
     * 返回秒杀商品列表
     */
    List<MSShopEntity> getByShop();
    /**
     * 商品详细参数
     */
    ShopInfoDTO getShopByInfo(Long shop_id);
}

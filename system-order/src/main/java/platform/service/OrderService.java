package platform.service;

import mybatis_plus.service.BaseService;
import platform.dto.ShopInfoDTO;
import platform.entity.OrderEntity;
import utils.Result;

public interface OrderService extends BaseService<OrderEntity> {
    /**
     * 下单接口
     */
    Result confirmOrder(ShopInfoDTO dto);

    /**
     * 取消订单
     */
    Result cancelOrder(Long shop_id);

    /**
     * 获取取货码
     */
    Result malOrder(Long shop_id);

}
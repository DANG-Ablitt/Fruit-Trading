package platform.service;

import mybatis_plus.service.BaseService;
import page.PageData;
import platform.dto.CouponReturnDTO;
import platform.dto.PmsCouponDTO;
import platform.dto.ReturnInfoDTO;
import platform.entity.CouponEntity;

import java.text.ParseException;
import java.util.Map;

/**
 * 优惠商品管理（抢购和秒杀）
 */
public interface CouponService extends BaseService<CouponEntity> {
    /**
     * 分页查询
     */
    PageData<CouponReturnDTO> page(Map<String, Object> params);
    /**
     * 保存
     */
    void save(PmsCouponDTO dto) throws ParseException, Exception;
    /**
     * 根据id查询
     */
    CouponEntity getShopByShopId(Long shopId);
    /**
     * 查询商品详细信息
     */
    ReturnInfoDTO get(Long id);
}

package platform.service;

import mybatis_plus.service.BaseService;
import page.PageData;
import platform.dto.CouponReturnDTO;
import platform.dto.PmsCouponDTO;
import platform.dto.SysParamsDTO;
import platform.entity.CouponEntity;

import java.util.List;
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
    void save(PmsCouponDTO dto);
}

package platform.controller;

import annotation.LogOperation;
import mybatis_plus.annotation.DataFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import page.PageData;
import platform.dto.CouponReturnDTO;
import platform.dto.PmsCouponDTO;
import platform.dto.ReturnInfoDTO;
import platform.dto.SysParamsDTO;
import platform.entity.CouponEntity;
import platform.service.CouponService;
import utils.DateUtils;
import utils.Result;
import validator.AssertUtils;
import validator.ValidatorUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 优惠商品管理（抢购和秒杀）
 * 因为优惠商品
 */
@RestController
@RequestMapping("coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    /**
     * 分页显示
     */
    @DataFilter(tableAlias = "shop_coupon",deptId="dept_id",prefix="and")
    @GetMapping("page")
    public Result<PageData<CouponReturnDTO>> page(@RequestParam Map<String, Object> params){
        PageData<CouponReturnDTO> page = couponService.page(params);
        return new Result<PageData<CouponReturnDTO>>().ok(page);
    }

    /**
     * 添加
     */
    @PostMapping("save")
    public Result save(@RequestBody PmsCouponDTO dto) throws Exception {
        //效验数据
        ValidatorUtils.validateEntity(dto);
        couponService.save(dto);
        return new Result();
    }

    /**
     * 根据id查询
     */
    @PostMapping("coupon_info_id")
    public Result coupon_info_id(@RequestBody Map<String,String> params){
        Long shopId= Long.parseLong(params.get("shopId"));
        CouponEntity couponEntity=couponService.getShopByShopId(shopId);
        return new Result().ok(couponEntity);
    }

    /**
     * 查询商品详细信息
     */
    @GetMapping("{id}")
    public Result<ReturnInfoDTO> coupon_info(@PathVariable("id") Long id){
        ReturnInfoDTO data=couponService.get(id);
        return new Result<ReturnInfoDTO>().ok(data);
    }
}
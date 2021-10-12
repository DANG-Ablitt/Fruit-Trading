package platform.controller;

import annotation.LogOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import page.PageData;
import platform.dto.CouponReturnDTO;
import platform.dto.PmsCouponDTO;
import platform.dto.SysParamsDTO;
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
 */
@RestController
@RequestMapping("coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    /**
     * 分页显示
     */
    @GetMapping("page")
    public Result<PageData<CouponReturnDTO>> page(@RequestParam Map<String, Object> params){
        PageData<CouponReturnDTO> page = couponService.page(params);
        return new Result<PageData<CouponReturnDTO>>().ok(page);
    }

    /**
     * 添加
     */
    @PostMapping
    public Result save(@RequestBody PmsCouponDTO dto){
        /**
         * bug记录：
         * 因为前端的 el-date-picker 插件（传递的参数名为time0）
         * 如果没有值时会传递 "" ,PmsCouponDTO中的time0为字符串数组
         * @RequestBody 注解转换时会出错
         *
         * 解决方案：
         * 在前端将time0的默认值设置为空（null）
         */
        //效验数据
        ValidatorUtils.validateEntity(dto);
        //dto.setEndtime(DateUtils.parse(dto.getEndtime(),DateUtils.DATE_TIME_PATTERN));
        couponService.save(dto);
        return new Result();
    }
}
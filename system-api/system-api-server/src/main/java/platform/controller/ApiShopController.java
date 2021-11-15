package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.dto.ShopInfoDTO;
import platform.entity.MSShopEntity;
import platform.service.MSShopService;
import utils.Result;

import java.util.List;

/**
 * 获取商品列表
 * （现在主要获取秒杀和抢购商品列表）
 * 用户无需登录
 */
@RestController
@RequestMapping("api")
public class ApiShopController {

    @Autowired
    private MSShopService msShopService;

    /**
     * 获取秒杀商品列表
     */
    @GetMapping("spike")
    public Result shop_spike( ){
        List<MSShopEntity> list = msShopService.getByShop();
        return new Result().ok(list);
    }

    /**
     * 获取商品详细信息（秒杀和抢购通用）
     */
    @GetMapping("{id}")
    public Result<ShopInfoDTO> shop_info(@PathVariable("id") Long id ){
        ShopInfoDTO info = msShopService.getShopByInfo(id);
        return new Result<ShopInfoDTO>().ok(info);
    }
}

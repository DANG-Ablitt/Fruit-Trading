package platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.dto.ShopInfoDTO;
import utils.Result;

/**
 * 获取商品列表
 * （现在主要获取秒杀和抢购商品列表）
 * 用户无需登录
 */
@RestController
@RequestMapping("api")
public class ApiShopController {

    /**
     * 获取秒杀商品列表
     */
    @GetMapping("spike")
    public Result shop_spike( ){

        return new Result();
    }

    /**
     * 获取商品详细信息（秒杀和抢购通用）
     */
    @GetMapping("{id}")
    public Result<ShopInfoDTO> shop_info(@PathVariable("id") Long id ){

        return new Result<ShopInfoDTO>();
    }
}

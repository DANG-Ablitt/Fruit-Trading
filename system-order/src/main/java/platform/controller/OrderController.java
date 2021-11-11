package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import platform.dto.ShopInfoDTO;
import platform.service.OrderService;
import utils.Result;
import validator.ValidatorUtils;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 生成订单
     */
    @PostMapping("save")
    public Result save(@RequestBody ShopInfoDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto);
        //生成订单
        orderService.confirmOrder(dto);
        return null;
    }
}

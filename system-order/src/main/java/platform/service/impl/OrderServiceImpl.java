package platform.service.impl;

import mybatis_plus.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import platform.dao.OrderDao;
import platform.dto.ShopInfoDTO;
import platform.entity.OrderEntity;
import platform.feign.ShopFeignClient;
import platform.feign.UserFeignClient;
import platform.service.OrderService;
import utils.Result;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderDao, OrderEntity> implements OrderService {
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private ShopFeignClient shopFeignClient;
    //存放对应商品的信息
    Result shop_result;
    @Override
    public Result confirmOrder(ShopInfoDTO dto) {
        //1.效验订单参数
        if(!checkOrder(dto)){
            //参数效验失败，执行返回操作
            return new Result();
        }
        //获取商品详细数据
        Map<String,String> shop_info= (Map<String, String>) shop_result.getData();
        //2.生成订单
        OrderEntity orderEntity=new OrderEntity();
        //记录用户id
        orderEntity.setUserId(dto.getUser_id());
        //记录商品id
        orderEntity.setShopId(dto.getShop_id());
        //订单状态为初始化值（1：订单待支付）
        orderEntity.setOrderStatus(1);
        //取货门店
        //商品价格（实付价格）
        //orderEntity.setGoodsPrice(shop_info.get("amount1"));
        //商品数量（强制为1）
        orderEntity.setGoodsAmount(1);
        //取货码
        //当前时间
        Date now =new Date();
        //订单生成时间（当前时间）
        orderEntity.setNowDate(now);
        //修改时间（初始化为当前时间）
        orderEntity.setUpdateDate(now);
        //保存到数据库
        this.insert(orderEntity);
        return new Result();
    }

    @Override
    public Result cancelOrder(Long shop_id) {
        return null;
    }

    @Override
    public Result malOrder(Long shop_id) {
        return null;
    }

    /**
     * 效验订单参数
     */
    public Boolean checkOrder(ShopInfoDTO dto) {
        //1.校验订单商品是否存在
        //参数封装
        Map<String,String> params=new HashMap<>();
        params.put("shopId",dto.getShop_id().toString());
        shop_result=shopFeignClient.coupon_info_id(params);
        //验证是否正确返回数据
        if(shop_result.getCode()==0&&shop_result.getMsg().equals("success")){
            if(shop_result.getData()==null){
                return false;
            }
        }
        //2.校验下单用户是否存在
        //参数封装
        Map<String,String> params1=new HashMap<>();
        params1.put("user_id",dto.getUser_id().toString());
        Result user_result=userFeignClient.info_user_id(params1);
        //验证是否正确返回数据
        if(user_result.getCode()==0&&user_result.getMsg().equals("success")){
            if(user_result.getData()==null){
                return false;
            }
        }
        return true;
    }

}

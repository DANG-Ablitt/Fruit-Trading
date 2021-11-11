package platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import constant.Constant;
import mybatis_plus.enums.DelFlagEnum;
import mybatis_plus.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import page.PageData;
import platform.dao.CouponDao;
import platform.dto.*;
import platform.entity.CouponEntity;
import platform.feign.RabbitMQFeignClient;
import platform.service.CouponService;
import security.user.SecurityStaff;
import security.user.StaffDetail;
import utils.ConvertUtils;
import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static utils.DateUtils.DATE_TIME_PATTERN;

/**
 * 优惠卷管理
 */
@Service
public class CouponServiceImpl extends BaseServiceImpl<CouponDao, CouponEntity> implements CouponService {

    @Resource(name = "twoRedisTemplate")
    private RedisTemplate<String, Object> twoRedisTemplate;
    /**
     * 时间截
     */
    // 抢购开始时间
    Long start_time_L;
    // 抢购结束时间
    Long end_time_L;
    // 抢购结果公布时间
    Long time_L;
    // 秒杀开始时间
    Long start_time_M;

    //12小时
    private static final Long START_TIME_1=1000*60*60*12*1L;
    //6小时
    private static final Long START_TIME_2=1000*60*60*6*1L;

    //消息中间件
    @Autowired
    private RabbitMQFeignClient rabbitMQFeignClient;


    @Override
    public PageData<CouponReturnDTO> page(Map<String, Object> params) {
        IPage<CouponEntity> page = baseDao.selectPage(
            getPage(params, Constant.CREATE_DATE, false),
            getWrapper(params)
        );
        return getPageData(page, CouponReturnDTO.class);
    }


    @Override
    public void save(PmsCouponDTO dto) {
        //常规数据转换
        CouponEntity entity = ConvertUtils.sourceToTarget(dto, CouponEntity.class);
        Gson gson = new Gson();
        List<ShopDetailDTO> persons = new ArrayList<ShopDetailDTO>(dto.getDetailList());
        String str = gson.toJson(persons);
        entity.setDetail(str);
        //部门
        StaffDetail staff = SecurityStaff.getStaff();
        entity.setDeptId(staff.getDeptId());
        //设置商品当前状态
        entity.setMemberlevel(0);
        // 如果当前商品为抢购商品
        if(dto.getType()==0){
            // 秒杀时间为空
            entity.setTime2("");
            SimpleDateFormat df1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
            //手动处理无法自动转换的参数
            //将前端传入的time0数组转换成json方便写入数据库
            //开始时间
            String start_time_d;
            Date start_d=transferDateFormat(dto.getTime0().get(0));
            start_time_d=df1.format(start_d);
            // 结束时间
            String end_time_d;
            Date end_d=transferDateFormat(dto.getTime0().get(1));
            end_time_d=df1.format(end_d);
            // 公告时间
            String time_d;
            Date _d=transferDateFormat(dto.getTime1());
            time_d=df1.format(_d);
            //记录时间截
            start_time_L=start_d.getTime();
            end_time_L=end_d.getTime();
            time_L=_d.getTime();
            //生成 json
            Map<String,String> map = new HashMap<String,String>();
            map.put("start_time",start_time_d);
            map.put("end_time",end_time_d);
            String json= JSON.toJSONString(map);
            //存放 json
            entity.setTime0(json);
            // 存放公告时间
            entity.setTime1(time_d);
        }else {
            // 抢购开始和结束时间为空
            entity.setTime0("");
            // 抢购通知时间为空
            entity.setTime1("");
            // 处理秒杀时间
            SimpleDateFormat df1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
            // 开始时间
            String start_time_d;
            Date start_d=transferDateFormat(dto.getTime2());
            start_time_d=df1.format(start_d);
            // 记录时间截
            start_time_M=start_d.getTime();
            // 存放秒杀时间
            entity.setTime2(start_time_d);
        }
        //将商品信息写入数据库
        insert(entity);
        // 获取商品 ID
        Long shop_id = entity.getId();
        // 秒杀的预处理
        /**
         * 秒杀服务专用
         * 分布式锁：商品id+"_lock"
         * 商品库存：商品id+"_count"
         * 商品状态：商品id+"_info"
         * 用户集合：商品id+"_list"
         * 用户标识：商品id
         * 接口重复调用：商品id+token
         */
        if( dto.getType()==1 ){
            // 如果距离秒杀开始时间小于12小时，直接将秒杀数据写入redis
            // 获取当前时间
            Date now = new Date();
            // 时间截对比（不允许距离秒杀开始时间小于6小时的产品进入秒杀服务）
            if(start_time_M-now.getTime()>START_TIME_2&&
                    start_time_M-now.getTime() < START_TIME_1){
                // 商品状态
                twoRedisTemplate.opsForValue().set(Long.toString(shop_id)+"_info",0);
                // 商品库存
                twoRedisTemplate.opsForValue().set(Long.toString(shop_id)+"_count",dto.getCount());
                // 用户集合
                twoRedisTemplate.opsForSet().add(Long.toString(shop_id)+"_list","");
                // 消息中间件：完成启动定时任务 在秒杀服务开始时将状态标记开启
                Map<String,Object> params = new HashMap<String,Object>();
                // 发送标志
                params.put("flag",0);
                // 秒杀商品 id
                params.put("shop_id",shop_id);
                // 秒杀商品开始时间
                params.put("start_time",start_time_M);
                // 与消息中间件通信
                rabbitMQFeignClient.ShopCouponsend(params);
            }else{
                //消息中间件完成以下工作
                //（1）如果距离秒杀开始时间不小于12小时，由定时任务将秒杀数据写入redis
                //（2）启动定时任务 在秒杀服务开始时将状态标记开启
                //（3）启动定时任务 在秒杀服务结束后出具统计信息
                Map<String,Object> params = new HashMap<String,Object>();
                // 发送标志
                params.put("flag",1);
                // 秒杀商品 id
                params.put("shop_id",shop_id);
                // 秒杀商品开始时间
                params.put("start_time",start_time_M);
                // 秒杀商品库存
                params.put("shop_count",dto.getCount());
                // 与消息中间件通信
                rabbitMQFeignClient.ShopCouponsend(params);
            }
        }
        // 抢购的预处理
        if( dto.getType()==0 ){

        }
    }

    @Override
    public CouponEntity getShopByShopId(Long shopId) {
        return baseDao.getShopByShopId(shopId);
    }

    @Override
    public ReturnInfoDTO get(Long id) {
        CouponEntity entity = baseDao.getById(id);
        ReturnInfoDTO dto = ConvertUtils.sourceToTarget(entity, ReturnInfoDTO.class);
        return dto;
    }

    private QueryWrapper<CouponEntity> getWrapper(Map<String, Object> params){
        //String paramCode = (String) params.get("paramCode");
        QueryWrapper<CouponEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(Constant.DEL_FLAG, DelFlagEnum.NORMAL.value());
        //wrapper.eq("param_type", 1);
        //wrapper.like(StringUtils.isNotBlank(paramCode), "param_code", paramCode);
        //普通管理员，只能查询所属部门及子部门的数据（数据权限）
//        StaffDetail user = SecurityStaff.getStaff();
//        if(user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
//            List<Long> deptIdList = sysDeptService.getSubDeptIdList(user.getDeptId());
//            wrapper.in(deptIdList != null, "dept_id", deptIdList);
//        }
        return wrapper;
    }

    /**
     * 将2019-06-03T16:00:00.000Z日期格式转换为2019-06-03 16:00:00格式
     */
    public static Date transferDateFormat(String oldDateStr) {
        if (StringUtils.isBlank(oldDateStr)){
            return null;
        }
        Date date1 = null;
        String dateStr = null;
        try {
            dateStr = oldDateStr.replace("Z", " UTC");//是空格+UTC
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            date1 = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }
}
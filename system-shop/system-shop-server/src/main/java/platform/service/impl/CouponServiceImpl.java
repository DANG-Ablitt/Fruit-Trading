package platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import constant.Constant;
import mybatis_plus.enums.DelFlagEnum;
import mybatis_plus.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import page.PageData;
import platform.dao.CouponDao;
import platform.dto.CouponReturnDTO;
import platform.dto.PmsCouponDTO;
import platform.dto.SysParamsDTO;
import platform.entity.CouponEntity;
import platform.service.CouponService;
import utils.ConvertUtils;
import utils.DateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 优惠卷管理
 */
@Service
public class CouponServiceImpl extends BaseServiceImpl<CouponDao, CouponEntity> implements CouponService {
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
        //设置商品当前状态
        entity.setMemberlevel(0);
        //手动处理无法自动转换的参数
        //将前端传入的time0数组转换成json方便写入数据库
        Map<String,String> map = new HashMap<String,String>();
        map.put("start_time",dto.getTime0()[0]);
        map.put("end_time",dto.getTime0()[1]);
        String json= JSON.toJSONString(map);
        entity.setTime0(json);
        //将商品信息写入数据库
        insert(entity);
        //判断优惠类型是预购还是秒杀
        //预购：0  秒杀：1
        if(dto.getType()==0){
            //获取开始时间
            String start_time=dto.getTime0()[0];
            //获取结束时间
            String end_time=dto.getTime0()[1];
            //获取通知时间
            String time=dto.getTime1();
            //通过消息队列发送短信通知用户

            //启动定时任务 在预购服务开始前15分钟将数据载入redis集群
            //封装用于定时任务的参数（JSON）
            Map<String,Object> map1 = new HashMap<String,Object>();
            map1.put("count",dto.getCount());
            map1.put("type",dto.getType());
            String json1= JSON.toJSONString(map1);
            //使用start_time生成表达式

            //启动定时任务 在预购服务开始时将状态标记开启

            //启动定时任务 在预购服务结束时将状态标记关闭

        }else{
            //获取秒杀开始时间
            String start_time=dto.getTime2();
            /**
             * 通过消息队列发送短信通知用户
             */
            //构建消息队列

            //


            //启动定时任务 在秒杀服务开始前15分钟将数据载入redis集群

            //启动定时任务 在秒杀服务开始时将状态标记开启

            //启动定时任务 在秒杀服务结束后出具统计信息
        }
    }

    private QueryWrapper<CouponEntity> getWrapper(Map<String, Object> params){
        //String paramCode = (String) params.get("paramCode");
        QueryWrapper<CouponEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(Constant.DEL_FLAG, DelFlagEnum.NORMAL.value());
        //wrapper.eq("param_type", 1);
        //wrapper.like(StringUtils.isNotBlank(paramCode), "param_code", paramCode);
        return wrapper;
    }
}
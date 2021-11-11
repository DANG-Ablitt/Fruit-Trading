package platform.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 返回商品详细信息
 */
@Data
public class ReturnInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 产品品牌
     */
    private String pic;
    /**
     * 抢购商品预约时间段（json）
     */
    private String time0;
    /**
     * 抢购商品结果发布时间
     */
    private String time1;
    /**
     * 秒杀商品开始时间
     */
    private String time2;
    /**
     * 图片url
     */
    private String url;
    /**
     * 详细参数（json）
     */
    private String detail;

}

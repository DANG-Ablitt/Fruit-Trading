package platform.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 产品品牌
     */
    private String pic;
    /**
     * 详细参数（json）
     */
    private String detail;
}

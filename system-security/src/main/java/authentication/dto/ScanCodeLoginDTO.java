package authentication.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 扫码登录
 */
@Data
public class ScanCodeLoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 二维码id
     */
    private String QRcodeid;
    /**
     * 二维码状态（具体定义在枚举中）
     * 1：已确认
     * 0：待确认
     * 2：无响应
     */
    private Integer QRcodeStatus;
}

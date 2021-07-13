package platform.dto;

import lombok.Data;

/**
 * 上传信息
 */
@Data
public class UploadDTO {
    /**
     * 文件URL
     */
    private String url;
    /**
     * 文件大小，单位字节
     */
    private Long size;
}

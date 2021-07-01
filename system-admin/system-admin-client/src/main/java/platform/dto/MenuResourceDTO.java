package platform.dto;

import lombok.Data;
/**
 * 菜单资源
 */
@Data
public class MenuResourceDTO {
    /**
     * 资源URL
     */
    private String resourceUrl;
    /**
     * 请求方式（如：GET、POST、PUT、DELETE）
     */
    private String resourceMethod;

}

package platform.cloud;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 云存储配置信息
 */
@Data
public class CloudStorageConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类型 1：Minio（未来会支持其他云盘）
     */
    private Integer type;

    private String minio_url;

    private String minio_name;

    private String minio_pass;

    private String minio_bucketName;

}

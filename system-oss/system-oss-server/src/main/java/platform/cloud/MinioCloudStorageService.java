package platform.cloud;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class MinioCloudStorageService extends AbstractCloudStorageService {

    public MinioCloudStorageService(CloudStorageConfig config) {
        this.config=config;
    }

    @Override
    public String upload(byte[] data, String path) throws IOException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, ServerException, InvalidKeyException, InvalidBucketNameException {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException, InvalidBucketNameException {
        return upload(data, null);
    }

    @Override
    public String upload(InputStream inputStream, String path) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException, InvalidBucketNameException {
        MinioClient minioClient = MinioClient.builder().endpoint(config.getMinio_url()).credentials(config.getMinio_name(), config.getMinio_pass()).build();
        /**
         * io.minio.errors.ErrorResponseException: The specified bucket does not exist
         * 指定的桶是不存在的,要么你把桶名写错了，要么没有创建桶
         * io.minio.errors.ErrorResponseException: The difference between the request time and the server's time is too large.
         * 请求时间和服务器的时间之间的差异过大(解决方案：https://www.jianshu.com/p/6ccfffd4402f)
         */
        //生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(config.getMinio_bucketName())
                .object(uuid)
                .stream(inputStream, inputStream.available(), -1)
                .build();
        minioClient.putObject(putObjectArgs);
        inputStream.close();
        return config.getMinio_url()+ "/" + config.getMinio_bucketName()+"/"+uuid;
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InternalException, InvalidBucketNameException {
        return upload(inputStream, getPath("img", suffix));
    }
}

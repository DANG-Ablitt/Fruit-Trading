package platform.controller;

import annotation.LogOperation;
import com.google.gson.Gson;
import page.PageData;
import platform.cloud.CloudStorageConfig;
import platform.cloud.OssFactory;
import platform.dto.UploadDTO;
import platform.entity.OssEntity;
import platform.enums.OssTypeEnum;
import platform.exception.ModuleErrorCode;
import platform.service.OssService;
import platform.utils.ModuleConstant;
import platform.remote.ParamsRemoteService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utils.Result;
import validator.ValidatorUtils;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 文件上传
 */
@RestController
@RequestMapping("file")
public class OssController {
	@Autowired
	private OssService ossService;
    @Autowired
    private ParamsRemoteService paramsRemoteService;

    private final static String KEY = ModuleConstant.CLOUD_STORAGE_CONFIG_KEY;

	/**
	 * 分页
	 */
	@GetMapping("page")
	public Result<PageData<OssEntity>> page(@RequestParam Map<String, Object> params){
		PageData<OssEntity> page = ossService.page(params);

		return new Result<PageData<OssEntity>>().ok(page);
	}

	/**
	 * 云存储配置信息
	 */
    @GetMapping("info")
    public Result<CloudStorageConfig> info(){
        CloudStorageConfig config = paramsRemoteService.getValueObject(KEY, CloudStorageConfig.class);
        return new Result<CloudStorageConfig>().ok(config);
    }

	/**
	 * 保存云存储配置信息
	 */
	@PostMapping
	@LogOperation("保存云存储配置信息")
	public Result saveConfig(@RequestBody CloudStorageConfig config){
		//校验类型
		ValidatorUtils.validateEntity(config);
		if(config.getType() == OssTypeEnum.MINIO.value()){
			//校验Minio数据
			ValidatorUtils.validateEntity(config);
		}
		paramsRemoteService.updateValueByCode(KEY, new Gson().toJson(config));
		return new Result();
	}

	/**
	 * 上传文件
	 */
	@PostMapping("upload")
	public Result<UploadDTO> upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			return new Result<UploadDTO>().error(ModuleErrorCode.UPLOAD_FILE_EMPTY);
		}
		//上传文件
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		String url = OssFactory.build().uploadSuffix(file.getBytes(), extension);
		//保存文件信息
		OssEntity ossEntity = new OssEntity();
		ossEntity.setUrl(url);
		ossEntity.setCreateDate(new Date());
		ossService.insert(ossEntity);

		//文件信息
		UploadDTO dto = new UploadDTO();
		dto.setUrl(url);
		dto.setSize(file.getSize());

		return new Result<UploadDTO>().ok(dto);
	}

	/**
	 * 删除
	 */
	@DeleteMapping
	@LogOperation("删除")
	public Result delete(@RequestBody Long[] ids){
		ossService.deleteBatchIds(Arrays.asList(ids));
		return new Result();
	}

}
package platform.exception;


import exception.ErrorCode;

/**
 * 模块错误编码，由9位数字组成，前6位为模块编码，后3位为业务编码
 * <p>
 * 如：100001001（100001代表模块，001代表业务代码）
 * </p>
 *
 */
public interface ModuleErrorCode extends ErrorCode {
    int JOB_ERROR = 100002001;
}

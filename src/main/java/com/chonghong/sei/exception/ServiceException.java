package com.chonghong.sei.exception;

/**
 * <strong>实现功能:</strong>.
 * <p>服务层异常</p>
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2017/2/8 10:20
 */
public class ServiceException extends BaseRuntimeException {

    private static final long serialVersionUID = -344443052461115514L;

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

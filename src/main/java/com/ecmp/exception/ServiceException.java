package com.ecmp.exception;

/**
 * *************************************************************************************************
 * <p>
 * 实现功能：
 * 服务层异常
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/2/8 10:20      马超(Vision)                新建
 * <p>
 * *************************************************************************************************
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

package com.ecmp.exception;

/**
 * *************************************************************************************************
 * <p>
 * 实现功能：
 * 业务逻辑校验异常，此类异常不会进行常规的logger.error记录，一般只在前端显示提示用户
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/2/8 10:20      马超(Vision)                新建
 * <p>
 * *************************************************************************************************
 */
public class ValidationException extends BaseRuntimeException {

    private static final long serialVersionUID = -1613416718940821955L;

    public ValidationException(String errorCode, String message) {
        super(errorCode, message);
    }

    public ValidationException(String message) {
        super(message);
    }
}

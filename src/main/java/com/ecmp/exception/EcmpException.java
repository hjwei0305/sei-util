package com.ecmp.exception;

/**
 * *************************************************************************************************
 * <p>
 * 实现功能：
 * 重复提交异常
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/2/8 10:20      马超(Vision)                新建
 * <p>
 * *************************************************************************************************
 */
public class EcmpException extends BaseRuntimeException {

    private static final long serialVersionUID = -2896111728089420354L;

    public EcmpException(String msg) {
        super(msg);
    }

    public EcmpException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

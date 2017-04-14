package com.ecmp.exception;

/**
 * *************************************************************************************************
 * <p>
 * 实现功能：
 * 数据访问权限异常
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/2/8 10:20      马超(Vision)                新建
 * <p>
 * *************************************************************************************************
 */
public class DataAccessDeniedException extends BaseRuntimeException {

    private static final long serialVersionUID = 4060301730710805767L;

    public DataAccessDeniedException() {
        super("无权数据访问");
    }

    public DataAccessDeniedException(String msg) {
        super(msg);
    }

    public DataAccessDeniedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

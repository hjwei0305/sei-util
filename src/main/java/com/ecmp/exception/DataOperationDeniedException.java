package com.ecmp.exception;

/**
 * *************************************************************************************************
 * <p>
 * 实现功能：
 * 数据操作异常
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/2/8 10:20      马超(Vision)                新建
 * <p>
 * *************************************************************************************************
 */
public class DataOperationDeniedException extends BaseRuntimeException {

    private static final long serialVersionUID = 4152774583665523431L;

    public DataOperationDeniedException() {
        super("无效数据操作");
    }

    public DataOperationDeniedException(String msg) {
        super(msg);
    }

    public DataOperationDeniedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

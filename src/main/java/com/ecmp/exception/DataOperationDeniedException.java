package com.ecmp.exception;

/**
 * <strong>实现功能:</strong>.
 * <p>数据操作异常</p>
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2017/2/8 10:20
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

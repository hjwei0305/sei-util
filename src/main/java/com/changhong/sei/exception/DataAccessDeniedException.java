package com.changhong.sei.exception;

/**
 * <strong>实现功能:</strong>.
 * <p>数据访问权限异常</p>
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2017/2/8 10:20
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

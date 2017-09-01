package com.ecmp.exception;

/**
 * <strong>实现功能:</strong>.
 * <p>重复提交异常</p>
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2017/2/8 10:20
 */
public class DuplicateTokenException extends BaseRuntimeException {

    private static final long serialVersionUID = -2896111728089420354L;

    public DuplicateTokenException(String msg) {
        super(msg);
    }

    public DuplicateTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

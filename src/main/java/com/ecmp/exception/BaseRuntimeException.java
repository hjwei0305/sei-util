package com.ecmp.exception;

/**
 * <strong>实现功能:</strong>.
 * <p>运行期异常</p>
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2017/2/8 10:20
 */
public abstract class BaseRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -23347847086757165L;

    private String errorCode;

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public String getErrorCode() {
        return errorCode;
    }
}

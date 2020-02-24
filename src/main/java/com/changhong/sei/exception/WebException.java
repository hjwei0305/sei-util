package com.changhong.sei.exception;

/**
 * <strong>实现功能:</strong>.
 * <p>web层异常</p>
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2017/2/8 10:20
 */
public class WebException extends BaseRuntimeException {

    private static final long serialVersionUID = 8235846269469200660L;

    public WebException(String msg) {
        super(msg);
    }

    public WebException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

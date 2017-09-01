package com.ecmp.exception;

/**
 * <strong>实现功能:</strong>.
 * <p>ECMP平台异常基类</p>
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2017/2/8 10:20
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

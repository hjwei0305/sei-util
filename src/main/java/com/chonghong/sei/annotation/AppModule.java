package com.chonghong.sei.annotation;

import java.lang.annotation.*;

/**
 * <strong>实现功能:</strong>.
 * <p>该注解用于API接口类上，以此获取对应应用模块的接口基地址</p>
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2017/5/15 10:59
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PACKAGE})
public @interface AppModule {
    /**
     * @return 系统应用模块代码
     */
    String value();
}

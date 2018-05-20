package com.ecmp.annotation;

import java.lang.annotation.*;

/**
 * 平台发布WebService服务相对地址注解
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2018/5/20 23:30
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface WebServicePath {
    String path();
}

package com.ecmp.annotation;

import java.lang.annotation.*;

/**
 * 忽略登录认证.
 * 该注解用于一些不许进行登录认证的方法或类上
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2018/3/13 19:35
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface IgnoreAuthentication {
    boolean value() default true;
}

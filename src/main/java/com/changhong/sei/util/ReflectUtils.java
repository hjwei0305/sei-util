package com.changhong.sei.util;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:反射帮助类
 * @Author: chenzhiquan
 * @Date: 2021/8/31.
 */
public class ReflectUtils {

    /**
     * 写入属性值
     *
     * @param object    实体类
     * @param fieldName 属性名称
     * @return 属性值
     */
    public static void setProperty(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param object    实体类
     * @param fieldName 属性名称
     * @return 属性值
     */
    public static Object getProperty(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
}

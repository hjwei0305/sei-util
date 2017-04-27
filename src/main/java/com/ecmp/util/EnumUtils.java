package com.ecmp.util;

import com.ecmp.annotation.MetaData;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * *************************************************************************************************<br>
 * <br>
 * 实现功能：<br>
 * <br>
 * ------------------------------------------------------------------------------------------------<br>
 * 版本          变更时间             变更人                     变更原因<br>
 * ------------------------------------------------------------------------------------------------<br>
 * 1.0.00      2017/4/27 13:41      马超(Vision.Mac)                新建
 * <br>
 * *************************************************************************************************
 */
@SuppressWarnings("unchecked")
public class EnumUtils {
    private static Map<Class<?>, Map<Integer, String>> enumDatasContainer = new HashMap<Class<?>, Map<Integer, String>>();

    /**
     * 基于Enum类返回对应的key-value Map构建对象
     */
    public static Map<Integer, String> getEnumDataMap(Class<? extends Enum> enumClass) {
        Map<Integer, String> enumDataMap = null;
        if (enumClass != null) {
            enumDataMap = enumDatasContainer.get(enumClass);
            if (enumDataMap != null) {
                return enumDataMap;
            }
            enumDataMap = new LinkedHashMap<Integer, String>();
            Field[] fields = enumClass.getFields();
            for (Field field : fields) {
                String name = field.getName();
                String label = name;
                MetaData entityComment = field.getAnnotation(MetaData.class);
                if (entityComment != null) {
                    label = entityComment.value();
                }
                enumDataMap.put(Enum.valueOf(enumClass, name).ordinal(), label);
            }
            enumDatasContainer.put(enumClass, enumDataMap);
        }
        return enumDataMap;
    }

    /**
     * 根据指定的枚举下标获取枚举名称
     * 若有@MetaData注解将获取@MetaData#value
     *
     * @param enumClass 枚举类
     * @param key       枚举下标
     * @return 返回枚举下标对应的描述或名称
     */
    public static String getEnumDataName(Class<? extends Enum> enumClass, int key) {
        Map<Integer, String> enumDataMap = getEnumDataMap(enumClass);
        return enumDataMap != null ? enumDataMap.get(key) : null;
    }
}

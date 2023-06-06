package com.changhong.sei.util;

import java.math.BigDecimal;

/**
 * @ClassName NumberUtils
 * @Description 数值处理工具类
 * @Author p09835
 * @Date 2022/5/26 8:53
 **/
public class NumberUtils {




    /**
     * 获取BigDecimal的值，如果为null则返回0
     * @param bigDecimal
     * @return
     */
    public static BigDecimal getBigDecimalValue(BigDecimal bigDecimal){
        if (bigDecimal == null){
            return BigDecimal.ZERO;
        }
        return bigDecimal;
    }

    /**
     * 获取BigDecimal的值，如果为null则返回默认值
     * @param bigDecimal
     * @param defaultValue
     * @return
     */
    public static BigDecimal getBigDecimalValueAndDefault(BigDecimal bigDecimal,BigDecimal defaultValue){
        if (bigDecimal == null){
            return defaultValue;
        }
        return bigDecimal;
    }

    /**
     * 两个BigDecimal相减
     * @param b1
     * @param b2
     * @return
     */
    public static BigDecimal subtractBigDecimal(BigDecimal b1 , BigDecimal b2){
        if (b1 == null){
            b1 = BigDecimal.ZERO;
        }
        if (b2 == null){
            b2 = BigDecimal.ZERO;
        }
        return b1.subtract(b2);
    }


    /**
     * 两个BigDecimal相加
     * @param b1
     * @param b2
     * @return
     */
    public static BigDecimal addBigDecimal(BigDecimal b1 , BigDecimal b2){
        if (b1 == null){
            b1 = BigDecimal.ZERO;
        }
        if (b2 == null){
            b2 = BigDecimal.ZERO;
        }
        return b1.add(b2);
    }

}

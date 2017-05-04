package com.ecmp.util;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import java.security.SecureRandom;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：
 * 封装各种生成唯一性ID算法的工具类.
 * 1.生产UUID
 * 2.生产随机数
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间             变更人                     变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00      2017/5/4 14:07      马超(Vision.Mac)                新建
 * <br>
 * *************************************************************************************************
 */
public class IdGenerator {

    private static SecureRandom random = new SecureRandom();

    // different ProcessEngines on the same classloader share one generator.
    private static TimeBasedGenerator timeBasedGenerator;

    private static void ensureGeneratorInitialized() {
        if (timeBasedGenerator == null) {
            synchronized (IdGenerator.class) {
                if (timeBasedGenerator == null) {
                    timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
                }
            }
        }
    }

    /**
     * 封装fasterxml.uuid,生成有序的UUID, 中间有-分割.
     */
    public static String uuid() {
        ensureGeneratorInitialized();
        return timeBasedGenerator.generate().toString().toUpperCase();
    }

    /**
     * 封装fasterxml.uuid,生成有序的UUID, 中间无-分割.
     */
    public static String uuid2() {
        ensureGeneratorInitialized();
        return timeBasedGenerator.generate().toString().toUpperCase().replaceAll("-", "");
    }

    @Deprecated
    public static String activitiId() {
        return "sid" + uuid().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    public static void main(String[] args) {
        for(int i=1;i<100;i++) {
            System.out.println(IdGenerator.uuid());
//            System.out.println(IdGenerator.uuid2());
//            System.out.println(IdGenerator.randomLong());
//            System.out.println();
        }
    }
}

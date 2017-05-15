package com.ecmp.util;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * *************************************************************************************************
 * <br>
 * 实现功能：
 * <br>
 * 序列化工具类
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 版本          变更时间             变更人                     变更原因
 * <br>
 * ------------------------------------------------------------------------------------------------
 * <br>
 * 1.0.00      2017/5/11 15:12      马超(Vision.Mac)                新建
 * <br>
 * *************************************************************************************************
 */
@SuppressWarnings("unchecked")
public class SerializeUtils {
    /**
     * 对象序列化成字符串
     */
    public static String serialize4Str(Object object) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            return Base64.encodeBase64String(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("serialize object error", e);
        }
    }

    /**
     * 字符串反序列化为对象
     */
    public static <T> T unserialize4Str(String sessionStr) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decodeBase64(sessionStr));
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (T) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("unserialize object error", e);
        }
    }

    /**
     * 对象序列化为二进制
     */
    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }

        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            throw new RuntimeException("serialize object error", e);
        }
    }

    /**
     * 二进制反序列化为对象
     */
    public static Object unserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ByteArrayInputStream bais;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("unserialize object error", e);
        }
    }
}

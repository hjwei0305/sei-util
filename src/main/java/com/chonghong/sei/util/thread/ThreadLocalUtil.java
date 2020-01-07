package com.chonghong.sei.util.thread;

import java.util.Map;

/**
 * 实现功能：本地线程变量通用方法
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-01-07 13:17
 */
public final class ThreadLocalUtil {
    /**
     * 需跨应用传递参数在header信息中的前缀
     */
    public static final String TRAN_PREFIX = "_tran_";

    /**
     * 是否有效
     */
    public static boolean isAvailable() {
        return ThreadLocalHolder.LOCAL_VAR.get() != null;
    }

    /**
     * 读本地线程变量
     */
    public static <T> T getLocalVar(String key) {
        return get(ThreadLocalHolder.LOCAL_VAR, key);
    }

    /**
     * 写本地线程变量
     */
    public static void setLocalVar(String key, Object value) {
        set(ThreadLocalHolder.LOCAL_VAR, key, value);
    }

    /**
     * 删本地线程变量
     */
    public static void removeLocalVar(String key) {
        remove(ThreadLocalHolder.LOCAL_VAR, key);
    }

    /**
     * 读可传播的线程变量
     */
    public static String getTranVar(String key) {
        return get(ThreadLocalHolder.TRAN_VAR, key.toLowerCase());
    }

    /**
     * 写可传播的线程变量
     */
    public static void setTranVar(String key, String value) {
        set(ThreadLocalHolder.TRAN_VAR, key.toLowerCase(), value);
    }

    /**
     * 删可传播的线程变量
     */
    public static void removeTranVar(String key) {
        remove(ThreadLocalHolder.TRAN_VAR, key.toLowerCase());
    }

    @SuppressWarnings("unchecked")
    private static <T> T get(ThreadLocal<Map<String, Object>> var, String key) {
        Map<String, Object> map = var.get();
        if (map == null) {
            return null;
        }
        return (T) map.get(key);
    }

    private static void set(ThreadLocal<Map<String, Object>> var, String key, Object value) {
        Map<String, Object> map = var.get();
        if (map == null) {
            return;
        }
        map.put(key, value);
    }

    private static void remove(ThreadLocal<Map<String, Object>> var, String key) {
        Map<String, Object> map = var.get();
        if (map == null) {
            return;
        }
        map.remove(key);
    }
}

package com.changhong.sei.util;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-09-15 08:53
 */
public class HashUtil {

    public static void main(String[] args) {
        System.out.println(md5("test"));
    }

    //获得字符串的md5值
    public static String md5(String input) {
        return md5(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String md5(byte[] input) {
        // 生成摘要数组
        byte[] digest = md5AsBytes(input);
        return toHexString(digest);
    }

    public static byte[] md5AsBytes(String input) {
        return md5AsBytes(input.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] md5AsBytes(byte[] input) {
        MessageDigest md;
        try {
            // 初始化摘要对象
            md = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        // 更新摘要数据
        md.update(input);
        // 生成摘要数组
        return md.digest();
    }

    /**
     * Generate SHA-1 as hex string (all lower-case).
     *
     * @param input Input as string.
     * @return Hex string.
     */
    public static String sha1(String input) {
        return sha1(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate SHA-1 as hex string (all lower-case).
     *
     * @param input Input as bytes.
     * @return Hex string.
     */
    public static String sha1(byte[] input) {
        byte[] digest = sha1AsBytes(input);
        return toHexString(digest);
    }

    public static byte[] sha1AsBytes(String input) {
        return sha1AsBytes(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate SHA-1 as bytes.
     *
     * @param input Input as bytes.
     * @return Bytes.
     */
    public static byte[] sha1AsBytes(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input);
        return md.digest();
    }

    /**
     * Generate SHA-256 as hex string (all lower-case).
     *
     * @param input Input as String.
     * @return Hex string.
     */
    public static String sha256(String input) {
        return sha256(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate SHA-256 as hex string (all lower-case).
     *
     * @param input Input as String.
     * @return Hex string.
     */
    public static byte[] sha256AsBytes(String input) {
        return sha256AsBytes(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate SHA-256 as hex string (all lower-case).
     *
     * @param input Input as bytes.
     * @return Hex string.
     */
    public static String sha256(byte[] input) {
        byte[] digest = sha256AsBytes(input);
        return toHexString(digest);
    }

    /**
     * Generate SHA-256 as bytes.
     *
     * @param input Input as bytes.
     * @return SHA bytes.
     */
    public static byte[] sha256AsBytes(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input);
        return md.digest();
    }

    /**
     * Generate SHA-512 as bytes.
     *
     * @param input Input as bytes.
     * @return SHA bytes.
     */
    public static byte[] sha512AsBytes(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input);
        return md.digest();
    }

    /**
     * Do HMAC-SHA256.
     *
     * @return Hex string.
     */
    public static byte[] hmacSha256AsBytes(byte[] data, byte[] key) {
        SecretKey skey = new SecretKeySpec(key, "HmacSHA256");
        Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(skey);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        mac.update(data);
        return mac.doFinal();
    }

    /**
     * Do HMAC-SHA256.
     *
     * @return Hex string.
     */
    public static String hmacSha256(byte[] data, byte[] key) {
        return toHexString(hmacSha256AsBytes(data, key));
    }

    /**
     * Do HMAC-SHA1.
     *
     * @return byte[] as result.
     */
    public static byte[] hmacSha1(byte[] data, byte[] key) {
        SecretKey skey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA1");
            mac.init(skey);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        mac.update(data);
        return mac.doFinal();
    }

    /**
     * Do HMAC-SHA256.
     *
     * @return byte[] as result.
     */
    public static String hmacSha256(String data, String key) {
        return hmacSha256(data.getBytes(StandardCharsets.UTF_8), key.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Do HMAC-SHA256.
     *
     * @return byte[] as result.
     */
    public static byte[] hmacSha256AsBytes(String data, String key) {
        return hmacSha256AsBytes(data.getBytes(StandardCharsets.UTF_8), key.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Do HMAC-SHA256.
     *
     * @return byte[] as result.
     */
    public static String hmacSha256(byte[] data, String key) {
        return hmacSha256(data, key.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将摘要字节数组转换为md5值
     */
    public static String toHexString(byte[] digest) {
        StringBuilder sb = new StringBuilder(digest.length * 2);
        int temp;
        for (byte b : digest) {
            //将数据转化为0到255之间的数据
            temp = b & 0xff;
            if (temp < 16) {
                sb.append(0);
            }
            //Integer.toHexString(temp)将10进制数字转换为16进制
            sb.append(Integer.toHexString(temp));
        }
        return sb.toString();
    }
}

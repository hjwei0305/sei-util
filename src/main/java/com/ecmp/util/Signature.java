package com.ecmp.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-07-05 15:20
 */
public class Signature {

    /**
     * 根据timestamp, appSecret计算签名值
     */
    private static String signature(String stringToSign, String appSecret) {
        String urlEncodeSignature;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(appSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signatureBytes = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            String signature = new String(Base64.encodeBase64(signatureBytes));
            urlEncodeSignature = urlEncode(signature);
        } catch (Exception e) {
            e.printStackTrace();
            urlEncodeSignature = StringUtils.EMPTY;
        }
        return urlEncodeSignature;
    }

    /**
     * encoding参数使用utf-8
     */
    private static String urlEncode(String value) {
        return urlEncode(value, StandardCharsets.UTF_8.name());
    }

    private static String urlEncode(String value, String encoding) {
        if (value == null) {
            return "";
        }
        try {
            String encoded = URLEncoder.encode(value, encoding);
            return encoded.replace("+", "%20").replace("*", "%2A")
                    .replace("~", "%7E").replace("/", "%2F");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("FailedToEncodeUri", e);
        }
    }
}

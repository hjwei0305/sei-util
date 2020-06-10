package com.changhong.sei.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 实现功能：敏感数据掩码显示
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-03-26 15:05
 */
public class MaskUtil {
    /**
     * 手机号显示首3末4位，中间用*号隐藏代替，如：188****5593
     */
    public static String maskMobile(String mobile) {
        if (StringUtils.isBlank(mobile) || mobile.length() <= 8) {
            return mobile;
        }

        return wordMask(mobile, 3, 4, "*");
    }

    /**
     * 电话号码显示区号及末4位，中间用*号隐藏代替，如：055****6666
     */
    public static String maskTelephone(String telephone) {
        if (StringUtils.isBlank(telephone)) {
            return telephone;
        }
        String result;
        if (telephone.length() > 8) {
            if (telephone.contains("-")) {
                String[] temp = telephone.split("-");
                result = temp[0] + "****" + temp[1].substring(temp[1].length() - 4, temp[1].length());
            } else {
                result = telephone.substring(0, 3) + "****" + telephone.substring(telephone.length() - 4, telephone.length());
            }
        } else {
            result = "****" + telephone.substring(telephone.length() - 4, telephone.length());
        }

        return result;
    }

    /**
     * 身份证号显示首6末4位，中间用4个*号隐藏代替，如：340121****3754
     */
    public static String maskIDCard(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            return idCard;
        }

        return wordMask(idCard, 3, 4, "*");
    }

    /**
     * 银行卡显示首6末4位，中间用4个*号隐藏代替，如：622202****4123
     */
    public static String maskBankCard(String cardNo) {
        if (StringUtils.isBlank(cardNo) || cardNo.length() < 10) {
            return cardNo;
        }

        return wordMask(cardNo, 6, 4, "*");
    }

    /**
     * 邮箱像是前两位及最后一位字符，及@后邮箱域名信息，如：ch****y@163.com
     */
    public static String maskEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return email;
        }
        String[] temp = email.split("@");

        return wordMask(temp[0], 2, 1, "*") + "@" + temp[1];
    }

    /**
     * 汉字掩码
     * <p>
     * 0-1字，如：用（用）
     * 2字，如：用于（*于）
     * 3-4字，如：用于掩（用*掩）、用于掩码（用**码）
     * 5-6字，如：用于掩码测（用于*码测）、用于掩码测试（用于**测试）
     * 大于6字，如：用于掩码测试的字符串（用于掩****字符串）
     */
    public static String maskName(String name) {
        int lenth = StringUtils.length(name);
        switch (lenth) {
            case 0:
            case 1:
                return name;
            case 2:
                return "*" + name.substring(1, 2);
            case 3:
            case 4:
                return wordMask(name, 1, 1, "*");
            case 5:
            case 6:
                return wordMask(name, 2, 2, "*");
            default:
                return wordMask(name, 3, 3, "*");
        }
    }

    /**
     * 全隐藏，如： ***
     */
    public static String maskAll(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }
        return "******";
    }

    /**
     * 对字符串进行脱敏处理
     *
     * @param word        被脱敏的字符
     * @param startLength 被保留的开始长度 前余n位
     * @param endLength   被保留的结束长度 后余n位
     * @param pad         填充字符
     */
    public static String wordMask(String word, int startLength, int endLength, String pad) {
        if (startLength + endLength > word.length()) {
            return StringUtils.leftPad("", word.length() - 1, pad);
        }

        String startStr = word.substring(0, startLength);

        String endStr = word.substring(word.length() - endLength, word.length());

        return startStr + StringUtils.leftPad("", word.length() - startLength - endLength, pad) + endStr;
    }

    public static void main(String[] args) {
        System.out.println(maskMobile("18888885593"));//188****5593
        System.out.println(maskTelephone("05516666666"));//055****6666
        System.out.println(maskIDCard("340121200006063754"));//340***********3754
        System.out.println(maskEmail("chaomc@163.com"));//ch***c@163.com
        System.out.println(maskName("用于掩码测试的字符串"));//用于掩****字符串
        System.out.println(maskName("用于掩码测试的字符"));//用于掩***的字符
        System.out.println(maskName("用于掩码测试的字"));//用于掩**试的字
        System.out.println(maskName("用于掩码测试的"));//用于掩*测试的
        System.out.println(maskName("用于掩码测试"));//用于**测试
        System.out.println(maskName("用于掩码测"));//用于*码测
        System.out.println(maskName("用于掩码"));//用**码
        System.out.println(maskName("用于掩"));//用*掩
        System.out.println(maskName("用于"));//*于
        System.out.println(maskName("用"));//用
        System.out.println(maskName(""));//
        System.out.println(maskAll("全部掩码"));//******
    }
}

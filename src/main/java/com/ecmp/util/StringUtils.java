package com.ecmp.util;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * *************************************************************************************************
 * <p>
 * 实现功能：
 * String 工具类
 * <p>
 * ------------------------------------------------------------------------------------------------
 * 版本          变更时间             变更人                     变更原因
 * ------------------------------------------------------------------------------------------------
 * 1.0.00      2017/03/06 21:33      马超(Vision)                新建
 * <p>
 * *************************************************************************************************
 */
public class StringUtils {

    /**
     * 下划线字符
     */
    public static final char UNDERLINE = '_';

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";

    /**
     * <p>
     * 判断字符串是否为空
     * </p>
     *
     * @param str 需要判断字符串
     * @return 判断结果
     */
    public static boolean isEmpty(String str) {
        return str == null || EMPTY_STRING.equals(str.trim());
    }

    /**
     * <p>
     * 判断字符串是否不为空
     * </p>
     *
     * @param str 需要判断字符串
     * @return 判断结果
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * <p>
     * 字符串驼峰转下划线格式
     * </p>
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String camelToUnderline(String param) {
        if (isEmpty(param)) {
            return EMPTY_STRING;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len * 2);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append(UNDERLINE);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * <p>
     * 字符串下划线转驼峰格式
     * </p>
     *
     * @param param 需要转换的字符串
     * @return 转换好的字符串
     */
    public static String underlineToCamel(String param) {
        if (isEmpty(param)) {
            return EMPTY_STRING;
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * <p>
     * 判断字符串是否为纯大写字母
     * </p>
     *
     * @param str 要匹配的字符串
     * @return
     */
    public static boolean isUpperCase(String str) {
        return match("^[A-Z]+$", str);
    }

    /**
     * <p>
     * 正则表达式匹配
     * </p>
     *
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * <p>
     * SQL 参数填充
     * </p>
     *
     * @param content 填充内容
     * @param args    填充参数
     * @return
     */
    public static String sqlArgsFill(String content, Object... args) {
        if (null == content) {
            return null;
        }
        if (args != null) {
            int length = args.length;
            if (length >= 1) {
                for (int i = 0; i < length; i++) {
                    if (args[i] instanceof Collection) {
                        args[i] = StringUtils.quotaMarkList((Collection<?>) args[i]);
                    } else {
                        args[i] = StringUtils.quotaMark(args[i]);
                    }
                }
                content = MessageFormat.format(content, args);
            }
        }
        return content;
    }

    /**
     * <p>
     * 使用单引号包含字符串
     * </p>
     *
     * @param obj 原字符串
     * @return 单引号包含的原字符串
     */
    public static String quotaMark(Object obj) {
        String srcStr = String.valueOf(obj);
        if (obj instanceof String) {
            // fix #79
            return escapeString(srcStr);
        }
        return srcStr;
    }

    /**
     * <p>
     * 使用单引号包含字符串
     * </p>
     *
     * @param coll 集合
     * @return 单引号包含的原字符串的集合形式
     */
    public static String quotaMarkList(Collection<?> coll) {
        StringBuilder sqlBuild = new StringBuilder();
        sqlBuild.append("(");
        int _size = coll.size();
        int i = 0;
        Iterator<?> iterator = coll.iterator();
        while (iterator.hasNext()) {
            String tempVal = StringUtils.quotaMark(iterator.next());
            if (i + 1 == _size) {
                sqlBuild.append(tempVal);
            } else {
                sqlBuild.append(tempVal);
                sqlBuild.append(",");
            }
            i++;
        }
        sqlBuild.append(")");
        return sqlBuild.toString();
    }

    /**
     * <p>
     * 拼接字符串第二个字符串第一个字母大写
     *
     * @param concatStr
     * @param str
     * @return
     */
    public static String concatCapitalize(String concatStr, final String str) {
        if (isEmpty(concatStr)) {
            concatStr = EMPTY_STRING;
        }
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        final char firstChar = str.charAt(0);
        if (Character.isTitleCase(firstChar)) {
            // already capitalized
            return str;
        }

        return new StringBuilder(strLen).append(concatStr).append(Character.toTitleCase(firstChar)).append(str.substring(1))
                .toString();
    }

    /**
     * <p>
     * 字符串第一个字母大写
     * </p>
     *
     * @param str
     * @return
     */
    public static String capitalize(final String str) {
        return concatCapitalize(null, str);
    }

    /**
     * 字符串是否需要转义
     *
     * @param x
     * @param stringLength
     * @return
     */
    private static boolean isEscapeNeededForString(String x, int stringLength) {

        boolean needsHexEscape = false;

        for (int i = 0; i < stringLength; ++i) {
            char c = x.charAt(i);

            switch (c) {
                case 0: /* Must be escaped for 'mysql' */

                    needsHexEscape = true;
                    break;

                case '\n': /* Must be escaped for logs */
                    needsHexEscape = true;

                    break;

                case '\r':
                    needsHexEscape = true;
                    break;

                case '\\':
                    needsHexEscape = true;

                    break;

                case '\'':
                    needsHexEscape = true;

                    break;

                case '"': /* Better safe than sorry */
                    needsHexEscape = true;

                    break;

                case '\032': /* This gives problems on Win32 */
                    needsHexEscape = true;
                    break;
            }

            if (needsHexEscape) {
                break; // no need to scan more
            }
        }
        return needsHexEscape;
    }

    /**
     * 转义字符串
     *
     * @param x
     * @return
     */
    public static String escapeString(String x) {

        if (x.matches("\'(.+)\'")) {
            x = x.substring(1, x.length() - 1);
        }

        String parameterAsString = x;
        int stringLength = x.length();
        if (isEscapeNeededForString(x, stringLength)) {

            StringBuilder buf = new StringBuilder((int) (x.length() * 1.1));

            //
            // Note: buf.append(char) is _faster_ than appending in blocks,
            // because the block append requires a System.arraycopy().... go
            // figure...
            //

            for (int i = 0; i < stringLength; ++i) {
                char c = x.charAt(i);

                switch (c) {
                    case 0: /* Must be escaped for 'mysql' */
                        buf.append('\\');
                        buf.append('0');

                        break;

                    case '\n': /* Must be escaped for logs */
                        buf.append('\\');
                        buf.append('n');

                        break;

                    case '\r':
                        buf.append('\\');
                        buf.append('r');

                        break;

                    case '\\':
                        buf.append('\\');
                        buf.append('\\');

                        break;

                    case '\'':
                        buf.append('\\');
                        buf.append('\'');

                        break;

                    case '"': /* Better safe than sorry */
                        buf.append('\\');
                        buf.append('"');

                        break;

                    case '\032': /* This gives problems on Win32 */
                        buf.append('\\');
                        buf.append('Z');

                        break;

                    default:
                        buf.append(c);
                }
            }

            parameterAsString = buf.toString();
        }
        return "\'" + parameterAsString + "\'";
    }
}

package com.changhong.sei.util;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

/**
 * <strong>实现功能:</strong>.
 * <p>类型转换工具栏</p>
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2017/4/27 13:47
 */
public final class ConverterUtils {

    private ConverterUtils() {
    }

    /**
     * 转换为Enum对象<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     *
     * @param clazz Enum的Class
     * @param value 值
     * @return Enum
     */
    public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object value) {
        return toEnum(clazz, value, null);
    }

    /**
     * 转换为Enum对象<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     *
     * @param clazz        Enum的Class
     * @param value        值
     * @param defaultValue 默认值
     * @return Enum
     */
    public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object value, E defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (clazz.isAssignableFrom(value.getClass())) {
            @SuppressWarnings("unchecked")
            E myE = (E) value;
            return myE;
        }
        final String valueStr = getAsString(value, null);
        if (StringUtils.isEmpty(valueStr)) {
            return defaultValue;
        }
        try {
            return Enum.valueOf(clazz, valueStr);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 转换为字符<br>
     * 如果给定的值为null，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static Character toChar(Object value, Character defaultValue) {
        if (null == value) {
            return defaultValue;
        }
        if (value instanceof Character) {
            return (Character) value;
        }

        final String valueStr = getAsString(value, null);
        return StringUtils.isBlank(valueStr) ? defaultValue : valueStr.charAt(0);
    }

    /**
     * 转换为字符<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Character toChar(Object value) {
        return toChar(value, null);
    }

    /**
     * Gets a String from a Object in a null-safe manner.
     * <p>
     * The String is obtained via <code>toString</code>.
     *
     * @param obj the object to use
     * @return the value of the Object as a String, <code>null</code> if null object input
     */
    public static String getAsString(final Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                return (String) obj;
            } else {
                return obj.toString();
            }
        }
        return null;
    }

    /**
     * Gets a String from a Object in a null-safe manner.
     * <p>
     * The String is obtained via <code>toString</code>.
     *
     * @param obj          the object to use
     * @param defaultValue what to return if the value is null or if the conversion fails
     * @return the value of the Object as a String, <code>defaultValue</code> if null object input
     */
    public static String getAsString(final Object obj, String defaultValue) {
        String answer = getAsString(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a Number from a Object in a null-safe manner.
     * <p>
     * If the value is a <code>Number</code> it is returned directly.
     * If the value is a <code>String</code> it is converted using
     * {@link NumberFormat#parse(String)} on the system default formatter
     * returning <code>null</code> if the conversion fails.
     * Otherwise, <code>null</code> is returned.
     *
     * @param obj the object to use
     * @return the value of the Object as a Number, <code>null</code> if null object input
     */
    public static Number getAsNumber(final Object obj) {
        if (obj != null) {
            if (obj instanceof Number) {
                return (Number) obj;
            } else if (obj instanceof Boolean) {
                Boolean flag = (Boolean) obj;
                if (Boolean.TRUE.equals(flag)) {
                    return 1;
                }
                return 0;
            } else if (obj instanceof String) {
                try {
                    String text = (String) obj;
                    return NumberFormat.getInstance().parse(text);
                } catch (ParseException e) {
                    // ignore exception
                }
            }
        }
        return null;
    }

    /**
     * Converting the Object into a number,
     * using the default value if the the conversion fails.
     *
     * @param obj          the object to use
     * @param defaultValue what to return if the value is null or if the conversion fails
     * @return the value of the object as a number, or defaultValue if the
     * original value is null, the object is null or the number conversion
     * fails
     */
    public static Number getAsNumber(final Object obj, Number defaultValue) {
        Number answer = getAsNumber(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a Boolean from a Object in a null-safe manner.
     * <p>
     * If the value is a <code>Boolean</code> it is returned directly.
     * If the value is a <code>String</code> and it equals 'true' ignoring case
     * then <code>true</code> is returned, otherwise <code>false</code>.
     * If the value is a <code>Number</code> an integer zero value returns
     * <code>false</code> and non-zero returns <code>true</code>.
     * Otherwise, <code>null</code> is returned.
     *
     * @param obj the object to use
     * @return the value of the Object as a Boolean, <code>null</code> if null object input
     */
    public static Boolean getAsBoolean(final Object obj) {
        if (obj != null) {
            if (obj instanceof Boolean) {
                return (Boolean) obj;
            } else if (obj instanceof String) {
                String temp = (String) obj;
                if ("1".equals(temp)) {
                    return Boolean.TRUE;
                } else {
                    return BooleanUtils.toBoolean(temp);
                }
            } else if (obj instanceof Number) {
                Number n = (Number) obj;
                return (n.intValue() != 0) ? Boolean.TRUE : Boolean.FALSE;
            }
        }
        return null;
    }

    /**
     * Gets a Boolean from a Object in a null-safe manner.
     * <p>
     * If the value is a <code>Boolean</code> it is returned directly.
     * If the value is a <code>String</code> and it equals 'true' ignoring case
     * then <code>true</code> is returned, otherwise <code>false</code>.
     * If the value is a <code>Number</code> an integer zero value returns
     * <code>false</code> and non-zero returns <code>true</code>.
     * Otherwise, <code>null</code> is returned.
     *
     * @param obj          the object to use
     * @param defaultValue what to return if the value is null or if the conversion fails
     * @return the value of the Object as a Boolean, <code>defaultValue</code> if null object input
     */
    public static Boolean getAsBoolean(final Object obj, Boolean defaultValue) {
        Boolean answer = getAsBoolean(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a boolean from a Object in a null-safe manner.
     * <p>
     * If the value is a <code>Boolean</code> its value is returned.
     * If the value is a <code>String</code> and it equals 'true' ignoring case
     * then <code>true</code> is returned, otherwise <code>false</code>.
     * If the value is a <code>Number</code> an integer zero value returns
     * <code>false</code> and non-zero returns <code>true</code>.
     * Otherwise, <code>false</code> is returned.
     *
     * @param obj the object to use
     * @return the value of the Object as a Boolean, <code>false</code> if null object input
     */
    public static boolean getAsBooleanValue(final Object obj) {
        Boolean booleanObject = getAsBoolean(obj);
        if (booleanObject == null) {
            return false;
        }
        return booleanObject;
    }

    /**
     * Gets a boolean from a Object in a null-safe manner.
     * <p>
     * If the value is a <code>Boolean</code> its value is returned.
     * If the value is a <code>String</code> and it equals 'true' ignoring case
     * then <code>true</code> is returned, otherwise <code>false</code>.
     * If the value is a <code>Number</code> an integer zero value returns
     * <code>false</code> and non-zero returns <code>true</code>.
     * Otherwise, <code>false</code> is returned.
     *
     * @param obj          the object to use
     * @param defaultValue what to return if the value is null or if the conversion fails
     * @return the value in the Map as a Boolean, <code>defaultValue</code> if null object input
     */
    public static boolean getAsBooleanValue(final Object obj, boolean defaultValue) {
        Boolean booleanObject = getAsBoolean(obj);
        if (booleanObject == null) {
            return defaultValue;
        }
        return booleanObject;
    }

    /**
     * Gets a Byte from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The Byte is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj the object to use
     * @return the value of Object as a Byte, <code>null</code> if null object input
     */
    public static Byte getAsByte(final Object obj) {
        Number answer = getAsNumber(obj);
        if (answer == null) {
            return null;
        } else if (answer instanceof Byte) {
            return (Byte) answer;
        }
        return answer.byteValue();
    }

    /**
     * Gets a Byte from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The Byte is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj          the object to use
     * @param defaultValue return if the value is null or if the conversion fails
     * @return the value of Object as a Byte, <code>defaultValue</code> if null object input
     */
    public static Byte getAsByte(final Object obj, Byte defaultValue) {
        Byte answer = getAsByte(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a byte from a Object in a null-safe manner.
     * <p>
     * The byte is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj the object to use
     * @return the value of the Object as a byte, <code>0</code> if null object input
     */
    public static byte getAsByteValue(final Object obj) {
        Byte byteObject = getAsByte(obj);
        if (byteObject == null) {
            return 0;
        }
        return byteObject;
    }

    /**
     * Gets a byte from a Object in a null-safe manner.
     * <p>
     * The byte is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj          the object to use
     * @param defaultValue return if the value is null or if the conversion fails
     * @return the value of the Object as a byte, <code>defaultValue</code> if null object input
     */
    public static byte getAsByteValue(final Object obj, byte defaultValue) {
        Byte byteObject = getAsByte(obj);
        if (byteObject == null) {
            return defaultValue;
        }
        return byteObject;
    }

    /**
     * Gets a Short from a Object in a null-safe manner.
     * <p>
     * The Short is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj the object to use
     * @return the value of the Object as a Short, <code>null</code> if null object input
     */
    public static Short getAsShort(final Object obj) {
        Number answer = getAsNumber(obj);
        if (answer == null) {
            return null;
        } else if (answer instanceof Short) {
            return (Short) answer;
        }
        return answer.shortValue();
    }

    /**
     * Gets a Short from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The Short is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj          the object to use
     * @param defaultValue return if the value is null or if the conversion fails
     * @return the value of Object as a Short, <code>defaultValue</code> if null object input
     */
    public static Short getAsShort(final Object obj, Short defaultValue) {
        Short answer = getAsShort(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a short from a Object in a null-safe manner.
     * <p>
     * The short is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj the object to use
     * @return the value of the Object as a short, <code>0</code> if null object input
     */
    public static short getAsShortValue(final Object obj) {
        Short shortObject = getAsShort(obj);
        if (shortObject == null) {
            return 0;
        }
        return shortObject;
    }

    /**
     * Gets a short from a Object in a null-safe manner.
     * <p>
     * The short is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj          the object to use
     * @param defaultValue return if the value is null or if the conversion fails
     * @return the value of the Object as a short, <code>defaultValue</code> if null object input
     */
    public static short getAsShortValue(final Object obj, short defaultValue) {
        Short shortObject = getAsShort(obj);
        if (shortObject == null) {
            return defaultValue;
        }
        return shortObject;
    }

    /**
     * Gets a Integer from a Object in a null-safe manner.
     * <p>
     * The Integer is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj the object to use
     * @return the value of the Object as a Integer, <code>null</code> if null object input
     */
    public static Integer getAsInteger(final Object obj) {
        Number answer = getAsNumber(obj);
        if (answer == null) {
            return null;
        } else if (answer instanceof Integer) {
            return (Integer) answer;
        }
        return answer.intValue();
    }

    /**
     * Gets a Integer from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The Integer is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj          the object to use
     * @param defaultValue return if the value is null or if the conversion fails
     * @return the value of Object as a Integer, <code>defaultValue</code> if null object input
     */
    public static Integer getAsInteger(final Object obj, Integer defaultValue) {
        Integer answer = getAsInteger(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a int from a Object in a null-safe manner.
     * <p>
     * The int is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj the object to use
     * @return the value of the Object as a int, <code>0</code> if null object input
     */
    public static int getAsIntValue(final Object obj) {
        Integer integerObject = getAsInteger(obj);
        if (integerObject == null) {
            return 0;
        }
        return integerObject;
    }

    /**
     * Gets a int from a Object in a null-safe manner.
     * <p>
     * The int is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj          the object to use
     * @param defaultValue return if the value is null or if the conversion fails
     * @return the value of the Object as a int, <code>defaultValue</code> if null object input
     */
    public static int getAsIntValue(final Object obj, int defaultValue) {
        Integer integerObject = getAsInteger(obj);
        if (integerObject == null) {
            return defaultValue;
        }
        return integerObject;
    }

    /**
     * Gets a Long from a Object in a null-safe manner.
     * <p>
     * The Long is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj the object to use
     * @return the value of the Object as a Long, <code>null</code> if null object input
     */
    public static Long getAsLong(final Object obj) {
        Number answer = getAsNumber(obj);
        if (answer == null) {
            return null;
        } else if (answer instanceof Long) {
            return (Long) answer;
        }
        return answer.longValue();
    }

    /**
     * Gets a Long from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The Long is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj          the object to use
     * @param defaultValue return if the value is null or if the conversion fails
     * @return the value of Object as a Long, <code>defaultValue</code> if null object input
     */
    public static Long getAsLong(final Object obj, Long defaultValue) {
        Long answer = getAsLong(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a long from a Object in a null-safe manner.
     * <p>
     * The long is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj the object to use
     * @return the value of the Object as a long, <code>0L</code> if null object input
     */
    public static long getAsLongValue(final Object obj) {
        Long longObject = getAsLong(obj);
        if (longObject == null) {
            return 0L;
        }
        return longObject;
    }

    /**
     * Gets a long from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The long is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj          the object to use
     * @param defaultValue return if the value is null or if the conversion fails
     * @return the value of Object as a long, <code>defaultValue</code> if null object input
     */
    public static long getAsLongValue(final Object obj, long defaultValue) {
        Long longObject = getAsLong(obj);
        if (longObject == null) {
            return defaultValue;
        }
        return longObject;
    }

    /**
     * Gets a Float from a Object in a null-safe manner.
     * <p>
     * The Float is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj the object to use
     * @return the value of the Object as a Float, <code>null</code> if null object input
     */
    public static Float getAsFloat(final Object obj) {
        Number answer = getAsNumber(obj);
        if (answer == null) {
            return null;
        } else if (answer instanceof Float) {
            return (Float) answer;
        }
        return answer.floatValue();
    }

    /**
     * Gets a Float from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The Float is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj          the object to use
     * @param defaultValue return if the value is null or if the conversion fails
     * @return the value of Object as a Float, <code>defaultValue</code> if null object input
     */
    public static Float getAsFloat(final Object obj, Float defaultValue) {
        Float answer = getAsFloat(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a float from a Object in a null-safe manner.
     * <p>
     * The float is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj the object to use
     * @return the value of Object as a float, <code>0.0F</code> if null object input
     */
    public static float getAsFloatValue(final Object obj) {
        Float floatObject = getAsFloat(obj);
        if (floatObject == null) {
            return 0f;
        }
        return floatObject;
    }

    /**
     * Gets a float from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The float is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj          the object to use
     * @param defaultValue return if the value is null or if the conversion fails
     * @return the value of Object as a float, <code>defaultValue</code> if null object input
     */
    public static float getAsFloatValue(final Object obj, float defaultValue) {
        Float floatObject = getAsFloat(obj);
        if (floatObject == null) {
            return defaultValue;
        }
        return floatObject;
    }

    /**
     * Gets a Double from a Object in a null-safe manner.
     * <p>
     * The Double is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj the object to use
     * @return the value of the Object as a Double, <code>null</code> if null object input
     */
    public static Double getAsDouble(final Object obj) {
        Number answer = getAsNumber(obj);
        if (answer == null) {
            return null;
        } else if (answer instanceof Double) {
            return (Double) answer;
        }
        return answer.doubleValue();
    }

    /**
     * Gets a Double from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The Double is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj          the object to use
     * @param defaultValue return if the value is null or if the conversion fails
     * @return the value of Object as a Double, <code>defaultValue</code> if null object input
     */
    public static Double getAsDouble(final Object obj, Double defaultValue) {
        Double answer = getAsDouble(obj);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * Gets a double from a Object in a null-safe manner.
     * <p>
     * The double is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj the object to use
     * @return the value of Object as a double, <code>0.0</code> if null object input
     */
    public static double getAsDoubleValue(final Object obj) {
        Double doubleObject = getAsDouble(obj);
        if (doubleObject == null) {
            return 0d;
        }
        return doubleObject;
    }

    /**
     * Gets a double from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The double is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj          the object to use
     * @param defaultValue return if the value is null or if the conversion fails
     * @return the value of Object as a double, <code>defaultValue</code> if null object input
     */
    public static double getAsDoubleValue(final Object obj, double defaultValue) {
        Double doubleObject = getAsDouble(obj);
        if (doubleObject == null) {
            return defaultValue;
        }
        return doubleObject;
    }

    /**
     * Gets a BigInteger from a Object in a null-safe manner.
     * <p>
     * The BigInteger is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj the object to use
     * @return the value of Object as a BigInteger, <code>0</code> if null object input
     */
    public static BigInteger getAsBigInteger(final Object obj) {
        Long longObject = getAsLong(obj);
        if (longObject == null) {
            return BigInteger.ZERO;
        }
        return BigInteger.valueOf(longObject);
    }

    /**
     * Gets a BigInteger from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The BigInteger is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj          the object to use
     * @param defaultValue return if the value is null or if the conversion fails
     * @return the value of Object as a BigInteger, <code>defaultValue</code> if null object input
     */
    public static BigInteger getAsBigInteger(final Object obj, BigInteger defaultValue) {
        Long longObject = getAsLong(obj);
        if (longObject == null) {
            return defaultValue;
        }
        return BigInteger.valueOf(longObject);
    }

    /**
     * Gets a BigDecimal from a Object in a null-safe manner.
     * <p>
     * The BigDecimal is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj the object to use
     * @return the value of Object as a BigDecimal, <code>0</code> if null object input
     */
    public static BigDecimal getAsBigDecimal(final Object obj) {
        Double doubleObject = getAsDouble(obj);
        if (doubleObject == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(doubleObject);
    }

    /**
     * Gets a BigDecimal from a Object in a null-safe manner,
     * using the default value if the the conversion fails.
     * <p>
     * The BigDecimal is obtained from the results of {@link #getAsNumber(Object)}.
     *
     * @param obj          the object to use
     * @param defaultValue return if the value is null or if the conversion fails
     * @return the value of Object as a BigDecimal, <code>defaultValue</code> if null object input
     */
    public static BigDecimal getAsBigDecimal(final Object obj, BigDecimal defaultValue) {
        Double doubleObject = getAsDouble(obj);
        if (doubleObject == null) {
            return defaultValue;
        }
        return BigDecimal.valueOf(doubleObject);
    }

    public static Date getAsDate(Object date) {
        if (date == null) {
            return null;
        }
        if (date instanceof Long) {
            long ll = (Long) date;
            return ll == 0 ? null : new Date(ll);
        } else if (date instanceof Integer) {
            long ll = ((Integer) date).longValue();
            return ll == 0 ? null : new Date(ll);
        } else if (date instanceof BigDecimal) {
            long ll = ((BigDecimal) date).longValue();
            return ll == 0 ? null : new Date(ll);
        } else if (date instanceof Date) {
            return (Date) date;
        } else if (date instanceof java.sql.Date) {
            return (Date) date;
        } else if (date instanceof java.sql.Timestamp) {
            return (Date) date;
        } else if (date instanceof LocalDate) {
            return DateUtils.localDate2Date((LocalDate) date);
        } else if (date instanceof LocalDateTime) {
            return DateUtils.localDateTime2Date((LocalDateTime) date);
        }
        String str = String.valueOf(date).trim();
        if ("".equals(str)) {
            return null;
        }

        String parse = str;
        parse = parse.replaceFirst("^[0-9]{4}([^0-9])", "yyyy$1");
        parse = parse.replaceFirst("^[0-9]{2}([^0-9])", "yy$1");
        parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9])", "$1MM$2");
        parse = parse.replaceFirst("([^0-9])[0-9]{1,2}( ?)", "$1dd$2");
        parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9])", "$1HH$2");
        parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9])", "$1mm$2");
        parse = parse.replaceFirst("([^0-9])[0-9]{1,2}([^0-9]?)", "$1ss$2");

        Date result = null;
        SimpleDateFormat format = new SimpleDateFormat(parse);
        try {
            result = format.parse(str);
        } catch (ParseException e) {
            throw new IllegalArgumentException("对象 " + str + " 转换成 Date 数据错误.");
        }

        return result;
    }

    /**
     * 半角转全角
     *
     * @param input String.
     * @return 全角字符串.
     */
    public static String toSBC(String input) {
        return toSBC(input, null);
    }

    /**
     * 半角转全角
     *
     * @param input         String
     * @param notConvertSet 不替换的字符集合
     * @return 全角字符串.
     */
    public static String toSBC(String input, Set<Character> notConvertSet) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (null != notConvertSet && notConvertSet.contains(c[i])) {
                // 跳过不替换的字符
                continue;
            }

            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

    /**
     * 全角转半角
     *
     * @param input String.
     * @return 半角字符串
     */
    public static String toDBC(String input) {
        return toDBC(input, null);
    }

    /**
     * 替换全角为半角
     *
     * @param text          文本
     * @param notConvertSet 不替换的字符集合
     * @return 替换后的字符
     */
    public static String toDBC(String text, Set<Character> notConvertSet) {
        char[] c = text.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (null != notConvertSet && notConvertSet.contains(c[i])) {
                // 跳过不替换的字符
                continue;
            }

            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
            }
        }

        return new String(c);
    }

    /**
     * 数字金额大写转换 先写个完整的然后将如零拾替换成零
     *
     * @param n 数字
     * @return 中文大写数字
     */
    public static String digitUppercase(double n) {
        String[] fraction = {"角", "分"};
        String[] digit = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String[][] unit = {{"元", "万", "亿"}, {"", "拾", "佰", "仟"}};

        String head = n < 0 ? "负" : "";
        n = Math.abs(n);

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < fraction.length; i++) {
            s.append((digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", ""));
        }
        if (s.length() < 1) {
            s = new StringBuilder("整");
        }
        int integerPart = (int) Math.floor(n);

        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            StringBuilder p = new StringBuilder();
            for (int j = 0; j < unit[1].length && n > 0; j++) {
                p.insert(0, digit[integerPart % 10] + unit[1][j]);
                integerPart = integerPart / 10;
            }
            s.insert(0, p.toString().replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i]);
        }
        return head + s.toString().replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }

    /**
     * @param obj          the object to use
     * @param defaultValue return if the value is null or if the conversion fails
     */
    @SuppressWarnings("unchecked")
    public static <R> R convert(final Object obj, R defaultValue) {
        R result = null;
        if (defaultValue == null) {
            return result;
        }
        Class<?> clz = defaultValue.getClass();
        if (Boolean.class.equals(clz)) {
            result = (R) getAsBoolean(obj, (Boolean) defaultValue);
        } else if (Byte.class.equals(clz)) {
            result = (R) getAsByte(obj, (Byte) defaultValue);
        } else if (Short.class.equals(clz)) {
            result = (R) getAsShort(obj, (Short) defaultValue);
        } else if (Integer.class.equals(clz)) {
            result = (R) getAsInteger(obj, (Integer) defaultValue);
        } else if (Long.class.equals(clz)) {
            result = (R) getAsLong(obj, (Long) defaultValue);
        } else if (Float.class.equals(clz)) {
            result = (R) getAsFloat(obj, (Float) defaultValue);
        } else if (Double.class.equals(clz)) {
            result = (R) getAsDouble(obj, (Double) defaultValue);
        } else if (BigInteger.class.equals(clz)) {
            result = (R) getAsBigInteger(obj, (BigInteger) defaultValue);
        } else if (BigDecimal.class.equals(clz)) {
            result = (R) getAsBigDecimal(obj, (BigDecimal) defaultValue);
        } else if (String.class.equals(clz)) {
            result = (R) getAsString(obj, (String) defaultValue);
        }
        return result;
    }

    /**
     * @param value the object to use
     * @param clz   the type for conversion
     */
    @SuppressWarnings("unchecked")
    public static <T> T convert(Class<T> clz, final Object value) {
        return (T) convert(clz.getTypeName(), value);
    }

    public static Object convert(String type, Object value) {
        Object val = null;
        if (value != null) {
            if ("java.lang.Byte".equals(type)) {
                if (value.toString().length() > 0) {
                    val = getAsByte(value);
                }
            } else if ("java.lang.Short".equals(type) || "short".equalsIgnoreCase(type)) {
                if (value.toString().length() > 0) {
                    val = getAsShort(value);
                }
            } else if ("java.lang.Integer".equals(type) || "int".equalsIgnoreCase(type)) {
                if (value.toString().length() > 0) {
                    val = getAsInteger(value);
                }
            } else if ("java.lang.Long".equals(type) || "long".equalsIgnoreCase(type)) {
                if (value.toString().length() > 0) {
                    val = getAsLong(value);
                }
            } else if ("java.lang.Float".equals(type) || "float".equalsIgnoreCase(type)) {
                if (value.toString().length() > 0) {
                    val = getAsFloat(value);
                }
            } else if ("java.lang.Double".equals(type) || "double".equalsIgnoreCase(type)) {
                if (value.toString().length() > 0) {
                    val = getAsDouble(value);
                }
            } else if ("java.math.BigDecimal".equals(type)) {
                if (value.toString().length() > 0) {
                    val = getAsBigDecimal(value);
                }
            } else if ("java.lang.Boolean".equals(type) || "boolean".equalsIgnoreCase(type) || "bool".equalsIgnoreCase(type)) {
                if (value.toString().length() > 0) {
                    val = getAsBoolean(value);
                }
            } else if ("java.lang.String".equals(type) || "string".equalsIgnoreCase(type) || "str".equalsIgnoreCase(type)) {
                val = getAsString(value);
            } else if ("java.util.Date".equals(type) || "date".equalsIgnoreCase(type)) {
                if (value.toString().length() > 0) {
                    val = getAsDate(value);
                }
            } else if ("java.time.LocalDate".equals(type) || "date8".equalsIgnoreCase(type)) {
                if (value.toString().length() > 0) {
                    if (value instanceof LocalDate) {
                        val = value;
                    } else if (value instanceof LocalDateTime) {
                        val = ((LocalDateTime) value).toLocalDate();
                    } else {
                        val = DateUtils.date2LocalDate(getAsDate(value));
                    }
                }
            } else if ("java.time.LocalDateTime".equals(type) || "datetime8".equalsIgnoreCase(type)) {
                if (value.toString().length() > 0) {
                    if (value instanceof LocalDate) {
                        val = ((LocalDate) value).atStartOfDay();
                    } else if (value instanceof LocalDateTime) {
                        val = value;
                    } else {
                        val = DateUtils.date2LocalDateTime(getAsDate(value));
                    }
                }
            } else {
                val = value;
            }
        }
        return val;
    }

    public static void main(String[] args) {
        String value = "2020-6-3 12:3:55";
        Object obj;
        obj = ConverterUtils.convert(Date.class, value);

        System.out.println(obj);
        System.out.println(digitUppercase(120.12));
    }
}

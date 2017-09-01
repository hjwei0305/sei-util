package com.ecmp.util;

import com.ecmp.exception.EcmpException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * <strong>实现功能:</strong>.
 * <p>
 * 实现功能：
 * JSON的工具类
 * <h3>Here is an example:</h3>
 * <pre>
 *     // 将json通过类型转换成对象
 *     {@link JsonUtils JsonUtil}.fromJson("{\"username\":\"username\", \"password\":\"password\"}", User.class);
 * </pre>
 * <pre>
 *     // 传入转换的引用类型
 *     {@link JsonUtils JsonUtil}.fromJson("[{\"username\":\"username\", \"password\":\"password\"}, {\"username\":\"username\", \"password\":\"password\"}]", new TypeReference&lt;List&lt;User&gt;&gt;);
 * </pre>
 * <pre>
 *     // 将对象转换成json
 *     {@link JsonUtils JsonUtil}.toJson(user);
 * </pre>
 * <pre>
 *     // 将对象转换成json, 可以设置输出属性
 *     {@link JsonUtils JsonUtil}.toJson(user, {@link JsonInclude.Include ALWAYS});
 * </pre>
 * <pre>
 *     // 将对象转换成json, 传入配置对象
 *     {@link ObjectMapper ObjectMapper} mapper = new ObjectMapper();
 *     mapper.setSerializationInclusion({@link JsonInclude.Include ALWAYS});
 *     mapper.configure({@link DeserializationFeature FAIL_ON_UNKNOWN_PROPERTIES}, false);
 *     mapper.configure({@link DeserializationFeature FAIL_ON_NUMBERS_FOR_ENUMS}, true);
 *     mapper.setDateFormat(new {@link SimpleDateFormat SimpleDateFormat}("yyyy-MM-dd HH:mm:ss"));
 *     {@link JsonUtils JsonUtil}.toJson(user, mapper);
 * </pre>
 * <pre>
 *     // 获取Mapper对象
 *     {@link JsonUtils JsonUtil}.mapper();
 * </pre>
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.1 2017-04-14 16:12
 * @see DeserializationFeature Feature
 * @see ObjectMapper ObjectMapper
 * @see JsonInclude.Include
 * @see IOException IOException
 * @see SimpleDateFormat SimpleDateFormat
 */
@SuppressWarnings("unchecked")
public final class JsonUtils {
    private static ObjectMapper MAPPER;

    static {
        MAPPER = generateMapper(JsonInclude.Include.ALWAYS);
    }

    private JsonUtils() {
    }

    /**
     * 将json通过类型转换成对象
     * <pre>
     *     {@link JsonUtils JsonUtil}.fromJson("{\"username\":\"username\", \"password\":\"password\"}", User.class);
     * </pre>
     *
     * @param <T>   泛型
     * @param json  json字符串
     * @param clazz 泛型类型
     * @return 返回对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (null == json || json.equals("")) {
            return null;
        } else {
            try {
                return clazz.equals(String.class) ? (T) json : MAPPER.readValue(json, clazz);
            } catch (Exception e) {
                throw new EcmpException(e.getMessage(), e);
            }
        }
    }

    /**
     * 将json通过类型转换成集合对象
     * <pre>
     *     {@link JsonUtils JsonUtil}.fromJson("[{\"username\":\"username\", \"password\":\"password\"}, {\"username\":\"username\", \"password\":\"password\"}]", new TypeReference&lt;List&lt;User&gt;&gt;);
     * </pre>
     * @param <T> 泛型
     * @param json          json字符串
     * @param typeReference 引用类型
     * @return 返回对象
     */
    public static <T> T fromJson(String json, TypeReference<?> typeReference) {
        if (null == json || json.equals("")) {
            return null;
        } else {
            try {
                return (T) (typeReference.getType().equals(String.class) ? json : MAPPER.readValue(json, typeReference));
            } catch (IOException e) {
                throw new EcmpException(e.getMessage(), e);
            }
        }
    }

    /**
     * 将对象转换成json
     * <pre>
     *     {@link JsonUtils JsonUtil}.toJson(user);
     * </pre>
     * @param <T> 泛型
     * @param src 对象
     * @return 返回json字符串
     */
    public static <T> String toJson(T src) {
        try {
            return src instanceof String ? (String) src : MAPPER.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            throw new EcmpException(e.getMessage(), e);
        }
    }

    /**
     * 将对象转换成json, 可以设置输出属性
     * <pre>
     *     {@link JsonUtils JsonUtil}.toJson(user, {@link JsonInclude Inclusion.ALWAYS});
     * </pre>
     * {@link JsonInclude Inclusion 对象枚举}
     * <ul>
     * <li>{@link JsonInclude.Include ALWAYS 全部列入}</li>
     * <li>{@link JsonInclude.Include NON_DEFAULT 字段和对象默认值相同的时候不会列入}</li>
     * <li>{@link JsonInclude.Include NON_EMPTY 字段为NULL或者""的时候不会列入}</li>
     * <li>{@link JsonInclude.Include NON_NULL 字段为NULL时候不会列入}</li>
     * </ul>
     * <p>
     *
     * @param <T>       泛型
     * @param src       对象
     * @param inclusion 传入一个枚举值, 设置输出属性
     * @return 返回json字符串
     * @throws IOException IOException
     */
    public static <T> String toJson(T src, JsonInclude.Include inclusion) throws IOException {
        if (src instanceof String) {
            return (String) src;
        } else {
            ObjectMapper customMapper = generateMapper(inclusion);
            return customMapper.writeValueAsString(src);
        }
    }

    /**
     * 将对象转换成json, 传入配置对象
     * <pre>
     *     {@link ObjectMapper ObjectMapper} mapper = new ObjectMapper();
     *     mapper.setSerializationInclusion({@link JsonInclude.Include ALWAYS});
     *     mapper.configure({@link DeserializationFeature FAIL_ON_UNKNOWN_PROPERTIES}, false);
     *     mapper.configure({@link DeserializationFeature FAIL_ON_NUMBERS_FOR_ENUMS}, true);
     *     mapper.setDateFormat(new {@link SimpleDateFormat SimpleDateFormat}("yyyy-MM-dd HH:mm:ss"));
     *     {@link JsonUtils JsonUtil}.toJson(user, mapper);
     * </pre>
     * {@link ObjectMapper ObjectMapper}
     *
     * @param <T>    泛型
     * @param src    对象
     * @param mapper 配置对象
     * @return 返回json字符串
     * @throws IOException IOException
     * @see ObjectMapper
     */
    public static <T> String toJson(T src, ObjectMapper mapper) throws IOException {
        if (null != mapper) {
            if (src instanceof String) {
                return (String) src;
            } else {
                return mapper.writeValueAsString(src);
            }
        } else {
            return null;
        }
    }

    /**
     * 返回{@link ObjectMapper ObjectMapper}对象, 用于定制性的操作
     *
     * @return {@link ObjectMapper ObjectMapper}对象
     */
    public static ObjectMapper mapper() {
        return MAPPER;
    }

    /**
     * 通过Inclusion创建ObjectMapper对象
     * <p/>
     * {@link JsonInclude.Include 对象枚举}
     * <ul>
     * <li>{@link JsonInclude.Include ALWAYS 全部列入}</li>
     * <li>{@link JsonInclude.Include NON_DEFAULT 字段和对象默认值相同的时候不会列入}</li>
     * <li>{@link JsonInclude.Include NON_EMPTY 字段为NULL或者""的时候不会列入}</li>
     * <li>{@link JsonInclude.Include NON_NULL 字段为NULL时候不会列入}</li>
     * </ul>
     *
     * @param include 传入一个枚举值, 设置输出属性
     * @return 返回ObjectMapper对象
     */
    private static ObjectMapper generateMapper(JsonInclude.Include include) {
        ObjectMapper objectMapper = new ObjectMapper();

        // 设置输出时包含属性的风格
        objectMapper.setSerializationInclusion(include);
        //空值不序列化
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //去掉默认的时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置为中国上海时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        //反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //序列化时，日期的统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 禁止使用int代表Enum的order()來反序列化Enum,非常危險
        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        //单引号处理
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        return objectMapper;
    }
}

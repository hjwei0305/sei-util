# 枚举工具类（EnumUtils）

## com.ecmp.annotation.MetaData.java
用于注解类或属性的元数据，这些元数据用于运行时动态内容生成
``` java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PACKAGE })
public @interface MetaData {
    /**
     * 简要注解说明
     */
    String value();

    /**
     * 注释说明：用于描述代码内部用法说明，一般不用于业务
     */
    String comments() default "";
}
```

## EnumUtils.java
枚举工具类
``` java
/**
 * 基于Enum类返回对应的key-value Map构建对象
 */
public static Map<Integer, String> getEnumDataMap(Class<? extends Enum> enumClass)

/**
 * 根据指定的枚举下标获取枚举描述或名称
 * 若有@MetaData注解将获取@MetaData#value的枚举描述，没有则是枚举名
 *
 * @param enumClass 枚举类
 * @param key       枚举下标
 * @return 返回枚举下标对应的描述或名称
 */
public static String getEnumDataText(Class<? extends Enum> enumClass, int key)

/**
 * 根据指定的枚举下标获取枚举名称
 *
 * @param enumClass 枚举类
 * @param key       枚举下标
 * @return 返回枚举下标对应的名称
 */
public static String getEnumDataName(Class<? extends Enum> enumClass, int key)

/**
 * 根据枚举下标获取枚举实例
 *
 * @param enumClass 枚举类
 * @param ordinal   枚举下标
 * @param <E>       枚举对象实例
 * @return 返回枚举对象实例
 */
public static <E extends Enum<E>> E getEnum(final Class<E> enumClass, final int ordinal)

/**
 * 根据枚举名获取枚举实例
 *
 * @param enumClass 枚举类
 * @param name      枚举名
 * @param <E>       枚举对象实例
 * @return 返回枚举对象实例
 */
public static <E extends Enum<E>> E getEnum(final Class<E> enumClass, final String name)
```

## 用法
- 枚举定义 示例
```
public enum Color {
    @MetaData("红色")
    red,
    @MetaData("蓝色")
    blue,
    green,
    yellow
}
```

- 将枚举传值到前端页面
```
   Map<Integer, String> colorEnumMap = getEnumDataMap(ColorEnum.class);
   
   request.setAttribute("key", colorEnumMap);
   或
   modelAndView.addObject("key", colorEnumMap)
```
-  后端服务使用
```
   Color color = EnumUtils.getEnum(Color.class, 1);
```
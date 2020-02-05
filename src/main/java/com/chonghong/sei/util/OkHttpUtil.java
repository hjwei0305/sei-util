//package com.chonghong.sei.util;
//
//
//import okhttp3.*;
//import org.apache.commons.lang3.StringUtils;
//import org.jetbrains.annotations.NotNull;
//
//import java.io.IOException;
//import java.net.CookieStore;
//import java.net.URLEncoder;
//import java.rmi.server.RemoteRef;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//public class OkHttpUtil {
//    public static final String CHARSET_NAME = "UTF-8";
//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//    private static OkHttpUtil instance;
//    private OkHttpClient okHttpClient;
//
//    private OkHttpUtil() {
//        okHttpClient = new OkHttpClient.Builder().build();
//    }
//
//    public static OkHttpUtil getInstance() {
//        if (instance == null) {
//            synchronized (OkHttpUtil.class) {
//                if (instance == null) {
//                    instance = new OkHttpUtil();
//                }
//            }
//        }
//        return instance;
//    }
//
//    public static String get(String url) throws Exception {
//        return getExecute(url, new HashMap<>());
//    }
//
//    public static String get(String url, Map<String, String> data) throws Exception {
//        url = getRequestUrl(url, data);
//        return getExecute(url, new HashMap<>());
//    }
//
//
//    public static String getWithHeaders(String url, Map<String, String> headers) throws Exception {
//        return getExecute(url, headers);
//    }
//
//    /**
//     * 同步get
//     *
//     * @param url
//     * @return
//     * @throws Exception
//     */
//    private static String getExecute(String url, Map<String, String> header) throws Exception {
//        if (Objects.isNull(header)) {
//            header = new HashMap<>();
//        }
//        Headers headerBuild = Headers.of(header);
//        Request request = new Request.Builder().url(url).headers(headerBuild).build();
//        Response response = execute(request);
//        if (response.isSuccessful()) {
//            return response.body().string();
//        } else {
//            throw new IOException("Unexpected code " + response);
//        }
//    }
//
//    /**
//     * 同步get请求
//     *
//     * @param url
//     * @param data
//     * @return
//     * @throws Exception
//     */
//    public static String getWithHeaders(String url, Map<String, String> data, Map<String, String> header) throws Exception {
//        url = getRequestUrl(url, data);
//        return get(url, header);
//    }
//
//    /**
//     * 异步get请求
//     *
//     * @param url
//     * @param responseCallback
//     * @return
//     * @throws Exception
//     */
//    public static void get(String url, Callback responseCallback) throws Exception {
//        Request request = new Request.Builder().url(url).build();
//
//        enqueue(request, responseCallback);
//    }
//
//    /**
//     * 异步get
//     *
//     * @param url
//     * @param data
//     * @param responseCallback
//     * @return
//     * @throws Exception
//     */
//    public static void get(String url, Map<String, String> data, Callback responseCallback) throws Exception {
//        url = getRequestUrl(url, data);
//        Request request = new Request.Builder().url(url).build();
//
//        enqueue(request, responseCallback);
//    }
//
//    public static String post(String url, String json) throws IOException {
//        return postWithHeaders(url, json, new HashMap<>());
//    }
//
//    /**
//     * 同步post json数据
//     *
//     * @param url
//     * @param json
//     * @return
//     * @throws IOException
//     */
//    public static String postWithHeaders(String url, String json, Map<String, String> header) throws IOException {
//        if (Objects.isNull(header)) {
//            header = new HashMap<>();
//        }
//        Headers headerBuild = Headers.of(header);
//        RequestBody body = RequestBody.create(json, JSON);
//        Request request = new Request.Builder().url(url).post(body).headers(headerBuild).build();
//
//        Response response = execute(request);
//        if (response.isSuccessful()) {
//            return response.body().string();
//        } else {
//            throw new IOException("Unexpected code " + response);
//        }
//    }
//
//    public static String postWithHeaders(String url, Map<String, String> data, Map<String, String> header) throws IOException {
//        return post(url, data, header);
//    }
//
//    public static String post(String url, Map<String, String> data) throws IOException {
//        return post(url, data, new HashMap<>());
//    }
//
//
//    /**
//     * 同步post 键值对数据
//     *
//     * @param url
//     * @param data
//     * @return
//     * @throws IOException
//     */
//    private static String post(String url, Map<String, String> data, Map<String, String> header) throws IOException {
//        FormBody.Builder builder = new FormBody.Builder();
//        for (Map.Entry<String, String> item : data.entrySet()) {
//            builder.add(item.getKey(), item.getValue());
//        }
//
//        RequestBody body = builder.build();
//        Headers headerBuild = Headers.of(header);
//        Request request = new Request.Builder().url(url).post(body).headers(headerBuild).build();
//
//        Response response = execute(request);
//        if (response.isSuccessful()) {
//            return response.body().string();
//        } else {
//            throw new IOException("Unexpected code " + response);
//        }
//    }
//
//    /**
//     * 异步post json
//     *
//     * @param url
//     * @param json
//     * @param responseCallback
//     * @throws IOException
//     */
//    public static void post(String url, String json, Callback responseCallback) throws IOException {
//        RequestBody body = RequestBody.create(json, JSON);
//        Request request = new Request.Builder().url(url).post(body).build();
//
//        enqueue(request, responseCallback);
//    }
//
//    /**
//     * 异步post key-value
//     *
//     * @param url
//     * @param data
//     * @param responseCallback
//     * @throws IOException
//     */
//    public static void post(String url, Map<String, String> data, Callback responseCallback) throws IOException {
//        FormBody.Builder builder = new FormBody.Builder();
//        for (Map.Entry<String, String> item : data.entrySet()) {
//            builder.add(item.getKey(), item.getValue());
//        }
//
//        RequestBody body = builder.build();
//        Request request = new Request.Builder().url(url).post(body).build();
//
//        enqueue(request, responseCallback);
//    }
//
//    /**
//     * 同步put
//     *
//     * @param url
//     * @param json
//     * @return
//     * @throws IOException
//     */
//    public static String put(String url, String json) throws IOException {
//        RequestBody body = RequestBody.create(json, JSON);
//
//        Request request = new Request.Builder().url(url).put(body).build();
//
//        Response response = execute(request);
//        if (response.isSuccessful()) {
//            return response.body().string();
//        } else {
//            throw new IOException("Unexpected code " + response);
//        }
//    }
//
//    /**
//     * 同步put key-value
//     *
//     * @param url
//     * @param data
//     * @return
//     * @throws IOException
//     */
//    public static String put(String url, Map<String, String> data) throws IOException {
//        FormBody.Builder builder = new FormBody.Builder();
//        for (Map.Entry<String, String> item : data.entrySet()) {
//            builder.add(item.getKey(), item.getValue());
//        }
//
//        RequestBody body = builder.build();
//        Request request = new Request.Builder().url(url).put(body).build();
//
//        Response response = execute(request);
//        if (response.isSuccessful()) {
//            return response.body().string();
//        } else {
//            throw new IOException("Unexpected code " + response);
//        }
//    }
//
//    /**
//     * 异步put json
//     *
//     * @param url
//     * @param json
//     * @throws IOException
//     */
//    public static void put(String url, String json, Callback responseCallback) throws IOException {
//        RequestBody body = RequestBody.create(json, JSON);
//
//        Request request = new Request.Builder().url(url).put(body).build();
//        enqueue(request, responseCallback);
//    }
//
//    /**
//     * 异步put key-value
//     *
//     * @param url
//     * @param data
//     * @param responseCallback
//     * @throws IOException
//     */
//    public static void put(String url, Map<String, String> data, Callback responseCallback) throws IOException {
//        FormBody.Builder builder = new FormBody.Builder();
//        for (Map.Entry<String, String> item : data.entrySet()) {
//            builder.add(item.getKey(), item.getValue());
//        }
//        RequestBody body = builder.build();
//        Request request = new Request.Builder().url(url).put(body).build();
//
//        enqueue(request, responseCallback);
//    }
//
//    /**
//     * 通用同步请求。
//     *
//     * @param request
//     * @return
//     * @throws IOException
//     */
//    public static Response execute(Request request) throws IOException {
//        return getInstance().okHttpClient.newCall(request).execute();
//    }
//
//    /**
//     * 通用异步请求
//     *
//     * @param request
//     * @param responseCallback
//     */
//    public static void enqueue(Request request, Callback responseCallback) {
//        getInstance().okHttpClient.newCall(request).enqueue(responseCallback);
//    }
//
//    /**
//     * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
//     *
//     * @param request
//     */
//    public static void enqueue(Request request) {
//        getInstance().okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//
//            }
//
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//
//            }
//        });
//    }
//
//    /**
//     * get方式URL拼接
//     *
//     * @param url
//     * @param map
//     * @return
//     */
//    private static String getRequestUrl(String url, Map<String, String> map) {
//        if (map == null || map.size() == 0) {
//            return url;
//        } else {
//            StringBuilder newUrl = new StringBuilder(url);
//            if (url.indexOf("?") == -1) {
//                newUrl.append("?rd=" + Math.random());
//            }
//
//            for (Map.Entry<String, String> item : map.entrySet()) {
//                if (StringUtils.isNotBlank(item.getKey().trim())) {
//                    try {
//                        newUrl.append("&" + item.getKey().trim() + "=" + URLEncoder.encode(item.getValue().trim(), "UTF-8"));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            return newUrl.toString();
//        }
//    }
//
//
//    public static void main(String[] args) {
//        Map<String, String> params = new HashMap<>();
//        params.put("className", "com.changhong.sei.configcenter.entity.Environment");
//
//        Map<String, String> header = new HashMap<>();
//        header.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOIiwiZXhwIjoxNTgwNzkwNTAxfQ.RIqJDE4aPsfbUpE_8lJfLVQLgG57SzFZ-qLAwYggrEY");
//        try {
//            String result = OkHttpUtil.get("http://127.0.0.1:8080/config-center/serialNumberConfig/findByClassName", params);
//            String post = "{\"createDate\":\"2020-02-04T02:15:53.891+0000\",\"createAccount\":\"admin\",\"editDate\":\"2020-02-04T02:15:53.891+0000\",\"editAccount\":\"admin\",\"id\":\"4028c683700a642c01700dc690a00000\",\"entityClassName\":\"com.changhong.sei.configcenter.entity.Environment\",\"name\":\"配置中心环境实体类2\",\"expressionConfig\":\"ENV${company}${YYYYMM}#{000000}\",\"initialSerial\":2,\"currentSerial\":0,\"genFlag\":false,\"cycleStrategy\":\"MAX_CYCLE\",\"activated\":true}";
//            String a = OkHttpUtil.postWithHeaders("http://127.0.0.1:8080/config-center/serialNumberConfig/save", post, header);
//            System.out.println(a);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

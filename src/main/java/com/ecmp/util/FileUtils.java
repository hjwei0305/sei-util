package com.ecmp.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

/**
 * 实现功能：
 * 实现文件或InputStream与base64编码字符串的相互转化
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2019-08-22 17:18
 */
public final class FileUtils {

    /**
     * 文件转为Base64字符串
     *
     * @param file 文件
     * @return base64编码字符串
     * @throws IOException
     */
    public static String file2Str(File file) throws IOException {
        if (file == null || !file.exists()) {
            return null;
        }

        String str;
        byte[] b = Files.readAllBytes(Paths.get(file.getPath()));
        str = Base64.getEncoder().encodeToString(b);
        return str;
    }

    /**
     * 输入流转为Base64字符串
     *
     * @param inputStream 输入流
     * @return base64编码字符串
     * @throws IOException
     */
    public static String stream2Str(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        String str;
        byte[] data;
        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[inputStream.available()];
            int rc;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } finally {
            try {
                inputStream.close();
            } catch (IOException ignored) {
            }
        }
        // 返回Base64编码过的字节数组字符串
        str = Base64.getEncoder().encodeToString(data);

        return str;
    }

    /**
     * @param base64str Base64编码字符串
     * @param filePath  文件路径
     * @return 返回文件
     * @throws Exception
     */
    public static File str2File(String base64str, String filePath) throws Exception {
        if (base64str == null || base64str.trim().length() == 0) {
            return null;
        }

        Files.write(Paths.get(filePath), Base64.getDecoder().decode(base64str), StandardOpenOption.CREATE);
        File file = new File(filePath);
        return file;
    }

    /**
     * @param base64str Base64编码字符串
     * @return 返回输入流
     * @throws IOException
     */
    public static InputStream str2InputStream(String base64str) throws IOException {
        if (base64str == null || base64str.trim().length() == 0) {
            return null;
        }

        ByteArrayInputStream stream;
        byte[] bytes1 = Base64.getDecoder().decode(base64str);
        stream = new ByteArrayInputStream(bytes1);
        return stream;
    }

    public static void main(String[] args) {
        File file = new File("/Users/chaoma/Downloads/移动组工作清单.docx");
        String s = null;
        try {
            s = FileUtils.file2Str(file);

            InputStream stream = FileUtils.str2InputStream(s);
            File file1 = new File("/Users/chaoma/Downloads/123.docx");
            OutputStream os = new FileOutputStream(file1);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            stream.close();

            InputStream in = new FileInputStream(file1);

            String s1 = FileUtils.stream2Str(in);
            System.out.println(s1);
            System.out.println(s.equals(s1));

            File file2 = FileUtils.str2File(s1, "/Users/chaoma/Downloads/12.docx");
            System.out.println(file2.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(s);
    }
}

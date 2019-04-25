package com.tssss.bysj.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {
    public static String getImageStr(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        //String imgFile = "d:\\111.jpg";// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(data);
    }

    public static byte[] getImageByte(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        //String imgFile = "d:\\111.jpg";// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回Base64编码过的字节数组字符串
        return data;
    }

    /**
     * 将字符串转为图片
     *
     * @param imgStr
     * @return
     */
    public static boolean generateImage(String imgStr, String imgFile) throws Exception {
        /*// 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            String imgFilePath = imgFile;// 新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            throw e;
        }*/
        return false;
    }

}

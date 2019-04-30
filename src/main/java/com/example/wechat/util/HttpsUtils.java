package com.example.wechat.util;


import net.sf.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName: HttpsUtils HTTPS连接工具类
 * @Description: TODO
 * @Author DingDaWei
 * @date 2018/7/1715:41
 * @Version 1.0.0
 */
public class HttpsUtils {

    /**
     * 发起https请求并获取结果
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject (通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject sendRequest(String requestUrl, String requestMethod, String outputStr) {
        try {
            //设置请求参数
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = connection.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            //将返回的输入流转换成字符串
            InputStream inputStream = connection.getInputStream();
            int size = inputStream.available();
            byte[] bs = new byte[size];
            inputStream.read(bs);
            String message = new String(bs, "UTF-8");

            //将字符串转换成jsonObject对象
            JSONObject jsonObject = JSONObject.fromObject(message);

            //关闭输入流,释放资源
            inputStream.close();
            return jsonObject;
        } catch (Exception e){
            System.err.println("https请求异常："+e);
            return null;
        }
    }
}

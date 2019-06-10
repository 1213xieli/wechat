package com.example.wechat.service.impl;

import com.example.wechat.config.MyX509TrustManager;
import com.example.wechat.entity.AccessTokenInfo;
import com.example.wechat.entity.CheckinDataInfo;
import com.example.wechat.entity.UserInfo;
import com.example.wechat.service.IDataService;
import com.example.wechat.util.CommonConst;
import com.example.wechat.util.Func;
import com.example.wechat.util.HttpsUtils;
import com.example.wechat.util.WechatUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DataServiceImpl
 * time:2019/6/4
 * author:xieli
 */
@Service
public class DataServiceImpl implements IDataService {

    @Override
    public List<UserInfo> getUserInfoList() throws Exception {
        AccessTokenInfo tokenInfo = WechatUtil.getAccessToken();
        if (tokenInfo == null || Func.checkNull(tokenInfo.getAccess_token()))
            throw new Exception("无数据");

        String requestUrl = CommonConst.SIMPLES_URL.replace("ACCESS_TOKEN", tokenInfo.getAccess_token())
                .replace("DEPARTMENT_ID", CommonConst.DEPARTMENT_ID).replace("FETCH_CHILD", CommonConst.FETCH_CHILD);
        JSONObject jsonObject = HttpsUtils.sendRequest(requestUrl, "GET", null);
        String errcode = Func.parseStr(jsonObject.get("errcode"));
        if (Func.checkNull(errcode) || !errcode.equals("0"))
            return new ArrayList<>();

        JSONArray jsonArray = jsonObject.getJSONArray("userlist");
        if (jsonArray == null || jsonArray.size() <= 0)
            return new ArrayList<>();

        return JSONArray.toList(jsonArray, new UserInfo(), new JsonConfig());
    }

    @Override
    public List<CheckinDataInfo> getCheckinDataInfoList(long starttime, long endtime, List useridlist) throws Exception {
        if (useridlist == null || useridlist.size() <= 0)
            return new ArrayList<>();

        AccessTokenInfo tokenInfo = WechatUtil.getAccessToken();
        if (tokenInfo == null || Func.checkNull(tokenInfo.getAccess_token()))
            throw new Exception("无数据");

        JSONObject output = new JSONObject();
        output.put("opencheckindatatype", 3);
        // DateUtil.convertStartTime(starttime)
        // DateUtil.convertEndTime(endtime)
        output.put("starttime", 1559320721);
        output.put("endtime", 1559666321);
        JSONArray arr = new JSONArray();
        arr.addAll(useridlist);
        output.put("useridlist", arr);


//        Map<String, Object> map = new HashMap<>();
//        map.put("opencheckindatatype", 3);
//        map.put("starttime", DateUtil.convertStartTime(starttime));
//        map.put("endtime", DateUtil.convertEndTime(endtime));
//        map.put("useridlist", useridlist);


        String requestUrl = CommonConst.CHECKINOPTION_URL.replace("ACCESS_TOKEN", tokenInfo.getAccess_token());
        JSONObject jsonObject = HttpsUtils.sendRequest(requestUrl, "POST", output.toString());
        String errcode = Func.parseStr(jsonObject.get("errcode"));
        if (Func.checkNull(errcode) || !errcode.equals("0"))
            return new ArrayList<>();

        JSONArray jsonArray = jsonObject.getJSONArray("checkindata");
        if (jsonArray == null || jsonArray.size() <= 0)
            return new ArrayList<>();

        return JSONArray.toList(jsonArray, new CheckinDataInfo(), new JsonConfig());
    }

    /**
     * 下载微信图片
     * @return
     */
    @Override
    public String getmedia(String media_id) throws Exception {
        AccessTokenInfo tokenInfo = WechatUtil.getAccessToken();
        if (tokenInfo == null || Func.checkNull(tokenInfo.getAccess_token()))
            throw new Exception("无数据");

        String fileName = "";
        try {
            String requestUrl = CommonConst.MEDIA_URL.replace("ACCESS_TOKEN", tokenInfo.getAccess_token()).replace("MEDIA_ID", media_id);
//            JSONObject tokenJson=HttpsUtils.sendRequest(requestUrl, "POST",null);

            URL url = new URL(requestUrl);
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            String file_name =".jpg";
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String currentTime = sdf.format(d);

            // 目录
            String imageDirectory = CommonConst.Temp_Pic_Path + currentTime;
            File dirFile = new File(imageDirectory);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            fileName = Func.newGuid() + file_name;
            File file = new File(dirFile, fileName); // 文件
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            byte[] buf = new byte[2048];
            int length = bis.read(buf);
            while(length != -1){
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            try {
                e.printStackTrace(pw);
                pw.flush();
                sw.flush();
                sw.close();
                pw.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally{
                try {
                    if(sw!=null){
                        sw.close();
                    }
                    if(pw!=null){
                        pw.close();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return fileName;
    }


}

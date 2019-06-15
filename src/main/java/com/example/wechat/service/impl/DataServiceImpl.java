package com.example.wechat.service.impl;

import com.example.wechat.config.MyX509TrustManager;
import com.example.wechat.entity.AccessTokenInfo;
import com.example.wechat.entity.CheckinDataInfo;
import com.example.wechat.entity.EventInfo;
import com.example.wechat.entity.UserInfo;
import com.example.wechat.service.IDataService;
import com.example.wechat.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

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
        output.put("starttime", DateUtil.toUnixTimeStamp(DateUtil.convertStartTime(starttime)));
        output.put("endtime", DateUtil.toUnixTimeStamp(DateUtil.convertEndTime(endtime)));
//        output.put("starttime", 1559320721);
//        output.put("endtime", 1559666321);

        JSONArray arr = new JSONArray();
        arr.addAll(useridlist);
        output.put("useridlist", arr);


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


    @Override
    public Map queryData(String userid, String month) throws Exception {
        if (Func.checkNullOrEmpty(userid) || Func.checkNullOrEmpty(month))
            throw new Exception("未传入参数");

        Map<String, Object> result = new HashMap<>();
        getEventList(userid, month, result);
        return result;
    }

    /* *
     * @description 获取打卡数据, 并封装成显示数据
     * @author xieli
     * @date  17:18 2019/6/13
     * @param [userid, month, result]
     * @return void
     **/
    private void getEventList(String userid, String month, Map result) throws Exception
    {
        if (Func.checkNullOrEmpty(userid))
            throw new Exception("未传入用户id");

        AccessTokenInfo tokenInfo = WechatUtil.getAccessToken();
        if (tokenInfo == null || Func.checkNull(tokenInfo.getAccess_token()))
            throw new Exception("无数据");

        List useridlist = new ArrayList();
        useridlist.add(userid);

        String startTime = DateUtil.getMonthStart(Func.parseInt(month)) + " 00:00:00";
        String endTime = DateUtil.getMonthEnd(Func.parseInt(month)) + " 23:59:59";

        long starlong = DateUtil.stringToLong(startTime, DateUtil.Formate_Second);
        long endlong = DateUtil.stringToLong(endTime, DateUtil.Formate_Second);

        List<CheckinDataInfo> checkinList = this.getCheckinDataInfoList(starlong, endlong, useridlist);
        List<EventInfo> eventList = new ArrayList<>();
        EventInfo eventInfo = new EventInfo();

        for (CheckinDataInfo info : checkinList)
        {
            eventInfo = new EventInfo();
            String showCheckInTime = DateUtil.longToString(DateUtil.toTimestamp(Func.parseLong(info.getCheckin_time())), DateUtil.Formate_HMS);
            String checkDay = DateUtil.longToString(DateUtil.toTimestamp(Func.parseLong(info.getCheckin_time())), DateUtil.Formate_Day);
            String checkTime =  DateUtil.longToString(DateUtil.toTimestamp(Func.parseLong(info.getCheckin_time())), DateUtil.Formate_HM);
            eventInfo.setStart(checkDay);

            // 时间异常则标红背景
            if (!Func.checkNullOrEmpty(info.getException_type()))
            {
                eventInfo.setBackgroundColor("red");
                eventInfo.setBorderColor("red");
            }

            // 未打卡
            if (!Func.checkNullOrEmpty(info.getException_type()) && info.getException_type().equals(CommonConst.Exception_Type))
            {
                eventInfo.setTitle(info.getCheckin_type() +":"+ info.getException_type());
                eventList.add(eventInfo);
                continue;
            }

//            if (!checkTime(info, checkTime, eventInfo))
//            {
//                eventInfo.setBackgroundColor("red");
//                eventInfo.setBorderColor("red");
//            }

            eventInfo.setTitle(info.getCheckin_type() + " '" + showCheckInTime +"'");

            if (Func.checkNullOrEmpty(info.getException_type()) && info.getMediaids() != null && info.getMediaids().length >= 1 && !Func.checkNullOrEmpty(info.getMediaids()[0]))
            {
                String requestUrl = CommonConst.MEDIA_URL.replace("ACCESS_TOKEN", tokenInfo.getAccess_token()).replace("MEDIA_ID", info.getMediaids()[0]);
                eventInfo.setUrl(requestUrl);
            }

            eventList.add(eventInfo);
        }
        result.put("eventList", eventList);
    }

    /* *
     * @description 验证打卡时间
     * @author xieli
     * @date  15:57 2019/6/14
     * @param [info, checkTime, eventInfo]
     * @return boolean
     **/
    private boolean checkTime(CheckinDataInfo info, String checkTime, EventInfo eventInfo)
    {
        if (CommonConst.WorkTypeEnum.上班打卡.toString().equals(info.getCheckin_type()))
        {
            if (Func.parseDbl(checkTime) > Func.parseDbl(CommonConst.StartWork_Time))
            {
                return false;
            }
        }
        else if (CommonConst.WorkTypeEnum.下班打卡.toString().equals(info.getCheckin_type()))
        {
            if (Func.parseDbl(checkTime) < Func.parseDbl(CommonConst.EndWork_Time))
            {
                return false;
            }
        }

        return true;
    }


    @Override
    public Map initData() throws Exception {
        List<UserInfo> userlist = getUserInfoList();
        if (userlist == null || userlist.size() <= 0)
            return null;


        UserInfo defaultUser = new UserInfo();
        Map<String, List<UserInfo>> map = new HashedMap();
        for (UserInfo info : userlist) {
            if (Func.checkNull(info.getUserid()) || !checkUserName(info.getName()))
                continue;

            // 中英文转化 用户命名
            String userAllName = info.getName().replace("（","(").replace("）", ")");
            String company = userAllName.substring(userAllName.indexOf("(") + 1, userAllName.indexOf(")"));
            String username = userAllName.substring(0, userAllName.indexOf("("));

            info.setUsershowname(username);
            info.setCompany(company);

            if (info.getUserid().equals(CommonConst.defaultuserid))
                defaultUser = info;

            if (map.containsKey(company))
            {
                map.get(company).add(info);
            }
            else 
            {
                List<UserInfo> list = new ArrayList<>();
                list.add(info);
                map.put(company, list);
            }
        }

//        List useridlist = new ArrayList();
//        useridlist.add(defaultUser.getUserid());
//
//        String startTime = DateUtil.getMonthStart() + " 00:00:00";
//        String endTime = DateUtil.getMonthEnd() + " 23:59:59";
//
//        long starlong = DateUtil.stringToLong(startTime, DateUtil.Formate_Second);
//        long endlong = DateUtil.stringToLong(endTime, DateUtil.Formate_Second);
//
//        List<CheckinDataInfo> checkinList = this.getCheckinDataInfoList(starlong, endlong, useridlist);
//        List<EventInfo> eventList = new ArrayList<>();
//        EventInfo eventInfo = new EventInfo();
//        for (CheckinDataInfo info : checkinList)
//        {
//            eventInfo = new EventInfo();
//            String checkTime = DateUtil.longToString(DateUtil.toTimestamp(Func.parseLong(info.getCheckin_time())), DateUtil.Formate_HMS);
//            String checkDay = DateUtil.longToString(DateUtil.toTimestamp(Func.parseLong(info.getCheckin_time())), DateUtil.Formate_Day);
//            eventInfo.setTitle(info.getCheckin_type() + " '" + checkTime +"'");
//            eventInfo.setStart(checkDay);
//            eventList.add(eventInfo);
//        }

        Map<String, Object> result = new HashMap<>();
        getEventList(defaultUser.getUserid(), null, result);
        result.put("defaultUser", defaultUser);
        result.put("companyList", map);

        return result;
    }

    /* *
     * @description 检查用户名： 谢李（南谷外包）
     * @author xieli
     * @date  9:14 2019/6/12
     * @param [username]
     * @return boolean
     **/
    private boolean checkUserName(String username) throws Exception
    {
        if (Func.checkNull(username))
            return false;

        if (username.contains("(") && username.contains(")"))
            return true;

        if (username.contains("（") && username.contains("（"))
            return true;

        return false;
    }

}

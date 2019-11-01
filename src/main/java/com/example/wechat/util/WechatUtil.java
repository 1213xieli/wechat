package com.example.wechat.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.wechat.entity.AccessTokenInfo;
import org.apache.commons.lang.RandomStringUtils;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @Auther: xieli
 * @Date: 2019/4/30 14:39
 * @Description:
 */
public class WechatUtil {

    /* *
     * @description 获得tokenid
     * @author xieli
     * @date  17:10 2019/6/4
     * @param []
     * @return com.example.wechat.entity.AccessTokenInfo
     **/
    public static AccessTokenInfo getAccessToken() {
        AccessTokenInfo accessToken = null;
        String requestUrl = CommonConst.ACCESS_TOKEN_URL.replace("CORPID", CommonConst.CORPID).replace("CORPSECRET", CommonConst.CORPSECRET);
        String jsonStr = HttpsUtils.sendRequest(requestUrl,"GET",null);
        // 如果请求成功
        if (!Func.checkNullOrEmpty(jsonStr)) {
            try {
                accessToken = (AccessTokenInfo) JSON.parseObject(jsonStr, AccessTokenInfo.class);
            } catch (Exception e) {
                accessToken = null;
                // 获取token失败 , jsonObject.getInt("errcode"), jsonObject.getString("errmsg")
                System.out.println("获取token失败 errcode:{} errmsg:{}");
            }
        }
        return accessToken;
    }


    public static String getAccessTicket(String accessToken) {

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
        String jsonStr = HttpsUtils.sendRequest(requestUrl, "GET", null);

        String ticket = "";
        // 如果请求成功
        if (!Func.checkNullOrEmpty(jsonStr)) {
            try {
                JSONObject jsonObject = JSON.parseObject(jsonStr);
                ticket = jsonObject.getString("ticket");
//                ticket = jsonStr.getString("ticket").toString();

            } catch (Exception e) {

                // 获取token失败 , jsonObject.getInt("errcode"), jsonObject.getString("errmsg")
                System.out.println("获取token失败 errcode:{} errmsg:{}");
            }
        }
        return ticket;
    }


    public static String[] getSign(String jsapi_ticket, String u)
            throws NoSuchAlgorithmException {
        String[] data = new String[3];
        Long timestamp = System.currentTimeMillis() / 1000;
        String noncestr = RandomStringUtils.randomAlphanumeric(16);

        String url = u;
        String shaStr = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
                + url;
        System.out.println(shaStr);
        return data;
    }

    public String sort(String ticket, String noncestr, String timestamp, String url) {
        String[] strArray = {ticket, noncestr, timestamp, url};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }

        return sb.toString();
    }


}

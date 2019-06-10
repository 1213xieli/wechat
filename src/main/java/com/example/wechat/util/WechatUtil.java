package com.example.wechat.util;

import com.example.wechat.entity.AccessTokenInfo;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
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
        JSONObject jsonObject = HttpsUtils.sendRequest(requestUrl,"GET",null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = (AccessTokenInfo) JSONObject.toBean(jsonObject, AccessTokenInfo.class);
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败 , jsonObject.getInt("errcode"), jsonObject.getString("errmsg")
                System.out.println("获取token失败 errcode:{} errmsg:{}");
            }
        }
        return accessToken;
    }


    public static String getAccessTicket(String accessToken) {

        String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
        JSONObject jsonObject = HttpsUtils.sendRequest(requestUrl, "GET", null);

        String ticket = "";
        // 如果请求成功
        if (null != jsonObject) {
            try {
                ticket = jsonObject.getString("ticket").toString();

            } catch (JSONException e) {

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
//        String sign = sha1(shaStr);

//        System.out.println("sigin="+sign);
//        data[0] = timestamp.toString();
//        data[1] = noncestr;
//        data[2] = sign;
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


//    public static String sha1(String str) {
//        try {
//            MessageDigest digest = MessageDigest.getInstance("SHA-1");
//            digest.update(str.getBytes());
//            byte messageDigest[] = digest.digest();
//            // Create Hex String
//            StringBuffer hexString = new StringBuffer();
//            // 字节数组转换为 十六进制 数
//            for (int i = 0; i < messageDigest.length; i++) {
//                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
//                if (shaHex.length() < 2) {
//                    hexString.append(0);
//                }
//                hexString.append(shaHex);
//            }
//            return hexString.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

}

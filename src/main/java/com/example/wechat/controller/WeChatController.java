package com.example.wechat.controller;

import com.example.wechat.entity.AccessToken;
import com.example.wechat.util.WechatUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @Auther: 周作为
 * @Date: 2019/4/30 10:04
 * @Description:
 */
@RestController
public class WeChatController {

    private String TOKEN = "good";


    @GetMapping("/wechat/test")
    public String test(@RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("echostr") String echostr) {

        //排序
        String sortString = sort(TOKEN, timestamp, nonce);
        //加密
        String myString = sha1(sortString);
        //校验
        if (myString != null && myString != "" && myString.equals(signature)) {
            System.out.println("签名校验通过");
            //如果检验成功原样返回echostr，微信服务器接收到此输出，才会确认检验完成。
            return echostr;
        } else {
            System.out.println("签名校验失败");
            return "";
        }
    }

    @GetMapping("/wechat/hello")
    public String getTest() {
        return "helloWorld";
    }


    @GetMapping("/wechat/getSign")
    @ResponseBody
    public String[] getSign(String url) {
        AccessToken accessToken = WechatUtil.getAccessToken();
        System.out.println(accessToken.getAccess_token());
        if (accessToken != null && accessToken.getAccess_token() != null) {
            String ticket = WechatUtil.getAccessTicket(accessToken.getAccess_token());
            System.out.println(ticket);
            if (ticket != null && !"".equals(ticket)) {
                try {
                    String[] data = WechatUtil.getSign(ticket,url);

                    return data;
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }


    public String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }

        return sb.toString();
    }

    public String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}


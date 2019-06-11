package com.example.wechat.controller;

import com.example.wechat.entity.AccessTokenInfo;
import com.example.wechat.entity.CheckinDataInfo;
import com.example.wechat.entity.UserInfo;
import com.example.wechat.service.IDataService;
import com.example.wechat.util.WechatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: xieli
 * @Date: 2019/4/30 10:04
 * @Description:
 */
@Api(description = "微信接口")
@RestController
@RequestMapping("/wechat")
public class WeChatController {

    @Autowired
    private IDataService dataService;

    private String TOKEN = "good";


    @GetMapping("/test")
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

    @ApiOperation(value = "测试value" ,  notes="测试note")
    @GetMapping("/hello")
    public String getTest() {
        return "helloWorld";
    }

    @GetMapping("/getData")
    public String getData() {
        try {
            List<UserInfo> list = dataService.getUserInfoList();
            UserInfo xileiInfo = new UserInfo();
            for (UserInfo user: list ) {
                System.out.println(user.getName());
                if (user.getUserid().equals("xieli999"))
                    xileiInfo = user;
            }

            List username = new ArrayList();
            username.add("xieli999");
            username.add("xieli");

            List<CheckinDataInfo> checkDataList = dataService.getCheckinDataInfoList(1559320721, 1559666321, username);
            for (CheckinDataInfo info: checkDataList) {
                System.out.println(info.getMediaids());
            }

            String str = dataService.getmedia(checkDataList.get(0).getMediaids()[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "helloWorld";
    }

    @GetMapping("/getSign")
    @ResponseBody
    public String[] getSign(String url) {
        AccessTokenInfo accessToken = WechatUtil.getAccessToken();
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


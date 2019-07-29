package com.example.wechat.controller;

import com.example.wechat.entity.AccessTokenInfo;
import com.example.wechat.entity.ResponseEntity;
import com.example.wechat.service.IDataService;
import com.example.wechat.util.WechatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * @Auther: xieli
 * @Date: 2019/4/30 10:04
 * @Description:
 */
@Api(description = "微信打卡接口")
@RestController
@RequestMapping("/wechat")
public class WeChatController {

    @Autowired
    private IDataService dataService;

    private String TOKEN = "good";

    @ApiOperation(value = "初始化数据" ,  notes="初始化数据")
    @GetMapping("/initData")
    public ResponseEntity<?> initData() {
        try {
            Map<String, Object> datamap = dataService.initData();
            return ResponseEntity.buildSuccess(datamap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return ResponseEntity.buildSuccess(null);
    }

    @ApiOperation(value = "筛选条件查询数据" ,  notes="筛选条件查询数据")
    @GetMapping("/queryData")
    public ResponseEntity<?> queryData(@RequestParam("userid") String userid, @RequestParam("month") String month) {
        try {
            Map<String, Object> datamap = dataService.queryData(userid, month);
            return ResponseEntity.buildSuccess(datamap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return ResponseEntity.buildSuccess(null);
    }


    @ApiOperation(value = "getData" ,  notes="getData")
    @GetMapping("/getData")
    public String getData() {
        try {
            String str = dataService.getmedia("WWCISP_TGB90yEQE02DFmVnlPeVJewD5C2eInvR9-cxL7rUHvzhj9YpiXUiHrv00euhky-B_6gvf9mG_MU-t_3eILBGhw");
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


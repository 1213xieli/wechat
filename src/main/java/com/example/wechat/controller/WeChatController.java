package com.example.wechat.controller;

import com.example.wechat.entity.AccessTokenInfo;
import com.example.wechat.entity.ResponseEntity;
import com.example.wechat.service.IDataService;
import com.example.wechat.util.WechatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> queryData(@RequestParam("userid") String userid,
                                       @RequestParam("month") String month, @RequestParam("year") String year) {
        try {
            Map<String, Object> datamap = dataService.queryData(userid, year, month);
            return ResponseEntity.buildSuccess(datamap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return ResponseEntity.buildSuccess(null);
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

}


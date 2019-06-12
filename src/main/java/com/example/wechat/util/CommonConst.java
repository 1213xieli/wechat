package com.example.wechat.util;

import java.io.File;

/**
 * 常用变量
 * time:2019/6/4
 * author:xieli
 */
public class CommonConst {


    public final static String CORPID = "wx1f7ff047c6a860ed";
    public final static String CORPSECRET = "L0dwWwQo-3YPEp7jzAdIyePQI8_32CAaIEIKtNSsK_8";

    //获取access_token 接口地址   Get
    public final static String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET";
    // 获取外包成员
    public final static String SIMPLES_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD";
    // 获取打卡接口
    public final static String CHECKINOPTION_URL = "https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckindata?access_token=ACCESS_TOKEN";
    // 获取照片
    public final static String MEDIA_URL = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

    public final static String DEPARTMENT_ID = "180330"; // 部门id,外包人员，固定id
    public final static String FETCH_CHILD = "1"; // 全部

    public static final String Temp_Pic_Path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" +
            File.separator + "resources" + File.separator + "static" + File.separator + "picture"+ File.separator;

    public static final String defaultuserid= "xieli999";

}

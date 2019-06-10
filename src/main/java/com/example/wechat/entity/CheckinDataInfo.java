package com.example.wechat.entity;

import java.io.Serializable;

/**
 * CheckinDataInfo
 * 打卡记录实体
 * time:2019/6/5
 * author:xieli
 */
@lombok.Data
public class CheckinDataInfo implements Serializable {

    private String userid; // "xieli999",
    private String groupname;// "外包人员打卡",
    private String checkin_type;// "上班打卡",
    private String exception_type;
    private String checkin_time;// 1559350314,
    private String location_title;// "正邦集团",
    private String location_detail;// "江西省南昌市青山湖区艾溪湖一路569号",
    private String wifiname;
    private String notes;
    private String wifimac;
    private String[]  mediaids;

}

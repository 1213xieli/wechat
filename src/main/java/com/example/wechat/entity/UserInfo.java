package com.example.wechat.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * UserInfo 用户信息
 * time:2019/6/4
 * author:xieli
 */
@Data
public class UserInfo implements Serializable {


    private String userid;
    private String name;
    private String[] department;
    private String usershowname;
    private String company;
    private String extendone;


}

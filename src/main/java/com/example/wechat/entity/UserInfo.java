package com.example.wechat.entity;

import java.io.Serializable;

/**
 * UserInfo 用户信息
 * time:2019/6/4
 * author:xieli
 */
public class UserInfo implements Serializable {

    private String userid;
    private String name;
    private String[] department;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getDepartment() {
        return department;
    }

    public void setDepartment(String[] department) {
        this.department = department;
    }
}

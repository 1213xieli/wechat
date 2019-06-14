package com.example.wechat.service;

import com.example.wechat.entity.CheckinDataInfo;
import com.example.wechat.entity.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * IDataService 数据业务层Service
 * time:2019/6/4
 * author:xieli
 */
public interface IDataService {

    /* *
     * @description 获取用户数据列表
     * @author xieli
     * @date  9:17 2019/6/5
     * @param []
     * @return java.util.List<com.example.wechat.entity.UserInfo>
     **/
    List<UserInfo> getUserInfoList() throws Exception;

    /* *
     * @description 获取打卡记录列表
     * @author xieli
     * @date  9:39 2019/6/5
     * @param []
     * @return java.util.List<com.example.wechat.entity.CheckinDataInfo>
     **/
    List<CheckinDataInfo> getCheckinDataInfoList(long starttime, long endtime,List useridlist) throws Exception;

    /* *
     * @description 获取微信打卡图片
     * @author xieli
     * @date  10:27 2019/6/5
     * @param [token, media_id]
     * @return java.lang.String
     **/
    String getmedia(String media_id)  throws Exception;

    /* *
     * @description 初始化数据
     * @author xieli
     * @date  8:54 2019/6/12
     * @param []
     * @return java.lang.String
     **/
    Map initData()throws Exception;

    /* *
     * @description 筛选条件查询数据
     * @author xieli
     * @date  17:01 2019/6/13
     * @param [userid, month]
     * @return java.util.Map
     **/
    Map queryData(String userid, String month) throws Exception;
}

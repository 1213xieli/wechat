package com.example.wechat.entity;

/* *
 * @description 日程表
 * @author xieli
 * @date  15:12 2019/6/13
 * @param
 * @return
 **/
public class ScheduleInfo {

  private String id;
  private String handerid;
  private String handername;
  private String title;
  private String type;
  private String editable;
  private java.sql.Date starttime;
  private java.sql.Date endtime;
  private java.sql.Date createtime;
  private String createuserid;
  private String extendone;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getHanderid() {
    return handerid;
  }

  public void setHanderid(String handerid) {
    this.handerid = handerid;
  }


  public String getHandername() {
    return handername;
  }

  public void setHandername(String handername) {
    this.handername = handername;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getEditable() {
    return editable;
  }

  public void setEditable(String editable) {
    this.editable = editable;
  }


  public java.sql.Date getStarttime() {
    return starttime;
  }

  public void setStarttime(java.sql.Date starttime) {
    this.starttime = starttime;
  }


  public java.sql.Date getEndtime() {
    return endtime;
  }

  public void setEndtime(java.sql.Date endtime) {
    this.endtime = endtime;
  }


  public java.sql.Date getCreatetime() {
    return createtime;
  }

  public void setCreatetime(java.sql.Date createtime) {
    this.createtime = createtime;
  }


  public String getCreateuserid() {
    return createuserid;
  }

  public void setCreateuserid(String createuserid) {
    this.createuserid = createuserid;
  }


  public String getExtendone() {
    return extendone;
  }

  public void setExtendone(String extendone) {
    this.extendone = extendone;
  }

}

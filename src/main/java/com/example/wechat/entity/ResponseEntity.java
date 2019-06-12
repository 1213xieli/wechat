package com.example.wechat.entity;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * ResponseEntity
 * time:2019/6/12
 * author:xieli
 */
public class ResponseEntity<T> implements Serializable {
    private static final long serialVersionUID = -2305573436444343979L;
    private int status;
    private List<Message> message;
    private T data;
    private long date = (new Date()).getTime();

    public ResponseEntity() {
    }

    public static <T> ResponseEntity build(int status, Message message) {
        ResponseEntity<T> result = new ResponseEntity();
        result.setStatus(status);
        result.setMessage(Arrays.asList(message));
        return result;
    }

    public static <T> ResponseEntity build(int status, Message message, T t) {
        ResponseEntity result = new ResponseEntity();
        result.setStatus(status);
        result.setData(t);
        result.setMessage(Arrays.asList(message));
        return result;
    }

    public static <T> ResponseEntity build(int status, String msg, T t) {
        ResponseEntity<T> result = new ResponseEntity();
        result.setStatus(status);
        result.setData(t);
        if (StringUtils.isNotEmpty(msg)) {
            Message mess = new Message();
            mess.setCode(String.valueOf(status));
            mess.setMessage(msg);
            result.setMessage(Arrays.asList(mess));
        }

        return result;
    }

    public static <T> ResponseEntity build(int status, List<Message> messages) {
        ResponseEntity<T> result = new ResponseEntity();
        result.setStatus(status);
        result.setMessage(messages);
        return result;
    }

    public static <T> ResponseEntity buildCodes(int status, String... codes) {
        ResponseEntity<T> result = new ResponseEntity();
        result.setStatus(status);
        if (codes != null) {
            List<Message> messages = new ArrayList();
            String[] var4 = codes;
            int var5 = codes.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String code = var4[var6];
                messages.add(Message.message(code, code));
            }

            result.setMessage(messages);
        }

        return result;
    }

    public static <T> ResponseEntity buildSuccess(T data) {
        ResponseEntity<T> result = new ResponseEntity();
        result.setStatus(200);
        result.setData(data);
        return result;
    }

    public int getStatus() {
        return this.status;
    }

    public List<Message> getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public long getDate() {
        return this.date;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public void setMessage(final List<Message> message) {
        this.message = message;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public void setDate(final long date) {
        this.date = date;
    }
}

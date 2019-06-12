package com.example.wechat.entity;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * Message
 * time:2019/6/12
 * author:xieli
 */
public class Message implements Serializable {
    private String code;
    private String message;

    public Message() {
    }

    public static Message message(String code, String message) {
        Message apiMessage = new Message();
        apiMessage.setCode(code);
        apiMessage.setMessage(message);
        return apiMessage;
    }

    public static Message message(String code, String message, Object... args) {
        Message apiMessage = new Message();
        apiMessage.setCode(code);
        if (message != null) {
            String errorMessage = MessageFormat.format(message, args);
            apiMessage.setMessage(errorMessage);
        }

        return apiMessage;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}

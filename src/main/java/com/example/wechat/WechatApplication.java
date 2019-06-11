package com.example.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WechatApplication {

    private static final Logger logger = LoggerFactory.getLogger(WechatApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WechatApplication.class, args);
        logger.info("Swagger程序启动成功!");
    }
}


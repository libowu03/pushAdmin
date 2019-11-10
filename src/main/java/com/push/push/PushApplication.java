package com.push.push;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PushApplication {

    public static void main(String[] args) {
        SpringApplication.run(PushApplication.class, args);
        System.out.println("===================push已启动======================");
    }

}

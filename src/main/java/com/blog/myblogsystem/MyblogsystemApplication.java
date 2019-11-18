package com.blog.myblogsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan("com.blog.myblogsystem.dao")
public class MyblogsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyblogsystemApplication.class, args);
    }

}

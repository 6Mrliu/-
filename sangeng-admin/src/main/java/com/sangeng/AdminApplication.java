package com.sangeng;

import com.sangeng.utils.OssUtils;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("com.sangeng.mapper")
@EnableConfigurationProperties
public class AdminApplication {
    @Autowired
    OssUtils ossUtils;
    public static void main(String[] args) {
        SpringApplication.run( AdminApplication.class, args);
    }
}
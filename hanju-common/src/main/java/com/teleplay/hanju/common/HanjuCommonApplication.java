package com.teleplay.hanju.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.teleplay.hanju.*")
public class HanjuCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanjuCommonApplication.class, args);
    }

}

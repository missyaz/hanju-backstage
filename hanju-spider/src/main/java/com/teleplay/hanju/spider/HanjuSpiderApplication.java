package com.teleplay.hanju.spider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.teleplay.hanju.**.mapper")
@ComponentScan("com.teleplay.hanju")
public class HanjuSpiderApplication {
    public static void main(String[] args) {
        SpringApplication.run(HanjuSpiderApplication.class, args);
    }
}

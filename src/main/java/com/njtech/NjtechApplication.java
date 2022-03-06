package com.njtech;

import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.njtech.dao"})
public class NjtechApplication {

    public static void main(String[] args) {
        SpringApplication.run(NjtechApplication.class, args);
    }

}

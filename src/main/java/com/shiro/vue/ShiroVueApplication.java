package com.shiro.vue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: QuincySu
 * Date: 2018-10-10
 * Time: 16:22
 */
@MapperScan("com.shiro.vue.mapper")
@SpringBootApplication
public class ShiroVueApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShiroVueApplication.class, args);
    }
}

package com.zhupeng;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@MapperScan("com.zhupeng.mapper")
public class MybatisplusApplication {

    public static void main(String[] args) {
        log.info("启动中……");
        SpringApplication.run(MybatisplusApplication.class,args);
        log.info("启动完成！");
    }
}

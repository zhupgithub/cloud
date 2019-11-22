package com.zhupeng;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Service2Application {

    public static void main(String[] args) {
        log.info("启动中……");
        SpringApplication.run(Service2Application.class,args);
        log.info("启动完成！");
    }
}

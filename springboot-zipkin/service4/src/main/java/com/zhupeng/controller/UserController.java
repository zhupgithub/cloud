package com.zhupeng.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private CloseableHttpClient closeableHttpClient;

    @RequestMapping("test4")
    public String test4(String name){

        log.info("服务4" + name);

        return name + "服务4";
    }
}

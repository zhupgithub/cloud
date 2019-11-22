package com.zhupeng.controller;

import com.zhupeng.utils.HttpClientUtils;
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

    @RequestMapping("test1")
    public String test1(String name){

        log.info("服务1" + name);
        String requestUrl = "http://localhost:9086/test2?name=zhupneg";
        String requestJson = null;
        String httpMethod = "Get";


        String result = HttpClientUtils.httpRequest(closeableHttpClient , requestUrl, requestJson, httpMethod);

        log.info("服务1 ， 返回结果" + result);

        return name + "服务1";
    }
}

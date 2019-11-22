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

    @RequestMapping("test2")
    public String test2(String name){

        log.info("服务2" + name);
        String requestUrl = "http://localhost:9087/test3?name=zhupneg";
        String requestJson = null;
        String httpMethod = "Get";


        String result = HttpClientUtils.httpRequest(closeableHttpClient , requestUrl, requestJson, httpMethod);

        log.info("服务2 ， 返回结果" + result);


        String requestUrl2 = "http://localhost:9088/test4?name=zhupneg";
        String requestJson2 = null;
        String httpMethod2 = "Get";


        String result2 = HttpClientUtils.httpRequest(closeableHttpClient , requestUrl2, requestJson2, httpMethod2);

        log.info("服务2 ， 返回结果" + result2);

        return name + "服务2";
    }
}

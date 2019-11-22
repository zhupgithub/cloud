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

    @RequestMapping("test3")
    public String test3(String name){

        log.info("服务3" + name);
        String requestUrl2 = "http://localhost:9088/test4?name=zhupneg";
        String requestJson2 = null;
        String httpMethod2 = "Get";


        String result = HttpClientUtils.httpRequest(closeableHttpClient , requestUrl2, requestJson2, httpMethod2);

        log.info("服务3 ， 返回结果" + result);

        return name + "服务3";
    }
}

package com.zhupeng.service.impl;

import com.zhupeng.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Override
    public void test() {
        log.info("testService =====Test");
    }
}

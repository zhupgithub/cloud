package com.zhupeng.config;

import com.alibaba.fastjson.JSONObject;
import com.zhupeng.entity.ApplicationMessage;
import com.zhupeng.utils.ApplicationProvider;
import lombok.experimental.UtilityClass;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@UtilityClass
public class ApplicationSender {

    public void send(@Valid ApplicationMessage message){

        ApplicationProvider.getApplicationContext().publishEvent(JSONObject.toJSONString(message));
    }
}

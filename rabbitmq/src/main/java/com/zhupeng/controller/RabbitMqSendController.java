package com.zhupeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhupeng.contant.RabbitMqContant;
import com.zhupeng.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * https://blog.csdn.net/qq_35387940/article/details/100514134
 */
@RestController
@Slf4j
public class RabbitMqSendController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("addUser")
    public String addUser(String routingkey){
        User user = new User();
        user.setUsername("zhuepng");
        user.setPassword("123");
        user.setAge(11);
        user.setBirthday(new Date());

        log.info("添加用户:" + user.toString());

//        rabbitTemplate.convertAndSend(RabbitMqContant.USER_DIRECT_EXCHANGE ,
//                RabbitMqContant.ADD_USER_ROUTINGKEY , JSONObject.toJSONString(user));

        rabbitTemplate.convertAndSend(RabbitMqContant.USER_FANOUT_EXCHANGE , null , JSONObject.toJSONString(user));

        return "sucess";
    }

    @PostMapping("deleteUser")
    public String deleteUser(){
        User user = new User();
        user.setUsername("zhuepng");
        user.setPassword("123");
        user.setAge(11);
        user.setBirthday(new Date());

        log.info("删除用户:" + user.toString());

//        rabbitTemplate.convertAndSend(RabbitMqContant.USER_DIRECT_EXCHANGE ,
//                RabbitMqContant.DELETE_USER_ROUTINGKEY , JSONObject.toJSONString(user));

        rabbitTemplate.convertAndSend(RabbitMqContant.USER_TOPIC_EXCHANGE ,
                RabbitMqContant.DELETE_USER_ROUTINGKEY , JSONObject.toJSONString(user));
        return "success";
    }


    @RequestMapping("randleQueueTest")
    public String randleQueue(){
        User user = new User();
        user.setUsername("zhuepng");
        user.setPassword("123");
        user.setAge(11);
        user.setBirthday(new Date());

        rabbitTemplate.convertAndSend(RabbitMqContant.USER_DIRECT_EXCHANGE ,
                "test", JSONObject.toJSONString(user));
        return "success";
    }


    @RequestMapping("jsonTest")
    public String jsonTest(){
        User user = new User();
        user.setUsername("zhuepng");
        user.setPassword("123");
        user.setAge(11);
        user.setBirthday(new Date());

        rabbitTemplate.convertAndSend(RabbitMqContant.TEST_DIRECT_EXCHANGE ,
                RabbitMqContant.TEST_DIRECT_ROUTINGKEY, user , new CorrelationData(11 + ""));
        return "success";
    }

}

package com.zhupeng.controller;

import com.zhupeng.constant.RabbitMqContant;
import com.zhupeng.entity.User;
import com.zhupeng.receiver.UserRabbitReceiver;
import com.zhupeng.sender.UserSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@Slf4j
public class UserController {

    @Autowired
    UserSender userSender;

    @RequestMapping("deleteUser")
    public String deleteUser(){
        String userId = UUID.randomUUID().toString();

        User user = new User();
        user.setId(userId);
        user.setUsername("zhuepng");
        user.setPassword("123");
        user.setBirthday(new Date());

        userSender.send(user , RabbitMqContant.USER_DIRECT_EXCHANGE ,
                RabbitMqContant.DELETE_USER_ROUTINGKEY , new CorrelationData(userId));

        return userId;
    }
}

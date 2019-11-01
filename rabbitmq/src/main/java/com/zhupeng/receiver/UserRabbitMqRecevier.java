package com.zhupeng.receiver;

import com.rabbitmq.client.Channel;
import com.zhupeng.contant.RabbitMqContant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRabbitMqRecevier {

//    @RabbitListener(queues = RabbitMqContant.ADD_USER_QUEUE)
//    public void addUserRecevier(String message){
//        log.info("接收到添加用户的消息：" + message);
//    }

    @RabbitListener(queues = RabbitMqContant.DELETE_USER_QUEUE)
    public void deleteUserRecevier(String message){
        log.info("接收到删除用户的消息：" + message);
    }

    @RabbitListener(queues = RabbitMqContant.USER_QUEUE)
    public void userRecevier(String message){
        log.info("接收到用户操作的消息：" + message);
    }

}

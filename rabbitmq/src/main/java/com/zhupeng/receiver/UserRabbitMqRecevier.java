package com.zhupeng.receiver;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.zhupeng.contant.RabbitMqContant;
import com.zhupeng.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class UserRabbitMqRecevier {

//    @RabbitListener(queues = RabbitMqContant.ADD_USER_QUEUE)
//    public void addUserRecevier(String message){
//        log.info("接收到添加用户的消息：" + message);
//    }

    @RabbitListener(queues = RabbitMqContant.DELETE_USER_QUEUE)
    public void deleteUserRecevier(String message){
        log.info("接收到删除用户的消息111111：" + message);
    }

    @RabbitListener(queues = RabbitMqContant.DELETE_USER_QUEUE)
    public void deleteUserRecevier2(String message){
        log.info("接收到删除用户的消息2222222：" + message);
    }

    @RabbitListener(queues = RabbitMqContant.USER_QUEUE)
    public void userRecevier(String message){
        log.info("接收到用户操作的消息33333：" + message);
    }

    @RabbitListener(bindings = @QueueBinding(
           value = @Queue(durable = "true",autoDelete = "true"),
            exchange = @Exchange(value = RabbitMqContant.USER_DIRECT_EXCHANGE),
            key = "test"
    ))
    public void test(Message message){
        try {
            String userStr = new String(message.getBody() , "UTF-8");

            User user  =JSONObject.parseObject(userStr , User.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("随机生成队列");
    }

}

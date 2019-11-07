package com.zhupeng.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class UserSender implements RabbitTemplate.ConfirmCallback, ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Serclet的inti()方法。
     * 被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
     */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息是否到达Exchange:{}", ack == true ? "消息成功到达Exchange" : "消息到达Exchange失败");
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息报文:{}", new String(message.getBody()));
        log.info("消息编号:{}", replyCode);
        log.info("描述:{}", replyText);
        log.info("交换机名称:{}", exchange);
        log.info("路由名称:{}", routingKey);
    }


    public void send(Object msg , String exchange , String routingKey , CorrelationData correlationId){

        System.out.println("开始发送消息 : " + msg.toString());
        rabbitTemplate.convertSendAndReceive(exchange, routingKey, msg, correlationId);
    }
}

package com.zhupeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhupeng.config.ApplicationSender;
import com.zhupeng.contant.RabbitMqContant;
import com.zhupeng.entity.Address;
import com.zhupeng.entity.ApplicationMessage;
import com.zhupeng.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.amqp.rabbit.connection.CorrelationData;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 注意：当virtualHost不存在时，启动项目会失败
 * ①消息推送到server，但是在server里找不到交换机
 * ②消息推送到server，找到交换机了，但是没找到队列
 * ③消息推送到sever，交换机和队列啥都没找到
 * ④消息推送成功
 */
@RestController
@RequestMapping("rabbitTest")
@Slf4j
public class RabbitMqSendCallbackTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //消息推送到server，但是在server里找不到交换机
    //消息推送到sever，交换机和队列啥都没找到
    @RequestMapping("noExistExchange")
    public String noExistExchange(){
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(String.valueOf(System.currentTimeMillis()));

        Map<String , Object> map = new HashMap<>();

        map.put("username" , "zhupeng");
        map.put("password" , "123");
        map.put("birthday" ,  new Date());
        map.put("age" ,  123);
        map.put("messageId" , correlationData.getId());

        rabbitTemplate.convertAndSend("noExistExchange" , RabbitMqContant.DELETE_USER_ROUTINGKEY , map);

        /**
         * ConfirmCallback相关数据correlationData ：null
         * ConfirmCallback确认情况ack ：false
         * ConfirmCallback原因cause ：channel error; protocol method: #method<channel.close>
         *  (reply-code=404, reply-text=NOT_FOUND - no exchange 'noExistExchange' in vhost '/', class-id=60, method-id=40)
         */
        return "success";
    }

    // 消息推送到server，找到交换机了，但是没找到队列
    @RequestMapping("noExistQueue")
    public String noExistQueue(){
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(String.valueOf(System.currentTimeMillis()));

        Map<String , Object> map = new HashMap<>();

        map.put("username" , "zhupeng");
        map.put("password" , "123");
        map.put("birthday" ,  new Date());
        map.put("age" ,  123);
        map.put("messageId" , correlationData.getId());

        rabbitTemplate.convertAndSend(RabbitMqContant.TEST_FANOUT_EXCHANGE , RabbitMqContant.DELETE_USER_ROUTINGKEY , map);

        /**
         * ReturnCallback:     消息：(Body:'{birthday=Fri Nov 01 09:38:26 CST 2019, password=123, messageId=1572572306819, age=123, username=zhupeng}
         * ' MessageProperties [headers={}, contentType=application/x-java-serialized-object, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, deliveryTag=0])
         * ReturnCallback:     回应码：312
         * ReturnCallback:     回应信息：NO_ROUTE
         * ReturnCallback:     交换机：test_fanout_exchange
         * ReturnCallback:     路由键：delete.user.routingkey
         * ConfirmCallback相关数据correlationData ：null
         * ConfirmCallback确认情况ack ：true
         * ConfirmCallback原因cause ：null
         */
        return "success";
    }

    //消息推送到server，找到交换机了，但是没找到routingkey(与 消息推送到server，找到交换机了，但是没找到队列 一样)
    @RequestMapping("noExistRoutingKey")
    public String noExistRoutingKey(){
        Map<String , Object> map = new HashMap<>();

        map.put("username" , "zhupeng");
        map.put("password" , "123");
        map.put("birthday" ,  new Date());
        map.put("age" ,  123);

        rabbitTemplate.convertAndSend(RabbitMqContant.USER_TOPIC_EXCHANGE ,
                "noExistRoutingKey" , map);

        /**
         * ReturnCallback:     消息：(Body:'{birthday=Fri Nov 01 09:38:26 CST 2019, password=123, messageId=1572572306819, age=123, username=zhupeng}
         * ' MessageProperties [headers={}, contentType=application/x-java-serialized-object, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, deliveryTag=0])
         * ReturnCallback:     回应码：312
         * ReturnCallback:     回应信息：NO_ROUTE
         * ReturnCallback:     交换机：test_fanout_exchange
         * ReturnCallback:     路由键：delete.user.routingkey
         * ConfirmCallback相关数据correlationData ：null
         * ConfirmCallback确认情况ack ：true
         * ConfirmCallback原因cause ：null
         */
        return "success";
    }


    //消息推送成功
    @RequestMapping("sucess")
    public String sucess(String routingkey){
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(String.valueOf(System.currentTimeMillis()));

        Map<String , Object> map = new HashMap<>();

        map.put("username" , "zhupeng");
        map.put("password" , "123");
        map.put("birthday" ,  new Date());
        map.put("age" ,  123);
        map.put("messageId" , correlationData.getId());
        rabbitTemplate.convertAndSend(RabbitMqContant.USER_FANOUT_EXCHANGE , null , map);

        /**
         * ConfirmCallback相关数据correlationData ：null
         * ConfirmCallback确认情况ack ：true
         * ConfirmCallback原因cause ：null
         */
        return "sucess";
    }


    //消息推送成功
    @RequestMapping("testAck")
    public String testAck(){
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(String.valueOf(System.currentTimeMillis()));

        Map<String , Object> map = new HashMap<>();

        map.put("username" , "zhupeng");
        map.put("password" , "123");
        map.put("birthday" ,  new Date());
        map.put("age" ,  123);
        map.put("messageId" , correlationData.getId());
        rabbitTemplate.convertAndSend(RabbitMqContant.USER_DIRECT_EXCHANGE ,
                RabbitMqContant.TEST_ACK_ROUTINGKEY , map , new CorrelationData(22 + "ack============="));

        /**
         * ConfirmCallback相关数据correlationData ：null
         * ConfirmCallback确认情况ack ：true
         * ConfirmCallback原因cause ：null
         */
        return "sucess";
    }

    @RequestMapping("sender")
    public String sender(){
        Address address = new Address("source" , "123号" , "zhupeng");

        address.setMessageType(ApplicationMessage.messageType.TEST);

        ApplicationSender.send(address);

        return "success";

    }
}

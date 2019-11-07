package com.zhupeng.config;

import com.zhupeng.receiver.TestAckRabbitMqRecevier;
import com.zhupeng.receiver.UserRabbitMqRecevier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 将指定队列设置为手动签收模式
 */
@Configuration
@Slf4j
public class RabbitMqRecevierConfig {

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    TestAckRabbitMqRecevier testAckRabbitMqRecevier;

    @Autowired
    RabbitMqConfig rabbitMqConfig;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(){
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(cachingConnectionFactory);

        //最小的并发消费者的数量
        simpleMessageListenerContainer.setConcurrentConsumers(1);
        //设置最大的并发的消费者数量
        simpleMessageListenerContainer.setMaxConcurrentConsumers(1);
        // RabbitMQ默认是自动确认(AcknowledgeMode.NONE)，这里改为手动确认消息
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        simpleMessageListenerContainer.setQueues(rabbitMqConfig.testAckQueue());
        simpleMessageListenerContainer.setMessageListener(testAckRabbitMqRecevier);

        return simpleMessageListenerContainer;

    }


}

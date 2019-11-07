package com.zhupeng.config;

import com.zhupeng.constant.RabbitMqContant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class RabbitConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }



    //交换机
    @Bean
    public DirectExchange userDirectExchange(){
        return  new DirectExchange(RabbitMqContant.USER_DIRECT_EXCHANGE);
    }

//    @Bean
//    public TopicExchange dlxExchange(){
//        return  new TopicExchange(RabbitMqContant.DLX_EXCHANGE);
//    }
//





    //队列
    @Bean
    public Queue userDeleteQueue(){
        return new Queue(RabbitMqContant.DELETE_USER_QUEUE ,true);
    }

//    @Bean
//    public Queue userQueue(){
//        Map<String , Object> arguments = new HashMap<>(1);
//        arguments.put(RabbitMqContant.X_DEAD_LETTER_EXCHANGE , RabbitMqContant.DLX_EXCHANGE);//x-dead-letter-exchange 来标识一个交换机
//        arguments.put(RabbitMqContant.X_DEAD_LETTER_ROUTING_KEY , RabbitMqContant.ADD_USER_ROUTINGKEY);//x-dead-letter-routing-key  来标识一个绑定键（RoutingKey）
//
//        return new Queue(RabbitMqContant.USER_QUEUE ,true , false , false , arguments);
//    }


    //绑定规则
    public Binding userDirectExchange2UserDeleteQueue(){
        return BindingBuilder.bind(userDeleteQueue()).to(userDirectExchange()).with(RabbitMqContant.DELETE_USER_ROUTINGKEY);
    }

//    //绑定规则
//    public Binding dlxExchange2UserQueue(){
//        return BindingBuilder.bind(userQueue()).to(dlxExchange()).with(RabbitMqContant.ADD_USER_ROUTINGKEY);
//    }
}

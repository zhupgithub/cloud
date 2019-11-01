package com.zhupeng.config;


import com.zhupeng.contant.RabbitMqContant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMqConfig {
    /**
    交换机、routingkey、queue的对应关系
    queue------:
            exchange:
            routingkey:



     */

    /**
     * 队列
     */
    @Bean
    public Queue addUserQueue(){
        return new Queue(RabbitMqContant.ADD_USER_QUEUE);
    }

    /**
     * Direct交换机
     */
    @Bean
    public DirectExchange userDirectExchange(){
        return new DirectExchange(RabbitMqContant.USER_DIRECT_EXCHANGE);
    }

    /**
     *  绑定  将队列和交换机绑定, 并设置用于匹配键RabbitMqContant.ADD_USER_ROUTINGKEY
     */
    @Bean
    public Binding bindingAddUserQueue2UserDirectExchange(){
        return BindingBuilder.bind(addUserQueue()).to(userDirectExchange())
                .with(RabbitMqContant.ADD_USER_ROUTINGKEY);
    }

    /**
     * 队列
     */
    @Bean
    public Queue deleteUserQueue(){
        return new Queue(RabbitMqContant.DELETE_USER_QUEUE);
    }

    @Bean
    public Binding bindingTestAckQueue2UserDirectExchange(){
        return BindingBuilder.bind(testAckQueue()).to(userDirectExchange()).with(RabbitMqContant.TEST_ACK_ROUTINGKEY);
    }


    @Bean
    public Queue testAckQueue(){
        return new Queue(RabbitMqContant.TEST_ACK_QUEUE);
    }

    /**
     *  绑定  将队列和交换机绑定, 并设置用于匹配键RabbitMqContant.ADD_USER_ROUTINGKEY
     */
    @Bean
    public Binding bindingDeleteUserQueue2UserDirectExchange(){
        return BindingBuilder.bind(deleteUserQueue()).to(userDirectExchange())
                .with(RabbitMqContant.DELETE_USER_ROUTINGKEY);
    }

    @Bean
    public Queue userQueue(){
        return new Queue(RabbitMqContant.USER_QUEUE);
    }

    /**
     * Direct交换机
     */
    @Bean
    public TopicExchange userTopicExchange(){
        return new TopicExchange(RabbitMqContant.USER_TOPIC_EXCHANGE);
    }

    @Bean
    public Binding bindingUserQueue2UserTopicExchange(){
        return BindingBuilder.bind(userQueue()).to(userTopicExchange()).with(RabbitMqContant.USER_TOPIC_ROUTINGKEY);
    }

    @Bean
    public Binding bindingDeleteUserQueue2UserTopicExchange(){
        return BindingBuilder.bind(deleteUserQueue()).to(userTopicExchange()).with(RabbitMqContant.DELETE_USER_ROUTINGKEY);
    }


    @Bean
    public FanoutExchange userFanoutExchange(){
        return new FanoutExchange(RabbitMqContant.USER_FANOUT_EXCHANGE);
    }

    @Bean
    public Binding bindDeleteUser2UserFanoutExchange(){
        return BindingBuilder.bind(deleteUserQueue()).to(userFanoutExchange());
    }

    @Bean
    public Binding bindAddUserQueue2UserFanoutExchange(){
        return BindingBuilder.bind(addUserQueue()).to(userFanoutExchange());
    }


    @Bean
    public FanoutExchange testFanoutExchange(){
        return new FanoutExchange(RabbitMqContant.TEST_FANOUT_EXCHANGE);
    }



    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        //确认消息已发送到交换机(Exchange)
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("ConfirmCallback相关数据correlationData ：" + correlationData);
                log.info("ConfirmCallback确认情况ack ：" + ack);
                log.info("ConfirmCallback原因cause ：" + cause);
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("ReturnCallback:     "+"消息："+message);
                log.info("ReturnCallback:     "+"回应码："+replyCode);
                log.info("ReturnCallback:     "+"回应信息："+replyText);
                log.info("ReturnCallback:     "+"交换机："+exchange);
                log.info("ReturnCallback:     "+"路由键："+routingKey);
            }
        });
        return rabbitTemplate;
    }
}

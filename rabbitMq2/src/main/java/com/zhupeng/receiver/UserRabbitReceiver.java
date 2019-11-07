package com.zhupeng.receiver;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.zhupeng.constant.RabbitMqContant;
import com.zhupeng.constant.RedisContant;
import com.zhupeng.constant.SystemContant;
import com.zhupeng.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class UserRabbitReceiver {

    @Autowired
    RedisUtil redisUtil;

    @RabbitListener(queues = RabbitMqContant.DELETE_USER_QUEUE)
    public void deleteUser(Message message , Channel channel){
        Long deliveryTag = message.getMessageProperties().getDeliveryTag();
        JSONObject messageContentJson = null;
        log.info("呵呵哈哈哈或==========");
        try {
            String messageContent = new String(message.getBody() , SystemContant.CHARSET);
            messageContentJson = JSONObject.parseObject(messageContent);
//            Thread.sleep(2000); // 当前线程休眠5秒钟，模拟消息消费过程

            throw new RuntimeException();
//            channel.basicAck(deliveryTag, false);
            //log.error("消息确认处理成功[{}]", messageContent);

        } catch (Exception e) {
            //该消息是否是重发的消息，当不是重发消息，为false，否则为true
            Boolean redelivered = message.getMessageProperties().getRedelivered();
            log.info("是否重发：" + redelivered);
            /**
             * 这里对消息重入队列做设置，例如将消息序列化缓存至 Redis, 并记录重入队列次数
             * 如果该消息重入队列次数达到一定次数，比如3次，将不再重入队列，直接拒绝
             * 这时候需要对消息做补偿机制处理
             *
             * channel.basicNack与channel.basicReject要结合越来使用
             *
             */
            try {

                if (redelivered) {

                    /**
                     * 1. 对于重复处理的队列消息做补偿机制处理
                     * 2. 从队列中移除该消息，防止队列阻塞
                     */
                    int times = Integer.parseInt(redisUtil.hget(RedisContant.RABBIT_RESEND_TIMES ,
                            messageContentJson.get("id").toString()).toString());

                    if(times < SystemContant.REENTER_TIMES){
                        // 消息重新放回队列
                        channel.basicNack(deliveryTag, false, true);

                        redisUtil.hincr(RedisContant.RABBIT_RESEND_TIMES , messageContentJson.get("id").toString() , 1 );

                        log.error("消息处理失败，重新放回队列[{}]", message);
                    }else{
                        // 消息已重复处理失败, 扔掉消息
                        channel.basicReject(deliveryTag, false); // 拒绝消息

                        //将消息日志打印
                        log.error("消息重新处理失败，扔掉消息[{}]", message);
                    }
                }else{
                    //设置重入队列1次
                    redisUtil.hset(RedisContant.RABBIT_RESEND_TIMES , messageContentJson.get("id").toString() , 1);

                    // 消息重新放回队列
                    channel.basicNack(deliveryTag, false, true);

                    log.error("消息处理失败，重新放回队列[{}]", message);
                }
            } catch (IOException e1) {
                log.info("发生异常1==========");
            }
            log.info("发生异常2==========");
        }

    }


 //   @RabbitListener(queues = RabbitMqContant.USER_QUEUE)
    public void addUserQueue(Message message){
        log.info("死信队列接收到消息" + message + "MessageId:" + message.getMessageProperties().getMessageId());

        String messageContent = null;
        try {
            messageContent = new String(message.getBody() , SystemContant.CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JSONObject messageContentJson = JSONObject.parseObject(messageContent);

        log.info("死信队列接收到消息" + messageContentJson.get("id"));
    }
}

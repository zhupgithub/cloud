package com.zhupeng.receiver;

import com.rabbitmq.client.Channel;
import com.zhupeng.contant.RabbitMqContant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RabbitListener(queues = RabbitMqContant.TEST_ACK_QUEUE)
public class TestAckRabbitMqRecevier  implements ChannelAwareMessageListener {

    /**

     */

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        Boolean redelivered = message.getMessageProperties().getRedelivered(); //该消息是否是重发的消息
        try {
            /**通过basic.qos方法设置prefetch_count=1，这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message，
             换句话说,在接收到该Consumer的ack前,它不会将新的Message分发给它 */
            channel.basicQos(5);
            //因为传递消息的时候用的map传递,所以将Map从Message内取出需要做些处理
            String msg = message.toString();
            Thread.sleep(2000);
            String[] msgArray = msg.split("'");//可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
//            Map<String, String> msgMap = mapStringToMap(msgArray[1].trim());
//            String messageId=msgMap.get("messageId");
//            String messageData=msgMap.get("messageData");
//            String createTime=msgMap.get("createTime");
//            System.out.println("messageId:"+messageId+"  messageData:"+messageData+"  createTime:"+createTime);
//            channel.basicAck(deliveryTag, true);//为true会重新放回队列
            log.info("消息内容：" + message.getBody() + "是否重复消息： " +redelivered);
			channel.basicReject(deliveryTag, !redelivered);
        } catch (Exception e) {
            channel.basicReject(deliveryTag, false);
            e.printStackTrace();
        }
    }

    //{key=value,key=value,key=value} 格式转换成map
    private Map<String, String> mapStringToMap(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",");
        Map<String, String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key = string.split("=")[0].trim();
            String value = string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }
}

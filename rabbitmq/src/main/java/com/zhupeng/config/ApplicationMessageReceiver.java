package com.zhupeng.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class ApplicationMessageReceiver implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {

        log.info(event.toString() + event.getClass() + event.getTimestamp() + event.getSource());

        long timeStamp = System.currentTimeMillis();  //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
        String sd = sdf.format(new Date(timeStamp));   // 时间戳转换成时间
        System.out.println(sd);//打印出你要的时间
        sd = sdf.format(new Date(event.getTimestamp()));   // 时间戳转换成时间
        System.out.println(sd);//打印出你要的时间
    }
}

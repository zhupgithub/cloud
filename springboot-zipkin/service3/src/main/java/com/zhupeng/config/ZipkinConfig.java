package com.zhupeng.config;


import com.github.kristofa.brave.*;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.okhttp.BraveOkHttpRequestResponseInterceptor;
import okhttp3.OkHttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.kristofa.brave.Brave.Builder;
import com.github.kristofa.brave.http.HttpSpanCollector;
import com.github.kristofa.brave.http.HttpSpanCollector.Config;
import com.github.kristofa.brave.httpclient.BraveHttpRequestInterceptor;
import com.github.kristofa.brave.httpclient.BraveHttpResponseInterceptor;
import com.github.kristofa.brave.servlet.BraveServletFilter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @program: zipkin1
 * @description: 核心类ZipkinBean提供需要使用的Bean
 * @author: Sid
 * @date: 2018-11-16 17:30
 * @since: 1.0
 **/
@Configuration
public class ZipkinConfig {
    /**
     * 配置收集器
     *
     * @return
     */
    @Bean
    public SpanCollector spanCollector() {

        Config config = Config.builder()
                .compressionEnabled(false) //默认false，span在transport之前是否会被gzipped
                .connectTimeout(5000)
                .flushInterval(1)   //flushInterval表示span的传递间隔 实际为定时任务执行的间隔时间
                .readTimeout(6000)
                .build();
        return HttpSpanCollector.create("http://localhost:9411", config, new EmptySpanCollectorMetricsHandler());
    }

    /**
     * Brave各工具类的封装
     * 作为各调用链路，只需要负责将指定格式的数据发送给zipkin
     *
     * @param spanCollector
     * @return
     */
    @Bean
    public Brave brave(SpanCollector spanCollector) {
        Builder builder = new Builder("service3");// 指定serviceName
        builder.spanCollector(spanCollector);
        //builder.traceSampler(Sampler.create(1.0f));// 采集率
        builder.traceSampler(Sampler.ALWAYS_SAMPLE);// 采集率
        return builder.build();
    }

    /**
     * 过滤器，需要serverRequestInterceptor,serverResponseInterceptor 分别完成sr和ss操作
     *
     * @param brave
     * @return
     */
    @Bean
    public BraveServletFilter braveServletFilter(Brave brave) {

        BraveServletFilter filter = new BraveServletFilter(
                brave.serverRequestInterceptor(),
                brave.serverResponseInterceptor(),
                new DefaultSpanNameProvider());
        return filter;
    }

    /**
     * httpClient客户端，需要clientRequestInterceptor,clientResponseInterceptor分别完成cs和cr操作
     *
     * controller中使用HTTP请求的时候必须用这个httpClient
     *
     * 如果用自己随便写的httpClient，没有添加BraveHttpRequestInterceptor、BraveHttpResponseInterceptor拦截器
     * zipkin收到的数据不会成树形
     * 比如 service1 service2 service3 都是单独的traceId
     *
     * @param brave
     * @return
     */
    @Bean
    public CloseableHttpClient httpClient(Brave brave) {
        CloseableHttpClient httpclient = HttpClients.custom()
                .addInterceptorFirst(new BraveHttpRequestInterceptor(brave.clientRequestInterceptor(), new DefaultSpanNameProvider()))
                .addInterceptorFirst(new BraveHttpResponseInterceptor(brave.clientResponseInterceptor())).build();
        return httpclient;
    }

    /** android 端常用这个*/
    @Bean
    public OkHttpClient okHttpClient(Brave brave){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BraveOkHttpRequestResponseInterceptor(
                        brave.clientRequestInterceptor(),
                        brave.clientResponseInterceptor(),
                        new DefaultSpanNameProvider()))
                .build();
        return client;
    }

}

package com.xiaomi.client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @Author: xiaomi
 * @Description:
 * @Date:上午 10:39 2018/7/9 0009
 * @Email:a1205938863@gmail.com
 **/
@Configuration
public class MyConfig {
    @Bean
    public TransportClient client() throws UnknownHostException {


        Settings settings = Settings.builder()
                .put("cluster.name","elasticsearch")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("47.89.17.7"), 9300));
        return client;
    }
}

package com.soda.learn.config;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author soda
 * @date 2021/5/9
 */
@Configuration
public class ESConfig {

    @Bean
    public TransportClient client() throws UnknownHostException {


        //java client不能使用9200端口，会报错
        TransportAddress transportAddress = new TransportAddress(InetAddress.getByName("es1"), 9300);
        TransportClient transportClient = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(transportAddress);
        return transportClient;


    }

    /*
    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(TransportClient transportClient) {
        return new ElasticsearchTemplate(transportClient);
    }
    */
}

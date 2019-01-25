package com.sirding.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;

/**
 * TODO
 *
 * @author zc.ding
 * @create 2019/1/10
 */
public class EsClient {

    private static final String HOST = "119.255.240.63";
    public static void main(String[] args) throws IOException {
        Settings settings = Settings.builder().put("cluster.name", "")
                .put("client.transport.sniff", true).build();


        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(HOST, 9200, "http"),
                        new HttpHost(HOST, 9201, "http"),
                        new HttpHost(HOST, 9202, "http")));
        System.out.println("ok");
        client.close();
        
    }
    
}

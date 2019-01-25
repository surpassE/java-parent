package com.sirding.redis;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * TODO
 *
 * @author zc.ding
 * @create 2019/1/25
 */
public class TestRedisCluster {

    private final static String ip = "10.112.12.203:7000, 10.112.12.203:7001, 10.112.12.203:7002";
    
    @Before
    public void before(){
        System.out.println("before...");
        List<RedisURI> list = new ArrayList<>();
        for (String tmp : ip.split(",")) {
            String[] arr = tmp.split(":"); 
            list.add(RedisURI.Builder.redis(arr[0].trim(), Integer.parseInt(arr[1].trim())).build());
        }
        RedisClusterClient clusterClient = RedisClusterClient.create(list);
        StatefulRedisClusterConnection<String, String> connection = clusterClient.connect();


        RedisAdvancedClusterCommands<String, String> syncCommands = connection.sync();
        
    }
    
    @Test
    public void test1(){
        System.out.println("hello");
    }
    
}

package com.sirding.redis;

import com.sirding.match.Start;
import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.RedisAdvancedClusterAsyncCommandsImpl;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author zc.ding
 * @create 2019/1/25
 */
public class TestRedisCluster {

//    private final static String ip = "10.112.12.203:7000, 10.112.12.203:7001, 10.112.12.203:7002";
    private final static String ip = "119.255.240.63:7001, 119.255.240.63:7002, 119.255.240.63:7003, 119.255.240.63:7004, 119.255.240.63:7005, 119.255.240.63:7000";
    private static JedisCluster jedisCluster;
    private final static String prefix = "regUser";
    private static Jedis jedis;
    private static RedisClusterClient clusterClient;
    private static List<RedisURI> list = new ArrayList<>();
    
    @Before
    public void before(){
        System.out.println("before...");
        Set<HostAndPort> nodes = new HashSet<>();
        for (String tmp : ip.split(",")) {
            String[] arr = tmp.split(":"); 
            String ip = arr[0].trim();
            int port = Integer.parseInt(arr[1].trim());
            list.add(RedisURI.Builder.redis(ip, port).build());
            nodes.add(new HostAndPort(ip, port));
        }

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(200);
        poolConfig.setMaxTotal(1000);
        poolConfig.setMaxWaitMillis(20000);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        jedisCluster = new JedisCluster(nodes, 20000, poolConfig);

        JedisPool jedisPool = new JedisPool("119.255.240.63", 6380);
        jedis = jedisPool.getResource();
    }
    
    @Test
    public void test1(){
//        clusterClient = RedisClusterClient.create(list);
//        StatefulRedisClusterConnection<String, String> connection = clusterClient.connect();
//        connection.setAutoFlushCommands(false);
//        RedisAdvancedClusterCommands<String, String> commands = connection.sync();
//        for (int i = 0; i < 1000; i++) {
//            commands.get(prefix + i);
//        }
//        connection.flushCommands();
//        connection.close();

    }
    
    
    @Test
    public void jedisClusterSet(){
        String prefix = "regUser";
        for (int i = 0; i <= 1000   ; i++) {
            System.out.println("index: " + i);
            jedisCluster.set(prefix + i, UUID.randomUUID().toString());
        }
        System.out.println("okok");
    }
    
    @Test
    public void jedisClusterGet(){
        long startTime = System.currentTimeMillis();
        for (int i = 0; i <= 1000   ; i++) {
            System.out.println("index: " + i);
            System.out.println(jedisCluster.get(prefix + i));
        }
        System.out.println("time: " + (System.currentTimeMillis() - startTime));
    }

   
    @Test
    public void testJedis() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i <= 1000   ; i++) {
            System.out.println("index: " + i);
            jedis.set(prefix + i, UUID.randomUUID().toString());

        }
        System.out.println("time: " + (System.currentTimeMillis() - startTime));
    }

    @Test
    public void testJedisGet() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i <= 1000   ; i++) {
            System.out.println("index: " + i);
            System.out.println(jedis.get(prefix + i));
        }
        System.out.println("time: " + (System.currentTimeMillis() - startTime));
    }

    @Test
    public void testJedisBatchGet() {
        Pipeline pipeline = jedis.pipelined();
        long startTime = System.currentTimeMillis();
        Map<String, Response<String>> result = new HashMap<>();
        for (int i = 0; i <= 1000   ; i++) {
            System.out.println("index: " + i);
            String key = prefix + i;
            result.put(key, pipeline.get(key));
        }
        pipeline.sync();
        for (String key : result.keySet()) {
            System.out.println(result.get(key).get());
        }
        System.out.println("time: " + (System.currentTimeMillis() - startTime));
    }
    
    public void jedisClusterPipelineSet(){
        
    }
    
    @Test
    public void jedisClusterPipelineGet(){
        RedisClusterPipeline pipeline = new RedisClusterPipeline();
        pipeline.setJedisCluster(jedisCluster);
        long startTime = System.currentTimeMillis();
        Map<String, Response<String>> result = new HashMap<>();
        for (int i = 0; i <= 1000   ; i++) {
            System.out.println("index: " + i);
            String key = prefix + i;
            result.put(key, pipeline.get(key));
        }
        pipeline.sync();

        
        for (String key : result.keySet()) {
            System.out.println(result.get(key).get());
        }
//        List<String> dstList = new ArrayList<>();
//        for (int i = 0; i <= 1000   ; i++) {
//            dstList.add(jedisCluster.get(prefix + i));
//        }
//
//        for (int i = 0; i <= 1000   ; i++) {
//            if(!dstList.get(i).equals(preList.get(i).get())){
//                System.out.println("error:" + i);
//                return;
//            }else{
//                System.out.println("ok");
//            }
//        }
        pipeline.close();
        System.out.println("time: " + (System.currentTimeMillis() - startTime));
    }
    

    /**
    *  压测
    * 
    *  @since                   ：2019/1/27
    *  @author                  ：zc.ding@foxmail.com
    */
    @Test
    public void stressTesting() throws Exception{
        for(int i = 0; i < 1000; i++){
            new Thread(() -> {
//                System.out.println(Thread.currentThread().getName());
                RedisClusterPipeline pipeline = RedisClusterPipeline.pipeline(jedisCluster);
                for(int j = 0; j < 100; j++){
                    System.out.println(Thread.currentThread().getName() + ":insert:" + j);
                    pipeline.set(Thread.currentThread().getName() + j, UUID.randomUUID().toString());
                }
                pipeline.sync();
                pipeline.close();
            }, "name" + i).start();
        }
        
        Thread.sleep(20000);
        System.out.println("ok");
    }
    
}

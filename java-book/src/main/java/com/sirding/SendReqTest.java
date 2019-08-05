package com.dobe.flow.statis.test;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zc.ding
 * @since 2019/4/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SendReqTest {

    
    Map<String, String>  USER_AGENT_MAP = new HashMap<String, String>(){{
        // 360
        put("1", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E)");
        // 谷歌
        put("2", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
        // 火狐
        put("3", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0) Gecko/20100101 Firefox/6.0");
        // 搜狗
        put("4", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3; .NET4.0C; .NET4.0E; SE 2.X MetaSr 1.0)");
    }};

    Map<String, String>  URL_MAP = new HashMap<String, String>(){{
        // javaassist入门(一)-no such field:
        put("1", "https://blog.csdn.net/chao_1990/article/details/70256503");
        // nginx共享session方式及redis+cookie、ip_hash方式实现
        put("2", "https://blog.csdn.net/chao_1990/article/details/54405759");
        // SpringMVC no URL paths identified 404
        put("3", "https://blog.csdn.net/chao_1990/article/details/52854588");
        // PowerDesigner16配置显示注释comment配置方法
        put("4", "https://blog.csdn.net/chao_1990/article/details/52620206");
        // Mybatis实现*mapper.xml热部署-分子级更新
        put("5", "https://blog.csdn.net/chao_1990/article/details/85116284");
        // zookeeper实现分布式锁(公平锁|非公平锁)
        put("6", "https://blog.csdn.net/chao_1990/article/details/88785998");
        // activemq配置jmx
        put("7", "https://blog.csdn.net/chao_1990/article/details/84024074");
        // tcc transaction扩展redis原生集群
        put("8", "https://blog.csdn.net/chao_1990/article/details/80708062");
        // Idea 配置method注释模板
        put("9", "https://blog.csdn.net/chao_1990/article/details/79896979");
        // java实现redis分布式锁实例
        put("10", "https://blog.csdn.net/chao_1990/article/details/70767614");
        // Oauth2授权模式password单一账号并发问题
        put("11", "https://blog.csdn.net/chao_1990/article/details/83782147");
        // jenkins配合docker实现测试环境多分支无等待持续集成实现方案
        put("12", "https://blog.csdn.net/chao_1990/article/details/82630099");
        // ActiveMq认证与授权配置
        put("13", "https://blog.csdn.net/chao_1990/article/details/55270922");
        // SpringMVC Interceptor 配置由简单到复杂
        put("14", "https://blog.csdn.net/chao_1990/article/details/25921685");
        
        // stroll
        put("15", "https://surpass1990.github.io/stroll/pages/what.html");
        put("16", "https://surpass1990.github.io/stroll/pages/buildEnv.html");
        put("17", "https://surpass1990.github.io/stroll/pages/useCases.html");
        put("18", "https://surpass1990.github.io/stroll/pages/doc.html");
        put("19", "https://surpass1990.github.io/stroll/pages/cmdDoc.html");
        put("20", "https://surpass1990.github.io/stroll/pages/fqa.html");
        put("21", "https://surpass1990.github.io/stroll/pages/toDo.html");
        
        
    }};

    // 设置连接超时时间，单位毫秒。
    private static final int CONNECT_TIMEOUT = 6000;

    // 请求获取数据的超时时间(即响应时间)，单位毫秒。
    private static final int SOCKET_TIMEOUT = 6000;

    private static final String DST_URL = "https://www.cbedai.net/chao1990";
    
    @Test
    public void send() throws Exception {
        System.out.println("开始时间: " + new Date());
        int count = 0;
        while (count < 500) {
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//            String url = "http://localhost:8081/user/userList";
            String urlKey = "" + (int)(1 + Math.random()*(211));
            if (hour > 8 && hour <= 24 && URL_MAP.containsKey(urlKey)) {
                // 创建httpClient对象
                CloseableHttpClient httpClient = HttpClients.createDefault();
                // 创建http对象
                HttpPost httpPost = new HttpPost(DST_URL);
                RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
                httpPost.setConfig(requestConfig);
                // 设置请求头
                httpPost.setHeader("Cookie", "");
                httpPost.setHeader("Connection", "keep-alive");
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
                httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
                String uamKey = "" + (int)(1 + Math.random()*(4));
                httpPost.setHeader("User-Agent", USER_AGENT_MAP.get(uamKey));
                httpPost.setHeader("Referer", URL_MAP.get(urlKey));
                try {
                    httpClient.execute(httpPost);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            count++;
            if (count % 20 == 0) {
                Thread.sleep((long)(300 + Math.random()*(300)) * 1000);
            }
            Thread.sleep((long)(30 + Math.random()*(60)) * 1000);
            System.out.println("请求次数: " + count);
        }
        System.out.println("结束时间: " + new Date());
    }
}

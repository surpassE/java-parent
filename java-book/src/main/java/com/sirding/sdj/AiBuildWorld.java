package com.sirding.sdj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.UnsupportedEncodingException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * 自动创建世界奇迹
 *
 * @author zc.ding
 * @since 1.0
 */
public class AiBuildWorld {

    private final Map<String, String> URL_MAP = new HashMap<String, String>() {{
        put("index1", "/v6/earth/index?state=2&tab=1");
        put("index2", "/v6/earth/index?state=1&tab=2");
        // 增加人口
        put("createdPopulation", "/v6/earth/createdPopulation");
        // 管理人口
        put("allotPopulation", "/v6/earth/allotPopulation");
    }};

    // 设置连接超时时间，单位毫秒。
    private static final int CONNECT_TIMEOUT = 6000;

    // 请求获取数据的超时时间(即响应时间)，单位毫秒。
    private static final int SOCKET_TIMEOUT = 6000;

    private static final String USER_AGENT = "shandianji/4.2.1 (com.sdj.shandianji; build:2; iOS 13.3.0) Alamofire/4.8.2";

    private static final String URL_ROOT = "http://ios-api.shandianji.cn";


    private HttpResponse aiBuildGet(String key) throws Exception {
        HttpClient httpClient = sslClient();
        // 创建http对象
        String dstUrl = URL_ROOT + URL_MAP.get(key);
        System.out.println("请求的URL: " + dstUrl);
        HttpGet proxy = new HttpGet(dstUrl);
        this.setHeader(proxy);
        return httpClient.execute(proxy);
    }

    private HttpResponse aiBuildPost(String key, List<NameValuePair> list) throws Exception {
        HttpClient httpClient = sslClient();
        // 创建http对象
        String dstUrl = URL_ROOT + URL_MAP.get(key);
        System.out.println("请求的URL: " + dstUrl);
        HttpPost proxy = new HttpPost(dstUrl);
        this.setHeader(proxy);
        // 使用URL实体转换工具
        UrlEncodedFormEntity entityParam = new UrlEncodedFormEntity(list, "UTF-8");
        proxy.setEntity(entityParam);
        return httpClient.execute(proxy);

    }

    private void setHeader(HttpRequestBase proxy) {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        proxy.setConfig(requestConfig);
        // 设置请求头
        proxy.setHeader("User-Agent", USER_AGENT);
        proxy.setHeader("Accept-Encoding", "gzip;q=1.0, compress;q=0.5");
        proxy.setHeader("x-token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImp0aSI6ImV5SnBkaUk2SWxaeVVHSXllbkUxUjFCc2JHaDZXVmhKT0ZkMGMzYzlQU0lzSW5aaGJIVmxJam9pVUUxamJUbDRURzFsZEZVd1ZWTm9NbU1ySzFKMVp6MDlJaXdpYldGaklqb2laRGt6WVRVMk56WTBZakF5WldZMFltSTFNRGhqWkdRME4yUmlabVJqTW1aaU1qUm1Zak0yTXpkak56UTJOR1V5TkRjell6TTJOakUwWW1FMk5UWXlaaUo5In0.eyJpc3MiOiJodHRwczpcL1wvd3d3LnNoYW5kaWFuamkuY24iLCJhdWQiOiJodHRwczpcL1wvd3d3LnNoYW5kaWFuamkuY24iLCJqdGkiOiJleUpwZGlJNklsWnlVR0l5ZW5FMVIxQnNiR2g2V1ZoSk9GZDBjM2M5UFNJc0luWmhiSFZsSWpvaVVFMWpiVGw0VEcxbGRGVXdWVk5vTW1NcksxSjFaejA5SWl3aWJXRmpJam9pWkRrellUVTJOelkwWWpBeVpXWTBZbUkxTURoalpHUTBOMlJpWm1Sak1tWmlNalJtWWpNMk16ZGpOelEyTkdVeU5EY3pZek0yTmpFMFltRTJOVFl5WmlKOSIsImlhdCI6MTU3OTIyMzM0OSwibmJmIjoxNTc5MjIzMzQ5LCJleHAiOjE1ODE4MTUzNDl9.z6cUjjY_CmC6C0MocrAIN4iehCTzsjUIRENnAOhQgcE");
        proxy.setHeader("Accept", "*/*");
        proxy.setHeader("Connection", "keep-alive");
        proxy.setHeader("device", "iOS");
        proxy.setHeader("Accept-Language", "zh-Hans-CN;q=1.0");
        proxy.setHeader("Authorization", "sdj123456{|}2d2b97888b9f4ec1e9fd9223f882c909");
        proxy.setHeader("accesstoken", "1902d9c03cc748f72f3c2cebf4326f23a36538f3");
        proxy.setHeader("version_name", "4.2.1");
    }


    /**
     * 在调用SSL之前需要重写验证方法，取消检测SSL
     * 创建ConnectionManager，添加Connection配置信息
     *
     * @return HttpClient 支持https
     */
    private static HttpClient sslClient() {
        try {
            // 在调用SSL之前需要重写验证方法，取消检测SSL
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                }
            };
            SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            ctx.init(null, new TrustManager[]{trustManager}, null);
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            // 创建Registry
            RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
                    .setExpectContinueEnabled(Boolean.TRUE).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Collections.singleton(AuthSchemes.BASIC)).build();
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", socketFactory).build();
            // 创建ConnectionManager，添加Connection配置信息
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            return HttpClients.custom().setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(requestConfig).build();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    @Test
    public void getInfo() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            HttpResponse response = this.aiBuildPost("createdPopulation", Collections.singletonList(new BasicNameValuePair("number", "10")));
            HttpEntity entity = response.getEntity();
            System.out.println("响应结果:" + EntityUtils.toString(entity, "UTF-8"));
            Thread.sleep(1000);
            response = this.aiBuildGet(i%2 == 0 ? "index1" : "index2");
            entity = response.getEntity();
            String content = EntityUtils.toString(entity, "UTF-8");
            JSONObject json  = JSON.parseObject(content);
            JSONObject data = json.getJSONObject("data");
            long population = data.getLong("population");
            long popFood = data.getLong("popFood");
            long popWood = data.getLong("popWood");
            long popLand = data.getLong("popLand");
            long popWonder = data.getLong("popWonder");
            long popUnused = data.getLong("popUnused");
            long total = population + popFood + popWood + popLand + popWonder + popUnused;
            System.out.println("人口数量总量：" + total);
        }
        System.out.println(System.currentTimeMillis() - start);
    }


}

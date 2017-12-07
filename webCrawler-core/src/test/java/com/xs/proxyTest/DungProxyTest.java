package com.xs.proxyTest;

import com.virjar.dungproxy.client.httpclient.DunProxyHttpRequestRetryHandler;
import com.virjar.dungproxy.client.httpclient.conn.ProxyBindRoutPlanner;
import com.virjar.dungproxy.client.ippool.IpPoolHolder;
import com.virjar.dungproxy.client.ippool.config.DungProxyContext;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class DungProxyTest {
    public static void main(String[] args) throws IOException {
        IpPoolHolder.init(DungProxyContext.create().setPoolEnabled(true));
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setRetryHandler(new DunProxyHttpRequestRetryHandler(null))
                .setRoutePlanner(new ProxyBindRoutPlanner());
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        CloseableHttpResponse response = closeableHttpClient.execute(httpGet);

        String string = IOUtils.toString(response.getEntity().getContent());
        System.out.println(string);
    }
}

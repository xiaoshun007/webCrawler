package com.xs.proxyTest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.proxy.Proxy;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleProxyTest {
    private static List<Proxy> buildProxyIP() throws IOException {
        Document parse = Jsoup.parse(new URL("http://www.89ip.cn/tiqv.php?sxb=&tqsl=500&ports=&ktip=&xl=on&submit=%CC%E1++%C8%A1"), 5000);
        String pattern = "(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+):(\\d+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(parse.toString());
        List<Proxy> proxies = new ArrayList<Proxy>();
        while (m.find()) {
            String[] group = m.group().split(":");
            int prot = Integer.parseInt(group[1]);
            proxies.add(new Proxy(group[0], prot));
        }
        return proxies;
    }

    public static void main(String[] args) throws IOException {
        List<Proxy> proxies = buildProxyIP();
        int index = 0;
        for(Proxy proxy: proxies) {
            index += 1;
            System.out.println("host" + index + ": " + proxy.getHost());
        }
    }
}

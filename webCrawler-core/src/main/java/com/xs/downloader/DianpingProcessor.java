package com.xs.downloader;

import com.alibaba.fastjson.JSONObject;
import com.virjar.dungproxy.client.ippool.IpPoolHolder;
import com.virjar.dungproxy.client.ippool.config.DungProxyContext;
import com.virjar.dungproxy.webmagic7.DungProxyDownloader;
import com.xs.configure.DianpingConfiguration;
import com.xs.configure.LianjiaConfiguration;
import com.xs.pipeliner.LianJiaMongoPipleline;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DianpingProcessor implements PageProcessor {
    private Site site = new DianpingConfiguration().getSite();

    // 列表URL
    // http://www.dianping.com/wuhan/ch55/g163p3
    private static final String URL_LIST = "http://www\\.dianping\\.com/wuhan/ch55/g163p\\d+";
    // 详情URL
    // http://www.dianping.com/shop/107986541
    private static final String URL_DETAIL = "http://www\\.dianping\\.com/shop/\\d+";

    /**
     * 列表页采用非静态，需要手动拼接
     * @param page page信息
     */
    private void generateListUrl(Page page) {
        List<String> listUrl = page.getHtml().xpath("//a[@class=\"PageLink\"]/@title").all();
        Integer size = listUrl.size();
        Integer totalPage = Integer.valueOf(listUrl.get(size - 1));
        List<String> urls = new LinkedList<String>();
        for (int i = 1; i <= totalPage; i++) {
            urls.add("http://www.dianping.com/wuhan/ch55/g163p" + i + "/");
            page.addTargetRequests(urls);
        }
    }

    /**
     * 详情信息
     * @param page page信息
     */
    private void buildDetails(Page page) {
        Html html = page.getHtml();
        page.putField("content", 1);
    }

    @Override
    public void process(Page page) {
        // 列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"shop-wrap\"]").links().regex(URL_DETAIL).all());

            this.generateListUrl(page);
            page.setSkip(true);
            // 详情页
        } else {
            page.putField("title", page.getHtml().xpath("//a[@class=\"shopname\"]/text()").toString());
            page.putField(CrawlerPipeline.URL, page.getUrl().toString());

            this.buildDetails(page);
        }
    }

    /**
     * 不错的免费代理IP站点
     * www.89ip.cn
     *
     * @return
     */
    private static List<Proxy> buildProxyIP() throws IOException {

        Document parse = Jsoup.parse(new URL("http://www.ip3366.net/free/"), 5000);

        Elements elements = parse.getElementsByTag("tr");
        Element element;

        List<Proxy> proxies = new ArrayList<Proxy>();
        for (int i = 1; i < elements.size(); i++) {
            element = elements.get(i);
            proxies.add(new Proxy(element.child(0).text(), Integer.parseInt(element.child(1).text())));
        }

        return proxies;
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws IOException {
        DianpingConfiguration configuration = new DianpingConfiguration();
        String pipelinePath = configuration.getDianpingPath();

//        DungProxyContext dungProxyContext = DungProxyContext.create();
//        dungProxyContext.setPoolEnabled(true);
//        dungProxyContext.getGroupBindRouter().buildCombinationRule("www.dianping.com:.*dianping.*");
//        IpPoolHolder.init(dungProxyContext);

        // IP代理池
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        try {
            List<Proxy> proxies = buildProxyIP();
            System.out.println("请求代理IP： " + proxies);
            httpClientDownloader.setProxyProvider(new SimpleProxyProvider(proxies));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Spider.create(new DianpingProcessor())
                .setScheduler(new FileCacheQueueScheduler(pipelinePath))
                .addPipeline(new LianJiaMongoPipleline())
                .setDownloader(httpClientDownloader)
                .addUrl("http://www.dianping.com/wuhan/ch55/g163p1")
                .thread(5)
                .run();
    }
}

package com.xs.downloader;

import com.virjar.dungproxy.client.ippool.IpPoolHolder;
import com.virjar.dungproxy.client.ippool.config.DungProxyContext;
import com.virjar.dungproxy.webmagic7.DungProxyDownloader;
import com.virjar.dungproxy.webmagic7.DungProxyProvider;
import com.xs.configure.CrawlerConfiguration;
import com.xs.util.StringHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.selector.Json;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by brian on 16/11/24.
 *
 * 爬取知乎用户的关注者
 * step 1: 运行该类的 main 方法开始爬取
 */
public class ZhihuFolloweePageProcessor implements PageProcessor {

    private Site site = new CrawlerConfiguration().getSite();

    private static HttpClientDownloader addProxy() {
        // IP代理池
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        try {
            List<Proxy> proxies = buildProxyIP();
            System.out.println("请求代理IP： " + proxies);
            httpClientDownloader.setProxyProvider(new SimpleProxyProvider(proxies));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return httpClientDownloader;
    }

    /**
     * 不错的免费代理IP站点
     * www.89ip.cn
     *
     * @return
     */
    private static List<Proxy> buildProxyIP() throws IOException {
        Document parse = Jsoup.parse(new URL("http://www.89ip.cn/tiqv.php?sxb=&tqsl=50&ports=&ktip=&xl=on&submit=%CC%E1++%C8%A1"), 5000);
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

    public void process(Page page) {
        Json json = page.getJson();
        //System.out.println(json);
        page.putField(CrawlerPipeline.URL, page.getUrl());
        page.putField(CrawlerPipeline.RESPONSE, json);

        String isEnd = json.jsonPath("$.paging.is_end").get();
        if (!Boolean.parseBoolean(isEnd)) {
            page.addTargetRequest(json.jsonPath("$.paging.next").get());
        }

        List<String> urlTokens = json.jsonPath("$.data[*].url_token").all();
        List<String> urls = generateFolloweeUrls(urlTokens);
        page.addTargetRequests(urls);
    }

    public Site getSite() {
        return site;
    }

    public static String generateFolloweeUrl(String urlToken) {
        final String URL_TEMPLATE = "https://www.zhihu.com/api/v4/members/%s/followees";
        final String QUERY_PARAMS = "?include=data%5B*%5D.answer_count%2Carticles_count%2Cgender%2Cfollower_count%2Cis_followed%2Cis_following%2Cbadge%5B%3F(type%3Dbest_answerer)%5D.topics&offset=0&limit=20";

        String encoded = StringHelper.urlEncode(urlToken);
        return String.format(URL_TEMPLATE, encoded) + QUERY_PARAMS;
    }

    public static List<String> generateFolloweeUrls(List<String> urlTokens) {
        List<String> urls = new ArrayList<>(20);
        urlTokens.stream().map(ZhihuFolloweePageProcessor::generateFolloweeUrl).forEach(urls::add);
        return urls;
    }

    public void downloadFollowees() {

    }

    /**
     * 下载关注列表的用户数据,用于提取 url_tokens
     * @param args 无须其他参数
     */
    public static void main(String[] args) throws IOException {
        String pipelinePath = new CrawlerConfiguration().getFolloweePath();
        int crawlSize = 100_0000;

        //以下是通过代码配置规则的方案,如果不使用配置文件,则可以解开注释,通过代码的方式
//        WhiteListProxyStrategy whiteListProxyStrategy = new WhiteListProxyStrategy();
//        whiteListProxyStrategy.addAllHost("www.zhihu.com");
//
//        // Step2 创建并定制代理规则
//        DungProxyContext dungProxyContext = DungProxyContext.create().setNeedProxyStrategy(whiteListProxyStrategy).setPoolEnabled(false);
//
//        // Step3 使用代理规则初始化默认IP池
//        IpPoolHolder.init(dungProxyContext);

//        WhiteListProxyStrategy whiteListProxyStrategy = new WhiteListProxyStrategy();
//        whiteListProxyStrategy.addAllHost("www.zhihu.com");
//        JSONFileAvProxyDumper jsonFileAvProxyDumper = new JSONFileAvProxyDumper();
//        jsonFileAvProxyDumper.setDumpFileName("availableProxy.json");
//        DungProxyContext dungProxyContext = DungProxyContext.create().setNeedProxyStrategy(whiteListProxyStrategy)
//                .setAvProxyDumper(jsonFileAvProxyDumper).setPoolEnabled(true);
//        dungProxyContext.getGroupBindRouter().buildCombinationRule("www.zhihu.com:.*zhihu.*");
//        IpPoolHolder.init(dungProxyContext);

        DungProxyContext dungProxyContext = DungProxyContext.create();
        dungProxyContext.setPoolEnabled(true);
        dungProxyContext.getGroupBindRouter().buildCombinationRule("www.zhihu.com:.*zhihu.*");
        IpPoolHolder.init(dungProxyContext);
//
//
//
//
//        DungProxyDownloader downloader = new DungProxyDownloader();
//        DungProxyProvider proxyProvider = new DungProxyProvider("www.zhihu.com","https://www.zhihu.com");
////        downloader.setProxyProvider(proxyProvider);
////
//        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//        httpClientDownloader.setProxyProvider(proxyProvider);

        Spider.create(new ZhihuFolloweePageProcessor())
                .setScheduler(//new QueueScheduler()
                        new FileCacheQueueScheduler(pipelinePath)
                                .setDuplicateRemover(new BloomFilterDuplicateRemover(crawlSize)))
//                .setDownloader(addProxy())
                .setDownloader(new DungProxyDownloader())
                .addPipeline(new CrawlerPipeline(pipelinePath))
                .addUrl(generateFolloweeUrl("kaifulee"))
                .thread(60)
                .run();
    }
}

使用WebMagic练习

## 快速开始

### 使用maven
webCrawler使用maven管理依赖，基于webMagic，因此在项目中需要添加webmagic对应的依赖：

```xml
<dependency>
    <groupId>us.codecraft</groupId>
    <artifactId>webmagic-core</artifactId>
    <version>0.7.3</version>
</dependency>
<dependency>
    <groupId>us.codecraft</groupId>
    <artifactId>webmagic-extension</artifactId>
    <version>0.7.3</version>
</dependency>
```

### 项目结构

### 添加代理

#### 代理依赖
   代理使用proxyip项目（https://gitee.com/virjar/proxyipcenter）
    1、预热ip池：https://gitee.com/virjar/proxyipcenter/blob/master/doc/client/userGuide/warm.md
    2、添加依赖
   ```xml
    <dependency>
        <groupId>com.virjar</groupId>
        <artifactId>dungproxy-webmagic7</artifactId>
        <version>0.0.1</version>
    </dependency>
   ```
   3、在项目resources目录下添加proxyclient.properties()
   * 配置说明
   ```xml
   #对于预热器,配置任务规则
   #proxyclient.DefaultAvProxyDumper.dumpFileName是一个文件地址,他存放了预热的结果,也就是可用IP缓存
   proxyclient.DefaultAvProxyDumper.dumpFileName=/Users/san/crawlDatas/availableProxy.json
   
   #需要检查的网站的URL,注意需要是GET请求,访问结果是HTTP_OK则认为检查通过。多个URL的话,逗号分割即可如:http://www.baidu.com,https://www.douban.com/group/explore
   proxyclient.preHeater.testList=http://www.zhihu.com
   
   #ͬ
   proxyclient.proxyUseIntervalMillis=10000
   
   #proxyclient.proxyDomainStrategy.whiteList=www.zhihu.com
   
   #配置增量序列化,是指每当达到一定数目的IP测试通过之后,就将数据序列化一次。实例配置是指,每当30个IP测试通过,就将数据写入到proxyclient.DefaultAvProxyDumper.dumpFileName
   proxyclient.preHeater.serialize.step=30
   ```
   4、调用
   * 在processor中添加如下代码，如本项目在 ZhihuPageProcessor.java中添加
   ```java
        DungProxyContext dungProxyContext = DungProxyContext.create();
        dungProxyContext.setPoolEnabled(true);
        dungProxyContext.getGroupBindRouter().buildCombinationRule("www.zhihu.com:.*zhihu.*");
        IpPoolHolder.init(dungProxyContext);
   ```
   * 设置downloader为DungProxyDownloader
   ```java
        Spider.setDownloader(new DungProxyDownloader())
   ```
### 开始爬虫
打开webCrawler-core模块：src/main/java/com.xs.downloader.ZhihuFolloweePageProcessor.java
run main方法






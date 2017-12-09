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
   * 代理使用proxyip项目（https://gitee.com/virjar/proxyipcenter）
   
   1.预热ip池：https://gitee.com/virjar/proxyipcenter/blob/master/doc/client/userGuide/warm.md
   
   2.添加依赖
   ```xml
    <dependency>
        <groupId>com.virjar</groupId>
        <artifactId>dungproxy-webmagic7</artifactId>
        <version>0.0.1</version>
    </dependency>
   ```
   3.在项目resources目录下添加proxyclient.properties
   * 配置说明
   ```xml
   #对于预热器,配置任务规则
   #proxyclient.DefaultAvProxyDumper.dumpFileName是一个文件地址,他存放了预热的结果,也就是可用IP缓存
   proxyclient.DefaultAvProxyDumper.dumpFileName=/Users/san/crawlDatas/availableProxy.json
   
   #需要检查的网站的URL,注意需要是GET请求,访问结果是HTTP_OK则认为检查通过。多个URL的话,逗号分割即可如:http://www.baidu.com,https://www.douban.com/group/explore
   proxyclient.preHeater.testList=http://www.zhihu.com
   
   proxyclient.proxyUseIntervalMillis=10000
   
   #proxyclient.proxyDomainStrategy.whiteList=www.zhihu.com
   
   #配置增量序列化,是指每当达到一定数目的IP测试通过之后,就将数据序列化一次。实例配置是指,每当30个IP测试通过,就将数据写入到proxyclient.DefaultAvProxyDumper.dumpFileName
   proxyclient.preHeater.serialize.step=30
   ```
   * 在执行preHeat.sh的时候，如果使用mac请注意Java的默认安装目录，根据不同的路径修改preHeat.sh的第38行
   ###### 检查本机Java环境变量
   ```xml
      打开终端，执行     /usr/libexec/java_home -V
      
      
      MacBook-Air:~ eng$ /usr/libexec/java_home -V
      Matching Java Virtual Machines (4):
          1.8.0_101, x86_64:  "java SE 8"     /Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home
          1.7.0_79, x86_64:   "Java SE 7"     /Library/Java/JavaVirtualMachines/jdk1.7.0_79.jdk/Contents/Home
          1.6.0_65-b14-466.1, x86_64: "java se 6"     /System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home
          1.6.0_65-b14-466.1, i386:   "Java SE 6"     /System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home
      /Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home
      
      
      默认JDK1.6(Apple自带JDK)路径：   /System/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home，修改38行为JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/${JAVA_VERSION}/Home
      默认JDK1.7、1.8(Oracle) Home :  /Library/Java/JavaVirtualMachines/jdk1.8.0_101.jdk/Contents/Home，修改38行为JAVA_HOME=/Library/Frameworks/JavaVM.framework/Versions/${JAVA_VERSION}/Home
      
   ```
   4.调用代理
   * 在processor的main方法中添加如下代码，如本项目在 ZhihuPageProcessor.java中添加
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






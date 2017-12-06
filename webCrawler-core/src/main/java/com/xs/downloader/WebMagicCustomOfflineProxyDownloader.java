package com.xs.downloader;

import com.virjar.dungproxy.webmagic7.DungProxyDownloader;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;

import java.io.IOException;

public class WebMagicCustomOfflineProxyDownloader extends DungProxyDownloader {

    @Override
    protected boolean needOfflineProxy(Page page) {
        return StringUtils.containsIgnoreCase(page.getRawText(), "系统检测到您的帐号或IP存在异常流量，请输入以下字符用于确认这些请求不是自动程序发出的");
    }

    @Override
    protected boolean needOfflineProxy(IOException e) {
        // return e instanceof SSLException;//如果异常类型是SSL,代表IP被封禁,你也可以不实现
        return false;
    }

    @Override
    protected boolean needOfflineProxy(int status) {
        // 根据响应码下线IP,这里的响应码是webMagic里面不在acceptStatusCode里面的
        return false;
    }
}

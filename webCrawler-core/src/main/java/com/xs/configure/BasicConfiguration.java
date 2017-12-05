package com.xs.configure;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import us.codecraft.webmagic.Site;

/**
 *
 */
public class BasicConfiguration extends AbstractConfiguration {
    protected Site site;

    protected String baseDir;

    public BasicConfiguration(){

    }

    public BasicConfiguration(String path) {
        super(path);
    }

    @Override
    protected void resolve() {
        JSONObject jsonObject = JSON.parseObject(config);
        site = JSON.parseObject(jsonObject.getString("site"), Site.class);
        setBaseDir(jsonObject.getString("baseDir"));
    }

    public String getBaseDir() {
        return baseDir;
    }

    private void setBaseDir(String dir) {
        baseDir = dir.endsWith("/") ? dir : dir + "/";
    }

    public Site getSite() {
        return site;
    }
}

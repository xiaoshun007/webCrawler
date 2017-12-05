package com.xs.configure;

import com.xs.util.FileHelper;

/**
 * 配置信息抽象类
 */
public abstract class AbstractConfiguration {
    public static final String DEFAULT_CONFIG_FILE = "config.json";
    public static final String DEFAULT_CONFIG_DIR = AbstractConfiguration.class.getResource("/").getPath() + "config/";

    protected String config;

    protected AbstractConfiguration() {
        this(DEFAULT_CONFIG_DIR + DEFAULT_CONFIG_FILE);
    }

    protected AbstractConfiguration(String path) {
        config = FileHelper.getRawText(path);
        resolve();
    }

    abstract protected void resolve();

    public String getConfig() {
        return config;
    }
}

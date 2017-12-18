package com.xs.configure;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xs.data.Mongo;

/**
 * mongodb配置
 */
public class MongoDBConfiguration extends AbstractConfiguration {
    public static final String DEFAULT_CONFIG_FILE = "mongo.json";
    public static final String DEFAULT_CONFIG_DIR = AbstractConfiguration.class.getResource("/").getPath() + "config/";

    protected Mongo mogo;

    public MongoDBConfiguration() {
        this(DEFAULT_CONFIG_DIR + DEFAULT_CONFIG_FILE);
    }

    public MongoDBConfiguration(String path) {
        super(path);
    }

    @Override
    protected void resolve() {
        JSONObject jsonObject = JSON.parseObject(config);
        mogo = JSON.parseObject(jsonObject.getString("mongo"), Mongo.class);
    }

    public Mongo getMogo() {
        return mogo;
    }

    public static void main(String[] args) {
        MongoDBConfiguration mongoDBConfiguration = new MongoDBConfiguration();
        Mongo mongo = mongoDBConfiguration.getMogo();
        System.out.println("address: " + mongo.getServerAddress());
        System.out.println("port: " + mongo.getPort());
        System.out.println("username: " + mongo.getUsername());
        System.out.println("password: " + mongo.getPassword());
    }
}

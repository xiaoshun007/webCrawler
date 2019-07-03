package com.xs.configure;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xs.data.Redis;

/**
 * redis配置
 */
public class RedisConfiguration extends AbstractConfiguration {
    public static final String DEFAULT_CONFIG_FILE = "redis.json";
    public static final String DEFAULT_CONFIG_DIR = AbstractConfiguration.class.getResource("/").getPath() + "config/";

    protected Redis redis;

    public RedisConfiguration() {
        this(DEFAULT_CONFIG_DIR + DEFAULT_CONFIG_FILE);
    }

    public RedisConfiguration(String path) {
        super(path);
    }

    @Override
    protected void resolve() {
        JSONObject jsonObject = JSON.parseObject(config);
        redis = JSON.parseObject(jsonObject.getString("redis"), Redis.class);
    }

    public Redis getRedis() {
        return redis;
    }

    public static void main(String[] args) {
        RedisConfiguration redisConfiguration = new RedisConfiguration();
        Redis redis = redisConfiguration.getRedis();
        System.out.println("address: " + redis.getServerAddress());
        System.out.println("port: " + redis.getPort());
        System.out.println("username: " + redis.getUsername());
        System.out.println("password: " + redis.getPassword());
        System.out.println("key: " + redis.getKey());
    }
}

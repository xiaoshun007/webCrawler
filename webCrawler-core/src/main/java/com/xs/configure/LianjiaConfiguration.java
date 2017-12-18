package com.xs.configure;

/**
 * 链家爬虫-configuration
 */
public class LianjiaConfiguration extends BasicConfiguration {
    public static final String SUBDIR_MEMBER = "wuhan1/";

    public LianjiaConfiguration(String path) {
        super(path);
    }

    public LianjiaConfiguration() {}

    public String getLianjiaPath() {
        return getBaseDir() + SUBDIR_MEMBER;
    }

    public String getLianjiaDataPath() {
        return getLianjiaPath() + site.getDomain() + "/";
    }

    public static void main(String[] args) {
        LianjiaConfiguration configuration = new LianjiaConfiguration();
        System.out.println(configuration.getSite());
        System.out.println(configuration.getBaseDir());
        System.out.println(configuration.getLianjiaPath());
    }

}

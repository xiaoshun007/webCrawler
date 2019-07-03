package com.xs.configure;

/**
 * 链家爬虫-configuration
 */
public class DianpingConfiguration extends BasicConfiguration {
    public static final String SUBDIR_MEMBER = "wuhan/";

    public DianpingConfiguration(String path) {
        super(path);
    }

    public DianpingConfiguration() {}

    public String getDianpingPath() {
        return getBaseDir() + SUBDIR_MEMBER;
    }

    public String getLianjiaDataPath() {
        return getDianpingPath() + site.getDomain() + "/";
    }

    public static void main(String[] args) {
        DianpingConfiguration configuration = new DianpingConfiguration();
        System.out.println(configuration.getSite());
        System.out.println(configuration.getBaseDir());
        System.out.println(configuration.getDianpingPath());
    }

}

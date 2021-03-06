package com.xs.configure;

public class CrawlerConfiguration extends BasicConfiguration {
    public static final String SUBDIR_MEMBER = "member/";
    public static final String SUBDIR_FOLLOWEE = "followee/";

    public CrawlerConfiguration(String path) {
        super(path);
    }

    public CrawlerConfiguration() {

    }

    public String getMemberPath() {
        return getBaseDir() + SUBDIR_MEMBER;
    }

    public String getFolloweePath() {
        return getBaseDir() + SUBDIR_FOLLOWEE;
    }

    public String getMemberDataPath() {
        return getMemberPath() + site.getDomain() + "/";
    }

    public String getFolloweeDataPath() {
        return getFolloweePath() + site.getDomain() + "/";
    }

    public static void main(String[] args) {
        CrawlerConfiguration configuration = new CrawlerConfiguration();
        System.out.println(configuration.getSite());
        System.out.println(configuration.getBaseDir());
        System.out.println(configuration.getMemberPath());
    }
}

package com.xs.data.domain.lianjia;

/**
 * 链家结果数据
 */
public class LianjiaResultData {
    /**
     * 标题
     */
    private String title;

    /**
     * url
     */
    private String url;

    /**
     * 内容
     */
    private Object content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}

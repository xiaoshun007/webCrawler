package com.xs.data.domain.lianjia;

/**
 * 楼盘动态
 */
public class EstateDynamic {
    /**
     * 动态标题
     */
    private String dynamicTitle;

    /**
     * 动态信息
     */
    private String dynamicDetail;

    public String getDynamicTitle() {
        return dynamicTitle;
    }

    public void setDynamicTitle(String dynamicTitle) {
        this.dynamicTitle = dynamicTitle;
    }

    public String getDynamicDetail() {
        return dynamicDetail;
    }

    public void setDynamicDetail(String dynamicDetail) {
        this.dynamicDetail = dynamicDetail;
    }
}

package com.xs.data.domain.lianjia;

import java.util.List;

/**
 * 链家最新楼盘信息
 */
public class LianjiaDetail {
    /**
     * 楼盘位置
     */
    private String houseLocation;

    /**
     * 楼盘名称
     */
    private String houseName;

    /**
     * 均价
     */
    private double avgPrice;

    /**
     * 最近更新时间
     */
    private String lastUpdateTime;

    /**
     * 物业类型
     */
    private String propertyType;

    /**
     * 项目地址
     */
    private String projectAddress;

    /**
     * 最新开盘
     */
    private LatestOpening latestOpening;

    /**
     * 状态：在售
     */
    private String state;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 楼盘动态
     */
    private EstateDynamic estateDynamic;

    /**
     * 户型介绍
     */
    private List<HouseOnline> houseOnlines;

    /**
     * 用户点评
     */
    private UserComment userComment;

    /**
     * 楼盘详情
     */
    private BuildInfo buildInfo;

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public LatestOpening getLatestOpening() {
        return latestOpening;
    }

    public void setLatestOpening(LatestOpening latestOpening) {
        this.latestOpening = latestOpening;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public EstateDynamic getEstateDynamic() {
        return estateDynamic;
    }

    public void setEstateDynamic(EstateDynamic estateDynamic) {
        this.estateDynamic = estateDynamic;
    }

    public List<HouseOnline> getHouseOnlines() {
        return houseOnlines;
    }

    public void setHouseOnlines(List<HouseOnline> houseOnlines) {
        this.houseOnlines = houseOnlines;
    }

    public UserComment getUserComment() {
        return userComment;
    }

    public void setUserComment(UserComment userComment) {
        this.userComment = userComment;
    }

    public BuildInfo getBuildInfo() {
        return buildInfo;
    }

    public void setBuildInfo(BuildInfo buildInfo) {
        this.buildInfo = buildInfo;
    }
}

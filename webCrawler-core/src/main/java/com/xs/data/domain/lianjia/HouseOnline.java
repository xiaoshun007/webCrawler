package com.xs.data.domain.lianjia;

/**
 * 户型介绍
 */
public class HouseOnline {
    /**
     * 户型概述
     */
    private String overview;

    /**
     * 房型
     */
    private String houseStyle;

    /**
     * 建面
     */
    private int houseArea;

    /**
     * 朝向
     */
    private String orientation;

    /**
     * 状态
     */
    private String state;

    /**
     * 均价
     */
    private double avgPrice;

    /**
     * 户型解读
     */
    private String interpretation;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getHouseStyle() {
        return houseStyle;
    }

    public void setHouseStyle(String houseStyle) {
        this.houseStyle = houseStyle;
    }

    public int getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(int houseArea) {
        this.houseArea = houseArea;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getInterpretation() {
        return interpretation;
    }

    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }
}

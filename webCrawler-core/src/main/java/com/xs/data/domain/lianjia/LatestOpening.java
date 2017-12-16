package com.xs.data.domain.lianjia;

/**
 * 最新开盘
 */
public class LatestOpening {
    /**
     * 开盘时间
     */
    private String openingTime;

    /**
     * 第几期
     */
    private String stage;

    /**
     * 几号楼
     */
    private String houseNum;

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }
}

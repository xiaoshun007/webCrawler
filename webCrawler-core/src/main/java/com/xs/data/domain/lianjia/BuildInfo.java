package com.xs.data.domain.lianjia;

/**
 * 楼盘详情
 */
public class BuildInfo {
    /**
     * 楼盘地址
     */
    private String buildingAdress;

    /**
     * 售楼处地址
     */
    private String cellingAddress;

    /**
     * 开发商
     */
    private String developer;

    /**
     * 物业公司
     */
    private String tenement;

    /**
     * 交房时间
     */
    private String handoverTime;

    /**
     * 容积率
     */
    private double plotRatio;

    /**
     * 产权年限
     */
    private String holding;

    /**
     * 绿化率
     */
    private double greenRatio;

    /**
     * 规划户数
     */
    private int houseHolds;

    /**
     * 物业费用
     */
    private String tenementCost;

    public String getBuildingAdress() {
        return buildingAdress;
    }

    public void setBuildingAdress(String buildingAdress) {
        this.buildingAdress = buildingAdress;
    }

    public String getCellingAddress() {
        return cellingAddress;
    }

    public void setCellingAddress(String cellingAddress) {
        this.cellingAddress = cellingAddress;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getTenement() {
        return tenement;
    }

    public void setTenement(String tenement) {
        this.tenement = tenement;
    }

    public String getHandoverTime() {
        return handoverTime;
    }

    public void setHandoverTime(String handoverTime) {
        this.handoverTime = handoverTime;
    }

    public double getPlotRatio() {
        return plotRatio;
    }

    public void setPlotRatio(double plotRatio) {
        this.plotRatio = plotRatio;
    }

    public String getHolding() {
        return holding;
    }

    public void setHolding(String holding) {
        this.holding = holding;
    }

    public double getGreenRatio() {
        return greenRatio;
    }

    public void setGreenRatio(double greenRatio) {
        this.greenRatio = greenRatio;
    }

    public int getHouseHolds() {
        return houseHolds;
    }

    public void setHouseHolds(int houseHolds) {
        this.houseHolds = houseHolds;
    }

    public String getTenementCost() {
        return tenementCost;
    }

    public void setTenementCost(String tenementCost) {
        this.tenementCost = tenementCost;
    }
}

package com.xs.data.domain.lianjia;

import java.util.List;

/**
 * 用户点评
 */
public class UserComment {
    /**
     * 综合评分
     */
    private double totalscore;

    /**
     * 评分备注：高于98.8%同类新盘
     */
    private String ratio;

    /**
     * 详细评分：周边配套：4.6分 交通方便：4.1分 绿化环境：4.2分
     */
    private String itemscore;

    /**
     * 详细评论
     */
    private List<UserCommentDetail> userCommentDetails;

    public double getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(double totalscore) {
        this.totalscore = totalscore;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getItemscore() {
        return itemscore;
    }

    public void setItemscore(String itemscore) {
        this.itemscore = itemscore;
    }

    public List<UserCommentDetail> getUserCommentDetails() {
        return userCommentDetails;
    }

    public void setUserCommentDetails(List<UserCommentDetail> userCommentDetails) {
        this.userCommentDetails = userCommentDetails;
    }
}

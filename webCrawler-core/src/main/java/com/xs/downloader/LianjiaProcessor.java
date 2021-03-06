package com.xs.downloader;

import com.alibaba.fastjson.JSONObject;
import com.xs.pipeliner.LianJiaMongoPipleline;
import com.xs.pipeliner.OneFilePipeline;
import com.xs.configure.LianjiaConfiguration;
import com.xs.data.domain.lianjia.BuildInfo;
import com.xs.data.domain.lianjia.EstateDynamic;
import com.xs.data.domain.lianjia.HouseOnline;
import com.xs.data.domain.lianjia.LatestOpening;
import com.xs.data.domain.lianjia.LianjiaDetail;
import com.xs.data.domain.lianjia.UserComment;
import com.xs.data.domain.lianjia.UserCommentDetail;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 爬去链家-武汉所有的房源信息
 */
public class LianjiaProcessor implements PageProcessor {
    private Site site = new LianjiaConfiguration().getSite();

    // 列表URL
    private static final String URL_LIST = "https://wh\\.fang\\.lianjia\\.com/loupan/pg\\d+/";
    // 详情URL
    private static final String URL_DETAIL = "https://wh\\.fang\\.lianjia\\.com/loupan/p_\\w+/";

    /**
     * 列表页采用非静态，需要手动拼接
     * @param page page信息
     */
    private void generateListUrl(Page page) {
        String listUrl = page.getHtml().xpath("//div[@class=\"house-lst-page-box\"]/@page-data").toString();
        JSONObject jsonObject = JSONObject.parseObject(listUrl);
        Integer totalPage = jsonObject.getInteger("totalPage");
        List<String> urls = new LinkedList<String>();
        for (int i = 1; i <= totalPage; i++) {
            urls.add("https://wh.fang.lianjia.com/loupan/pg" + i + "/");
            page.addTargetRequests(urls);
        }
    }

    /**
     * 详情信息
     * @param page page信息
     */
    private void buildDetails(Page page) {
        Html html = page.getHtml();

        LianjiaDetail lianjiaDetail = new LianjiaDetail();

        // 楼盘名称
        String houseName = html.xpath("//a[@class=\"clear\"]//h1/text()").toString();
        lianjiaDetail.setHouseName(houseName);
        // 均价
        String avgPrice = html.xpath("//span[@class=\"junjia\"]/text()").toString();
        // 单位
        String unit = html.xpath("//span[@class=\"yuan\"]/text()").toString();
        // 不指定单位（元/平），方便ES统计
        avgPrice = StringUtils.isNotBlank(avgPrice) ? avgPrice : "0";
        lianjiaDetail.setAvgPrice(Double.parseDouble(avgPrice));
        // 最近更新时间
        String lastUpdateTime = html.xpath("//p[@class=\"update\"]//span/text()").toString();
        lianjiaDetail.setLastUpdateTime(lastUpdateTime);
        // 物业类型
        String propertyType = html.xpath("//p[@class=\"wu-type\"]//span[2]/text()").toString();
        lianjiaDetail.setPropertyType(propertyType);
        // 项目地址
        String projectAddress = html.xpath("//p[@class=\"where\"]//span/text()").toString();
        lianjiaDetail.setProjectAddress(projectAddress);

        LatestOpening latestOpening = new LatestOpening();

        // 开盘时间
        String openingTime = html.xpath("//div[@class=\"more-date\"]/text()").toString();
        latestOpening.setOpeningTime(openingTime);
        // 第几期
        String stage = html.xpath("//div[@class=\"more-loupan\"]/text()").toString();
        latestOpening.setStage(stage);
        // 几号楼
        String houseNum = html.xpath("//div[@class=\"more-detail\"]/text()").toString();
        latestOpening.setHouseNum(houseNum);

        lianjiaDetail.setLatestOpening(latestOpening);

        // 状态
        String state = html.xpath("//span[@class=\"state\"]/text()").toString();
        lianjiaDetail.setState(state);
        // 电话
        String telephone = html.xpath("//div[@class=\"btn_phone_ll\"]//span/text()").toString();
        lianjiaDetail.setTelephone(telephone);

        EstateDynamic estateDynamic = new EstateDynamic();

        // 动态标题
        String dynamicTitle = html.xpath("//*[@id=\"estate-dynamic\"]/div/div[1]/div[2]/div/div[1]/text()").toString();
        estateDynamic.setDynamicTitle(dynamicTitle);
        // 动态信息
        String dynamicDetail = html.xpath("//*[@id=\"estate-dynamic\"]/div/div[1]/div[2]/div/a/text()").toString();
        estateDynamic.setDynamicDetail(dynamicDetail);

        lianjiaDetail.setEstateDynamic(estateDynamic);

        // 户型介绍
        List<HouseOnline> houseOnlines = new ArrayList<>();

        List<Selectable> houseOnlineNodes = html.xpath("//*[@id=\"house-online\"]/div[1]/div[1]/ul").nodes();
        if (CollectionUtils.isNotEmpty(houseOnlineNodes)) {
            for(Selectable htmlNode: houseOnlineNodes) {
                HouseOnline houseOnline = new HouseOnline();
                // 房型
                String houseStyle = htmlNode.xpath("//p[@class=\"p1\"]/text()").toString().trim();
                houseOnline.setHouseStyle(houseStyle);
                // 建面
                String originalArea = htmlNode.xpath("//p[@class=\"p1\"]//span/text()").toString();
                String houseArea = "0";
                if (StringUtils.isNotBlank(originalArea)) {
                    houseArea = originalArea.replace("建面 ", "").replace("m²", "");
                }

                houseOnline.setHouseArea(Integer.parseInt(houseArea));
                // 朝向
                String orientation = htmlNode.xpath("//span[@class=\"p1-orientation\"]/text()").toString();
                houseOnline.setOrientation(orientation);
                // 状态
                String houseState = htmlNode.xpath("//span[@class=\"p1-state\"]/text()").toString();
                houseOnline.setState(houseState);
                // 均价（单位：万/套）
                String houseAvgPrice = htmlNode.xpath("//p[@class=\"p2\"]//span/text()").toString();
                houseAvgPrice = StringUtils.isNotBlank(houseAvgPrice) ? houseAvgPrice : "0";
                houseOnline.setAvgPrice(Double.parseDouble(houseAvgPrice));
                // 户型解读
                String interpretation = htmlNode.xpath("//p[@class=\"p3\"]//span/text()").toString();
                houseOnline.setInterpretation(interpretation);

                houseOnlines.add(houseOnline);
            }
        }

        lianjiaDetail.setHouseOnlines(houseOnlines);

        UserComment userComment = new UserComment();
        List<UserCommentDetail> userCommentDetails = new ArrayList<>();
        // 综合评分
        String originalTotalScore = html.xpath("//span[@class=\"score\"]/text()").toString();
        String totalScore = "0";
        if (StringUtils.isNotBlank(originalTotalScore)) {
            totalScore = originalTotalScore.replace("分", "");
        }

        userComment.setTotalscore(Double.parseDouble(totalScore));
        // 评分备注：高于98.8%同类新盘
        String ratio = html.xpath("//span[@class=\"ratio\"]/text()").toString();
        userComment.setRatio(ratio);
        // 详细评分：周边配套：4.6分 交通方便：4.1分 绿化环境：4.2分
        String itemscore = html.xpath("//*[@id=\"user-comment\"]/div[1]/div/div[2]/allText()").toString();
        userComment.setItemscore(itemscore);

        // 全部评价
        List<Selectable> commentNodes = html.xpath("//div[@class=\"r_comment\"]").nodes();
        if (CollectionUtils.isNotEmpty(commentNodes)) {
            for (Selectable htmlNode : commentNodes) {
                UserCommentDetail userCommentDetail = new UserCommentDetail();
                // 评价内容
                String comment = htmlNode.xpath("//div[@class=\"words\"]/text()").toString();
                userCommentDetail.setComment(comment);

                userCommentDetails.add(userCommentDetail);
            }
        }

        userComment.setUserCommentDetails(userCommentDetails);
        lianjiaDetail.setUserComment(userComment);


        BuildInfo buildInfo = new BuildInfo();
        // 楼盘信息（多个相同到标签处理方法）
        List<Selectable> buildingNodes = html.xpath("//span[@class=\"label-val\"]/allText()").nodes();
        if (CollectionUtils.isNotEmpty(buildingNodes)) {
            // 楼盘地址
            String buildingAdress = buildingNodes.get(0).get();
            buildInfo.setBuildingAdress(buildingAdress);
            // 售楼处地址
            String cellingAddress = buildingNodes.get(1).get();
            buildInfo.setCellingAddress(cellingAddress);
            // 开发商
            String developer = buildingNodes.get(2).get();
            buildInfo.setDeveloper(developer);
            // 物业公司
            String tenement = buildingNodes.get(3).get();
            buildInfo.setTenement(tenement);
            // 交房时间
            String handoverTime = buildingNodes.get(6).get();
            buildInfo.setHandoverTime(handoverTime);
            // 容积率
            String plotRatio = buildingNodes.get(7).get();
            plotRatio = plotRatio.equals("暂无") ? "0" : plotRatio;
            buildInfo.setPlotRatio(Double.parseDouble(plotRatio));
            // 产权年限
            String holding = buildingNodes.get(8).get();
            buildInfo.setHolding(holding);
            // 绿化率
            String originalGreenRatio = buildingNodes.get(9).get();
            String greenRatio = "0";
            if (StringUtils.isNotBlank(originalGreenRatio) && !originalGreenRatio.equals("暂无")) {
                greenRatio = originalGreenRatio.replace("%", "");
            }
            buildInfo.setGreenRatio(Double.parseDouble(greenRatio));
            // 规划户数
            String houseHolds = buildingNodes.get(10).get();
            houseHolds = StringUtils.isNotBlank(houseHolds) ? houseHolds : "0";
            buildInfo.setHouseHolds(Integer.parseInt(houseHolds));
            // 物业费用
            String tenementCost = buildingNodes.get(11).get();

            buildInfo.setTenementCost(tenementCost);
        }

        lianjiaDetail.setBuildInfo(buildInfo);

        page.putField("content", lianjiaDetail);
    }

    @Override
    public void process(Page page) {
        // 列表页
        if (page.getUrl().regex(URL_LIST).match()) {
            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"list-wrap\"]").links().regex(URL_DETAIL).all());

            this.generateListUrl(page);
            page.setSkip(true);
            // 详情页
        } else {
            page.putField("title", page.getHtml().xpath("//a[@class='clear']/h1/text()").toString());
            page.putField(CrawlerPipeline.URL, page.getUrl().toString());

            this.buildDetails(page);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) throws IOException {
        LianjiaConfiguration configuration = new LianjiaConfiguration();
        String pipelinePath = configuration.getLianjiaPath();

        Spider.create(new LianjiaProcessor())
                .setScheduler(new FileCacheQueueScheduler(pipelinePath))
                .addPipeline(new LianJiaMongoPipleline())
                .addUrl("https://wh.fang.lianjia.com/loupan/pg1/")
                .thread(30)
                .run();
    }
}

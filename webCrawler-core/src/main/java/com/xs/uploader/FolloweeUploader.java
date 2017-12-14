package com.xs.uploader;

import com.xs.configure.CrawlerConfiguration;
import com.xs.data.BaseAssembler;
import com.xs.data.DataProcessor;
import com.xs.data.FileRawInput;
import com.xs.data.elasticSearch.Document;
import com.xs.processor.ZhihuFolloweeDataProcessor;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class FolloweeUploader {
    public static void upload() {
        String index = "zhihu";
        String type = "followee";
        CrawlerConfiguration configuration = new CrawlerConfiguration();

        String folder = configuration.getFolloweeDataPath();
        DataProcessor<File, Document> processor = new ZhihuFolloweeDataProcessor();

        ZhihuElasticsearchUploader outPipeline = new ZhihuElasticsearchUploader(index, type);
        outPipeline.setTimeout(5, TimeUnit.MINUTES);

        BaseAssembler.create(new FileRawInput(folder), processor)
                .addOutPipeline(outPipeline)
                .thread(10)
                .run();

        System.out.println("out sent :" + outPipeline.getCount());
        System.out.println(outPipeline.getBulkProcessor());
    }

    public static void main(String[] args) {
        FolloweeUploader.upload();
    }

}

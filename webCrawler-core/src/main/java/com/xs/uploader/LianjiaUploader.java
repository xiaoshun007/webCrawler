package com.xs.uploader;

import com.xs.configure.LianjiaConfiguration;
import com.xs.data.BaseAssembler;
import com.xs.data.DataProcessor;
import com.xs.data.LianjiaFileRawInput;
import com.xs.data.elasticSearch.Document;
import com.xs.processor.LianjiaDataProcessor;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * 链家数据上传类
 */
public class LianjiaUploader {
    public static void upload() {
        String index = "lianjia";
        String type = "wuhan";
        LianjiaConfiguration configuration = new LianjiaConfiguration();

        // 读取的是domain下的数据
        String folder = configuration.getLianjiaDataPath();
        DataProcessor<File, Document> processor = new LianjiaDataProcessor();

        LianjiaElasticsearchUploader outPipeline = new LianjiaElasticsearchUploader(index, type);
        outPipeline.setTimeout(5, TimeUnit.MINUTES);

        BaseAssembler.create(new LianjiaFileRawInput(folder), processor)
                .addOutPipeline(outPipeline)
                .thread(10)
                .run();

        System.out.println("out sent :" + outPipeline.getCount());
        System.out.println(outPipeline.getBulkProcessor());
    }

    public static void main(String[] args) {
        LianjiaUploader.upload();
    }
}

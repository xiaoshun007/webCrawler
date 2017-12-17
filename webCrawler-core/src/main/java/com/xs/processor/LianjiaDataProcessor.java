package com.xs.processor;

import com.xs.data.DataProcessor;
import com.xs.data.HashSetDuplicateRemover;
import com.xs.data.elasticSearch.Document;
import com.xs.util.FileHelper;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.selector.Json;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 从原始数据生成满足 Elasticsearch 格式的 json 数据
 */
public class LianjiaDataProcessor implements DataProcessor<File, Document> {
    // 去重
    private HashSetDuplicateRemover<String> duplicateRemover = new HashSetDuplicateRemover<>();

    @Override
    public List<Document> process(File inItem) {
        String s = readData(inItem);
        List<Document> documents = null;

        if (!StringUtils.isEmpty(s)) {
            documents = new ArrayList<>(1);
            Json json = new Json(s);
            // 由于数据没有设置id，因此用url作为是否重复到标志
            String url = json.jsonPath("$.url").get();
            // 数据去重
            if (!duplicateRemover.isDuplicate(url)) {
                documents.add(new Document(url, s));
            }
        }
        return documents;
    }

    private static String readData(File inItem) {
        List<String> datas = FileHelper.processFile(inItem, br -> {
            String s = br.readLine();
            return Collections.singletonList(s);
        }).orElse(new ArrayList<>());

        return datas.size() == 0 ? null : datas.get(0);
    }
}

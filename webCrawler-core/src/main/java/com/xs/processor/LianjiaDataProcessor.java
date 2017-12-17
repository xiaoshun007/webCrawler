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
    private HashSetDuplicateRemover<String> duplicateRemover = new HashSetDuplicateRemover<>();

    @Override
    public List<Document> process(File inItem) {
        String s = readData(inItem);
        List<Document> documents = null;

        if (!StringUtils.isEmpty(s)) {
            documents = new ArrayList<>(1);
            Json json = new Json(s);
            String title = json.jsonPath("$.title").get();
            if (!duplicateRemover.isDuplicate(title)) {
                documents.add(new Document(title, s));
            }
        }
        return documents;
    }

    private static String readData(File inItem) {
        List<String> datas = FileHelper.processFile(inItem, br -> {
//            br.readLine();//pass first line
            String s = br.readLine();
            return Collections.singletonList(s);
        }).orElse(new ArrayList<>());

        return datas.size() == 0 ? null : datas.get(0);
    }
}

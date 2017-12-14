package com.xs.uploader;

import com.xs.data.OutPipeline;
import com.xs.data.elasticSearch.Document;
import com.xs.data.elasticSearch.ElasticsearchUploader;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ZhihuElasticsearchUploader extends ElasticsearchUploader implements OutPipeline<Document>, AutoCloseable {

    private String index;
    private String type;

    private long awaitTime = 1;
    private TimeUnit timeUnit = TimeUnit.MINUTES;

    private AtomicLong count = new AtomicLong(0);

    public ZhihuElasticsearchUploader(String index, String type) {
        this.index = index;
        this.type = type;
    }

    public void upload(Document document) {
        upload(this.index, this.type, document);
    }

    @Override
    public void process(Document outItem) {
        upload(outItem);
        count.incrementAndGet();
    }

    public AtomicLong getCount() {
        return count;
    }

    @Override
    public void close() throws InterruptedException {
        awaitClose(awaitTime, timeUnit);
    }

    public void setTimeout(long awaitTime, TimeUnit timeUnit) {
        this.awaitTime = awaitTime;
        this.timeUnit = timeUnit;
    }

}

package com.xs.pipeliner;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class OneFilePipeline extends FilePersistentBase implements Pipeline {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private PrintWriter printWriter;

    public OneFilePipeline() throws FileNotFoundException, UnsupportedEncodingException {
        this("/data/webmagic/");
    }

    public OneFilePipeline(String path) throws FileNotFoundException, UnsupportedEncodingException {
        setPath(path);
        printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile(path) + ".json"), "UTF-8"));
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        if (resultItems.getAll().size() == 0) {
            return;
        }

        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {

            if (entry.getValue() instanceof Iterable) {
                Iterable value = (Iterable) entry.getValue();
                printWriter.println(entry.getKey() + ":");
                for (Object o : value) {
                    printWriter.println(o);
                }
            } else {
//                printWriter.println(JSON.toJSONString(resultItems.getAll()));
            }
        }

        printWriter.println(JSON.toJSONString(resultItems.getAll()));
        printWriter.flush();
    }
}
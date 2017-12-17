package com.xs.data;

import java.io.File;
import java.util.Arrays;

/**
 * 链家文件添加到队列
 */
public class LianjiaFileRawInput extends RawInput<File> {

    public LianjiaFileRawInput(String path) {
        File folder = new File(path);
        File[] files = folder.listFiles((File dir, String name) -> {
            return name.endsWith(".json");
        });
        if (files != null) {
            queue.addAll(Arrays.asList(files));
        }
    }

    public int getLeftCount() {
        return queue.size();
    }

    public File poll() {
        return queue.poll();
    }

}

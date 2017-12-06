package com.xs.data;

public class RawInput<T> extends DataFlow<T> {

    public int getLeftCount() {
        return queue.size();
    }

    public T poll() {
        return queue.poll();
    }

}

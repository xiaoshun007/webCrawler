package com.xs.juc;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        MyRunnable myRunnable = new MyRunnable(lock);
        for (int i = 0; i < 5; i++) {
            // 多个线程共享一个runnable
            new Thread(myRunnable, "tt" + i).start();
        }
    }
}

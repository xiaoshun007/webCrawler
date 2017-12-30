package com.xs.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用ReetrantReadWriteLock实现一个简单的缓存管理器
 */
public class ReentrantReadWriteLockTest {

    // map缓存数据
    private static Map<String, String> map = new HashMap<>();
    // 读写锁
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private String getData(String name) {
        String value = null;

        // 读锁，多个线程可以共享
        readWriteLock.readLock().lock();
        value = map.get(name);

        if (null == value) {
            // 释放读锁
            readWriteLock.readLock().unlock();

            // 加写锁
            readWriteLock.writeLock().lock();
            try {
                if (null == value) {
                    System.out.println("缓存map中没有数据，从数据库读取。。。。。。");
                    value = "I want you!";
                    map.put("111", value);
                }

            } finally {
                readWriteLock.writeLock().unlock();
            }

            readWriteLock.readLock().lock();
        }

        readWriteLock.readLock().unlock();

        return value;
    }

    public static void main(String[] args) {
        ReentrantReadWriteLockTest test = new ReentrantReadWriteLockTest();
        // 第一次缓存中没有数据，从数据库读取
        System.out.println("第一次读取：" + test.getData("111"));
        // 第二次从缓存中读取数据
        System.out.println("第二次读取：" + test.getData("111"));
    }
}

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
                // Recheck state because another thread might have,acquired write lock and changed state before we did.
                if (null == value) {
                    System.out.println("缓存map中没有数据，从数据库读取。。。。。。");
                    value = "I want you!";
                    map.put("111", value);
                }

                // 使用锁降级，如果先释放写锁再加读锁，有可能其他线程也竞争到写锁，导致读取到数据是其他线程写入的值
                readWriteLock.readLock().lock();
            } finally {
                readWriteLock.writeLock().unlock();
            }

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

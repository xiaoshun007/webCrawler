package com.xs.juc;

import java.util.concurrent.locks.Lock;

public class MyRunnable implements Runnable {
    private Lock lock;

    private int i = 0;

    public MyRunnable(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        lock.lock();

        try {
            System.out.println(Thread.currentThread().getName() + " running......");
            Thread.sleep(5000);
            i++;
            System.out.println(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " stopped......");
            lock.unlock();
        }
    }
}

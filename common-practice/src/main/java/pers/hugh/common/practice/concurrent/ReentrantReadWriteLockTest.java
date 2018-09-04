package pers.hugh.common.practice.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁，只可同时读，不可同时写，不可同时读写
 *
 * @author xzding
 * @date 2018/9/4
 */
public class ReentrantReadWriteLockTest {

    public static void main(String[] args) {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Lock readLock = readWriteLock.readLock();
        Lock writeLock = readWriteLock.writeLock();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    readLock.lock();
                    System.out.println("read...");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("read end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    readLock.unlock();
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    writeLock.lock();
                    System.out.println("write...");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("write end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    writeLock.unlock();
                }
            }).start();
        }
    }
}

package pers.hugh.common.practice.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现了类似LinkedBlockingQueue的操作
 *
 * @author xzding
 * @date 2018/8/16
 */
public class ReentrantLockTest {

    private static AtomicInteger count = new AtomicInteger();
    private static int capacity = 2;

    private static ReentrantLock putLock = new ReentrantLock();
    private static Condition notFull = putLock.newCondition();
    private static ReentrantLock takeLock = new ReentrantLock();
    private static Condition notEmpty = takeLock.newCondition();

    public static void main(String[] args) {

        new Thread(() -> {
            int c = -1;
            putLock.lock();
            try {
                if (count.get() >= capacity) {
                    notFull.await();
                }
                System.out.println("put operation 1 ...");
                c = count.incrementAndGet();
                //自身发现notFull,就唤醒其他put操作，而非让take操作唤醒，防止两个take操作只唤醒了一个put操作
                if (c < capacity) {
                    notFull.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                putLock.unlock();
            }
            if (c > 0) {
                singalNotEmpty();
            }
        }).start();

        new Thread(() -> {
            int c = -1;
            putLock.lock();
            try {
                if (count.get() >= capacity) {
                    notFull.await();
                }
                System.out.println("put operation 2 ...");
                c = count.incrementAndGet();
                if (c < capacity) {
                    notFull.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                putLock.unlock();
            }
            if (c > 0) {
                singalNotEmpty();
            }
        }).start();

        new Thread(() -> {
            int c = -1;
            putLock.lock();
            try {
                if (count.get() >= capacity) {
                    notFull.await();
                }
                System.out.println("put operation 3 ...");
                c = count.incrementAndGet();
                if (c < capacity) {
                    notFull.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                putLock.unlock();
            }
            if (c > 0) {
                singalNotEmpty();
            }
        }).start();

        new Thread(() -> {
            int c = -1;
            takeLock.lock();
            try {
                if (count.get() == 0) {
                    notEmpty.await();
                }
                System.out.println("take operation 1 ...");
                c = count.decrementAndGet();
                if (c > 0) {
                    notEmpty.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                takeLock.unlock();
            }
            if (c < capacity) {
                singalNotFull();
            }
        }).start();

        new Thread(() -> {
            int c = -1;
            takeLock.lock();
            try {
                if (count.get() == 0) {
                    notEmpty.await();
                }
                System.out.println("take operation 2 ...");
                c = count.decrementAndGet();
                if (c > 0) {
                    notEmpty.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                takeLock.unlock();
            }
            if (c < capacity) {
                singalNotFull();
            }
        }).start();

        new Thread(() -> {
            int c = -1;
            takeLock.lock();
            try {
                if (count.get() == 0) {
                    notEmpty.await();
                }
                System.out.println("take operation 3 ...");
                c = count.decrementAndGet();
                if (c > 0) {
                    notEmpty.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                takeLock.unlock();
            }
            if (c < capacity) {
                singalNotFull();
            }
        }).start();

    }

    private static void singalNotEmpty() {
        takeLock.lock();
        try {
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }

    private static void singalNotFull() {
        putLock.lock();
        try {
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }
}

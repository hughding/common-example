package pers.hugh.common.practice.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @author xzding
 * @date 2018/8/15
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        new Thread(() -> {
            try {
                countDownLatch.countDown();
                System.out.println("Thread 1 countDown");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                countDownLatch.countDown();
                System.out.println("Thread 2 countDown");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                countDownLatch.countDown();
                System.out.println("Thread 3 countDown");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        try {
            System.out.println("Thread main await ...");
            countDownLatch.await();
            System.out.println("Thread main await finish");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

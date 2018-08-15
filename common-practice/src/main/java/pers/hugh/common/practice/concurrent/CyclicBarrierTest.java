package pers.hugh.common.practice.concurrent;

import java.util.concurrent.CyclicBarrier;

/**
 * @author xzding
 * @date 2018/8/15
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        new Thread(() -> {
            try {
                System.out.println("Thread 1 await ...");
                cyclicBarrier.await();
                System.out.println("Thread 1 await finish");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("Thread 2 await ...");
                cyclicBarrier.await();
                System.out.println("Thread 2 await finish");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        try {
            System.out.println("Thread main await ...");
            cyclicBarrier.await();
            System.out.println("Thread main await finish");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

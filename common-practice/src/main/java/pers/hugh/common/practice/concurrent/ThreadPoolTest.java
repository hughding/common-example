package pers.hugh.common.practice.concurrent;

import java.util.concurrent.*;

/**
 * @author hughding
 * @date 2019/3/8 14:54
 **/
public class ThreadPoolTest {

	public static void main(String[] args) {
//		test1();
//		test2();
//		test3();
	}

	private static void test1(){
		ExecutorService executorService = new ThreadPoolExecutor(0, 1,
				60L, TimeUnit.SECONDS, new SynchronousQueue<>(),
				(r) -> {
					Thread thread = new Thread(r);
					thread.setPriority(Thread.MAX_PRIORITY);
					return thread;
				},
				(r, e) -> System.out.println("rejectHandler"));

		for (int i = 0; i < 20; i++) {
			final String index = String.valueOf(i);
			executorService.submit(() -> System.out.println("Task " + index));
		}
	}
	private static void test2(){
		ExecutorService executorService = new ThreadPoolExecutor(0, 1,
				60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1),
				(r) -> {
					Thread thread = new Thread(r);
					thread.setPriority(Thread.MAX_PRIORITY);
					return thread;
				}/*,
				(r, e) -> System.out.println("rejectHandler")*/);

		for (int i = 0; i < 20; i++) {
			final String index = String.valueOf(i);
			executorService.submit(() -> System.out.println("Task " + index));
		}
	}

	private static void test3(){
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		for (int i = 0; i < 20; i++) {
			final String index = String.valueOf(i);
			executorService.submit(() -> System.out.println("Task " + index));
		}
	}
}

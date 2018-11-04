package question1.atomics;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Integer> buffer = new LinkedBlockingQueue<>();
		final int CAPACITY = 10;
		CountDownLatch latch = new CountDownLatch(7);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			Producer producer = new Producer(buffer, CAPACITY, latch);
			Thread t1 = new Thread(producer);
			t1.start();
		}
		for (int i = 0; i < 2; i++) {
			Consumer consumer = new Consumer(buffer, latch);
			Thread t2 = new Thread(consumer);
			t2.start();
		}
		latch.await();
		System.out.printf("Runtime with 5 producers & 2 consumers: %d ms%n",
				System.currentTimeMillis() - start);
	}
}

package question1.locks;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Queue<Integer> buffer = new LinkedList<>();
		final int CAPACITY = 10;
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		CountDownLatch latch = new CountDownLatch(7);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			Producer producer = new Producer(buffer, CAPACITY, lock, condition,
					latch);
			Thread t1 = new Thread(producer);
			t1.start();
		}
		for (int i = 0; i < 2; i++) {
			Consumer consumer = new Consumer(buffer, lock, condition, latch);
			Thread t2 = new Thread(consumer);
			t2.start();
		}
		latch.await();
		System.out.printf("Runtime with 5 produces & 2 consumers: %d ms%n",
				System.currentTimeMillis() - start);
	}
}

package question1.locks;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Producer implements Runnable {
	private Queue<Integer> buffer;
	private int capacity;
	private ReentrantLock lock;
	private Condition condition;
	private CountDownLatch latch;

	public Producer(Queue<Integer> buffer, int capacity, ReentrantLock lock,
			Condition condition, CountDownLatch latch) {
		this.buffer = buffer;
		this.capacity = capacity;
		this.lock = lock;
		this.condition = condition;
		this.latch = latch;
	}

	@Override
	public void run() {
		Random rand = new Random();
		final int MAX_ITEMS = 100;
		int totalItems = 0;
		// Produce
		while (true) {
			try {
				if (totalItems == MAX_ITEMS) {
					latch.countDown();
					break;
				}
				lock.lock();
				if (buffer.size() == capacity) {
					condition.await();
				}
				int value = rand.nextInt(10);
				buffer.add(value);
				totalItems++;
				System.out.printf("%s has produced %d%n", Thread
						.currentThread().getName(), value);
				condition.signal();
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName()
						+ " has been interrupted.");
			} finally {
				if (lock.isHeldByCurrentThread()) {
					lock.unlock();
				}
			}
		}
	}
}

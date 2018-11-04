package question1.isolatedSections;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Producer implements Runnable {
	private BlockingQueue<Integer> buffer;
	private int capacity;
	private CountDownLatch latch;
	private final int MAX_ITEMS = 100;
	private int totalItems;

	public Producer(BlockingQueue<Integer> buffer, int capacity,
			CountDownLatch latch) {
		this.buffer = buffer;
		this.capacity = capacity;
		this.latch = latch;
	}

	@Override
	public void run() {
		// Produce
		while (true) {
			if (!produce()) {
				break;
			}
		}
	}

	private boolean produce() {
		synchronized (this) {
			Random rand = new Random();

			if (totalItems == MAX_ITEMS) {
				latch.countDown();
				return false;
			}
			if (buffer.size() == capacity) {
				return true;
			}
			if (buffer.size() < capacity) {
				int value = rand.nextInt(10);
				buffer.add(value);
				totalItems++;
				System.out.printf("%s has produced %d%n", Thread
						.currentThread().getName(), value);
			}
			return true;
		}
	}
}

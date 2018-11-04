package question1.atomics;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class Producer implements Runnable {
	private static final long SENTINEL = -1;
	private static volatile AtomicLong threadNumber = new AtomicLong(SENTINEL);

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
		final long THREAD_NUMBER = Thread.currentThread().getId();
		while (true) {
			while (threadNumber.get() != SENTINEL) {
				Thread.yield();
			}
			threadNumber.set(THREAD_NUMBER);
			if (!produce()) {
				threadNumber.set(SENTINEL);
				break;
			}
			threadNumber.set(SENTINEL);
		}
	}

	private boolean produce() {
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
			System.out.printf("%s has produced %d%n", Thread.currentThread()
					.getName(), value);
		}
		return true;
	}

}

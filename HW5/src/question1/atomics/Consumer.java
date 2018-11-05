package question1.atomics;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class Consumer implements Runnable {
	private static final long SENTINEL = -1;
	private static volatile AtomicLong threadNumber = new AtomicLong(SENTINEL);

	private BlockingQueue<Integer> buffer;
	private CountDownLatch latch;

	Consumer(BlockingQueue<Integer> buffer, CountDownLatch latch) {
		this.buffer = buffer;
		this.latch = latch;
	}

	@Override
	public void run() {
		// Consume
		final long THREAD_NUMBER = Thread.currentThread().getId();
		while (true) {
			while (threadNumber.get() != SENTINEL) {
				Thread.yield();
			}
			threadNumber.set(THREAD_NUMBER);
			if (!consume()) {
				threadNumber.set(SENTINEL);
				break;
			}
			threadNumber.set(SENTINEL);
		}
	}

	private boolean consume() {
		if (buffer.size() == 0) {
			latch.countDown();
			return false;
		}
		Integer value = buffer.poll(); // <--- consume
		if (value != null) {
			System.out.printf("%s has consumed %d%n", Thread.currentThread()
					.getName(), value);
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
}

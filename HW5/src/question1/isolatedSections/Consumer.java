package question1.isolatedSections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Consumer implements Runnable {
	private BlockingQueue<Integer> buffer;
	private CountDownLatch latch;

	Consumer(BlockingQueue<Integer> buffer, CountDownLatch latch) {
		this.buffer = buffer;
		this.latch = latch;
	}

	@Override
	public void run() {
		// Consume
		while (true) {
			if (!consume()) {
				break;
			}

		}
	}

	private boolean consume() {
		synchronized (this) {
			if (buffer.size() == 0) {
				latch.countDown();
				return false;
			}
			Integer value = buffer.poll(); // <--- consume
			if (value != null) {
				System.out.printf("%s has consumed %d%n", Thread
						.currentThread().getName(), value);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;

		}
	}
}

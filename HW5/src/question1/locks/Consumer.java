package question1.locks;

import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Consumer implements Runnable {
	private Queue<Integer> buffer;
	private ReentrantLock lock;
	private Condition condition;
	private CountDownLatch latch;

	Consumer(Queue<Integer> buffer, ReentrantLock lock, Condition condition,
			CountDownLatch latch) {
		this.buffer = buffer;
		this.lock = lock;
		this.condition = condition;
		this.latch = latch;
	}

	@Override
	public void run() {
		// Consume
		while (true) {
			try {
				lock.lock();
				if (buffer.size() == 0) {
					condition.await(100, TimeUnit.MILLISECONDS);
					if (buffer.size() == 0) {
						latch.countDown();
					} else {
						continue;
					}
				}
				int value = buffer.poll(); // <--- consume
				System.out.printf("%s has consumed %d%n", Thread
						.currentThread().getName(), value);
				Thread.sleep(1000);
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

package question1.locks;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Consumer implements Runnable {
	private Queue<Integer> buffer;
	private Lock lock;
	private Condition condition;

	Consumer(Queue<Integer> buffer, Lock lock, Condition condition) {
		this.buffer = buffer;
		this.lock = lock;
		this.condition = condition;
	}

	@Override
	public void run() {
		// Consume
		while (true) {
			try {
				lock.lock();
				if (buffer.size() == 0) {
					condition.await();
				}
				int value = buffer.poll(); // <--- consume
				System.out.printf("%s has consumed %d%n", Thread
						.currentThread().getName(), value);
				condition.signal();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName()
						+ " has been interrupted.");
			} finally {
				lock.unlock();
			}
		}
	}

}

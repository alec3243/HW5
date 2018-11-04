package question1.locks;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Producer implements Runnable {
	private Queue<Integer> buffer;
	private int capacity;
	private Lock lock;
	private Condition condition;

	public Producer(Queue<Integer> buffer, int capacity, Lock lock,
			Condition condition) {
		this.buffer = buffer;
		this.capacity = capacity;
		this.lock = lock;
		this.condition = condition;
	}

	@Override
	public void run() {
		Random rand = new Random();
		// Produce
		while (true) {
			try {
				lock.lock();
				if (buffer.size() == capacity) {
					condition.await();
				}
				int value = rand.nextInt(10);
				buffer.add(value);
				System.out.printf("%s has produced %d%n", Thread
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

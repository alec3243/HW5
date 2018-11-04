package question1.locks;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

	public static void main(String[] args) {
		Queue<Integer> buffer = new LinkedList<>();
		final int CAPACITY = 10;
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		Producer producer = new Producer(buffer, CAPACITY, lock, condition);
		Consumer consumer = new Consumer(buffer, lock, condition);
		Thread t1 = new Thread(producer);
		Thread t2 = new Thread(consumer);
		t1.start();
		t2.start();

	}

}

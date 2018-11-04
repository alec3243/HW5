package question1.actors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import akka.actor.AbstractActor;

public class Consumer extends AbstractActor {

	private BlockingQueue<Integer> buffer;
	private CountDownLatch latch;

	Consumer(BlockingQueue<Integer> buffer, CountDownLatch latch) {
		this.buffer = buffer;
		this.latch = latch;
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

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Consumer.class, r -> {
			
		}).build();
	}
}

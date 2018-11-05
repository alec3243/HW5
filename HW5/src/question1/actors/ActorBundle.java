package question1.actors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import akka.actor.ActorRef;

public class ActorBundle {
	private volatile BlockingQueue<Integer> buffer;
	private CountDownLatch latch;
	private volatile BlockingQueue<ActorRef> consumers;

	ActorBundle(BlockingQueue<Integer> buffer, CountDownLatch latch,
			BlockingQueue<ActorRef> consumers) {
		setBuffer(buffer);
		setLatch(latch);
		setConsumers(consumers);
	}

	public BlockingQueue<Integer> getBuffer() {
		return buffer;
	}

	public void setBuffer(BlockingQueue<Integer> buffer) {
		this.buffer = buffer;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public void setLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	public BlockingQueue<ActorRef> getConsumers() {
		return consumers;
	}

	public void setConsumers(BlockingQueue<ActorRef> consumers) {
		this.consumers = consumers;
	}

}

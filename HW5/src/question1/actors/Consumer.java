package question1.actors;

import java.util.concurrent.BlockingQueue;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class Consumer extends AbstractActor {

	public static Props props() {
		return Props.create(Consumer.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Receive createReceive() {
		return receiveBuilder().matchAny(buffer -> {
			// Consume the data
				System.out.printf("%s has consumed %d%n", Thread
						.currentThread().getName(),
						((BlockingQueue<Integer>) buffer).poll());
				Thread.sleep(1000);
			}).build();
	}
}

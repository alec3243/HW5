package question1.actors;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Integer> buffer = new LinkedBlockingQueue<>();
		BlockingQueue<ActorRef> consumers = new LinkedBlockingQueue<>();

		final int PRODUCERS = 2;
		CountDownLatch latch = new CountDownLatch(PRODUCERS);
		ActorBundle bundle = new ActorBundle(buffer, latch, consumers);
		long start = System.currentTimeMillis();

		ActorSystem system = ActorSystem.create("Producer-Consumer");
		for (int i = 0; i < 5; i++) {
			ActorRef consumer = system.actorOf(Consumer.props());
			consumers.add(consumer);
		}
		for (int i = 0; i < PRODUCERS; i++) {
			ActorRef producer = system.actorOf(Producer.props());
			producer.tell(bundle, ActorRef.noSender());
		}
		latch.await();

		System.out.printf("Runtime with 2 producers & 5 consumers: %d ms%n",
				System.currentTimeMillis() - start);
		// System.exit(0);
	}
}

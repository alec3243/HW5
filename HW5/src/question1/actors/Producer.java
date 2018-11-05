package question1.actors;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

import akka.actor.ActorRef;
import akka.actor.AbstractActor;
import akka.actor.Props;

public class Producer extends AbstractActor {
	private static final int MAX_ITEMS = 100;
	private static final int CAPACITY = 10;

	private int totalItems;

	public static Props props() {
		return Props.create(Producer.class);
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().matchAny(
				obj -> {
					BlockingQueue<Integer> buffer = ((ActorBundle) obj)
							.getBuffer();
					BlockingQueue<ActorRef> consumers = ((ActorBundle) obj)
							.getConsumers();
					Random rand = new Random();
					main: while (true) {
						if (totalItems >= MAX_ITEMS) {
							System.out.println(Thread.currentThread().getName()
									+ " has finished producing.");
							((ActorBundle) obj).getLatch().countDown();
							break main;
						}
						while (buffer.size() < CAPACITY) {
							int value = rand.nextInt(10);
							buffer.add(value);
							totalItems++;
							System.out.printf("%s has produced %d%n", Thread
									.currentThread().getName(), value);
							if (totalItems >= MAX_ITEMS) {
								System.out
										.println(Thread.currentThread()
												.getName()
												+ " has finished producing.");
								((ActorBundle) obj).getLatch().countDown();
								break main;
							}
						}
						if (!buffer.isEmpty()) {
							ActorRef consumer = consumers.poll();
							consumer.tell(buffer, self());
							consumers.add(consumer);
						}
					}
					((ActorBundle) obj).getLatch().countDown();
				}).build();
	}
}

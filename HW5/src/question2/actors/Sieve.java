package question2.actors;

import java.util.BitSet;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Sieve extends AbstractActor {
	private static final int n = 1000000;
	private static final long start = System.currentTimeMillis();
	private static final ActorSystem system = ActorSystem.create("Sieve");;

	public static Props props() {
		return Props.create(PrimeFilter.class);
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().matchAny(numbers -> {

			BitSet primes = new BitSet();
			for (int i = 2; i < n; i++) {
				primes.set(i, true);
			}
			ActorRef primeActor = system.actorOf(PrimeFilter.props());
			primeActor.tell(new Bundle(2, primes), self());
		}).build();
	}

	static class PrimeFilter extends AbstractActor {

		public static Props props() {
			return Props.create(PrimeFilter.class);
		}

		@Override
		public Receive createReceive() {
			return receiveBuilder().matchAny(
					bundle -> {
						int current = ((Bundle) bundle).current;
						System.out.println(current);
						BitSet numbers = ((Bundle) bundle).numbers;
						for (int i = current + 1; i < n; i++) {
							if (i % current == 0) {
								numbers.set(i, false);
							}
						}
						if (current == n) {
							System.out.printf("Sieve runtime: %d ms%n",
									System.currentTimeMillis() - start);
						} else {
							system.actorOf(PrimeFilter.props()).tell(
									new Bundle(current + 1, numbers), self());
						}
					}).build();
		}
	}

	static class Bundle {
		int current;
		BitSet numbers;

		Bundle(int current, BitSet numbers) {
			this.current = current;
			this.numbers = numbers;
		}
	}
}

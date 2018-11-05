package question2.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Main {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("Sieve");
		ActorRef sieve = system.actorOf(Sieve.props());
		sieve.tell(null, ActorRef.noSender());

	}

}

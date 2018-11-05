package question2.sequential;

import java.util.BitSet;

public class Main {

	public static void main(String[] args) {
		final int n = 1000000;
		long start = System.currentTimeMillis();
		BitSet primes = Sieve.getPrimes(n);
		System.out.printf("Sieve runtime: %d ms%n", System.currentTimeMillis()
				- start);
		// for (int i = 0; i < n; i++) {
		// if (primes.get(i)) {
		// System.out.println(i);
		// }
		// }

	}

}

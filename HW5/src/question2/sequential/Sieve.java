package question2.sequential;

import java.util.BitSet;

public class Sieve {

	public static BitSet getPrimes(int n) {
		BitSet primes = new BitSet();
		for (int i = 2; i < n; i++) {
			primes.set(i, true);
		}
		for (int i = 2; i < Math.sqrt(n); i++) {
			if (primes.get(i)) {
				int multiplier = 1;
				for (int j = i * i; j < n; j = i * i + multiplier * i, multiplier++) {
					primes.set(j, false);
				}
			}
		}
		return primes;
	}

}

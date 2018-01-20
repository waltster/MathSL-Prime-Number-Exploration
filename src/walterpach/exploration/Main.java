/** 
 * MIT License
 * Copyright (c) 2018 Walter Pach
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package walterpach.exploration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	/**
	 * This value represents the end of the set of numbers to check for primes. Note
	 * that the set starts at
	 */
	public static int SET_BEGINNING = 0;
	public static int SET_END = 0;
	public static boolean USE_DOTS = false;
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		for(String s : args) {
			if(s.toLowerCase() == "-no_dots") {
				USE_DOTS = false;
				System.out.println("Dots disabled");
			}
		}
		/**
		 * This list stores all of the prime numbers that the program uses.
		 */
		ArrayList<Integer> primes = new ArrayList<Integer>();

		/**
		 * This list stores the spaces between each prime number.
		 */
		ArrayList<Integer> spacing = new ArrayList<Integer>();

		System.out.println("Welcome to PrimeScanner... See github.com/waltster!");
		System.console().printf("Beginning number: ");
		String input;
		int number = 0;
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			input = bufferedReader.readLine();
			number = Integer.parseInt(input);
		} catch (NumberFormatException ex) {
			System.out.println("Not a number !");
		} catch (IOException e) {
			e.printStackTrace();
		}
		SET_BEGINNING = number;

		System.console().printf("End number: ");
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			input = bufferedReader.readLine();
			number = Integer.parseInt(input);
		} catch (NumberFormatException ex) {
			System.out.println("Not a number !");
		} catch (IOException e) {
			e.printStackTrace();
		}
		SET_END = number;

		/**
		 * This function scans the set of whole numbers from $SET_BEGINNING to $SET_END
		 * See function below for implementation.
		 */
		primes = findPrimes(SET_BEGINNING, SET_END);

		System.out.println("\n=== Calculating stats... ===");

		int lastPrime = 1;

		/**
		 * Calculate the space between the last prime number and the current, add this
		 * spacing to the array, and move to the next prime.
		 */
		for (Integer prime : primes) {
			spacing.add(prime - lastPrime);
			lastPrime = prime;
		}

		/**
		 * $mean - The mean of the spaces between primes $standardDeviation - The
		 * standard deviation from the mean of all the primes $sum - The sum of all the
		 * spaces (used to calculate standard deviation) $max - The maximum space
		 * between two primes $min - The minimum space between two primes (by default 0,
		 * but can be lower in case of error)
		 */
		float mean = 0, standardDeviation = 0, sum = 0;
		int max = 0, min = 0;

		/**
		 * For each of the data-items, add to the sum then update the $min and $max
		 * values as needed. Note: This allows the mean to be calculated using this sum.
		 */
		for (Integer space : spacing) {
			sum += space;
			if (space > max) {
				max = space;
			}

			if (space < min && space > -1) {
				min = space;
			}
		}

		/**
		 * Calculate the mean using the length of the list and the sum of the terms.
		 */
		mean = sum / spacing.size();

		/**
		 * Calculate standard deviation using the mean and the data.
		 */
		standardDeviation = calculateStandardDeviation(mean, spacing);

		/**
		 * Print out the results.
		 */
		System.out.println("=== Program by Walter Pach ===");
		System.out.println("Scanned " + (SET_END - SET_BEGINNING) + " numbers");
		System.out.println("Primes found: " + spacing.size() + ", Max-spacing: " + max + ", Min-spacing: " + min);
		System.out.println("Mean: " + mean + ", Standard Deviation: " + standardDeviation);

		while (true) {

		}
	}

	/**
	 * Find all the prime numbers in a set of data within the parameters.
	 * 
	 * @param SET_BEGINNING
	 *            The beginning of the set
	 * @param SET_END
	 *            The end of the set
	 * @return A list of the prime numbers
	 */
	public static ArrayList<Integer> findPrimes(int SET_BEGINNING, int SET_END) {
		/**
		 * This stores the number of factors that the number has.
		 */
		int factors = 0;
		ArrayList<Integer> primes = new ArrayList<Integer>();

		/**
		 * Iterate over each number from the beginning of the set to the end.
		 */
		for (float n = SET_BEGINNING; n < SET_END; n++) {
			if(USE_DOTS) {
				if ((int) n == (int) (SET_END * 0.1) || (int) n == (int) (SET_END * 0.2) || (int) n == (int) (SET_END * 0.3)
					|| (int) n == (int) (SET_END * 0.4) || (int) n == (int) (SET_END * 0.5)
					|| (int) n == (int) (SET_END * 0.6) || (int) n == (int) (SET_END * 0.7)
					|| (int) n == (int) (SET_END * 0.8) || (int) n == (int) (SET_END * 0.9)
					|| (int) n == (int) (SET_END)) {
					System.console().printf(".");
				}
			}

			/**
			 * Sort through the numbers from 2 to "n" minus one.
			 */
			for (float i = 1; i < n; i++) {
				/**
				 * If the remainder of n/i is a whole number and the divisor isn't equal to one
				 * then #i is a factor of #n, and #n is not a prime number.
				 */
				if ((n / i) % 1 == 0 && i != n && i != 1) {
					factors++;
					break;
				}
			}

			/**
			 * If there are no factors then the number is prime so it is added to the list
			 */
			if (factors == 0) {
				primes.add((int) n);
				factors = 0;
				continue;
			}

			factors = 0;
		}

		return primes;
	}

	public static float calculateStandardDeviation(float mean, ArrayList<Integer> spacing) {
		/**
		 * The sum is reset to be used for the summation of ∑((x - μ)^2) instead of the
		 * mean, now that it has already been calculated.
		 */
		float sum = 0;

		/**
		 * For each space subtract the value from the mean and square the result, then
		 * add it to the sum.
		 * 
		 * Equivalent: ∑((x - μ)^2)
		 */
		for (Integer space : spacing) {
			sum += Math.pow((space - mean), 2);
		}

		/**
		 * Divide the sum by the number of data items
		 * 
		 * Equivalent: (∑((x - μ)^2)) / n
		 */
		sum /= spacing.size();

		/**
		 * Take the square root of the sum by raising the sum to the power of (1/2)
		 * 
		 * Equivalent: √((∑((x - μ)^2)) / n)
		 */
		return (float) Math.pow((double) sum, (double) 0.5);
	}
}
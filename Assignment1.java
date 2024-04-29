package assignment1;
import java.util.*;
import java.math.*;
import java.io.*;

/**
 * Assignment 1
 * @author Sebastian Perez Fematt 
 * Panther ID: 6348080
 * Section: U01
 *
 */
public class Assignment1 {
	/**
	 * Main method
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		new Assignment1();
	}
	
	/**
	 * Assignment 1 Default constructor
	 */
	public Assignment1() {
		PrintWriter output = null;
		try {
            output = new PrintWriter(new FileWriter("output.csv"));
        } 
		catch (IOException ex) {
            System.exit(1);
        }
		
		output.println("Value,Time of DA,Prime? (According to DA),Time of RA 10 iterations,Prime (According to RA 10 iterations),Time of RA 500 iterations,Prime (According to RA 500 iterations)");
		
		long time;
		boolean isPrime;
		long startTime = System.nanoTime();
		for(BigInteger i = new BigInteger("10000000000000819"); System.nanoTime() - startTime < 3.6E12; i = i.add(ONE)) {
			output.print(i + ",");
			
			time = System.nanoTime();
			isPrime = isPrimeDeterministic(i);
			time = System.nanoTime() - time;
			output.print(time + ",");
			if(isPrime)
				output.print("YES,");
			else
				output.print("NO,");
			
			time = System.nanoTime();
			isPrime = isPrimeRandomized(i, 10);
			time = System.nanoTime() - time;
			output.print(time + ",");
			if(isPrime)
				output.print("YES,");
			else
				output.print("NO,");
			
			time = System.nanoTime();
			isPrime = isPrimeRandomized(i, 500);
			time = System.nanoTime() - time;
			output.print(time + ",");
			if(isPrime)
				output.print("YES,");
			else
				output.print("NO");
			
			output.println();
		}
		output.close();
	}
	
	BigInteger ZERO = BigInteger.ZERO;
	BigInteger ONE = BigInteger.ONE;
	BigInteger TWO = BigInteger.TWO;
	
	/**
	 * Tests if a number is prime using a deterministic method
	 * @param n number to be tested
	 * @return true if n is prime, false if not prime
	 */
	private boolean isPrimeDeterministic(BigInteger n) {
		if(n.compareTo(ONE) == 0)
			return false;
		if(n.compareTo(TWO) == 0)
			return true;
		
		BigInteger s = n.sqrt();
		for(BigInteger i = TWO; i.compareTo(s) <= 0; i = i.add(ONE)) {
			if(n.mod(i).compareTo(ZERO) == 0)
				return false;
		}
		return true;
	}
	
	/**
	 * Tests if a number is prime using a randomized method
	 * @param n number to be tested
	 * @param iterations number of iterations
	 * @return true if n is prime, false if not prime
	 */
	private boolean isPrimeRandomized(BigInteger n, int iterations) {
		if(n.compareTo(ONE) == 0)
			return false;
		if(n.compareTo(TWO) == 0)
			return true;
		
		for(int i = 0; i < iterations; i++) {
			BigInteger a = randomBigInteger(n.subtract(TWO)).add(TWO);
			if(a.modPow(n.subtract(ONE), n).compareTo(ONE) != 0)
				return false;
		}
		return true;
	}
	
	/**
	 * Generates a random BigInteger value
	 * @param value maximum value for random number
	 * @return random BigInteger value
	 */
	BigInteger randomBigInteger(BigInteger value) {
		int bitLength = value.bitLength();
		return (new BigInteger(bitLength, new Random()).mod(value));
	}
}

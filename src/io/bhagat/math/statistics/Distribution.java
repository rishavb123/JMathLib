package io.bhagat.math.statistics;

/**
 * A class for distributions
 * @author Bhagat
 */
public abstract class Distribution<E> {
	
	/**
	 * finds the probability that a number x is returned
	 * @param x the number
	 * @return the probability
	 */
	public abstract double probability(E x) throws InvalidInputException;
	
	/**
	 * creates the simulation represented by this distribution
	 * @return the simulation object
	 */
	public abstract Simulation<E> createSimulation();
	
	/**
	 * An exception for invalid input for the distribution
	 * @author Bhagat
	 */
	public static class InvalidInputException extends RuntimeException {

		private static final long serialVersionUID = 4357416092128140927L;
		
		/**
		 * creates a new invalid input exception
		 * @param input the invalid input object
		 */
		public InvalidInputException(Object input) {
			super("The input: " + input.toString() + " is invalid for this distribution");
		}
		
	}
	
}

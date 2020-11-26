package io.bhagat.math.statistics;

import java.util.ArrayList;

/**
 * @author Bhagat
 *
 */
public abstract class Simulation<E> {

	/**
	 * runs the simulation
	 * @return the result of the simulation
	 */
	public abstract E run();
	
	/**
	 * gets the last result of the simulation
	 * @return the last result
	 */
	public abstract E getLastResult();
	
	/**
	 * runs the simulation
	 * @param iterations the number of iterations to run the simulation
	 * @return an array list with the results
	 */
	public ArrayList<E> run(int iterations) {
		ArrayList<E> results = new ArrayList<E>();
		for(int i = 0; i < iterations; i++)
			results.add(run());
		return results;
	}
	
}

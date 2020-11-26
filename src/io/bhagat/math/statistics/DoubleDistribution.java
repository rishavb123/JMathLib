package io.bhagat.math.statistics;

import java.util.ArrayList;

/**
 * A class for distributions
 */
public abstract class DoubleDistribution extends Distribution<Double> {

	/**
	 * @return the mean
	 */
	public abstract double getMean();
	
	/**
	 * @return the standard deviation
	 */
	public abstract double getStandardDeviation();
		
	/**
	 * creates the simulation represented by this distribution
	 * @return the simulation object
	 */
	public abstract DoubleSimulation createSimulation();
	
	/**
	 * finds the z score of a certain number using the mean and standard deviation
	 * @param x the number
	 * @return the z score
	 */
	public double zScore(double x)
	{
		return (x - getMean()) / getStandardDeviation();
	}
	
	/**
	 * The same as a distribution simulation except it returns a QualititativeDataList rather than an array list from run(iterations)
	 * @author Bhagat
	 */
	public abstract class DoubleSimulation extends Simulation<Double> {
		
		/**
		 * runs the simulation
		 * @param iterations the number of iterations to run the simulation
		 * @return a quantitative data list
		 */
		public ArrayList<Double> run(int iterations) {
			QuantitativeDataList results = new QuantitativeDataList();
			for(int i = 0; i < iterations; i++)
				results.add(run());
			return results;
		}
		
	}
	
}
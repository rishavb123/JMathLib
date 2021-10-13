package io.bhagat.math.statistics;

import java.util.Random;

import io.bhagat.math.functions.Function;
import io.bhagat.math.settheory.Interval;
import io.bhagat.math.calculus.Calculus;

/**
 * An object that uses the equation for a normal distribute to calculate values 
 */
public class NormalDistribution extends DoubleDistribution {
	
	private double mean;
	private double standardDeviation;
	private Function<Double, Double> distributionFunction;
	
	/**
	 * Constructs a new normal distribution
	 * @param mean the mean / center
	 * @param standardDeviation the spread of the data
	 */
	public NormalDistribution(double mean, double standardDeviation) {
		
		this.mean = mean;
		this.standardDeviation = standardDeviation;
		
		distributionFunction = x -> Math.pow(Math.E, -Math.pow(x - mean, 2) / (2 * standardDeviation * standardDeviation)) / (standardDeviation * Math.sqrt(2 * Math.PI));
		
	}
	
	/**
	 * fits a normal distribution to a quantitative data list
	 * @param list the list
	 */
	public NormalDistribution(QuantitativeDataList list) {
		this(list.mean(), list.standardDeviation());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DoubleSimulation createSimulation() {
		return new DoubleSimulation() {

			private Double lastResult;
			
			@Override
			public Double run() {
				lastResult = mean + new Random().nextGaussian() * standardDeviation;
				return lastResult;
			}

			@Override
			public Double getLastResult() {
				return lastResult;
			}
			
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double probability(Double x) throws InvalidInputException {
		return distributionFunction.f(x);
	}

	/**
	 * finds the probability of the value to be in [lowerBound, upperBound] using calculus
	 * @param lowerBound the lower bound
	 * @param upperBound the upper bound
	 * @return the probability
	 */
	public double probability(double lowerBound, double upperBound) {
		return Calculus.integral(distributionFunction).f(new Double[] { lowerBound, upperBound });
	}
	
	/**
	 * finds the probability of the value to be in an interval using calculus
	 * @param interval the interval
	 * @return the probability
	 */
	public double probability(Interval<Double> interval) {
		return probability(interval.getLowerBound(), interval.getUpperBound());
	}
	
	/**
	 * finds the probability of the value to be in an interval using repeated simulations
	 * @param lowerBound the lower bound for the interval
	 * @param upperBound the upper bound for the interval
	 * @param iterations the number of iterations for the simulation
	 * @return the calculated probability
	 */
	public double experimentalProbability(double lowerBound, double upperBound, int iterations) {
		return experimentalProbability(new Interval(lowerBound, upperBound), iterations);
	}
	
	/**
	 * finds the probability of the value to be in an interval using repeated simulations
	 * @param interval the interval
	 * @param iterations the number of iterations for the simulation
	 * @return the calculated probability
	 */
	public double experimentalProbability(Interval<Double> interval, int iterations) {
		return ((QuantitativeDataList) createSimulation().run(iterations)).inInterval(interval);
	}

	/**
	 * @return the mean
	 */
	public double getMean() {
		return mean;
	}

	/**
	 * @return the standardDeviation
	 */
	public double getStandardDeviation() {
		return standardDeviation;
	}

	/**
	 * @return the distribution
	 */
	public Function<Double, Double> getDistribution() {
		return distributionFunction;
	}
	
}

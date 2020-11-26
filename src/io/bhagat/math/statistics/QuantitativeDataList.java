package io.bhagat.math.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import io.bhagat.math.Function;
import io.bhagat.math.settheory.Interval;
import io.bhagat.math.linearalgebra.Vector;

// TODO create CategoricalDataList implements DataList
// TODO create DataSet for JSON and CSV

/**
 * A class for qualitative data lists containing doubles
 * @author Bhagat
 */
public class QuantitativeDataList extends ArrayList<Double> { //TODO implements DataList 

	private static final long serialVersionUID = 1229037288513585530L;

	/**
	 * creates an empty list
	 */
	public QuantitativeDataList()
	{
		super();
	}
	
	/**
	 * initializes the list with certain values
	 * @param data the data to put into the list
	 */
	public QuantitativeDataList(double... data)
	{
		super();
		for(double d: data)
			add(d);
		sort();
	}
	
	/**
	 * initializes the list with certain values
	 * @param data the data to put into the list
	 */
	public QuantitativeDataList(Double... data) {
		super();
		for(double d: data)
			add(d);
		sort();
	}

	/**
	 * @see java.util.ArrayList#set(int, java.lang.Object)
	 */
	@Override
	public Double set(int index, Double element) {
		double d = super.set(index, element);
		sort();
		return d;
	}

	/**
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	@Override
	public boolean add(Double e) {
		boolean b = super.add(e);
		sort();
		return b;
	}

	/**
	 * @see java.util.ArrayList#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, Double element) {
		super.add(index, element);
		sort();
	}

	/**
	 * @see java.util.ArrayList#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends Double> c) {
		boolean b = super.addAll(c);
		sort();
		return b;
	}

	/**
	 * @see java.util.ArrayList#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int index, Collection<? extends Double> c) {
		boolean b = super.addAll(index, c);
		sort();
		return b;
	}
	
	/**
	 * sorts the list
	 */
	public void sort()
	{
		Collections.sort(this);
	}

	/**
	 * computes the sum of all the data
	 * @return the sum
	 */
	public double sum()
	{
		double sum = 0;
		for (Double aDouble : this) sum += aDouble;
		return sum;
	}
	
	/**
	 * computes the mean of all the data
	 * @return the mean
	 */
	public double mean()
	{
		return sum() / size();
	}
	
	/**
	 * computes the average of all the data
	 * @return the average
	 */
	public double average()
	{
		return mean();
	}
	
	/**
	 * finds the median of the data set
	 * @return the median
	 */
	public double median()
	{
		if(size() % 2 == 0)
			return (get(size() / 2 - 1) + get(size() / 2)) / 2;
		return get(size() / 2);
	}
	
	/**
	 * finds the first quartile
	 * @return the first quartile
	 */
	public double quartile1()
	{
		QuantitativeDataList qdl = new QuantitativeDataList(new double[] {});
		for(int i = 0; i < ((size() % 2 == 0)? size() / 2 - 1 : size() / 2); i++)
			qdl.add(get(i));
		return qdl.median();
	}
	
	/**
	 * finds the third quartile
	 * @return the third quartile
	 */
	public double quartile3()
	{
		QuantitativeDataList qdl = new QuantitativeDataList(new double[] {});
		for(int i = ((size() % 2 == 0)? size() / 2 : size() / 2 + 1); i < size(); i++)
			qdl.add(get(i));
		return qdl.median();
	}
	
	/**
	 * uses other methods to create a five point summary
	 * @return the five data points
	 */
	public double[] fivePointSummary()
	{
		return new double[] {get(0), quartile1(), median(), quartile3(), get(size() - 1)}; 
	}
	
	/**
	 * computes the range of the data
	 * @return the range
	 */
	public double range()
	{
		return get(size() - 1) - get(0);
	}
	
	/**
	 * computes the interquartile range
	 * @return the interquartile range
	 */
	public double iqr()
	{
		return interQuartileRange();
	}
	
	/**
	 * computes the interquartile range
	 * @return the interquartile range
	 */
	public double interQuartileRange()
	{
		return quartile3() - quartile1();
	}
	
	/**
	 * finds all the outliers using the 1.5 x iqr rule
	 * @return an array list containing the outliers
	 */
	public ArrayList<Double> outliers() 
	{
		ArrayList<Double> outliers = new ArrayList<>();
		for(int i = 0; i < size(); i++)
			if(isOutlier(get(i)))
				outliers.add(get(i));
		return outliers;				
	}
	
	/**
	 * decides if the double is an outlier using the 1.5 x iqr rule
	 * @param d the double
	 * @return whether or not it is an outlier
	 */
	public boolean isOutlier(double d)
	{
		return quartile1() - d >= 1.5 * iqr() || d - quartile3() >= 1.5 * iqr();
	}
	
	/**
	 * maps a function onto each value in the list
	 * @param function the function
	 * @return a reference to this list
	 */
	public QuantitativeDataList map(Function<Double, Double> function)
	{
		for(int i = 0; i < size(); i++)
			set(i, function.f(get(i)));
		return this;
	}
	
	/**
	 * adds a number to each number in the list
	 * @param x the number to add
	 * @return a reference to this list
	 */
	public QuantitativeDataList addNumber(double x)
	{
		for(int i = 0; i < size(); i++)
			set(i, get(i) + x);
		return this;
	}
	
	/**
	 * subtracts a number to each number in the list
	 * @param x the number to subtract
	 * @return a reference to this list
	 */
	public QuantitativeDataList subtract(double x)
	{
		for(int i = 0; i < size(); i++)
			set(i, get(i) - x);
		return this;
	}
	
	/**
	 * multiplies a number to each number in the list
	 * @param x the number to multiply
	 * @return a reference to this list
	 */
	public QuantitativeDataList multiply(double x)
	{
		for(int i = 0; i < size(); i++)
			set(i, get(i) * x);
		return this;
	}
	
	/**
	 * divides a number to each number in the list
	 * @param x the number to divide
	 * @return a reference to this list
	 */
	public QuantitativeDataList divide(double x)
	{
		for(int i = 0; i < size(); i++)
			set(i, get(i) / x);
		return this;
	}
	
	/**
	 * finds the z score of a parameter x
	 * @param x the parameter
	 * @return the z score
	 */
	public double z(double x)
	{
		return (x - mean()) / standardDeviation();
	}
	
	/**
	 * gives the proportion of values in the list in an interval
	 * @param interval the interval
	 * @return the proportion
	 */
	public double inInterval(Interval<Double> interval)
	{
		int count = 0;
		for(double d: this)
			if(interval.contains(d))
				count++;
		return (double) count / size();
	}
	
	/**
	 * gives the proportion of values in the list in an interval
	 * @param lowerBound the lower bound of the interval
	 * @param upperBound the upper bound of the interval
	 * @return the proportion
	 */
	public double inInterval(double lowerBound, double upperBound)
	{
		return inInterval(new Interval(lowerBound, upperBound));
	}
	
	/**
	 * removes all the outliers
	 * @return a reference to this list after cleaning it
	 */
	public QuantitativeDataList clean()
	{
		for(int i = 0; i < size(); i++)
			if(isOutlier(get(i)))
			{
				remove(i);
				i--;
			}
		return this;
	}
	
	/**
	 * creates another QualitativeDataList in a separate memory location
	 */
	public QuantitativeDataList clone()
	{
		return new QuantitativeDataList(toArray(new Double[0]));
	}
	
	/**
	 * creates a Vector from the data in this list
	 * @return the vector
	 */
	public Vector toVector()
	{
		double[] arr = new double[size()];
		for (int i = 0; i < arr.length; i++)
			arr[i] = get(i);
		Vector v = new Vector(arr);
		return v;
	}
	
	/**
	 * computes the variance of all the data, which is assuming that the sample is the entire population
	 * @return the variance
	 */
	public double variance()
	{
		Vector v = toVector().translate(-mean());
		return v.squaredMagnitude() / size();
	}
	
	/**
	 * computes the sample variance of the data, which is assuming that the sample is a part of the population
	 * @return the sample variance
	 */
	public double sampleVariance()
	{
		return variance() * size() / (size() - 1);
	}
	
	/**
	 * computes the standard deviation of all the data, which is assuming that the sample is the entire population
	 * @return the standard deviation
	 */
	public double standardDeviation()
	{
		return Math.sqrt(variance());
	}
	
	/**
	 * computes the sample standard deviation of the data, which is assuming that the sample is a part of the population
	 * @return the sample standard deviation
	 */
	public double sampleStandardDeviation()
	{
		return Math.sqrt(sampleVariance());
	}
	
}

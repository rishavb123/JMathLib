package io.bhagat.math.calculus;

import io.bhagat.math.functions.Function;
import io.bhagat.math.settheory.Interval;
import io.bhagat.math.linearalgebra.Vector;

import java.util.ArrayList;

// TODO: add more calculus

/**
 * A class containing many of the calculus operations
 */
public class Calculus {

    private static final int DEFAULT_NUMBER_OF_ITERATIONS = 1000000;
    private static final double DEFAULT_DX = 0.000000000001;

    /**
     * Takes the derivative of a double function using a non default dx
     * @param function the function
     * @param limitNumber the dx
     * @return a function for the derivative of the input function
     */
    public static Function<Double, Double> derivative(Function<Double, Double> function, double limitNumber)
    {
        return x -> (function.f(x + limitNumber) - function.f(x)) / limitNumber;
    }

    /**
     * Takes the derivative of a float function using a non default dx
     * @param function the function
     * @param limitNumber the dx
     * @return a function for the derivative of the input function
     */
    public static Function<Float, Float> derivative(Function<Float, Float> function, float limitNumber)
    {
        return x -> (function.f(x + limitNumber) - function.f(x)) / limitNumber;
    }

    /**
     * Takes the derivative of a function using a default dx
     * @param function the function
     * @return a function for the derivative of the input function
     */
    public static Function<Double, Double> derivative(Function<Double, Double> function)
    {
        return derivative(function, DEFAULT_DX);
    }

    // FIXME: fix higher order derivatives
    /**
     * takes multiple derivatives on a double function
     * @param function the function
     * @param order the order of the derivative you want to take
     * @return the function after multiple derivatives
     */
    public static Function<Double, Double> higherOrderDerivative(Function<Double, Double> function, int order)
    {
        System.out.println(function.f(4.0));
        for(int i = 0; i < order; i++)
        {
            function = derivative(function);
            System.out.println(function.f(4.0));
        }
        return function;
    }

    // TODO: change to simpson rule
    // TODO: add error function for simpson (or trapezoid)
    /**
     * takes the integral on a function
     * @param function the function to take the integral of
     * @param n the number of iterations
     * @return a function for the definite integral
     */
    public static Function<Double[], Double> integral(Function<Double, Double> function, int n)
    {
        return x -> (x[1] - x[0])*(function.f(x[0]) + function.f(x[1]) + 2*(new Vector(new Vector(range(x[0] + (x[1] - x[0]) / n , x[1], (x[1] - x[0]) / n)).map(function)).sum())) / (2*n);
    }

    private static double[] range(double start, double stop, double step)
    {
        ArrayList<Double> list = new ArrayList<>();
        for(double i = start; i < stop; i += step)
            list.add(i);
        double[] arr = new double[list.size()];
        for(int i = 0; i < list.size(); i++)
            arr[i] = list.get(i);
        return arr;
    }

    /**
     * takes the integral on a function
     * @param function the function to take the integral of
     * @param n the number of iterations
     * @return a function for the definite integral
     */
    public static Function<Interval<Double>, Double> integralWithInterval(Function<Double, Double> function, int n)
    {
        Function<Double[], Double> integralFunction = integral(function, n);

        return x -> integralFunction.f(new Double[] {x.getLowerBound(), x.getUpperBound()});
    }

    /**
     * finds the average value of a function in an interval using the mean value theorem for integrals
     * @param function the function
     * @param interval the interval
     * @return the mean value
     */
    public static double averageValue(Function<Double, Double> function, Interval<Double> interval)
    {
        return integralWithInterval(function).f(interval) / (interval.getUpperBound() - interval.getLowerBound());
    }

    /**
     * finds the average value of a function in an interval using the mean value theorem for integrals
     * @param function the function
     * @param lowerBound the lower bound of the interval
     * @param upperBound the upper bound of the interval
     * @return the mean value
     */
    public static double averageValue(Function<Double, Double> function, double lowerBound, double upperBound)
    {
        return averageValue(function, new Interval<>(lowerBound, upperBound));
    }

    /**
     * takes the integral on a function
     * @param function the function to integrate
     * @return a function for the definite integral
     */
    public static Function<Double[], Double> integral(Function<Double, Double> function)
    {
        return integral(function, DEFAULT_NUMBER_OF_ITERATIONS);
    }

    /**
     * takes the integral on a function
     * @param function the function to integrate
     * @return a function for the definite integral
     */
    public static Function<Interval<Double>, Double> integralWithInterval(Function<Double, Double> function)
    {
        return integralWithInterval(function, DEFAULT_NUMBER_OF_ITERATIONS);
    }

}
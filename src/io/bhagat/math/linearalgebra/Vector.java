package io.bhagat.math.linearalgebra;

import io.bhagat.math.exceptions.InvalidShapeException;

import java.util.Arrays;

public class Vector extends Tensor<Double> {

    /**
     * Creates a vector with a definite length
     * @param length the length
     */
    public Vector(int length) {
        super(length);
    }

    /**
     * Creates a vector with predefined data
     * @param nums the predefined data
     */
    public Vector(double... nums) {
        super(toBackingArray(nums), new int[] { nums.length });
    }

    /**
     * Creates a vector from a tensor
     * @param tensor the tensor
     */
    public Vector(Tensor<Double> tensor) {
        super(tensor.getBackingArray(), new int[] { tensor.getLength() });
    }

    /**
     * Gets the data in the vector in the array format
     * @return the data array
     */
    public double[] getDataArray() {
        double[] data = new double[getLength()];
        for(int i = 0; i < getLength(); i++)
            data[i] = get(i);
        return data;
    }

    /**
     * Converts the vector into a row matrix
     * @return the row matrix
     */
    public Matrix rowMatrix() {
        return new Matrix(new Tensor<>(getBackingArray(), new int[] { 1, getLength() }));
    }

    /**
     * Converts the vector into a column matrix
     * @return the column matrix
     */
    public Matrix columnMatrix() {
        return new Matrix(new Tensor<>(getBackingArray(), new int[] { getLength(), 1 }));
    }

    /**
     * Normalizes the vector from one range to another
     * @param origMin the original minimum of the range
     * @param origMax the original maximum of the range
     * @param min the new minimum of the range
     * @param max the new maximum of the range
     * @return a reference to this vector
     */
    public Vector normalize(double origMin, double origMax, double min, double max) {
        Object[] backingArray = getBackingArray();
        for(int i = 0; i < getLength(); i++) {
            backingArray[i] = ((double) backingArray[i] - origMin) * (max - min) / (origMax - origMin) + min;
        }
        return this;
    }

    @Override
    public Double get(int... pos) {
        Double d = super.get(pos);
        return d == null? 0: d;
    }

    @Override
    public Vector clone() {
        return new Vector(super.clone());
    }

    private static Object[] toBackingArray(double[] nums) {
        Object[] arr = new Object[nums.length];
        for(int i = 0; i < nums.length; i++)
            arr[i] = nums[i];
        return arr;
    }

    /**
     * Calculates the dot product of the two input vectors
     * @param a the first input vector
     * @param b the second input vector
     * @return the scalar output
     */
    public static double dot(Vector a, Vector b) {
        return inner(a, b);
    }

    /**
     * Calculates the inner product of the two input vectors
     * @param a the first input vector
     * @param b the second input vector
     * @return the scalar output
     */
    public static double inner(Vector a, Vector b) {
        assert Tensor.equalShape(a, b);
        int sum = 0;
        for(int i = 0; i < a.getLength(); i++)
            sum += a.get(i) * b.get(i);
        return sum;
    }

    /**
     * Calculates the output product of the two input vectors
     * @param a the first input vector
     * @param b the second input vector
     * @return the matrix output
     */
    public static Matrix outer(Vector a, Vector b) {
        return Matrix.multiply(a.columnMatrix(), b.rowMatrix());
    }

    /**
     * Adds two vectors together
     * @param a the first vector
     * @param b the second vector
     * @return the resultant vector
     */
    public static Vector add(Vector a, Vector b) {
        if(!Arrays.equals(a.getDimensions(), b.getDimensions()))
            throw new InvalidShapeException(a.toString(), b.toString());
        Vector c = new Vector(a.getLength());
        for(int i = 0; i < a.getLength(); i++)
            c.set(a.get(i) + b.get(i), i);
        return c;
    }

    /**
     * Subtracts two vectors together
     * @param a the first vector
     * @param b the second vector
     * @return the resultant vector
     */
    public static Vector subtract(Vector a, Vector b) {
        if(!Arrays.equals(a.getDimensions(), b.getDimensions()))
            throw new InvalidShapeException(a.toString(), b.toString());
        Vector c = new Vector(a.getLength());
        for(int i = 0; i < a.getLength(); i++)
            c.set(a.get(i) - b.get(i), i);
        return c;
    }

}

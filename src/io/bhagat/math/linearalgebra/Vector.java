package io.bhagat.math.linearalgebra;

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

}

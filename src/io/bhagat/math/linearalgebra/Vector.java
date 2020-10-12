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

}

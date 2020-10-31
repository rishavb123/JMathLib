package io.bhagat.math.linearalgebra;

import io.bhagat.math.Constants;
import io.bhagat.math.Function;
import io.bhagat.math.exceptions.InvalidShapeException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Vector extends Tensor<Double> implements Comparable<Vector>{

    private VectorEntry[] vectorEntries;

    /**
     * Creates a vector with a definite length
     * @param length the length
     */
    public Vector(int length) {
        super(length);
        Object[] backingArray = getBackingArray();
        for(int i = 0; i < length; i++) {
            backingArray[i] = 0.0;
        }
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
     * Converts the vector into a matrix column
     * @return the matrix with one column
     */
    public Matrix toMatrix() {
        return columnMatrix();
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

    /**
     * Scales the vector by a scalar
     * @param c the scalar constant
     * @return a reference to this vector
     */
    public Vector scale(double c) {
        Object[] backingArray = getBackingArray();
        for(int i = 0; i < getLength(); i++) {
            backingArray[i] = (double) backingArray[i] * c;
        }
        return this;
    }

    /**
     * Translates the vector by a scalar
     * @param c the scalar constant
     * @return a reference to this vector
     */
    public Vector translate(double c) {
        Object[] backingArray = getBackingArray();
        for(int i = 0; i < getLength(); i++) {
            backingArray[i] = (double) backingArray[i] + c;
        }
        return this;
    }

    /**
     * Adds a vector to this vector
     * @param v the vector to add
     * @return a reference to this vector
     */
    public Vector add(Vector v) {
        assertShape(this, v);
        for(int i = 0; i < getLength(); i++)
            set(v.get(i) + get(i), i);
        return this;
    }

    /**
     * Subtracts a vector from this vector
     * @param v the vector to subtract
     * @return a reference to this vector
     */
    public Vector subtract(Vector v) {
        return add(v.clone().scale(-1));
    }

    /**
     * Multiplies a vector to this vector elementwise
     * @param v the vector to multiply
     * @return a reference to this vector
     */
    public Vector hadamard(Vector v) {
        assertShape(this, v);
        for(int i = 0; i < getLength(); i++)
            set(v.get(i) * get(i), i);
        return this;
    }

    /**
     * Fills the Vector with random values from min to max
     * @param min the minimum random number
     * @param max the maximum random number
     * @return a reference to this vector
     */
    public Vector randomize(double min, double max)
    {
        Object[] backingArray = getBackingArray();
        for(int i = 0; i < getLength(); i++)
            backingArray[i] = Math.random()*(max - min) + min;
        return this;
    }

    /**
     * Generates a random vector with values from 0 to 1
     * @return a reference to this vector
     */
    public Vector randomize() {
        return randomize(0, 1);
    }

    /**
     * Calculates the magnitude of the vector
     * @return the magnitude of the vector
     */
    public double magnitude() {
        return Math.sqrt(squaredMagnitude());
    }

    /**
     * Calculates the squared magnitude of the vector
     * @return the squared magnitude of the vector
     */
    public double squaredMagnitude() {
        double sum = 0;
        for(Double x: this)
            sum += x * x;
        return sum;
    }

    /**
     * Calculates the sum of the elements in the vector
     * @return the sum of the elements in the vector
     */
    public double sum() {
        double sum = 0;
        for(Double x: this)
            sum += x;
        return sum;
    }

    /**
     * Gets a vector entry object that is linked to this vector
     * @param i the index
     * @return the vector entry object
     */
    public VectorEntry getVectorEntry(int i) {
        if(vectorEntries == null)
            vectorEntries = new VectorEntry[getLength()];
        if(vectorEntries[i] == null)
            vectorEntries[i] = new VectorEntry(i);
        return vectorEntries[i];
    }

    /**
     * Gets an array of vector entry objects, each corresponding to the index it is at in the array
     * @return the array of entries
     */
    public VectorEntry[] getVectorEntries() {
        VectorEntry[] entries = new VectorEntry[getLength()];
        for(int i = 0; i < getLength(); i++) entries[i] = getVectorEntry(i);
        return entries;
    }

    /**
     * Maps a function onto each element in the vector
     * @param function a function that receives a vector entry
     * @return a reference to this vector
     */
    public Vector mapFromEntries(Function<VectorEntry, Double> function) {
        Object[] backingArray = getBackingArray();
        for(int i = 0; i < getLength(); i++)
            backingArray[i] = function.f(getVectorEntry(i));
        return this;
    }

    @Override
    public int compareTo(@NotNull Vector v) {
        return Double.compare(magnitude(), v.magnitude());
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

    private static void assertShape(Vector a, Vector b) {
        if(!Tensor.equalShape(a, b))
            throw new InvalidShapeException(a.toString(), b.toString());
    }

    /**
     * Generates a simple unit vector
     * @param index this value will be 1
     * @param size the size of the unit vector
     * @return the unit vector
     */
    public static Vector generateUnitVector(int index, int size) {
        Vector v = new Vector(size);
        v.set(1.0, index);
        return v;
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
     * Computes the cross product of n - 1 vectors of n dimension each
     * @param vs the array of vectors
     * @return the vector output
     */
    public static Vector cross(Vector... vs) {
        for(Vector v: vs) {
            if(v.getLength() - 1 != vs.length)
                throw new InvalidShapeException(v.toString());
        }
        return new CrossProductMatrix(vs).determinant();
    }

    /**
     * Calculates the inner product of the two input vectors
     * @param a the first input vector
     * @param b the second input vector
     * @return the scalar output
     */
    public static double inner(Vector a, Vector b) {
        assertShape(a, b);
        double sum = 0;
        for(int i = 0; i < a.getLength(); i++) {
            sum += a.get(i) * b.get(i);
        }
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
        assertShape(a, b);
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
        assertShape(a, b);
        Vector c = new Vector(a.getLength());
        for(int i = 0; i < a.getLength(); i++)
            c.set(a.get(i) - b.get(i), i);
        return c;
    }

    /**
     * Adds a scalar to a vector (element-wise)
     * @param a the vector
     * @param c the scalar
     * @return the resultant vector
     */
    public static Vector add(Vector a, double c) {
        return a.clone().translate(c);
    }

    /**
     * Subtracts a scalar from a vector (element-wise)
     * @param a the vector
     * @param c the scalar
     * @return the resultant vector
     */
    public static Vector subtract(Vector a, double c) {
        return a.clone().translate(-c);
    }

    /**
     * Multiplies a scalar with a vector
     * @param a the vector
     * @param c the scalar
     * @return the resultant vector
     */
    public static Vector multiply(Vector a, double c) {
        return a.clone().scale(c);
    }

    /**
     * Divides a scalar from a vector (element-wise)
     * @param a the vector
     * @param c the scalar
     * @return the resultant vector
     */
    public static Vector divide(Vector a, double c) {
        return a.clone().scale(1/c);
    }

    /**
     * Checks if two vectors are orthogonal
     * @param a the first vector
     * @param b the second vector
     * @return whether it is orthogonal or not
     */
    public static boolean orthogonal(Vector a, Vector b) {
        return Vector.dot(a, b) < Constants.EPSILON;
    }

    /**
     * Orthogonalize the set of input vectors
     * @param x the set of input vectors
     * @return the orthogonalized vectors
     */
    public static Vector[] orthogonalize(Vector[] x) {
        Vector[] v = new Vector[x.length];
        for(int y = 0; y < v.length; y++) {
            final int k = y;
            Vector sum = new Vector(x[k].getLength());
            for (int i = 0; i < k; i++) {
                sum.add(Vector.multiply(v[i], Vector.dot(x[k], v[i]) / v[i].squaredMagnitude()));
            }
            v[k] = Vector.subtract(x[k], sum);
        }
        return v;
    }



    /**
     * A class to hold the entries of a vector
     */
    public class VectorEntry {

        private int index;

        /**
         * Constructs a vector index object
         * @param index the index in the vector
         */
        public VectorEntry(int index) {
            this.index = index;
        }

        /**
         * Gets the value
         * @return the value
         */
        public double getVal() {
            return get(index);
        }

        /**
         * Sets the value in the vector
         * @param val the value to set it to
         */
        public void setVal(double val) {
            set(val, index);
        }

        /**
         * Gets the index
         * @return the index
         */
        public int getIndex() {
            return index;
        }

        /**
         * Gets the parent vector
         * @return the parent vector
         */
        public Vector getParent() {
            return Vector.this;
        }

        /**
         * String representation of the object
         * @return the string representation
         */
        public String toString() {
            return getParent() + "[" + index + "] = " + getVal();
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof VectorEntry))
                return false;
            VectorEntry o = (VectorEntry) obj;
            return index == o.getIndex() && getParent() == o.getParent();
        }
    }

    private static class CrossProductMatrix {

        private Matrix internalMatrix;
        private Vector[] topRow;

        private CrossProductMatrix(Vector[] vs)
        {
            topRow = new Vector[vs[0].getLength()];
            for(int i = 0; i < topRow.length; i++)
                topRow[i] = Vector.generateUnitVector(i, topRow.length);

            internalMatrix = new Matrix(vs);
        }

        private Vector determinant()
        {
            Vector sum = new Vector(topRow[0].getLength());

            for(int i = 0; i < topRow.length; i++)
            {
                sum.add(topRow[i].clone().scale(Math.pow(-1, i) * internalMatrix.clone().removeColumn(i).determinant()));
            }

            return sum;
        }

    }

}

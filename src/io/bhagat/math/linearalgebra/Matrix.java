package io.bhagat.math.linearalgebra;

import io.bhagat.math.exceptions.InvalidShapeException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Matrix extends Tensor<Double> implements Comparable<Matrix> {

    /**
     * Creates a matrix with a defined size
     * @param rows the number of rows in the matrix
     * @param cols the number of cols in the matrix
     */
    public Matrix(int rows, int cols) {
        super(rows, cols);
    }

    /**
     * Creates a matrix with specified data
     * @param data the data
     */
    public Matrix(double[][] data) {
        super(convertToWrapperClassArray(data));
    }

    /**
     * Creates a matrix with specified data
     * @param data the data
     */
    public Matrix(Double[][] data) {
        super(data);
    }

    /**
     * Creates a matrix from a tensor
     * @param tensor the tensor
     */
    public Matrix(Tensor<Double> tensor) {
        super(tensor.getBackingArray(), tensor.getDimensions());
    }

    /**
     * Creates a matrix from an array of vectors
     * @param vs the array of vectors
     */
    public Matrix(Vector... vs) {
        this(vs.length, vs[0].getLength());
        for(Vector v: vs) {
            if (v.getLength() != vs[0].getLength()) {
                throw new InvalidShapeException(vs[0].toString(), v.toString());
            }
        }
        Object[] backingArray = getBackingArray();
        for(int i = 0; i < vs.length; i++) {
            System.arraycopy(vs[i].getBackingArray(), 0, backingArray, i * getCols(), getCols());
        }
    }

    /**
     * Gets a specific row
     * @param r the row index
     * @return the row vector
     */
    public Vector getRowVector(int r) {
        return new Vector(getSubTensor(r));
    }

    /**
     * Sets a row to a specific vector
     * @param r the row index
     * @param v the vector
     */
    public void setRow(int r, Vector v) {
        if (v.getLength() != getCols()) {
            throw new InvalidShapeException(v.toString());
        }
        System.arraycopy(v.getBackingArray(), 0, getBackingArray(), r * getCols(), getCols());
    }

    /**
     * Gets a specific column
     * @param c the column index
     * @return the column vector
     */
    public Vector getColVector(int c) {
        int[] dim = getDimensions();
        Vector vec = new Vector(dim[0]);
        for(int i = 0; i < dim[0]; i++)
            vec.set(get(i, c), i);
        return vec;
    }

    /**
     * Sets a column to a specific vector
     * @param c the column index
     * @param v the vector
     */
    public void setCol(int c, Vector v) {
        if (v.getLength() != getRows()) {
            throw new InvalidShapeException(v.toString());
        }
        for (int i = 0; i < v.getLength(); i++)
            set(v.get(i), i, c);
    }

    /**
     * Gets the rows of the matrix
     * @return the array of all the row vectors
     */
    public Vector[] getRowVectors() {
        int[] dim = getDimensions();
        Vector[] vectors = new Vector[dim[0]];
        for(int i = 0; i < dim[0]; i++)
            vectors[i] = getRowVector(i);
        return vectors;
    }

    /**
     * Gets the columns of the matrix
     * @return the array of all the column vectors
     */
    public Vector[] getColVectors() {
        int[] dim = getDimensions();
        Vector[] vectors = new Vector[dim[1]];
        for(int i = 0; i < dim[1]; i++)
            vectors[i] = getColVector(i);
        return vectors;
    }

    /**
     * Gets the number of rows in the matrix
     * @return the number of rows
     */
    public int getRows() {
        return getDimensions()[0];
    }

    /**
     * Gets the number of columns in the matrix
     * @return the number of columns
     */
    public int getCols() {
        return getDimensions()[1];
    }

    /**
     * Takes the transpose of the matrix
     * @return the transpose of the matrix
     */
    public Matrix transpose() {
        int[] dim = getDimensions();
        double[][] data = new double[dim[1]][dim[0]];
        for(int i = 0; i < dim[0]; i ++)
            for(int j = 0; j < dim[1]; j++)
                data[j][i] = get(i, j);
        return new Matrix(data);
    }

    /**
     * Removes the row specified by the index and returns the resultant matrix
     * @param index the index
     * @return the new matrix with the removed row
     */
    public Matrix removeRow(int index) {
        if (index < 0 || index >= getRows()) {
            throw new IndexOutOfBoundsException(index + " is out of bounds for matrix of dimensions " + getRows()
                    + ", " + getCols());
        }
        Vector[] newRows = new Vector[getRows() - 1];
        Vector[] rows = getRowVectors();
        System.arraycopy(rows, 0, newRows, 0, index);
        System.arraycopy(rows, index + 1, newRows, index, getRows() - index - 1);
        return new Matrix(newRows);
    }

    /**
     * Removes the column specified by the index and returns the resultant matrix
     * @param index the index
     * @return the new matrix with removed column
     */
    public Matrix removeColumn(int index) {
        return transpose().removeRow(index).transpose();
    }

    /**
     * Checks if the matrix is a square matrix
     * @return whether the matrix is a square matrix
     */
    public boolean isSquare() {
        return getRows() == getCols();
    }

    public double determinant()
    {
        if(!isSquare())
            throw new InvalidShapeException(toString());

        if(getRows() == 1)
            return get(0, 0);

        double sum = 0;

        for(int i = 0; i < getRows(); i++)
        {
            sum += Math.pow(-1, i) * get(0, i) * removeRow(0).removeColumn(i).determinant();
        }

        return sum;

    }

    @Override
    public int compareTo(@NotNull Matrix o) {
        return Double.compare(determinant(), o.determinant());
    }

    /**
     * Normalizes the matrix from one range to another
     * @param origMin the original minimum of the range
     * @param origMax the original maximum of the range
     * @param min the new minimum of the range
     * @param max the new maximum of the range
     * @return a reference to this matrix
     */
    public Matrix normalize(double origMin, double origMax, double min, double max) {
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
    public Matrix clone() {
        return new Matrix(super.clone());
    }

    /**
     * Gets the data in the vector in the array format
     * @return the data array
     */
    public double[][] getDataArray() {
        int[] dim = getDimensions();
        double[][] data = new double[dim[0]][dim[1]];
        for(int i = 0; i < dim[0]; i ++)
            for(int j = 0; j < dim[1]; j++)
                data[i][j] = get(i, j);
        return data;
    }

    private static Double[][] convertToWrapperClassArray(double[][] data) {
        Double[][] dataArray = new Double[data.length][data[0].length];
        for(int i = 0; i < data.length; i++)
            for(int j = 0; j < data[i].length; j++)
                dataArray[i][j] = data[i][j];
        return dataArray;
    }

    /**
     * Multiplies two matricies
     * @param a the first matrix
     * @param b the second matrix
     * @return the output matrix
     */
    public static Matrix multiply(Matrix a, Matrix b) {
        if(a.getDimensions()[1] == b.getDimensions()[0])
            throw new InvalidShapeException(a.toString(), b.toString());

        Matrix m = new Matrix(a.getRows(), b.getCols());

        Vector[] aRows = a.getRowVectors();
        Vector[] bCols = b.getColVectors();

        for(int i = 0; i < m.getRows(); i++)
            for(int j = 0; j < m.getCols(); j++)
                m.set(Vector.dot(aRows[i], bCols[j]), i, j);

        return m;
    }

    /**
     * Multiplies a matrix with a vector
     * @param a the matrix
     * @param b the vector
     * @return the output matrix
     */
    public static Matrix multiply(Matrix a, Vector b) {
        return multiply(a, b.columnMatrix());
    }

    public static Matrix hadamardProduct(Matrix a, Matrix b) {
        assertShape(a, b);
        Matrix c = new Matrix(a.getRows(), a.getCols());
        for (int i = 0; i < c.getRows(); i++) {
            for (int j = 0; j < c.getCols(); j++) {
                c.set(a.get(i, j) * b.get(i, j), i, j);
            }
        }
        return c;
    }

    private static void assertShape(Matrix a, Matrix b) {
        if(!Tensor.equalShape(a, b))
            throw new InvalidShapeException(a.toString(), b.toString());
    }

    /**
     * Adds two matricies
     * @param a the first matrix
     * @param b the second matrix
     * @return the resulatant matrix
     */
    public static Matrix add(Matrix a, Matrix b) {
        if(!Arrays.equals(a.getDimensions(), b.getDimensions()))
            throw new InvalidShapeException(a.toString(), b.toString());
        Matrix c = new Matrix(a.getDimensions()[0], a.getDimensions()[1]);

        Object[] aArr = a.getBackingArray();
        Object[] bArr = b.getBackingArray();
        Object[] cArr = c.getBackingArray();
        for(int i = 0; i < c.getLength(); i++)
            cArr[i] = (double) aArr[i] + (double) bArr[i];

        return c;
    }

    /**
     * Subtracts two matricies
     * @param a the first matrix
     * @param b the second matrix
     * @return the resulatant matrix
     */
    public static Matrix subtract(Matrix a, Matrix b) {
        if(!Arrays.equals(a.getDimensions(), b.getDimensions()))
            throw new InvalidShapeException(a.toString(), b.toString());
        Matrix c = new Matrix(a.getDimensions()[0], a.getDimensions()[1]);

        Object[] aArr = a.getBackingArray();
        Object[] bArr = b.getBackingArray();
        Object[] cArr = c.getBackingArray();
        for(int i = 0; i < c.getLength(); i++)
            cArr[i] = (double) aArr[i] - (double) bArr[i];

        return c;
    }

    /**
     * generates an identity matrix with a specified size
     * @param size the size is the number of rows and columns
     * @return the generated matrix
     */
    public static Matrix identityMatrix(int size)
    {
        Matrix m = new Matrix(size, size);
        for(int i = 0; i < size; i++)
            m.set(1.0, i, i);
        return m;
    }

}

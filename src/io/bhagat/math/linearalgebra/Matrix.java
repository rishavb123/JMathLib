package io.bhagat.math.linearalgebra;

import io.bhagat.math.Function;
import io.bhagat.math.exceptions.InvalidShapeException;

import java.util.Arrays;

public class Matrix extends Tensor<Double> implements Comparable<Matrix> {

    MatrixEntry[][] matrixEntries;

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
        this(true, vs);
    }

    /**
     *
     * @param vs the array of vectors
     * @param asRows true if the vectors are going to set as rows false if they are as columns
     */
    public Matrix(boolean asRows, Vector... vs) {
        this(asRows? vs.length: vs[0].getLength(), asRows? vs[0].getLength(): vs.length);
        for(Vector v: vs) {
            if (v.getLength() != vs[0].getLength()) {
                throw new InvalidShapeException(vs[0].toString(), v.toString());
            }
        }
        if(asRows)
            setRowVectors(vs);
        else
            setColVectors(vs);
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
     * Sets the rows vectors of the matrix
     * @param rows the rows vectors
     */
    public void setRowVectors(Vector[] rows) {
        assert rows.length == getRows();
        for (int i = 0; i < getRows(); i++)
            setRow(i, rows[i]);
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
     * Sets the column vectors of the matrix
     * @param cols the column vectors
     */
    public void setColVectors(Vector[] cols) {
        assert cols.length == getCols();
        for (int i = 0; i < getCols(); i++)
            setCol(i, cols[i]);
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
            throw new IndexOutOfBoundsException("Row " + index + " is out of bounds for matrix of dimensions "
                    + getRows() + ", " + getCols());
        }
        Vector[] newRows = new Vector[getRows() - 1];
        Vector[] rows = getRowVectors();
        System.arraycopy(rows, 0, newRows, 0, index);
        System.arraycopy(rows, index + 1, newRows, index, getRows() - index - 1);
        return new Matrix(newRows);
    }

    /**
     * Adds a row to a matrix
     * @param v the vector row
     * @param index the index to add to
     * @return the new matrix
     */
    public Matrix addRow(Vector v, int index) {
        if (index < 0 || index > getRows()) {
            throw new IndexOutOfBoundsException("Row " + index + " is out of bounds for matrix of dimensions "
                    + getRows() + ", " + getCols());
        }
        Vector[] newRows = new Vector[getRows() + 1];
        Vector[] rows = getRowVectors();
        System.arraycopy(rows, 0, newRows, 0, index);
        newRows[index] = v;
        System.arraycopy(rows, index, newRows, index + 1, getRows() - index);
        return new Matrix(newRows);
    }

    /**
     * Adds a row to the end of the matrix
     * @param v the row vector
     * @return the new matrix
     */
    public Matrix addRow(Vector v) {
        return addRow(v, getRows());
    }

    /**
     * Removes the column specified by the index and returns the resultant matrix
     * @param index the index
     * @return the new matrix with removed column
     */
    public Matrix removeColumn(int index) {
        if (index < 0 || index >= getCols()) {
            throw new IndexOutOfBoundsException("Column " + index + " is out of bounds for matrix of dimensions "
                    + getRows() + ", " + getCols());
        }
        Matrix m = new Matrix(getRows(), getCols() - 1);
        Object[] mBackingArray = m.getBackingArray();
        Object[] backingArray = getBackingArray();
        int j = 0;
        for(int i = 0; i < backingArray.length; i++) {
            if (i % getCols() != index) {
                mBackingArray[j] = backingArray[i];
                j++;
            }
        }
        return m;
    }

    /**
     * Adds a column to a matrix
     * @param v the vector column
     * @param index the index to add to
     * @return the new matrix
     */
    public Matrix addColumn(Vector v, int index) {
        if (index < 0 || index > getCols()) {
            throw new IndexOutOfBoundsException("Column " + index + " is out of bounds for matrix of dimensions "
                    + getRows() + ", " + getCols());
        }
        Vector[] newCols = new Vector[getCols() + 1];
        Vector[] cols = getColVectors();
        System.arraycopy(cols, 0, newCols, 0, index);
        newCols[index] = v;
        System.arraycopy(cols, index, newCols, index + 1, getCols() - index);
        return new Matrix(newCols);
    }

    /**
     * Adds a column to the end of the matrix
     * @param v the column vector
     * @return the new matrix
     */
    public Matrix addColumn(Vector v) {
        return addColumn(v, getCols());
    }

    /**
     * Fills the Matrix with random values from min to max
     * @param min the minimum random number
     * @param max the maximum random number
     * @return a reference to this vector
     */
    public Matrix randomize(double min, double max)
    {
        Object[] backingArray = getBackingArray();
        for(int i = 0; i < getLength(); i++)
            backingArray[i] = Math.random()*(max - min) + min;
        return this;
    }

    /**
     * Generates a random matrix with values from 0 to 1
     * @return a reference to this vector
     */
    public Matrix randomize() {
        return randomize(0, 1);
    }

    /**
     * Checks if the matrix is a square matrix
     * @return whether the matrix is a square matrix
     */
    public boolean isSquare() {
        return getRows() == getCols();
    }

    /**
     * Calculates the determinant of a matrix
     * @return the determinant
     */
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

    /**
     * finds the cofactor of the matrix
     * @return the cofactor matrix
     */
    public Matrix cofactor()
    {
        if(!isSquare())
            throw new InvalidShapeException("The matrix is not a square matrix");
        Matrix C = new Matrix(getRows(), getCols());
        for(int i = 0; i < getRows(); i++)
            for(int j = 0; j < getCols(); j++)
                C.set((((i + j) % 2 == 0)? 1 : -1) * removeRow(i).removeColumn(j).determinant(), i, j);
        return C;
    }

    /**
     * Calculates the inverse matrix of this matrix
     * @return the inverse of this matrix
     */
    public Matrix inverse() {
        return cofactor().transpose().scale(1/determinant());
    }

    /**
     * Computes the QR factorization of a matrix
     * @return an array of matricies where the first matrix is Q and the second one is R;
     */
    public Matrix[] QR() {
        Matrix Q = new Matrix(false, Vector.orthonormalize(getColVectors()));
        Matrix R = Matrix.multiply(Q.transpose(), this);
        if (Double.isNaN(Q.get(0, 0)) || Double.isNaN(R.get(0, 0))) {
            Q = identityMatrix(getRows());
            R = clone();
        }
        return new Matrix[]{Q, R};
    }

    // TODO: LU Factorization
    // TODO: Eigen Problem
    // TODO: Convolutions

    /**
     * Scales the matrix by a scalar
     * @param c the scalar constant
     * @return a reference to this matrix
     */
    public Matrix scale(double c) {
        Object[] backingArray = getBackingArray();
        for(int i = 0; i < getLength(); i++) {
            backingArray[i] = (double) backingArray[i] * c;
        }
        return this;
    }

    /**
     * Translates the matrix by a scalar
     * @param c the scalar constant
     * @return a reference to this matrix
     */
    public Matrix translate(double c) {
        Object[] backingArray = getBackingArray();
        for(int i = 0; i < getLength(); i++) {
            backingArray[i] = (double) backingArray[i] + c;
        }
        return this;
    }

    /**
     * converts the matrix to row echelon form
     * @return the matrix in row echelon form
     */
    public Matrix rowEchelonForm()
    {
        Matrix ref = clone();
        Vector[] rows = ref.getRowVectors();
        for(int i = 0; i < getCols() - 1; i++)
            for(int j = i + 1; j < rows.length; j++)
                rows[j].subtract(rows[i].clone().scale(1/rows[i].get(i)).scale(rows[j].get(i)));
        return new Matrix(rows);
    }

    /**
     * converts the matrix to reduced row echelon form
     * @return the matrix in reduced row echelon form
     */
    public Matrix reducedRowEchelonForm()
    {
        Matrix ref = rowEchelonForm();
        Vector[] rows = ref.getRowVectors();
        for(int i = 0; i < getRows(); i++)
            rows[i].scale(1/rows[i].get(i));
        return ref;
    }

    /**
     * Gets a matrix entry object that is linked to this matrix
     * @param r the row
     * @param c the column
     * @return the matrix entry object
     */
    public MatrixEntry getMatrixEntry(int r, int c) {
        if (matrixEntries == null)
            matrixEntries = new MatrixEntry[getRows()][getCols()];
        if (matrixEntries[r][c] == null)
            matrixEntries[r][c] = new MatrixEntry(r, c);
        return matrixEntries[r][c];
    }

    /**
     * Gets a two-dimensional array of matrix entry objects, each corresponding to an entry in the matrix
     * @return the array of entries
     */
    public MatrixEntry[][] getMatrixEntries() {
        MatrixEntry[][] entries = new MatrixEntry[getRows()][getCols()];
        for (int r = 0; r < getRows(); r++)
            for (int c = 0; c < getCols(); c++)
                entries[r][c] = getMatrixEntry(r, c);
        return entries;
    }

    /**
     * Maps a function onto each element in the vector
     * @param function a function that receives a matrix entry
     * @return a reference to this matrix
     */
    public Matrix mapFromEntries(Function<MatrixEntry, Double> function) {
        Object[] backingArray = getBackingArray();
        for (int i = 0; i < getLength(); i++)
            backingArray[i] = function.f(getMatrixEntry(i / getCols(), i % getCols()));
        return this;
    }

    @Override
    public int compareTo(Matrix o) {
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
     * Takes the dot product across two matricies
     * @param a the first matrix
     * @param b the second matrix
     * @return the dot product
     */
    public static double dot(Matrix a, Matrix b) {
        assertShape(a, b);
        double sum = 0;
        Object[] aBackingArray = a.getBackingArray();
        Object[] bBackingArray = b.getBackingArray();
        for(int i = 0; i < a.getLength(); i++)
            sum += (Double) aBackingArray[i] * (Double) bBackingArray[i];
        return sum;
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

    /**
     * Calculates the hadamard (elementwise product) of two matricies
     * @param a the first matrix
     * @param b the second matrix
     * @return the resultant matrix
     */
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

    /**
     * Augments a matrix with a vector
     * @param A the matrix
     * @param b the vector
     * @return the augmented matrix
     */
    public static Matrix augment(Matrix A, Vector b) {
        return A.addColumn(b);
    }

    /**
     * A class to hold the entries of a vector
     */
    public class MatrixEntry {

        private final int row;
        private final int col;

        /**
         * Constructs a matrix index object
         * @param row the row in the matrix
         * @param col the col in the matrix
         */
        public MatrixEntry(int row, int col) {
            this.row = row;
            this.col = col;
        }

        /**
         * Gets the value
         * @return the value
         */
        public double getVal() {
            return get(row, col);
        }

        /**
         * Sets the value in the parent matrix
         * @param val the value to set it to
         */
        public void setVal(double val) {
            set(val, row, col);
        }

        /**
         * Gets the row
         * @return the row
         */
        public int getRow() {
            return row;
        }

        /**
         * Gets the column
         * @return the column
         */
        public int getCol() {
            return col;
        }

        /**
         * Gets the parent matrix
         * @return the parent matrix
         */
        public Matrix getParent() {
            return Matrix.this;
        }

        /**
         * String representation of the object
         * @return the string representation
         */
        public String toString() {
            return getParent() + "[" + row + "][" + col + "] = " + getVal();
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof MatrixEntry))
                return false;
            MatrixEntry o = (MatrixEntry) obj;
            return row == o.getRow() && col == o.getCol() && getParent() == o.getParent();
        }

    }

}

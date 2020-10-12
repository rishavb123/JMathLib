package io.bhagat.math.linearalgebra;

public class Matrix extends Tensor<Double> {

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
     * Gets a specific row
     * @param r the row index
     * @return the row vector
     */
    public Vector getRow(int r) {
        return new Vector(getSubTensor(r));
    }

    /**
     * Gets a specific column
     * @param c the column index
     * @return the column vector
     */
    public Vector getCol(int c) {
        int[] dim = getDimensions();
        Vector vec = new Vector(dim[0]);
        for(int i = 0; i < dim[0]; i++)
            vec.set(get(i, c), i);
        return vec;
    }

    /**
     * Gets the rows of the matrix
     * @return the array of all the row vectors
     */
    public Vector[] getRows() {
        int[] dim = getDimensions();
        Vector[] vectors = new Vector[dim[0]];
        for(int i = 0; i < dim[0]; i++)
            vectors[i] = getRow(i);
        return vectors;
    }

    /**
     * Gets the columns of the matrix
     * @return the array of all the column vectors
     */
    public Vector[] getCols() {
        int[] dim = getDimensions();
        Vector[] vectors = new Vector[dim[1]];
        for(int i = 0; i < dim[1]; i++)
            vectors[i] = getCol(i);
        return vectors;
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

}

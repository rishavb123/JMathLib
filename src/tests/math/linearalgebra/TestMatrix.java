package tests.math.linearalgebra;

import io.bhagat.math.linearalgebra.Matrix;
import io.bhagat.math.linearalgebra.Vector;

public class TestMatrix {

    public static void main(String[] args) {
        Matrix A = new Matrix(new Vector(2, 3, 4), new Vector(5, 6, 7), new Vector(1, 0, 1));
        System.out.println(A.determinant());
        A.setRow(2, new Vector(0, 1, 0));
        System.out.println(A);
    }

}

package tests.math.linearalgebra;

import io.bhagat.math.linearalgebra.Matrix;
import io.bhagat.math.linearalgebra.Vector;

public class TestMatrix {

    public static void main(String[] args) {
        Matrix A = new Matrix(new Vector(2, 3, 4), new Vector(5, 6, 7));
        System.out.println(A);
    }

}

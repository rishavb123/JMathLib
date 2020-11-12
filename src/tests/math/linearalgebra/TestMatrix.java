package tests.math.linearalgebra;

import io.bhagat.math.Constants;
import io.bhagat.math.Function;
import io.bhagat.math.linearalgebra.Matrix;
import io.bhagat.math.linearalgebra.Vector;

import java.util.HashMap;
import java.util.HashSet;

public class TestMatrix {

    public static void main(String[] args) {
//        Matrix A = new Matrix(new Vector(2, 3, 4), new Vector(5, 6, 7), new Vector(1, 0, 1));
//        System.out.println(A.determinant());
//        A.setRow(2, new Vector(0, 1, 0));
//        System.out.println(A);
//        System.out.println(A.removeColumn(1));
        Matrix A = new Matrix(new double[][] {
                {0, 1},
                {-1, -2}
        });

        HashMap<Double, Vector> eigensolution = A.eigenproblem(Constants.N);
//        System.out.println(eigensolution);

        Matrix A2 = new Matrix(new double[][] {
                {3, 2, 2},
                {2, 3, -2}
        });
        Matrix[] SVD = A2.singularValueDecomposition(Constants.N);
        System.out.println(SVD[0]);
        System.out.println(SVD[1]);
        System.out.println(SVD[2]);

    }

}

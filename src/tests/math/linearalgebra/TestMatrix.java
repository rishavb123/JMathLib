package tests.math.linearalgebra;

import io.bhagat.math.linearalgebra.Matrix;
import io.bhagat.math.linearalgebra.Vector;

public class TestMatrix {

    public static void main(String[] args) {
//        Matrix A = new Matrix(new Vector(2, 3, 4), new Vector(5, 6, 7), new Vector(1, 0, 1));
//        System.out.println(A.determinant());
//        A.setRow(2, new Vector(0, 1, 0));
//        System.out.println(A);
//        System.out.println(A.removeColumn(1));
        Matrix A = new Matrix(new double[][] {
                {2, 4},
                {6, 8}
        });
        System.out.println("A:");
        A.print();

        Matrix[] ans = A.LU();
        System.out.println("L:");
        ans[0].print();

        System.out.println("U:");
        ans[1].print();

        System.out.println("L*U:");
        Matrix.multiply(ans[0], ans[1]).print();

    }

}

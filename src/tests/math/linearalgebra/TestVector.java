package tests.math.linearalgebra;

import io.bhagat.math.linearalgebra.Vector;
import tests.TestUtils;

public class TestVector {

    public static void main(String[] args) {
        Vector a = new Vector(2, 3, 4);
        Vector b = new Vector(-2, -3, -4);
        Vector c = new Vector(0, 0, 0);
        Vector d = new Vector(4, 6, 8);

        TestUtils.check("Test vector addition", Vector.add(a, b), c);
        TestUtils.check("Test vector subtraction", Vector.subtract(a, b), d);
        TestUtils.check("Test dot product", Vector.dot(a, b), -29.0);
        TestUtils.check("Test magnitude equality", a.magnitude(), b.magnitude());
        TestUtils.check("Test sum", a.sum(), 9.0);
        TestUtils.endTest();

        TestUtils.check("Test get vector entry", a.getVectorEntry(0), a.new VectorEntry(0));
        TestUtils.check("Test get vector entry from entry array", a.getVectorEntries()[0], a.new VectorEntry(0));
        TestUtils.endTest();

        Vector v1 = new Vector(1, 0, 1);
        Vector v2 = new Vector(1, 1, 1);
        Vector v3 = new Vector(3, 3, 3);

        Vector[] arr = {v1, v2, v3};
        Vector[] output = Vector.orthogonalize(arr);
        for(Vector x: output) {
            System.out.println(x);
        }
        System.out.println(Vector.orthogonal(output[0], output[1]));
    }

}

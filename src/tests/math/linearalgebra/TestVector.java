package tests.math.linearalgebra;

import io.bhagat.math.linearalgebra.Vector;

public class TestVector {

    public static void main(String[] args) {
        Vector a = new Vector(2, 3, 4);
        Vector b = new Vector(-2, -3, -4);
        System.out.println(Vector.add(a, b));
        System.out.println(Vector.subtract(a, b));
        System.out.println(Vector.dot(a, b));
        System.out.println(a.equals(b));
        System.out.println(a.equals(b.scale(-1)));
        System.out.println((a.magnitude() == b.magnitude()) + " " + a.magnitude());
    }

}

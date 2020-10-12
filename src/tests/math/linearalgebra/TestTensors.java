package tests.math.linearalgebra;

import io.bhagat.math.linearalgebra.Tensor;
import tests.TestUtils;

public class TestTensors {
    public static void main(String[] args) {
        Tensor<String> tensor = new Tensor<>(3, 2);

        TestUtils.check("Set 2", tensor.set("hi", 2, 0), null);
        TestUtils.check("Set 0", tensor.set("Hello", 0, 0), null);
        TestUtils.check("Set 2", tensor.set("World", 2, 1), null);
        TestUtils.check("Get 0", tensor.get(0, 0), "Hello");
        TestUtils.check("Get 1", tensor.get(1, 0), null);
        TestUtils.check("Get 2", tensor.get(2, 0), "hi");
        TestUtils.check("Get 2", tensor.get(2, 1), "World");
        TestUtils.endTest();

        Tensor<String> tensor2 = new Tensor<>(new String[][] {
                {"Hello", null},
                {null, null},
                {"hi", "World"}
        });
        TestUtils.check("From Array - Get 0", tensor2.get(0, 0), "Hello");
        TestUtils.check("From Array - Get 1", tensor2.get(1, 0), null);
        TestUtils.check("From Array - Get 2", tensor2.get(2, 0), "hi");
        TestUtils.check("From Array - Get 2", tensor2.get(2, 1), "World");
        TestUtils.endTest();

        TestUtils.check("Equals", tensor2, tensor);
        TestUtils.endTest();

        TestUtils.check("toString", tensor.toString(), "[[Hello, null], [null, null], [hi, World]]");
        TestUtils.endTest();

        TestUtils.check("Clone", tensor.clone(), tensor);
        TestUtils.endTest();

        Tensor<String> tensor3 = new Tensor<>(new String[] {
                "hi", "World"
        });
        TestUtils.check("Sub Tensor", tensor.getSubTensor(2), tensor3);
        TestUtils.check("Sub Tensor as clone", tensor.getSubTensor(), tensor);
        TestUtils.endTest();

        Tensor<String> tensor4 = new Tensor<>();
        TestUtils.check("Scalar Set", tensor4.set("I'm a single value"), null);
        TestUtils.check("Scalar Set", tensor4.get(), "I'm a single value");
        TestUtils.endTest();


    }
}

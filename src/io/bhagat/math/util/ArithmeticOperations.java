package io.bhagat.math.util;

public abstract class ArithmeticOperations<T extends Number> {

    public static final ArithmeticOperations<Integer> INTEGER_ARITHMETIC_OPERATIONS = new ArithmeticOperations<Integer>() {
        @Override
        public Integer add(Integer a, Integer b) {
            return a + b;
        }

        @Override
        public Integer subtract(Integer a, Integer b) {
            return a - b;
        }

        @Override
        public Integer multiply(Integer a, Integer b) {
            return a * b;
        }

        @Override
        public Integer divide(Integer a, Integer b) {
            return a / b;
        }
    };

    public static final ArithmeticOperations<Double> DOUBLE_ARITHMETIC_OPERATIONS = new ArithmeticOperations<Double>() {
        @Override
        public Double add(Double a, Double b) {
            return a + b;
        }

        @Override
        public Double subtract(Double a, Double b) {
            return a - b;
        }

        @Override
        public Double multiply(Double a, Double b) {
            return a * b;
        }

        @Override
        public Double divide(Double a, Double b) {
            return a / b;
        }
    };

    public static final ArithmeticOperations<Float> FLOAT_ARITHMETIC_OPERATIONS = new ArithmeticOperations<Float>() {
        @Override
        public Float add(Float a, Float b) {
            return a + b;
        }

        @Override
        public Float subtract(Float a, Float b) {
            return a - b;
        }

        @Override
        public Float multiply(Float a, Float b) {
            return a * b;
        }

        @Override
        public Float divide(Float a, Float b) {
            return a / b;
        }
    };

    public static final ArithmeticOperations<Long> LONG_ARITHMETIC_OPERATIONS = new ArithmeticOperations<Long>() {
        @Override
        public Long add(Long a, Long b) {
            return a + b;
        }

        @Override
        public Long subtract(Long a, Long b) {
            return a - b;
        }

        @Override
        public Long multiply(Long a, Long b) {
            return a * b;
        }

        @Override
        public Long divide(Long a, Long b) {
            return a / b;
        }
    };

    public static final ArithmeticOperations<Short> SHORT_ARITHMETIC_OPERATIONS = new ArithmeticOperations<Short>() {
        @Override
        public Short add(Short a, Short b) {
            return (short)(a + b);
        }

        @Override
        public Short subtract(Short a, Short b) {
            return (short)(a - b);
        }

        @Override
        public Short multiply(Short a, Short b) {
            return (short)(a * b);
        }

        @Override
        public Short divide(Short a, Short b) {
            return (short)(a / b);
        }
    };

    public static final ArithmeticOperations<Byte> BYTE_ARITHMETIC_OPERATIONS = new ArithmeticOperations<Byte>() {
        @Override
        public Byte add(Byte a, Byte b) {
            return (byte)(a + b);
        }

        @Override
        public Byte subtract(Byte a, Byte b) {
            return (byte)(a - b);
        }

        @Override
        public Byte multiply(Byte a, Byte b) {
            return (byte)(a * b);
        }

        @Override
        public Byte divide(Byte a, Byte b) {
            return (byte)(a / b);
        }
    };

    public abstract T add(T a, T b);
    public abstract T subtract(T a, T b);
    public abstract T multiply(T a, T b);
    public abstract T divide(T a, T b);

}

package io.bhagat.math;

public abstract class MultiVariableFunction<E, T> extends Function<E[], T> {

    public T run(E... xs) {
        return f(xs);
    };

}

package io.bhagat.math;

public abstract class MultiVariableFunction<E, T> implements Function<E[], T> {

    public T g(E... xs) {
        return f(xs);
    };

}

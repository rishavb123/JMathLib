package io.bhagat.math.functions;

import io.bhagat.math.functions.Function;

public abstract class MultiVariableFunction<E, T> implements Function<E[], T> {

    public T g(E... xs) {
        return f(xs);
    };

}

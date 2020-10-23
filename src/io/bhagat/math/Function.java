package io.bhagat.math;

public abstract class Function<E, T> {

    public abstract T f(E x);

    public T run(E x) {
        return f(x);
    }

}

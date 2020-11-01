package io.bhagat.math;

public interface Function<E, T> {

    T f(E x);

    static <E> E[] map(E[] arr, Function<E, E> func) {
        for(int i = 0; i < arr.length; i++)
            arr[i] = func.f(arr[i]);
        return arr;
    }

}

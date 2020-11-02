package io.bhagat.math;

public interface Function<E, T> {

    T f(E x);

    static <E> E[] mapOnto(E[] arr, Function<E, E> func) {
        for(int i = 0; i < arr.length; i++)
            arr[i] = func.f(arr[i]);
        return arr;
    }

    static <E, T> T[] map(E[] arr, Function<E, T> func) {
        T[] newArr = (T[]) new Object[arr.length];
        for(int i = 0; i < arr.length; i++)
            newArr[i] = func.f(arr[i]);
        return newArr;
    }

}

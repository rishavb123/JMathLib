package io.bhagat.math.settheory;

/**
 * An interface for a set in math
 * @param <E> the data type for the set
 */
public interface SetBase<E> {

    boolean contains(E x);

    static <E> SetBase<E> union(SetBase<E> s1, SetBase<E> s2) {
        return x -> s1.contains(x) || s2.contains(x);
    }

    static <E> SetBase<E> intersection(SetBase<E> s1, SetBase<E> s2) {
        return x -> s1.contains(x) && s2.contains(x);
    }

    static <E> SetBase<E> difference(SetBase<E> s1, SetBase<E> s2) {
        return x -> s1.contains(x) && !s2.contains(x);
    }

    static <E> SetBase<E> complement(SetBase<E> s) {
        return x -> !s.contains(x);
    }

    static <E> SetBase<E> symmetricDifference(SetBase<E> s1, SetBase<E> s2) {
        return x -> s1.contains(x) != s2.contains(x);
    }

}

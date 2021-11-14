package io.bhagat.math.settheory;

/**
 * An interface for a set in math
 * @param <E> the data type for the set
 */
public interface SetBase<E> {

    /**
     * Returns true if the set contains the element
     * @param x the element to check
     * @return true if the set contains the element
     */
    boolean contains(E x);

    /**
     * Returns the union of the two sets
     * @param s1 the first set
     * @param s2 the second set
     * @param <E> the data type for the set
     * @return the union of the two sets
     */
    static <E> SetBase<E> union(SetBase<E> s1, SetBase<E> s2) {
        return x -> s1.contains(x) || s2.contains(x);
    }

    /**
     * Returns the intersection of the two sets
     * @param s1 the first set
     * @param s2 the second set
     * @param <E> the data type for the set
     * @return the intersection of the two sets
     */
    static <E> SetBase<E> intersection(SetBase<E> s1, SetBase<E> s2) {
        return x -> s1.contains(x) && s2.contains(x);
    }

    /**
     * Returns the difference of the two sets
     * @param s1 the first set
     * @param s2 the second set
     * @param <E> the data type for the set
     * @return the difference of the two sets
     */
    static <E> SetBase<E> difference(SetBase<E> s1, SetBase<E> s2) {
        return x -> s1.contains(x) && !s2.contains(x);
    }

    /**
     * Returns the complement of the set
     * @param s the set
     * @param <E> the data type for the set
     * @return the complement of the set
     */
    static <E> SetBase<E> complement(SetBase<E> s) {
        return x -> !s.contains(x);
    }

    /**
     * Returns the symmetric difference of the two sets
     * @param s1 the first set
     * @param s2 the second set
     * @param <E> the data type for the set
     * @return the symmetric difference of the two sets
     */
    static <E> SetBase<E> symmetricDifference(SetBase<E> s1, SetBase<E> s2) {
        return x -> s1.contains(x) != s2.contains(x);
    }

}

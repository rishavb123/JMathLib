package io.bhagat.math.settheory;

/**
 * A class for an interval of comparable objects
 * @param <E> the object type
 */
public class Interval<E extends Comparable<E>> implements SetBase<E>{

    private E lowerBound;
    private E upperBound;
    private boolean open;

    /**
     * Creates an instance of interval
     * @param lowerBound the lower bound
     * @param upperBound the upper bound
     * @param open whether or not the interval is open
     */
    public Interval(E lowerBound, E upperBound, boolean open) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.open = open;
    }

    /**
     * Creates an closed instance of the interval
     * @param lowerBound the lower bound
     * @param upperBound the upper bound
     */
    public Interval(E lowerBound, E upperBound) {
        this(lowerBound, upperBound, false);
    }

    /**
     * Checks if an object is within an interval
     * @param x the object to check
     * @return whether it is in the interval
     */
    public boolean contains(E x) {
        int c1 = lowerBound.compareTo(x);
        int c2 = x.compareTo(upperBound);
        if (c1 < 0 && c2 < 0) {
            return true;
        } else if (c1 == 0 || c2 == 0) {
            return !open;
        }
        return false;
    }

    /**
     * Checks if an interval is contained in this interval
     * @param x the interval to check
     * @return whether it is contained in the interval x
     */
    public boolean contains(Interval<E> x) {
        int c1 = lowerBound.compareTo(x.lowerBound);
        int c2 = x.upperBound.compareTo(upperBound);
        if (c1 < 0 && c2 < 0) {
            return true;
        } else if (c1 == 0 || c2 == 0) {
            return !open && !x.open;
        }
        return false;
    }

    /**
     * Checks if an interval is disjoint from this interval
     * @param x the interval to check
     * @returns whether it is disjoint from the interval x
     */
    public boolean disjoint(Interval<E> x) {
        int c1 = lowerBound.compareTo(x.upperBound);
        int c2 = x.lowerBound.compareTo(upperBound);
        if (c1 > 0 || c2 > 0) {
            return true;
        } else if (c1 == 0 || c2 == 0) {
            return open || x.open;
        }
        return false;
    }

    /**
     * Checks if an interval is equal to this interval
     * @param x the interval to check
     * @return whether it is equal to the interval x
     */
    public boolean equals(Interval<E> x) {
        return lowerBound.equals(x.lowerBound) && upperBound.equals(x.upperBound) && open == x.open;
    }

    /**
     * Gets the lower bound
     * @return the lower bound
     */
    public E getLowerBound() {
        return lowerBound;
    }

    /**
     * Gets the upper bound
     * @return the upper bound
     */
    public E getUpperBound() {
        return upperBound;
    }

    /**
     * Gets whether the interval is open
     * @return the boolean for if is open
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Sets the lower bound
     * @param lowerBound the lower bound to set it to
     */
    public void setLowerBound(E lowerBound) {
        this.lowerBound = lowerBound;
    }

    /**
     * Sets the upper bound
     * @param upperBound the upper bound to set it to
     */
    public void setUpperBound(E upperBound) {
        this.upperBound = upperBound;
    }

    /**
     * Sets whether the interval is open
     * @param open the boolean to set it to
     */
    public void setOpen(boolean open) {
        this.open = open;
    }
}

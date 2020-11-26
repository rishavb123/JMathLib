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

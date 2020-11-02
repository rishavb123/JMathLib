package io.bhagat.math.linearalgebra;

import io.bhagat.math.Function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * A class for multi dimensional tensors tensors as arrays
 * @param <T> the tensor type
 */
@SuppressWarnings("unchecked")
public class Tensor<T> implements Iterable<T>, Serializable {

    private final int rank;
    private final int[] dimensions;
    private final int[] multipliers;
    private Object[] backingArray;
    private int length;

    /**
     * Constructs a tensor with specified dimensions
     * @param dimensions the dimensions
     */
    public Tensor(int... dimensions) {
        this.dimensions = dimensions;
        rank = dimensions.length;
        multipliers = constructMultipliers(dimensions);
        length = 1;
        for(int d: dimensions)
            length *= d;
        assert length != 0;
        backingArray = new Object[length];
    }

    /**
     * Constructs a Tensor with specified data
     * @param obj the data array
     */
    public Tensor(Object[] obj) {
        this(getDimensions(obj));
        List<T> list = recTraverser(obj, new ArrayList<>());
        for(int i = 0; i < backingArray.length; i++) {
            backingArray[i] = list.get(i);
        }
    }

    /*
     * private constructor specifically to build a tensor with specified data
     * @param backingArray the backing array containing the data
     * @param dimensions the dimensions of the tensor
     */
    protected Tensor(Object[] backingArray, int[] dimensions) {
        this(dimensions);
        this.backingArray = backingArray;
    }

    /**
     * Gets the element at a position
     * @param pos the position
     * @return the element
     */
    public T get(int... pos) {
        return (T) backingArray[toBackingArrayPos(pos)];
    }

    /**
     * Gets the sub tensor at the specified position
     * @param pos the position
     * @return the sub tensor
     */
    public Tensor<T> getSubTensor(int... pos) {
        int[] newDim = new int[dimensions.length - pos.length];
        System.arraycopy(dimensions, pos.length, newDim, 0, newDim.length);
        int length = 1;
        for(int d: newDim)
            length *= d;
        Object[] newBackingArray = new Object[length];
        System.arraycopy(backingArray, toBackingArrayPos(pos), newBackingArray, 0, newBackingArray.length);
        return new Tensor<>(newBackingArray, newDim);
    }

    /**
     * Sets the position of the tensor to an object
     * @param obj the object to put in the position in the tensor
     * @param pos the position to set
     * @return the old element at this position
     */
    public T set(T obj, int... pos) {
        int idx = toBackingArrayPos(pos);
        T temp = (T) backingArray[idx];
        backingArray[idx] = obj;
        return temp;
    }

    /**
     * Maps a function onto the tensor
     * @param function the function to map
     * @return a reference to this tensor
     */
    public Tensor<T> map(Function<T, T> function) {
        for(int i = 0; i < backingArray.length; i++)
            backingArray[i] = function.f((T) backingArray[i]);
        return this;
    }

    /**
     * Flattens the tensor into a single array with all the elements (including null elements)
     * @return the array of all the elements
     */
    public Tensor<T> flatten() {
        Object[] backingArrayCopy = new Object[backingArray.length];
        System.arraycopy(backingArray, 0, backingArrayCopy, 0, backingArrayCopy.length);
        return new Tensor<>(backingArrayCopy, new int[] { length });
    }

    /**
     * Creates a clone of this tensor with the same data and dimensions with new memory allocation
     * @return the cloned tensor
     */
    @Override
    public Tensor<T> clone() {
        Object[] backingArrayCopy = new Object[backingArray.length];
        int[] dimensionsCopy = new int[dimensions.length];
        System.arraycopy(backingArray, 0, backingArrayCopy, 0, backingArrayCopy.length);
        System.arraycopy(dimensions, 0, dimensionsCopy, 0, dimensionsCopy.length);
        return new Tensor<>(backingArrayCopy, dimensionsCopy);
    }

    /**
     * Creates an iterator object to iterate through the backing array
     * @return the iterator
     */
    public Iterator<T> iterator() {
        return new Iterator<>() {

            int idx = 0;

            @Override
            public boolean hasNext() {
                return idx < length;
            }

            @Override
            public T next() {
                return (T) backingArray[idx++];
            }
        };
    }

    /**
     * Checks if a tensor is equivalent to this one
     * @param o the second tensor
     * @return whether or not it is equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tensor<?> tensor = (Tensor<?>) o;
        return rank == tensor.rank &&
                length == tensor.length &&
                Arrays.equals(dimensions, tensor.dimensions) &&
                Arrays.equals(multipliers, tensor.multipliers) &&
                Arrays.equals(backingArray, tensor.backingArray);
    }

    /**
     * The hash code method to map this object into an integer
     * @return the hash
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(rank, length);
        result = 31 * result + Arrays.hashCode(dimensions);
        result = 31 * result + Arrays.hashCode(multipliers);
        result = 31 * result + Arrays.hashCode(backingArray);
        return result;
    }

    protected String nullString() {
        return "null";
    }

    /**
     * A string representation of this object
     * @return the string
     */
    public String toString() {
        String nullStr = nullString();
        return toString(nullStr);
    }

    public String toString(String nullStr) {
        if(rank == 0)
            return get() == null? nullStr: get().toString();
        StringBuilder s = new StringBuilder("[");
        for(int i = 0; i < dimensions[0] - 1; i++) {
            s.append(getSubTensor(i).toString(nullStr)).append(", ");
        }
        s.append(getSubTensor(dimensions[0] - 1).toString(nullStr));
        s.append("]");
        return s.toString();
    }

    /**
     * Gets the rank of the tensor
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Gets the dimensions array
     * @return the dimensions array
     */
    public int[] getDimensions() {
        return dimensions;
    }

    /**
     * Gets the backing array of the tensor
     * @return the backing array
     */
    Object[] getBackingArray() {
        return backingArray;
    }

    /**
     * Gets the length of the backing array
     * @return the backing array length
     */
    public int getLength() {
        return length;
    }

    /*
     * Converts an array position into the index in the backing array
     * @param pos the array position
     * @return the index in the backing array
     */
    private int toBackingArrayPos(int... pos) {
        int idx = 0;
        for(int i = 0; i < pos.length; i++) {
            idx += pos[i] * multipliers[i];
        }
        return idx;
    }

    /*
     * Private recursive helper method for
     * @param obj
     * @param list
     * @return
     */
    private List<T> recTraverser(Object[] obj, List<T> list) {
        for(Object o: obj) {
            if(o instanceof Object[])
                recTraverser((Object[]) o, list);
            else
                list.add((T) o);
        }
        return list;
    }

    /*
     * Constructs the multipliers array which is used to convert a position array to a single backing array index
     * @param dimensions the dimensions array
     * @return the multipliers array
     */
    private static int[] constructMultipliers(int[] dimensions) {
        int[] multipliers = new int[dimensions.length];
        if(dimensions.length > 0) {
            multipliers[dimensions.length - 1] = 1;
            for (int i = dimensions.length - 2; i >= 0; i--) {
                multipliers[i] = multipliers[i + 1] * dimensions[i + 1];
            }
        }
        return multipliers;
    }

    /**
     * Creates a dimensions array of a multi-dimensional array
     * @param obj the multi-dimensional array
     * @return the dimensions array
     */
    public static int[] getDimensions(Object[] obj) {
        Object temp = obj;
        ArrayList<Integer> dim = new ArrayList<>();
        while(temp instanceof Object[]) {
            Object[] arr = (Object[]) temp;
            dim.add(arr.length);
            temp = arr[0];
        }
        return dim.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Maps a function onto a tensor
     * @param tensor the tensor
     * @param function the function to map
     * @param <T> the type of the tensor
     * @return the resultant tensor
     */
    public static <T> Tensor<T> map(Tensor<T> tensor, Function<T, T> function) {
        return tensor.clone().map(function);
    }

    public static boolean equalShape(Tensor<?> a, Tensor<?> b) {
        return Arrays.equals(a.getDimensions(), b.getDimensions());
    }

}

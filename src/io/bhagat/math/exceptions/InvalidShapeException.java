package io.bhagat.math.exceptions;

/**
 * An exception for when the shape of the related objects do work with each other
 */
public class InvalidShapeException extends RuntimeException {

    /**
     * The constructor to create an instance of the exception
     * @param objs the objects that do not have compatible shapes
     */
    public InvalidShapeException(String... objs) {
        super(buildString(objs));
    }

    public static String buildString(String... objs) {
        if(objs.length == 0)
            return "The shape of the related objects are not compatible for this operation";
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < objs.length - 1; i++)
            s.append(objs[i]).append(", ");
        s = new StringBuilder(s.substring(0, s.length() - 2));
        s.append(" and ").append(objs[objs.length - 1]);
        return "The shape of " + s + " " + ((objs.length > 1)? "are":"is") + " not compatible for this operation";
    }

}

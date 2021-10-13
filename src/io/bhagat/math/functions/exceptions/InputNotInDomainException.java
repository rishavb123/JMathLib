package io.bhagat.math.functions.exceptions;

/**
 *
 */
public class InputNotInDomainException extends Exception {

    /**
     * The constructor to create an instance of the exception
     * @param objs the input that was not in the domain
     */
    public InputNotInDomainException(String... objs) {
        super(buildString(objs));
    }

    private static String buildString(String... objs) {
        if(objs.length == 0)
            return "The input was not in the function's domain";
        else
            return "The input " + objs[0] + " is not in the function's domain";
    }

}

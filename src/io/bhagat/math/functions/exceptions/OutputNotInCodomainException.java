package io.bhagat.math.functions.exceptions;

public class OutputNotInCodomainException extends Exception {

    /**
     * The constructor to create an instance of the exception
     * @param objs the output that was not in the codomain
     */
    public OutputNotInCodomainException(String... objs) {
        super(buildString(objs));
    }

    private static String buildString(String... objs) {
        if(objs.length == 0)
            return "The output was not in the function's codomain";
        else
            return "The output " + objs[0] + " is not in the function's codomain";
    }

}

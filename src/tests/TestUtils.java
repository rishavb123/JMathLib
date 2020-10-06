package tests;

public class TestUtils {

    /**
     * Checks the results of a test
     * @param tag the tag or name of the test
     * @param value the result
     * @param expected the expected result
     * @param <T> the type of the result
     */
    public static <T> void check(String tag, T value, T expected) {
        String status = "FAILED";
        try {
            if(value == expected || value.equals(expected)) {
                status = "PASSED";
            }
        } catch(Exception e) {}
        System.out.println(tag + ": " + value + " - " + status + " (" + expected + " expected)");
    }

}

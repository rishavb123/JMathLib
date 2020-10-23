package tests;

public class TestUtils {

    public static boolean withinTest = false;

    /**
     * Checks the results of a test
     * @param tag the tag or name of the test
     * @param value the result
     * @param expected the expected result
     * @param <T> the type of the result
     */
    public static <T> void check(String tag, T value, T expected) {
        if(!withinTest) {
            System.out.println(
                    pad("Label", 50) + "| "
                    + pad("Value", 50) + "| "
                    + pad("Expected", 58) + "| "
                    + "Status");
            System.out.print("---------------------------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------------------------");
            withinTest = true;
        }

        String status = ConsoleColors.RED_BOLD_BRIGHT + "FAILED" + ConsoleColors.RESET;
        try {
            if(value == expected || value.equals(expected)) {
                status = ConsoleColors.GREEN_BOLD_BRIGHT + "PASSED" + ConsoleColors.RESET;
            }
        } catch(Exception e) {}
        System.out.println(
                pad( tag, 50) + "| "
                + pad(value.toString(), 50) + "| "
                + pad(expected + " expected", 58) + "| "
                + status
        );
    }

    /**
     * Ends the test section with a new line
     */
    public static void endTest() {
        withinTest = false;
        System.out.println();
    }

    private static String pad(String string, int length) {
        return String.format("%-"+length+ "s", string);
    }

}

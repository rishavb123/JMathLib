package tests;

public class TestUtils {

    public static boolean withinTest = false;
    public static int passed = 0;
    public static int total = 0;

    /**
     * Checks the results of a test
     * @param tag the tag or name of the test
     * @param value the result
     * @param expected the expected result
     * @param <T> the type of the result
     */
    public static <T> void check(String tag, T value, T expected) {
        if(!withinTest) {
            System.out.print("---------------------------------------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------------------------");
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
        total++;
        try {
            if(value == expected || value.equals(expected)) {
                status = ConsoleColors.GREEN_BOLD_BRIGHT + "PASSED" + ConsoleColors.RESET;
                passed++;
            }
        } catch(Exception e) {}
        System.out.println(
                pad( tag, 50) + "| "
                + pad(value + "", 50) + "| "
                + pad(expected + " expected", 58) + "| "
                + status
        );
    }

    /**
     * Ends the test section with a new line
     */
    public static void endTest() {
        withinTest = false;
        System.out.println(passed + "/" + total + ConsoleColors.GREEN_BOLD_BRIGHT + " PASSED" + ConsoleColors.RESET +
                "; " + (total - passed) + "/" + total + ConsoleColors.RED_BOLD_BRIGHT + " FAILED" + ConsoleColors.RESET
        );
        System.out.println();
        total = 0;
        passed = 0;
    }

    private static String pad(String string, int length) {
        if(string.length() > length) {
            string = string.substring(0, length - 3) + "...";
        }
        return String.format("%-"+length+ "s", string);
    }

}

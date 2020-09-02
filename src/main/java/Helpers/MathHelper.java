package Helpers;

public class MathHelper {
    /**
     * Checks if dividend % divider == 0
     * (exists integer n => divider * n == dividend)
     * and check for division by zero
     *
     * @param dividend
     * Integer number to divide
     * @param divider
     * Integer number what will divide
     * @return
     * True if exists integer n => divider * n == dividend, false if not
     */
    public static boolean isDivisible(int dividend, int divider) {
        return getModulo(dividend, divider) == 0;
    }

    /**
     * Gets a modulo from division divident by divider
     *
     * @param dividend
     * Integer number to divide
     * @param divider
     * Integer number what will divide
     * @return
     * Integer m => divider * (some number n) +  == dividend, m in {0, divider - 1}
     */
    public static int getModulo(int dividend, int divider) {
        if (divider == 0) {
            throw new ArithmeticException("Divider should be not equal to zero!");
        }
        return dividend % divider;
    }

    /**
     * Checks if string can be parsed to integer number
     *
     * @param value
     * Value to parse
     * @return
     * True if string can be parsed, false if not
     */
    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


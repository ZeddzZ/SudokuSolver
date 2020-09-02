package Helpers;

public class MathHelper {
    public static boolean isDivisible(int dividend, int divider) {
        return getModulo(dividend, divider) == 0;
    }

    public static int getModulo(int dividend, int divider) {
        if (divider == 0) {
            throw new ArithmeticException("Divider should be not equal to zero!");
        }
        return dividend % divider;
    }

    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


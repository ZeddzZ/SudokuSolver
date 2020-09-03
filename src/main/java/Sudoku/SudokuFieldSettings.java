package Sudoku;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuFieldSettings<T> {

    //100 values from 1 to 100
    private static final List<Integer> intPossibleValues = IntStream.rangeClosed(1, (int)Math.pow(10, 2)).boxed().collect(Collectors.toList());
    //52 values from 'a' to 'z' and 'A' to 'Z'
    private static final List<Character> charPossibleValues = new ArrayList<>(Arrays.asList(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));
    //52 values from ""a" to "z" and "A" to "'Z""
    private static final List<String> stringPossibleValues = new ArrayList<>(Arrays.asList(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));
    private static final Integer intDefaultValue = 0;
    private static final Character charDefaultValue = '-';
    private static final String stringDefaultValue = "_";

    protected int width;
    protected int height;
    protected int blockWidth;
    protected int blockHeight;

    protected List<T> possibleValues;
    protected T defaultValue;
    protected Class<T> typeOfElements;


    public SudokuFieldSettings(int width, int height, int blockWidth, int blockHeight, List<T> possibleValues, T defaultValue, Class<T> typeOfElements) {
        this.width = width;
        this.height = height;
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
        this.defaultValue = defaultValue;
        this.possibleValues = possibleValues;
        this.typeOfElements = typeOfElements;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getBlockWidth() {
        return blockWidth;
    }

    public int getBlockHeight() {
        return blockHeight;
    }

    public List<T> getPossibleValues() {
        return possibleValues;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public Class<T> getTypeOfElements() {
        return typeOfElements;
    }

    @SuppressWarnings("unchecked")
    public static<T> List<T> getListOfDefaultValues(Class<T> clazz, int count) {
        if (clazz.equals(String.class)) {
            return (List<T>) stringPossibleValues.subList(0, count);
        } if (clazz.equals(Integer.class)) {
            return (List<T>) intPossibleValues.subList(0, count);
        } if (clazz.equals(Character.class)) {
            return (List<T>) charPossibleValues.subList(0, count);
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    public static<T> T getDefaultValue(Class<T> clazz) {
        if (clazz.equals(String.class)) {
            return (T) stringDefaultValue;
        } if (clazz.equals(Integer.class)) {
            return (T) intDefaultValue;
        } if (clazz.equals(Character.class)) {
            return (T) charDefaultValue;
        }
        return null;
    }


    public static<T> SudokuFieldSettings<T> createSettings(int width, int height, int blockWidth, int blockHeight, Class<T> typeOfElements) {
        return new SudokuFieldSettings<>(width, height, blockWidth, blockHeight,
                getListOfDefaultValues(typeOfElements, blockWidth * blockHeight), getDefaultValue(typeOfElements), typeOfElements);
    }

    public static final SudokuFieldSettings<Integer> DEFAULT_9_TO_9_INTEGER_SETTINGS
            = new SudokuFieldSettings<>(9, 9, 3, 3, intPossibleValues.subList(0, 9), intDefaultValue, Integer.class);

    public static final SudokuFieldSettings<String> DEFAULT_9_TO_9_STRING_SETTINGS
            = new SudokuFieldSettings<>(9, 9, 3, 3, stringPossibleValues.subList(0, 9), stringDefaultValue, String.class);

    public static final SudokuFieldSettings<Character> DEFAULT_4_TO_4_STRING_SETTINGS
            = new SudokuFieldSettings<>(4, 4, 2, 2, charPossibleValues.subList(0, 4), charDefaultValue, Character.class);

    public static final SudokuFieldSettings<String> DEFAULT_6_TO_6_STRING_SETTINGS
            = new SudokuFieldSettings<>(6, 6, 3, 2, stringPossibleValues.subList(0, 6), stringDefaultValue, String.class);


}

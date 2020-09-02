package Helpers;

import Exceptions.ExceptionMessage;
import Sudoku.SudokuField;
import Sudoku.SudokuFieldSettings;

import java.util.List;

public class SudokuFieldHelper {
    protected static Integer intDefaultValue = 0;
    protected static Character charDefaultValue = '-';

    /**
     * Parses list of lines to matrix of items
     *
     * @param fieldFromFile
     * List of lines to parse
     * @return
     * Matrix ofg items, split by space, comma or semicolon
     */
    public static String[][] parseFileToField(List<String> fieldFromFile) {
        String[][] field = new String[fieldFromFile.size()][];
        for (int i = 0; i < fieldFromFile.size(); i++) {
            String[] items = fieldFromFile.get(i).split("[ ,;]");
            field[i] = items;
        }
        return field;
    }

    /**
     * Parses matrix of strings to matrix of integers
     *
     * @param field
     * Matrix of strings to parse
     * @return
     * Parsed matrix of integers
     */
    public static Integer[][] parseStringToIntegerField(String[][] field) {
        Integer[][] intField = new Integer[field.length][];
        for (int i = 0; i < field.length; i++) {
            intField[i] = new Integer[field[i].length];
            for(int j = 0; j < field[i].length; j++) {
                if (!MathHelper.tryParseInt(field[i][j])) {
                    System.err.println(ExceptionMessage.getFailedToParseIntegerMessage(field[i][j], intDefaultValue));
                    intField[i][j] = intDefaultValue;
                } else {
                    intField[i][j] = Integer.parseInt(field[i][j]);
                }
            }
        }
        return intField;
    }

    /**
     * Parses matrix of strings to matrix of chars
     *
     * @param field
     * Matrix of strings to parse
     * @return
     * Parsed matrix of chars
     */
    public static Character[][] parseStringToCharacterField(String[][] field) {
        Character[][] charField = new Character[field.length][];
        for (int i = 0; i < field.length; i++) {
            charField[i] = new Character[field[i].length];
            for(int j = 0; j < field[i].length; j++) {
                if (field[i][j].length() != 1) {
                    System.err.println(ExceptionMessage.getFailedToParseIntegerMessage(field[i][j], charDefaultValue));
                    charField[i][j] = charDefaultValue;
                } else {
                    charField[i][j] = field[i][j].charAt(0);
                }
            }
        }
        return charField;
    }

    public static boolean isFieldValid(String[][] field) {
        for (String[] line: field) {
            for(String item: line) {
                if(!MathHelper.tryParseInt(item)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static<T> boolean isFieldValid(T[][] field, List<T> possibleValues) {
        for (T[] line: field) {
            for(T item: line) {
                if(!possibleValues.contains(item)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static SudokuField<Integer> getIntegerFieldFromFile(String path, SudokuFieldSettings<Integer> settings) {
        List<String> lines = FileHelper.readFromFile(path);
        String[][] items = parseFileToField(lines);
        Integer[][] cells = parseStringToIntegerField(items);
        return new SudokuField<>(settings, cells);
    }

    public static SudokuField<Integer> getIntegerFieldFromFile(String path) {
        return getIntegerFieldFromFile(path, SudokuFieldSettings.DEFAULT_9_TO_9_INTEGER_SETTINGS);
    }

    public static SudokuField<String> getFieldFromFile(String path, SudokuFieldSettings<String> settings) {
        List<String> lines = FileHelper.readFromFile(path);
        String[][] items = parseFileToField(lines);
        return new SudokuField<>(settings, items);
    }
    public static SudokuField<String> getFieldFromFile(String path) {
        return getFieldFromFile(path, SudokuFieldSettings.DEFAULT_9_TO_9_STRING_SETTINGS);
    }
}

package Helpers;

import Exceptions.ExceptionMessage;
import Sudoku.SudokuField;

import java.util.List;

public class SudokuFieldHelper {
    protected static int defaultValue = 0;

    public static String[][] parseFileToField(List<String> fieldFromFile) {
        String[][] field = new String[fieldFromFile.size()][];
        for (int i = 0; i < fieldFromFile.size(); i++) {
            String[] items = fieldFromFile.get(i).split("[ ,;]");
            field[i] = items;
        }
        return field;
    }

    public static int[][] parseStringField(String[][] field) {
        int[][] intField = new int[field.length][];
        for (int i = 0; i < field.length; i++) {
            intField[i] = new int[field[i].length];
            for(int j = 0; j < field[i].length; j++) {
                if (!MathHelper.tryParseInt(field[i][j])) {
                    System.err.println(ExceptionMessage.getFailedToParseIntegerMessage(field[i][j], defaultValue));
                    intField[i][j] = defaultValue;
                } else {
                    intField[i][j] = Integer.parseInt(field[i][j]);
                }
            }
        }
        return intField;
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

    public static boolean isFieldValid(int[][] field, List<Integer> possibleValues) {
        for (int[] line: field) {
            for(int item: line) {
                if(!possibleValues.contains(item)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static SudokuField getFieldFromFile(String path) {
        List<String> lines = FileHelper.readFromFile(path);
        String[][] items = parseFileToField(lines);
        int[][] cells = parseStringField(items);
        return new SudokuField(cells, PropertiesHelper.getBlockWidth(), PropertiesHelper.getBlockHeight());
    }
}

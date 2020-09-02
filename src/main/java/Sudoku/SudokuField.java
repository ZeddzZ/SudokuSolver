package Sudoku;

import Exceptions.ExceptionMessage;
import Exceptions.SudokuFieldInitializeException;
import Helpers.MathHelper;
import Helpers.Pair;
import Helpers.SudokuFieldHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuField {

    protected int width;
    protected int height;
    protected int blockWidth;
    protected int blockHeight;

    protected int[][] field;
    protected int defaultValue = 0;
    protected List<Integer> possibleValues = IntStream.rangeClosed(1, 9).boxed().collect(Collectors.toList());

    public SudokuField(int width, int height, int blockWidth, int blockHeight, boolean shouldInitValues) {
        if(isWidthValid(width, blockWidth) && isHeightValid(height, blockHeight)) {
            this.width = width;
            this.height = height;
            this.blockWidth = blockWidth;
            this.blockHeight = blockHeight;
            this.field = new int[this.height][this.width];
            if (shouldInitValues) {
                initField();
            }
        }
    }

    public SudokuField(int width, int height, int blockWidth, int blochHeight) {
        this(width, height, blockWidth, blochHeight, true);
    }

    public SudokuField(SudokuField sudokuField) {
        this(sudokuField.getWidth(), sudokuField.getHeight(), sudokuField.getBlockWidth(), sudokuField.getBlockHeight(), false);
        this.field = copyField(sudokuField.getField());
        this.possibleValues = new ArrayList<>(sudokuField.possibleValues);
    }

    public SudokuField(int[][] field, int blockWidth, int blockHeight) {
        this(field[0].length, field.length, blockWidth, blockHeight, false);
        if(SudokuFieldHelper.isFieldValid(field, possibleValues)) {
            throw new SudokuFieldInitializeException(ExceptionMessage.getInvalidFieldValuesMessage(possibleValues));
        }
        this.field = copyField(field);
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

    public List<Integer> getPossibleValues() {
        return possibleValues;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public int[][] getField() {
        return field;
    }

    public int getFieldValue(int x, int y) {
        return field[x][y];
    }

    public void setFieldValue(int x, int y, int value) {
        field[x][y] = value;
    }

    public int getFieldValue(Pair<Integer, Integer> coordinates) {
        return getFieldValue(coordinates.getFirst(), coordinates.getSecond());
    }

    public void setFieldValue(Pair<Integer, Integer> coordinates, int value) {
        setFieldValue(coordinates.getFirst(), coordinates.getSecond(), value);
    }

    public Pair<Integer, Integer> getBlockStart(int x, int y) {
        if (y >= height) {
            throw new IndexOutOfBoundsException();
        }
        if (x >= width) {
            throw new IndexOutOfBoundsException();
        }
        int xStart = (x / blockWidth) * blockWidth;
        int yStart = (y / blockHeight) * blockHeight;
        return new Pair<>(xStart, yStart);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int[] lines: field) {
            for(int item: lines) {
                sb.append(item);
                sb.append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SudokuField)) {
            return false;
        }
        SudokuField sudokuField = (SudokuField) o;
        boolean areEqual =
                width == sudokuField.width &&
                height == sudokuField.height &&
                blockWidth == sudokuField.blockWidth &&
                blockHeight == sudokuField.blockHeight;
        if (!areEqual) {
            return false;
        }
        boolean arrayEquals = true;
        for(int i = 0; i < field.length; i++) {
            arrayEquals &= Arrays.equals(field[i], sudokuField.getField()[i]);
        }
        arrayEquals &= possibleValues.equals(sudokuField.possibleValues);
        return  arrayEquals;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(width, height, blockWidth, blockHeight, possibleValues);
        for(int[] line: field) {
            result += 31 * result + Arrays.hashCode(line);
        }
        return result;
    }

    private void initField() {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                field[i][j] = defaultValue;
            }
        }
    }

    private boolean isWidthValid(int width, int blockWidth) {
        return isDimensionValid(width, blockWidth, "Width");
    }

    private boolean isHeightValid(int height, int blockHeight) {
        return isDimensionValid(height, blockHeight, "Height");
    }

    private boolean isDimensionValid(int dimension, int dimensionBlock, String dimensionName) {
        if(isDimensionPositive(dimension, dimensionBlock, dimensionName)
                && !MathHelper.isDivisible(dimension, dimensionBlock)) {
            throw new SudokuFieldInitializeException(
                    ExceptionMessage.getWrongLinearSizeMessage(dimensionName, dimension, dimensionBlock, MathHelper.getModulo(dimension, dimensionBlock)));
        }
        return true;
    }

    private boolean isDimensionPositive(int dimension, int dimensionBlock, String dimensionName) {
        if (dimension <= 0) {
            throw new SudokuFieldInitializeException(
                    ExceptionMessage.getWrongFieldSizeMessage(dimensionName, dimension)
            );
        }
        if (dimensionBlock <= 0) {
            throw new SudokuFieldInitializeException(
                    ExceptionMessage.getWrongFieldSizeMessage("Block " + dimensionName, dimensionBlock)
            );
        }
        return true;
    }

    private int[][] copyField(int[][] initialField) {
        int[][] newField = new int[initialField.length][initialField[0].length];
        for(int i = 0; i < initialField.length; i++) {
            for (int j = 0; j < initialField[i].length; j++) {
                newField[i][j] = initialField[i][j];
            }
        }
        return newField;
    }

}

package Sudoku;

import Exceptions.ExceptionMessage;
import Exceptions.SudokuFieldInitializeException;
import Helpers.MathHelper;
import Helpers.MatrixHelper;
import Helpers.Pair;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SudokuField<T> {

    protected int width;
    protected int height;
    protected int blockWidth;
    protected int blockHeight;

    protected T[][] field;
    protected T defaultValue;
    protected List<T> possibleValues;
    protected Class<T> typeOfElements;

    public SudokuField(int width, int height, int blockWidth, int blockHeight,  List<T> possibleValues, T defaultValue, Class<T> typeOfElements, T[][] field) {
        this.width = width;
        this.height = height;
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
        this.defaultValue = defaultValue;
        this.possibleValues = possibleValues;
        this.typeOfElements = typeOfElements;
        this.field = MatrixHelper.copyMatrix(field, this.typeOfElements);
    }

    public SudokuField(int width, int height, int blockWidth, int blockHeight,  List<T> possibleValues, T defaultValue, Class<T> typeOfElements) {
        this(width, height, blockWidth, blockHeight,
                possibleValues, defaultValue, typeOfElements,
                MatrixHelper.createMatrix(typeOfElements, width, height));
    }

    public SudokuField(SudokuFieldSettings<T> settings, T[][] field) {
        this(settings.getWidth(), settings.getHeight(), settings.getBlockWidth(), settings.getBlockHeight(),
                settings.getPossibleValues(), settings.getDefaultValue(), settings.getTypeOfElements(), field);
    }

    public SudokuField(SudokuFieldSettings<T> settings) {
        this(settings,
                MatrixHelper.createMatrix(settings.getTypeOfElements(), settings.getWidth(), settings.getHeight()));
    }

    @SuppressWarnings("CopyConstructorMissesField")
    public SudokuField(SudokuField<T> sudokuField) {
        this(sudokuField.getWidth(), sudokuField.getHeight(), sudokuField.getBlockWidth(), sudokuField.getBlockHeight(),
                sudokuField.getPossibleValues(), sudokuField.getDefaultValue(), sudokuField.getTypeOfElements(),
                sudokuField.getField());
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

    public T[][] getField() {
        return field;
    }

    public T getFieldValue(int x, int y) {
        return field[x][y];
    }

    public T getFieldValue(Pair<Integer, Integer> coordinates) {
        return getFieldValue(coordinates.getFirst(), coordinates.getSecond());
    }

    public void setFieldValue(int x, int y, T value) {
        field[x][y] = value;
    }

    public void setFieldValue(Pair<Integer, Integer> coordinates, T value) {
        setFieldValue(coordinates.getFirst(), coordinates.getSecond(), value);
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
        for(T[] lines: field) {
            for(T item: lines) {
                sb.append(item);
                sb.append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SudokuField)) {
            return false;
        }
        SudokuField<T> sudokuField = (SudokuField<T>) o;

        boolean linearSizesEqual =
                width == sudokuField.width &&
                height == sudokuField.height &&
                blockWidth == sudokuField.blockWidth &&
                blockHeight == sudokuField.blockHeight;
        if (!linearSizesEqual) {
            return false;
        }

        boolean fieldsEqual = MatrixHelper.areEqual(field, sudokuField.getField());
        if (!fieldsEqual) {
            return false;
        }

        return possibleValues.equals(sudokuField.possibleValues);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(width, height, blockWidth, blockHeight, possibleValues);
        for(T[] line: field) {
            result += 31 * result + Arrays.hashCode(line);
        }
        return result;
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
}

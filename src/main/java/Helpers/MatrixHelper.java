package Helpers;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MatrixHelper {
    /**
     * Creates Matrix of size rowsCount * columnsCount of specified type
     *
     * @param clazz
     * Class of T
     * @param rowsCount
     * count of rows in matrix
     * @param columnsCount
     * count of columns in matrix
     * @param <T>
     * Type of matrix items
     * @return
     * new Matrix [rowsCount][columnsCount] of type T
     */
    @SuppressWarnings("unchecked")
    public static<T> T[][] createMatrix(Class<T> clazz, int rowsCount, int columnsCount) {
        return (T[][]) Array.newInstance(clazz, rowsCount, columnsCount);
    }

    /**
     * Create a copy of received matrix
     *
     * @param initialMatrix
     * matrix to copy
     * @param clazz
     * class of T
     * @param <T>
     * type of elements in matrix
     * @return
     * new matrix which is a full copy of initial matrix
     */
    public static<T> T[][] copyMatrix(T[][] initialMatrix, Class<T> clazz) {
        T[][] newField = createMatrix(clazz, initialMatrix.length, initialMatrix[0].length);
        for(int i = 0; i < initialMatrix.length; i++) {
            System.arraycopy(initialMatrix[i], 0, newField[i], 0, initialMatrix[i].length);
        }
        return newField;
    }

    /**
     * Checks whether two matrices are equal or not
     *
     * @param matrixOne
     * First matrix to check
     * @param matrixTwo
     * Second matrix to check
     * @param <T>
     * Type of elements in matrices
     * @return
     * True if matrices content are equal, false if not
     */
    public static<T> boolean areEqual(T[][] matrixOne, T[][] matrixTwo) {
        if (matrixOne.length != matrixTwo.length) {
            return false;
        }
        for(int i = 0; i < matrixOne.length; i++) {
            if (matrixOne[i].length != matrixTwo[i].length) {
                return false;
            }
            for(int j = 0; j < matrixOne[i].length; j++) {
                if(!matrixOne[i][j].equals(matrixTwo[i][j])) {
                    return  false;
                }
            }
        }
        return true;
    }

    /**
     * Initializes matrix with default value
     *
     * @param matrix
     * Matrix to initialize
     * @param value
     * Default value
     * @param <T>
     * Type of elements in matrix
     */
    public static<T> void initMatrix(T[][] matrix, T value) {
        for (T[] array : matrix) {
            Arrays.fill(array, value);
        }
    }
}

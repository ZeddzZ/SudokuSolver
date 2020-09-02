package Sudoku;

import java.util.*;

public class SudokuSolutions {
    protected static Map<SudokuField, Set<SudokuField>> sudokuSolutions;

    static {
        sudokuSolutions = new HashMap<>();
    }

    /**
     * Add new solution to map of solutions where key is Initial field and value is set  of solutions
     *
     * @param initialField
     * Initial state of the field that will be stored as a key
     * @param solution
     * New solution to add
     * @return
     * {@code true} if this set did not already contain the specified element
     */
    public static boolean addSolution(SudokuField initialField, SudokuField solution) {
        /*if(!isKeyExists(initialField)) {
            sudokuSolutions.put(initialField, new HashSet<>());
        }
        return addToSet(sudokuSolutions.get(initialField), solution);*/

        if(!sudokuSolutions.containsKey(initialField)) {
            sudokuSolutions.put(initialField, new HashSet<>());
        }
        return sudokuSolutions.get(initialField).add(solution);
    }

    /**
     * Get a list of solutions for specified field (key for solutions map)
     *
     * @param initialField
     * Initial state of the field which is used as a key to hold solutions
     * @return
     * Set of all possible solutions, empty set if no solutions exist
     */
    public static Set<SudokuField> getSolutions(SudokuField initialField) {
        if(!sudokuSolutions.containsKey(initialField)) {
            return new HashSet<>();
        }
        return sudokuSolutions.get(initialField);
    }

    /**
     * Prints list of solutions for specified field (key for solutions map)
     *
     * @param initialField
     *      * Initial state of the field which is used as a key to hold solutions
     */
    public static void printSolutions(SudokuField initialField) {
        Set<SudokuField> solutions = getSolutions(initialField);
        if(solutions.size() == 0) {
            System.out.println("No solutions were found.");
            return;
        } else if (solutions.size() == 1) {
            System.out.println("Only 1 solution was found. Solution is:");
        } else {
            System.out.println(solutions.size()  + " solution were found. Solution are:");
        }
        System.out.println();
        for(SudokuField sf: solutions) {
            System.out.println(sf.toString());
        }
        System.out.println();
    }

    /**
     * Get count of solutions for specified field (key for solutions map)
     *
     * @param initialField
     *      * Initial state of the field which is used as a key to hold solutions
     * @return
     * count of solutions for specified field
     */
    public static int getSolutionsCount(SudokuField initialField) {
        return getSolutions(initialField).size();
    }

    /*private static boolean isKeyExists(SudokuField key) {
        for(SudokuField sf: sudokuSolutions.keySet()) {
            if (sf.equals(key)) {
                return true;
            }
        }
        return false;
    }*/

    /*private static<T> boolean addToSet(Set<T> set, T value) {
        for (T key: set) {
            if(key.equals(value)) {
                return false;
            }
        }
        return set.add(value);
    }*/
}

package Sudoku;

import Helpers.Pair;

import java.util.*;

public class SudokuSolutions {
    protected static Map<SudokuField, Set<SudokuField>> sudokuSolutions;

    static {
        sudokuSolutions = new HashMap<>();
    }

    public static boolean addSolution(SudokuField initialField, SudokuField solution) {
        if(!isKeyExists(initialField)) {
            sudokuSolutions.put(initialField, new HashSet<>());
        }
        return addToSet(sudokuSolutions.get(initialField), solution);
    }

    public static Set<SudokuField> getSolutions(SudokuField initialField) {
        if(!sudokuSolutions.containsKey(initialField)) {
            return new HashSet<>();
        }
        return sudokuSolutions.get(initialField);
    }

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

    public static int getSolutionsCount(SudokuField initialField) {
        return getSolutions(initialField).size();
    }

    private static boolean isKeyExists(SudokuField key) {
        for(SudokuField sf: sudokuSolutions.keySet()) {
            if (sf.equals(key)) {
                return true;
            }
        }
        return false;
    }

    private static<T> boolean addToSet(Set<T> set, T value) {
        for (T key: set) {
            if(key.equals(value)) {
                return false;
            }
        }
        return set.add(value);
    }
}

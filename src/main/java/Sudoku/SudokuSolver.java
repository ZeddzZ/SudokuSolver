package Sudoku;

import Helpers.Pair;
import Helpers.PropertiesHelper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class SudokuSolver {

    public static<T> Set<SudokuField<T>> solve(SudokuField<T> solutionField) {
        return solve(solutionField, PropertiesHelper.getDefaultSolutionsCount());
    }

    public static<T> Set<SudokuField<T>> solve(SudokuField<T> solutionField, int maxSolutionsCount) {
        Set<SudokuField<T>> solutions = new CopyOnWriteArraySet<>();
        solve(solutionField, getEmptyCells(solutionField), solutions, maxSolutionsCount);
        return solutions;
    }

    public static<T> String printSolutions(Set<SudokuField<T>> solutions) {
        StringBuilder sb = new StringBuilder();
        if(solutions.size() == 0) {
            sb.append("No solutions were found.").append(System.lineSeparator());
            return sb.toString();
        } else if (solutions.size() == 1) {
            sb.append(("Only 1 solution was found. Solution is:")).append(System.lineSeparator());
        } else {
            sb.append(solutions.size()).append(" solution were found. Solution are:").append(System.lineSeparator());
        }
        System.out.println();
        for(SudokuField<T> sf: solutions) {
            sb.append(sf.toString()).append(System.lineSeparator());
        }
        return sb.append(System.lineSeparator()).toString();
    }

    private static<T> void solve(SudokuField<T> currentField, Set<Pair<Integer, Integer>> emptyCells, Set<SudokuField<T>> solutions, int maxSolutionsCount) {
        do {
            if(solutions.size() >= maxSolutionsCount) {
                //Desired solutions count achieved, returning
                return;
            }
            if(emptyCells.size() == 0) {
                //System.out.println("Solution found!");
                solutions.add(currentField);
                return;
            }
            Map<Pair<Integer, Integer>, List<T>> possibleSolutions = addAllPossibleSolutions(currentField, emptyCells);
            if(possibleSolutions.size() == 0) {
                //dead end, no solutions
                return;
            }
            Map<Pair<Integer, Integer>, List<T>> singleSolutions =
                    getSingleSolutions(possibleSolutions);
            if (singleSolutions.size() > 0) {
                unambiguousMatch(currentField, singleSolutions, emptyCells);
            } else {
                ambiguousMatch(currentField, possibleSolutions, emptyCells, solutions, maxSolutionsCount);
                return;
            }
        }while(true);
    }

    /**
     * For all solutions that contain only one possible value go through specified cells and
     * update solution field with this value, removing this cell from possibleSolutions Map and emptyCells List
     *
     * @param singleSolutions
     * Map of cells that can be filled with only one value at this iteration.
     */
    private static<T> void unambiguousMatch(SudokuField<T> currentField, Map<Pair<Integer, Integer>, List<T>> singleSolutions, Set<Pair<Integer, Integer>> emptyCells) {
        singleSolutions.keySet().parallelStream().forEach(coordinates -> {
            currentField.setFieldValue(coordinates, singleSolutions.get(coordinates).get(0));
            emptyCells.remove(coordinates);
        });
    }

    /**
     * From list of all cells with possible values count > 1
     * We take cell with min possible values count.
     * For each of such solutions we create new {@see SudkuSolver}
     * with possible value and run {@see SudokuSolver#solve()} method for it
     *
     * e.g. : if cell [0,0] have possible solutions [1,2,3] we create 3 new solutions
     * with already placed 1, 2 and 3 value to [0,0] position and run solve for them
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static<T> void ambiguousMatch(SudokuField<T> currentField, Map<Pair<Integer, Integer>, List<T>> possibleSolutions, Set<Pair<Integer, Integer>> emptyCells, Set<SudokuField<T>> solutions, int maxSolutionsCount) {
        if (getEmptySolutions(possibleSolutions).size() > 0) {
            //Failed to solve, dead end
            return;
        }

        Map.Entry<Pair<Integer, Integer>, List<T>> solution =
                possibleSolutions.entrySet()
                .stream()
                .min(Comparator.comparing(el -> el.getValue().size()))
                .get();

       solution.getValue().parallelStream().forEach(possibleValue ->
                initNewSolve(currentField, solution.getKey(), possibleValue, emptyCells, solutions, maxSolutionsCount));
    }



    /**
     * Checks if it possible for specified value to be placed on received coordinates
     *
     * @param rowNumber
     * Number of row of the cell (zero based)
     * @param columnNumber
     * Number of column of the cell (zero based)
     * @param value
     * Value to check
     * @return
     * True if it possible to place specified value for given coordinates, false if not
     */
    private static<T> boolean isPossibleToPlaceValue(SudokuField<T> solutionField, int rowNumber, int columnNumber, T value) {
        return isVerticalPossible(solutionField, columnNumber, value)
                && isHorizontalPossible(solutionField, rowNumber, value)
                && isBlockPossible(solutionField, solutionField.getBlockStart(rowNumber, columnNumber), value);
    }

    /**
     * Checks if it possible for specified value to be placed on given column
     *
     * @param column
     * Number of column of the cell (zero based)
     * @param value
     * Value to check
     * @return
     * True if it possible to place specified value for given column, false if not
     */
    private static<T> boolean isVerticalPossible(SudokuField<T> solutionField, int column, T value) {
        for(int i = 0; i < solutionField.getHeight(); i++) {
            if (solutionField.getFieldValue(i, column).equals(value)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Checks if it possible for specified value to be placed on given row
     *
     * @param row
     * Number of row of the cell (zero based)
     * @param value
     * Value to check
     * @return
     * True if it possible to place specified value for given row, false if not
     */
    private static<T> boolean isHorizontalPossible(SudokuField<T> solutionField, int row, T value) {
        for(int j = 0; j < solutionField.getWidth(); j++) {
            if (solutionField.getFieldValue(row, j).equals(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if it possible for specified value to be placed on given block
     *
     * @param blockStart
     * Coordinates of block top-left value
     * @param value
     * Value to check
     * @return
     * True if it possible to place specified value for given block, false if not
     */
    private static<T> boolean isBlockPossible(SudokuField<T> solutionField, Pair<Integer, Integer> blockStart, T value) {
        for(int i = blockStart.getFirst(); i < blockStart.getFirst() + solutionField.getBlockHeight(); i++) {
            for(int j = blockStart.getSecond(); j < blockStart.getSecond() + solutionField.getBlockWidth(); j++) {
                if (solutionField.getFieldValue(i, j).equals(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Goes through all field and add all cell coordinates with default value to emptyCells List
     */
    private static<T> Set<Pair<Integer, Integer>> getEmptyCells(SudokuField<T> solutionField) {
        Set<Pair<Integer, Integer>> emptyCells = new CopyOnWriteArraySet<>();
        for(int i =0; i < solutionField.getHeight(); i++) {
            for(int j = 0; j < solutionField.getWidth(); j++) {
                if (solutionField.getFieldValue(i, j) == (solutionField.getDefaultValue())) {
                    emptyCells.add(new Pair<>(i, j));
                }
            }
        }
        return emptyCells;
    }

    /**
     * Goes through all empty cells and add all possible values
     * for current cell to possibleSolutions List
     */
    private static<T> Map<Pair<Integer, Integer>, List<T>> addAllPossibleSolutions(SudokuField<T> solutionField, Set<Pair<Integer, Integer>> emptyCells) {
        Map<Pair<Integer, Integer>, List<T>> possibleSolutions = new ConcurrentHashMap<>();
        emptyCells.parallelStream().forEach(cell -> solutionField.getPossibleValues().stream()
                .filter(value -> isPossibleToPlaceValue(solutionField, cell.getFirst(), cell.getSecond(), value))
                .forEach(value -> addPossibleSolution(possibleSolutions, cell, value)));
        return possibleSolutions;
    }

    /**
     * Adds a possible value for specified cell to possibleSolutions Map
     *
     * @param possibleSolutions
     * Map where to add elements
     * @param coordinates
     * Coordinates of the cell
     * @param value
     * Value that can be placed
     * @param <T>
     * Type of elements
     * @return
     * True if successfully added new possible solution, false if it already exists
     */
    private static<T> boolean addPossibleSolution(Map<Pair<Integer, Integer>, List<T>> possibleSolutions, Pair<Integer, Integer> coordinates, T value) {
        if (!possibleSolutions.containsKey(coordinates)) {
            possibleSolutions.put(coordinates, new ArrayList<>());
        }
        return possibleSolutions.get(coordinates).add(value);
    }

    /**
     * If there are only cells with > 1 possible solutions,
     * we go in cycle of possible values and for each possible value
     * create new field and initiate new solve run for it
     *
     * This method created to ease work with forEach(...) and  parallelStream()
     *
     * @param currentField
     * Current state of sudoku field
     * @param cell
     * Cell coordinates which has multiple possible solutions
     * @param possibleValue
     * possible solution that we are working with
     * @param emptyCells
     * List of empty cells
     * @param solutions
     * List of solutions already found for initial field
     * @param maxSolutionsCount
     * max count of solutions that we will process
     * @param <T>
     * Type of cell value
     */
    private static<T> void initNewSolve(SudokuField<T> currentField, Pair<Integer, Integer> cell, T possibleValue, Set<Pair<Integer, Integer>> emptyCells, Set<SudokuField<T>> solutions, int maxSolutionsCount) {
        SudokuField<T> newField = new SudokuField<>(currentField);
        newField.setFieldValue(cell, possibleValue);

        Set<Pair<Integer, Integer>> newEmptyCells = new CopyOnWriteArraySet<>(emptyCells);
        newEmptyCells.remove(cell);

        solve(newField, newEmptyCells, solutions, maxSolutionsCount);
    }

    private static<T> Map<Pair<Integer, Integer>, List<T>> getSingleSolutions(Map<Pair<Integer, Integer>, List<T>> possibleSolutions) {
        return getSolutionsWithSpecifiedCount(possibleSolutions, 1);
    }

    private static<T> Map<Pair<Integer, Integer>, List<T>> getEmptySolutions(Map<Pair<Integer, Integer>, List<T>> possibleSolutions) {
        return getSolutionsWithSpecifiedCount(possibleSolutions, 0);
    }

    private static<T> Map<Pair<Integer, Integer>, List<T>> getSolutionsWithSpecifiedCount(Map<Pair<Integer, Integer>, List<T>> possibleSolutions, int count) {
        return possibleSolutions.entrySet()
                .stream()
                .filter(el -> el.getValue().size() == count)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

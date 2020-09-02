package Sudoku;

import Helpers.MapHelper;
import Helpers.Pair;

import java.util.*;

public class SudokuSolver {

    protected final SudokuField initialField;
    protected SudokuField solutionField;
    protected Set<Pair<Integer, Integer>> emptyCells;
    protected Map<Pair<Integer, Integer>, List<Integer>> possibleSolutions;

    public SudokuSolver(SudokuField sudokuField) {
        this.initialField = new SudokuField(sudokuField);
        this.solutionField = new SudokuField(sudokuField);
        emptyCells = new HashSet<>();
        possibleSolutions = new HashMap<>();

        populateEmptyCells();
    }

    /*public SudokuSolver(SudokuField sudokuField, Set<Pair<Integer, Integer>> emptyCells) {
        this.initialField = new SudokuField(sudokuField);
        this.emptyCells = new HashSet<>(emptyCells);
        possibleSolutions = new HashMap<>();
    }*/

    public SudokuSolver(SudokuField sudokuField, SudokuField solutionField, Set<Pair<Integer, Integer>> emptyCells) {
        this.initialField = new SudokuField(sudokuField);
        this.solutionField = new SudokuField(solutionField);
        this.emptyCells = new HashSet<>(emptyCells);
        possibleSolutions = new HashMap<>();
    }

    public SudokuField getInitialField() {
        return initialField;
    }

    public SudokuField getSolutionField() {
        return solutionField;
    }

    /**
     * Sets for solution field cell with given coordinates specified value
     *
     * @param coordinates
     * Coordinates of cell to update
     * @param value
     * New value to put
     */
    private void setFieldValue(Pair<Integer, Integer> coordinates, int value) {
        solutionField.setFieldValue(coordinates, value);
    }

    public void solve() {
        do {
            if(emptyCells.size() == 0) {
                //System.out.println("Solution found!");
                SudokuSolutions.addSolution(initialField, solutionField);
                return;
            }
            addAllPossibleSolutions();
            if(possibleSolutions.size() == 0) {
                //dead end, no solutions
                return;
            }
            Map<Pair<Integer, Integer>, List<Integer>> singleSolutions =
                    MapHelper.filter(possibleSolutions, el -> el.getValue().size() == 1);
            if (singleSolutions.size() > 0) {
                unambiguousMatch(singleSolutions);
                clearPossibleSolutions();
            } else {
                ambiguousMatch();
                return;
            }
        }while(true);
    }

    /**
     * Goes through all empty cells and add all possible values
     * fpr current cell to possibleSolutions List
     */
    private void addAllPossibleSolutions() {
        for (Pair<Integer, Integer> cell : emptyCells) {
            for (int value : solutionField.getPossibleValues()) {
                if (isPossibleToPlaceNumber(cell.getFirst(), cell.getSecond(), value)) {
                    addPossibleSolution(cell, value);
                }
            }
        }
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
    private boolean isPossibleToPlaceNumber(int rowNumber, int columnNumber, int value) {
        return isVerticalPossible(columnNumber, value)
                && isHorizontalPossible(rowNumber, value)
                && isBlockPossible(solutionField.getBlockStart(rowNumber, columnNumber), value);
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
    private boolean isVerticalPossible(int column, int value) {
        for (int i = 0; i < solutionField.getHeight(); i++) {
            if (solutionField.getFieldValue(i, column) == value) {
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
    private boolean isHorizontalPossible(int row, int value) {
        for (int j = 0; j < solutionField.getWidth(); j++) {
            if (solutionField.getFieldValue(row, j) == value) {
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
    private boolean isBlockPossible(Pair<Integer, Integer> blockStart, int value) {
        for (int i = blockStart.getFirst(); i < blockStart.getFirst() + solutionField.getBlockHeight(); i++) {
            for (int j = blockStart.getSecond(); j < blockStart.getSecond() + solutionField.getBlockWidth(); j++) {
                if (solutionField.getFieldValue(i, j) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Goes through all field and add all cell coordinates with default value to emptyCells List
     */
    private void populateEmptyCells() {
        for (int i =0; i < solutionField.getHeight(); i++) {
            for (int j = 0; j < solutionField.getWidth(); j++) {
                if (solutionField.getFieldValue(i, j) == (solutionField.getDefaultValue())) {
                    emptyCells.add(new Pair<>(i, j));
                }
            }
        }
    }

    /**
     * Adds a possible value for specified cell to possibleSolutions Map
     *
     * @param coordinates
     * Coordinates of the cell
     * @param value
     * Value that can be placed
     */
    private void addPossibleSolution(Pair<Integer, Integer> coordinates, int value) {
        if (!possibleSolutions.containsKey(coordinates)) {
            possibleSolutions.put(coordinates, new ArrayList<>());
        }
        possibleSolutions.get(coordinates).add(value);
    }

    /**
     * For all solutions that contain only one possible value go through specified cells and
     * update solution field with this value, removing this cell from possibleSolutions Map and emptyCells List
     *
     * @param singleSolutions
     * Map of cells that can be filled with only one value at this iteration.
     */
    private void unambiguousMatch(Map<Pair<Integer, Integer>, List<Integer>> singleSolutions) {
        for(Pair<Integer, Integer> coordinates: singleSolutions.keySet()) {
                setFieldValue(coordinates, possibleSolutions.get(coordinates).get(0));
                possibleSolutions.remove(coordinates);
                emptyCells.remove(coordinates);
        }
    }

    /**
     * Removes all data from possible  solutions map
     */
    private void clearPossibleSolutions() {
        for(Pair<Integer, Integer> coordinates: possibleSolutions.keySet()) {
            possibleSolutions.get(coordinates).clear();
        }
        //TODO: is it possible to replace with this line?
        //possibleSolutions.clear();
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
    private void ambiguousMatch() {
        if (MapHelper.filter(possibleSolutions, el -> el.getValue().size() == 0).size() > 0) {
            //Failed to solve, dead end
            return;
            }
        Map.Entry<Pair<Integer, Integer>, List<Integer>> solution =
                MapHelper.min(possibleSolutions, Comparator.comparing(v -> v.getValue().size()));
        for(int possibleValue: solution.getValue()) {
            SudokuSolver tempSolution = new SudokuSolver(initialField, solutionField, emptyCells);
            tempSolution.setFieldValue(solution.getKey(), possibleValue);
            emptyCells.remove(solution.getKey());
            tempSolution.emptyCells.remove(solution.getKey());
            tempSolution.solve();
        }
    }
}

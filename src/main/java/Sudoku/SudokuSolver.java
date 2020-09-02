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

    private void addAllPossibleSolutions() {
        for (Pair<Integer, Integer> cell : emptyCells) {
            for (int value : solutionField.getPossibleValues()) {
                if (isPossibleToPlaceNumber(cell.getFirst(), cell.getSecond(), value)) {
                    addPossibleSolution(cell, value);
                }
            }
        }
    }

    private boolean isPossibleToPlaceNumber(int rowNumber, int columnNumber, int number) {
        return isVerticalPossible(columnNumber, number)
                && isHorizontalPossible(rowNumber, number)
                && isBlockPossible(solutionField.getBlockStart(rowNumber, columnNumber), number);
    }

    private boolean isVerticalPossible(int column, int number) {
        for (int i = 0; i < solutionField.getHeight(); i++) {
            if (solutionField.getFieldValue(i, column) == number) {
                return false;
            }
        }
        return true;
    }

    private boolean isHorizontalPossible(int row, int number) {
        for (int j = 0; j < solutionField.getWidth(); j++) {
            if (solutionField.getFieldValue(row, j) == number) {
                return false;
            }
        }
        return true;
    }

    private boolean isBlockPossible(Pair<Integer, Integer> blockStart, int number) {
        for (int i = blockStart.getFirst(); i < blockStart.getFirst() + solutionField.getBlockHeight(); i++) {
            for (int j = blockStart.getSecond(); j < blockStart.getSecond() + solutionField.getBlockWidth(); j++) {
                if (solutionField.getFieldValue(i, j) == number) {
                    return false;
                }
            }
        }
        return true;
    }

    private void populateEmptyCells() {
        for (int i =0; i < solutionField.getHeight(); i++) {
            for (int j = 0; j < solutionField.getWidth(); j++) {
                if (solutionField.getFieldValue(i, j) == solutionField.getDefaultValue()) {
                    emptyCells.add(new Pair<>(i, j));
                }
            }
        }
    }

    private void addPossibleSolution(Pair<Integer, Integer> coordinates, int value) {
        if (!possibleSolutions.containsKey(coordinates)) {
            possibleSolutions.put(coordinates, new ArrayList<>());
        }
        possibleSolutions.get(coordinates).add(value);
    }

    private void unambiguousMatch(Map<Pair<Integer, Integer>, List<Integer>> singleSolutions) {
        for(Pair<Integer, Integer> coordinates: singleSolutions.keySet()) {
                setFieldValue(coordinates, possibleSolutions.get(coordinates).get(0));
                possibleSolutions.remove(coordinates);
                emptyCells.remove(coordinates);
        }
    }

    private void clearPossibleSolutions() {
        for(Pair<Integer, Integer> coordinates: possibleSolutions.keySet()) {
            possibleSolutions.get(coordinates).clear();
        }
    }

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

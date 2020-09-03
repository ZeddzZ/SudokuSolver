package SudokuTests;

import Helpers.PropertiesHelper;
import Sudoku.SudokuField;
import Sudoku.SudokuFieldSettings;
import Sudoku.SudokuSolver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class SudokuSolverTests {

    public Integer[][] field = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
    };
    public SudokuField<Integer> sf;
    @Before
    public void init() throws IOException {
        PropertiesHelper.initProperties();
        sf = new SudokuField<>(SudokuFieldSettings.DEFAULT_9_TO_9_INTEGER_SETTINGS, field);
    }

        @Test
        public void MyTest() {
            SudokuSolver.printSolutions(SudokuSolver.solve(sf));
        }
}

package SudokuTests;

import Helpers.PropertiesHelper;
import Sudoku.SudokuField;
import Sudoku.SudokuSolutions;
import Sudoku.SudokuSolver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class SudokuSolverTests {

    public int[][] field = {
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
    public SudokuField sf;
    public SudokuSolver ss;
    @Before
    public void init() throws IOException {
        PropertiesHelper.initProperties();
        sf = new SudokuField(field, PropertiesHelper.getBlockWidth(), PropertiesHelper.getBlockHeight());
        ss = new SudokuSolver(sf);
    }

        @Test
        public void MyTest() {
            ss.solve();
            System.out.println(SudokuSolutions.getSolutions(ss.getInitialField()).toString());
        }
}

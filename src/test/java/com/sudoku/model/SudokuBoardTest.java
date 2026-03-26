// Author: Nawaphon Isarathanachaikul, 06/19/2017
// Refactored by: GitHub Copilot, 03/25/2026
package com.sudoku.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {

    private static int[][] validSolution() {
        return new int[9][9];
    }

    private static boolean[][] validRevealed() {
        return new boolean[9][9];
    }

    @Test
    void constructorAcceptsValidNineByNineArrays() {
        assertDoesNotThrow(() -> new SudokuBoard(validSolution(), validRevealed()));
    }

    @Test
    void constructorThrowsWhenSolutionHasTooFewRows() {
        assertThrows(IllegalArgumentException.class,
                () -> new SudokuBoard(new int[8][9], validRevealed()));
    }

    @Test
    void constructorThrowsWhenSolutionHasTooManyRows() {
        assertThrows(IllegalArgumentException.class,
                () -> new SudokuBoard(new int[10][9], validRevealed()));
    }

    @Test
    void constructorThrowsWhenRevealedHasTooFewRows() {
        assertThrows(IllegalArgumentException.class,
                () -> new SudokuBoard(validSolution(), new boolean[8][9]));
    }

    @Test
    void constructorThrowsWhenSolutionRowHasTooFewColumns() {
        final int[][] solution = validSolution();
        solution[0] = new int[8];
        assertThrows(IllegalArgumentException.class,
                () -> new SudokuBoard(solution, validRevealed()));
    }

    @Test
    void constructorThrowsWhenRevealedRowHasTooFewColumns() {
        final boolean[][] revealed = validRevealed();
        revealed[0] = new boolean[8];
        assertThrows(IllegalArgumentException.class,
                () -> new SudokuBoard(validSolution(), revealed));
    }
}

package com.sudoku.game;

import com.sudoku.model.SudokuBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuGameTest {

    private static final int[][] SOLUTION = buildSolution();
    private static final boolean[][] ALL_HIDDEN = new boolean[9][9];

    private SudokuGame game;

    @BeforeEach
    void setUp() {
        final SudokuBoard board = new SudokuBoard(SOLUTION, ALL_HIDDEN);
        game = new SudokuGame(board, DifficultyLevel.NORMAL);
    }

    @Test
    void newGameIsNeitherCompletedNorFailed() {
        assertFalse(game.isCompleted());
        assertFalse(game.isFailed());
    }

    @Test
    void correctGuessMakesProgressAndReturnTrue() {
        assertTrue(game.guess(0, 0, SOLUTION[0][0]));
        assertFalse(game.isFailed());
        assertFalse(game.isCompleted());
    }

    @Test
    void wrongGuessFailsGameAndReturnFalse() {
        final int wrongValue = (SOLUTION[0][0] % 9) + 1;
        assertFalse(game.guess(0, 0, wrongValue));
        assertTrue(game.isFailed());
    }

    @Test
    void guessOnFailedGameReturnsFalse() {
        final int wrongValue = (SOLUTION[0][0] % 9) + 1;
        game.guess(0, 0, wrongValue);
        assertFalse(game.guess(0, 1, SOLUTION[0][1]));
    }

    @Test
    void fillingAllCellsCompletesGame() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                game.guess(row, col, SOLUTION[row][col]);
            }
        }
        assertTrue(game.isCompleted());
    }

    @Test
    void calculateFinalScoreIsZeroWhenNotCompleted() {
        assertEquals(0, game.calculateFinalScore());
    }

    @Test
    void calculateFinalScoreIsPositiveAfterCompletion() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                game.guess(row, col, SOLUTION[row][col]);
            }
        }
        assertTrue(game.calculateFinalScore() >= 0);
    }

    @Test
    void elapsedTimeStringIsNotEmpty() {
        assertFalse(game.getElapsedTimeString().isEmpty());
    }

    @Test
    void getDifficultyReturnsInitialDifficulty() {
        assertEquals(DifficultyLevel.NORMAL, game.getDifficulty());
    }

    @Test
    void guessingRevealedCellReturnsFalse() {
        final boolean[][] preRevealed = new boolean[9][9];
        preRevealed[0][0] = true;
        final SudokuBoard board = new SudokuBoard(SOLUTION, preRevealed);
        final SudokuGame gameWithRevealed = new SudokuGame(board, DifficultyLevel.EASY);
        assertFalse(gameWithRevealed.guess(0, 0, SOLUTION[0][0]));
    }

    private static int[][] buildSolution() {
        return new int[][]{
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };
    }
}

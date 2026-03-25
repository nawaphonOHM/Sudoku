// Author: Nawaphon Isarathanachaikul, 06/19/2017
// Refactored by: GitHub Copilot, 03/25/2026
package com.sudoku.game;

import com.sudoku.model.SudokuBoard;

public final class SudokuGame {

    private static final int TOTAL_CELLS = 81;

    private final SudokuBoard board;
    private final DifficultyLevel difficulty;
    private final long startTimeMillis;
    private int points;
    private boolean failed;
    private boolean completed;

    public SudokuGame(final SudokuBoard board, final DifficultyLevel difficulty) {
        this.board = board;
        this.difficulty = difficulty;
        this.startTimeMillis = System.currentTimeMillis();
        this.points = 0;
        this.failed = false;
        this.completed = false;
    }

    public boolean guess(final int row, final int col, final int value) {
        if (failed || completed || board.isRevealed(row, col)) {
            return false;
        }
        if (board.getSolution(row, col) == value) {
            board.reveal(row, col);
            points += difficulty.getPointsPerCell();
            if (board.isAllRevealed()) {
                completed = true;
            }
            return true;
        }
        failed = true;
        return false;
    }

    public int calculateFinalScore() {
        if (!completed) {
            return 0;
        }
        final long elapsedMillis = System.currentTimeMillis() - startTimeMillis;
        final int elapsedMinutes = (int) (elapsedMillis / 60_000);
        final int adjustedPoints = Math.max(0, points - elapsedMinutes);
        final int hiddenCells = TOTAL_CELLS - difficulty.getVisibleCells();
        return (100 * adjustedPoints) / hiddenCells;
    }

    public String getElapsedTimeString() {
        final long elapsedMillis = System.currentTimeMillis() - startTimeMillis;
        final int totalSeconds = (int) (elapsedMillis / 1000);
        final int seconds = totalSeconds % 60;
        final int totalMinutes = totalSeconds / 60;
        final int minutes = totalMinutes % 60;
        final int hours = totalMinutes / 60;
        final StringBuilder sb = new StringBuilder();
        if (hours > 0) {
            sb.append(hours).append(" hour(s) ");
        }
        if (minutes > 0) {
            sb.append(minutes).append(" minute(s) ");
        }
        sb.append(seconds).append(" second(s)");
        return sb.toString().trim();
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isFailed() {
        return failed;
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }
}

package com.sudoku.model;

public final class SudokuBoard {

    private static final int SIZE = 9;

    private final int[][] solution;
    private final boolean[][] initiallyRevealed;
    private final boolean[][] revealed;

    public SudokuBoard(final int[][] solution, final boolean[][] initiallyRevealed) {
        this.solution = deepCopy(solution);
        this.initiallyRevealed = deepCopy(initiallyRevealed);
        this.revealed = deepCopy(initiallyRevealed);
    }

    public int getSolution(final int row, final int col) {
        return solution[row][col];
    }

    public boolean isInitiallyRevealed(final int row, final int col) {
        return initiallyRevealed[row][col];
    }

    public boolean isRevealed(final int row, final int col) {
        return revealed[row][col];
    }

    public void reveal(final int row, final int col) {
        revealed[row][col] = true;
    }

    public boolean isAllRevealed() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (!revealed[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int[][] deepCopy(final int[][] array) {
        final int[][] copy = new int[array.length][];
        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i].clone();
        }
        return copy;
    }

    private static boolean[][] deepCopy(final boolean[][] array) {
        final boolean[][] copy = new boolean[array.length][];
        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i].clone();
        }
        return copy;
    }
}

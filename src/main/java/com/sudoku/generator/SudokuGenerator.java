// Author: Nawaphon Isarathanachaikul, 06/19/2017
// Refactored by: GitHub Copilot, 03/25/2026
package com.sudoku.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class SudokuGenerator implements PuzzleGenerator {

    private static final int SIZE = 9;
    private static final int BOX_SIZE = 3;

    private final Random random;

    public SudokuGenerator(final Random random) {
        this.random = random;
    }

    @Override
    public int[][] generate() {
        final int[][] grid = new int[SIZE][SIZE];
        solve(grid);
        return grid;
    }

    private boolean solve(final int[][] grid) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid[row][col] == 0) {
                    for (final int num : shuffledNumbers()) {
                        if (isPlaceable(grid, row, col, num)) {
                            grid[row][col] = num;
                            if (solve(grid)) {
                                return true;
                            }
                            grid[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private List<Integer> shuffledNumbers() {
        final List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(numbers, random);
        return numbers;
    }

    private boolean isPlaceable(final int[][] grid, final int row, final int col, final int num) {
        return !isInRow(grid, row, num) && !isInColumn(grid, col, num) && !isInBox(grid, row, col, num);
    }

    private boolean isInRow(final int[][] grid, final int row, final int num) {
        for (int col = 0; col < SIZE; col++) {
            if (grid[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInColumn(final int[][] grid, final int col, final int num) {
        for (int row = 0; row < SIZE; row++) {
            if (grid[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean isInBox(final int[][] grid, final int row, final int col, final int num) {
        final int boxRow = (row / BOX_SIZE) * BOX_SIZE;
        final int boxCol = (col / BOX_SIZE) * BOX_SIZE;
        for (int r = boxRow; r < boxRow + BOX_SIZE; r++) {
            for (int c = boxCol; c < boxCol + BOX_SIZE; c++) {
                if (grid[r][c] == num) {
                    return true;
                }
            }
        }
        return false;
    }
}

// Author: Nawaphon Isarathanachaikul, 06/19/2017
// Refactored by: GitHub Copilot, 03/25/2026
package com.sudoku.generator;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SudokuGeneratorTest {

    private final SudokuGenerator generator = new SudokuGenerator(new Random(42));

    @Test
    void generateReturnsNineByNineGrid() {
        final int[][] grid = generator.generate();
        assertEquals(9, grid.length);
        for (final int[] row : grid) {
            assertEquals(9, row.length);
        }
    }

    @Test
    void generateGridHasUniqueNumbersInEachRow() {
        final int[][] grid = generator.generate();
        for (final int[] row : grid) {
            final boolean[] seen = new boolean[10];
            for (final int value : row) {
                assertTrue(value >= 1 && value <= 9, "Value must be 1-9");
                assertFalse(seen[value], "Duplicate value in row: " + value);
                seen[value] = true;
            }
        }
    }

    @Test
    void generateGridHasUniqueNumbersInEachColumn() {
        final int[][] grid = generator.generate();
        for (int col = 0; col < 9; col++) {
            final boolean[] seen = new boolean[10];
            for (int row = 0; row < 9; row++) {
                final int value = grid[row][col];
                assertTrue(value >= 1 && value <= 9, "Value must be 1-9");
                assertFalse(seen[value], "Duplicate value in column: " + value);
                seen[value] = true;
            }
        }
    }

    @Test
    void generateGridHasUniqueNumbersInEach3x3Box() {
        final int[][] grid = generator.generate();
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                final boolean[] seen = new boolean[10];
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        final int value = grid[boxRow * 3 + r][boxCol * 3 + c];
                        assertFalse(seen[value], "Duplicate value in 3x3 box: " + value);
                        seen[value] = true;
                    }
                }
            }
        }
    }

    @Test
    void generateProducesDifferentGridsWithDifferentSeeds() {
        final int[][] grid1 = new SudokuGenerator(new Random(1)).generate();
        final int[][] grid2 = new SudokuGenerator(new Random(99)).generate();
        boolean different = false;
        outer:
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid1[row][col] != grid2[row][col]) {
                    different = true;
                    break outer;
                }
            }
        }
        assertTrue(different, "Two grids with different seeds should differ");
    }
}

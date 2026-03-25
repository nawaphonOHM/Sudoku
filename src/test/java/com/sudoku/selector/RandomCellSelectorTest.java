// Author: Nawaphon Isarathanachaikul, 06/19/2017
// Refactored by: GitHub Copilot, 03/25/2026
package com.sudoku.selector;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RandomCellSelectorTest {

    private final RandomCellSelector selector = new RandomCellSelector(new Random(42));

    @Test
    void selectReturnsNineByNineArray() {
        final boolean[][] selected = selector.select(10);
        assertEquals(9, selected.length);
        for (final boolean[] row : selected) {
            assertEquals(9, row.length);
        }
    }

    @Test
    void selectReturnsExactCountOfTrueCells() {
        final int count = 45;
        final boolean[][] selected = new RandomCellSelector(new Random(7)).select(count);
        int trueCount = 0;
        for (final boolean[] row : selected) {
            for (final boolean cell : row) {
                if (cell) {
                    trueCount++;
                }
            }
        }
        assertEquals(count, trueCount);
    }

    @Test
    void selectWithZeroCountReturnsAllFalse() {
        final boolean[][] selected = new RandomCellSelector(new Random()).select(0);
        for (final boolean[] row : selected) {
            for (final boolean cell : row) {
                assertFalse(cell);
            }
        }
    }

    @Test
    void selectWithFullCountReturnsAllTrue() {
        final boolean[][] selected = new RandomCellSelector(new Random()).select(81);
        for (final boolean[] row : selected) {
            for (final boolean cell : row) {
                assertTrue(cell);
            }
        }
    }

    @Test
    void selectWithNegativeCountThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new RandomCellSelector(new Random()).select(-1));
    }

    @Test
    void selectWithTooLargeCountThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new RandomCellSelector(new Random()).select(82));
    }
}

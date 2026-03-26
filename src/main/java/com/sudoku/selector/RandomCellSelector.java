// Author: Nawaphon Isarathanachaikul, 06/19/2017
// Refactored by: GitHub Copilot, 03/25/2026
package com.sudoku.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class RandomCellSelector implements CellSelector {

    private static final int SIZE = 9;
    private static final int TOTAL_CELLS = SIZE * SIZE;

    private final Random random;

    public RandomCellSelector(final Random random) {
        this.random = random;
    }

    @Override
    public boolean[][] select(final int count) {
        if (count < 0 || count > TOTAL_CELLS) {
            throw new IllegalArgumentException(
                "count must be between 0 and " + TOTAL_CELLS + " (inclusive), got: " + count);
        }
        final List<Integer> indices = new ArrayList<>(TOTAL_CELLS);
        for (int i = 0; i < TOTAL_CELLS; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, random);
        final boolean[][] selected = new boolean[SIZE][SIZE];
        for (int i = 0; i < count; i++) {
            final int index = indices.get(i);
            selected[index / SIZE][index % SIZE] = true;
        }
        return selected;
    }
}

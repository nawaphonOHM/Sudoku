// Author: Nawaphon Isarathanachaikul, 06/19/2017
// Refactored by: GitHub Copilot, 03/25/2026
package com.sudoku.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DifficultyLevelTest {

    @Test
    void mostEasyHasCorrectVisibleCellsAndPoints() {
        assertEquals(72, DifficultyLevel.MOST_EASY.getVisibleCells());
        assertEquals(1, DifficultyLevel.MOST_EASY.getPointsPerCell());
    }

    @Test
    void veryEasyHasCorrectVisibleCellsAndPoints() {
        assertEquals(63, DifficultyLevel.VERY_EASY.getVisibleCells());
        assertEquals(2, DifficultyLevel.VERY_EASY.getPointsPerCell());
    }

    @Test
    void easyHasCorrectVisibleCellsAndPoints() {
        assertEquals(54, DifficultyLevel.EASY.getVisibleCells());
        assertEquals(3, DifficultyLevel.EASY.getPointsPerCell());
    }

    @Test
    void normalHasCorrectVisibleCellsAndPoints() {
        assertEquals(45, DifficultyLevel.NORMAL.getVisibleCells());
        assertEquals(4, DifficultyLevel.NORMAL.getPointsPerCell());
    }

    @Test
    void hardHasCorrectVisibleCellsAndPoints() {
        assertEquals(36, DifficultyLevel.HARD.getVisibleCells());
        assertEquals(5, DifficultyLevel.HARD.getPointsPerCell());
    }

    @Test
    void veryHardHasCorrectVisibleCellsAndPoints() {
        assertEquals(27, DifficultyLevel.VERY_HARD.getVisibleCells());
        assertEquals(6, DifficultyLevel.VERY_HARD.getPointsPerCell());
    }

    @Test
    void mostHardHasCorrectVisibleCellsAndPoints() {
        assertEquals(18, DifficultyLevel.MOST_HARD.getVisibleCells());
        assertEquals(7, DifficultyLevel.MOST_HARD.getPointsPerCell());
    }

    @Test
    void displayNamesMatchExpected() {
        assertEquals("Most Easy", DifficultyLevel.MOST_EASY.getDisplayName());
        assertEquals("Very Easy", DifficultyLevel.VERY_EASY.getDisplayName());
        assertEquals("Easy", DifficultyLevel.EASY.getDisplayName());
        assertEquals("Normal", DifficultyLevel.NORMAL.getDisplayName());
        assertEquals("Hard", DifficultyLevel.HARD.getDisplayName());
        assertEquals("Very Hard", DifficultyLevel.VERY_HARD.getDisplayName());
        assertEquals("Most Hard", DifficultyLevel.MOST_HARD.getDisplayName());
    }
}

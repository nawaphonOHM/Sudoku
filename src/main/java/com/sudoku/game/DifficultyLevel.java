// Author: Nawaphon Isarathanachaikul, 06/19/2017
// Refactored by: GitHub Copilot, 03/25/2026
package com.sudoku.game;

public enum DifficultyLevel {

    MOST_EASY("Most Easy", 72, 1),
    VERY_EASY("Very Easy", 63, 2),
    EASY("Easy", 54, 3),
    NORMAL("Normal", 45, 4),
    HARD("Hard", 36, 5),
    VERY_HARD("Very Hard", 27, 6),
    MOST_HARD("Most Hard", 18, 7);

    private final String displayName;
    private final int visibleCells;
    private final int pointsPerCell;

    DifficultyLevel(final String displayName, final int visibleCells, final int pointsPerCell) {
        this.displayName = displayName;
        this.visibleCells = visibleCells;
        this.pointsPerCell = pointsPerCell;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getVisibleCells() {
        return visibleCells;
    }

    public int getPointsPerCell() {
        return pointsPerCell;
    }
}

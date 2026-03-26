// Author: Nawaphon Isarathanachaikul, 06/19/2017
// Refactored by: GitHub Copilot, 03/25/2026
package com.sudoku.gui;

import com.sudoku.game.DifficultyLevel;
import com.sudoku.generator.SudokuGenerator;
import com.sudoku.model.SudokuBoard;
import com.sudoku.selector.RandomCellSelector;

import javax.swing.SwingWorker;
import java.util.Random;
import java.util.concurrent.ExecutionException;

final class PuzzleGenerationWorker extends SwingWorker<SudokuBoard, Void> {

    private final SudokuGui gui;
    private final DifficultyLevel difficulty;

    PuzzleGenerationWorker(final SudokuGui gui, final DifficultyLevel difficulty) {
        this.gui = gui;
        this.difficulty = difficulty;
    }

    @Override
    protected SudokuBoard doInBackground() {
        final int[][] solution = new SudokuGenerator(new Random()).generate();
        final boolean[][] revealed =
                new RandomCellSelector(new Random()).select(difficulty.getVisibleCells());
        return new SudokuBoard(solution, revealed);
    }

    @Override
    protected void done() {
        try {
            gui.onPuzzleGenerated(get(), difficulty);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            gui.onPuzzleGenerationFailed();
        } catch (ExecutionException e) {
            gui.onPuzzleGenerationFailed();
        }
    }
}

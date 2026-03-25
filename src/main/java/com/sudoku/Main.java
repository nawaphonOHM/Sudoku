// Author: Nawaphon Isarathanachaikul, 06/19/2017
// Refactored by: GitHub Copilot, 03/25/2026
package com.sudoku;

import com.sudoku.gui.SudokuGui;

import javax.swing.*;

public final class Main {

    private Main() {
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final SudokuGui gui = new SudokuGui();
            gui.start();
        });
    }
}

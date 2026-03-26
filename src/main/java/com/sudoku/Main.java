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
            applySystemLookAndFeel();
            final SudokuGui gui = new SudokuGui();
            gui.start();
        });
    }

    private static void applySystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

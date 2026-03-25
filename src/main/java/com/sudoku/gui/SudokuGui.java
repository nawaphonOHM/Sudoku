// Author: Nawaphon Isarathanachaikul, 06/19/2017
// Refactored by: GitHub Copilot, 03/25/2026
package com.sudoku.gui;

import com.sudoku.game.DifficultyLevel;
import com.sudoku.game.SudokuGame;
import com.sudoku.generator.SudokuGenerator;
import com.sudoku.model.SudokuBoard;
import com.sudoku.selector.RandomCellSelector;
import org.jspecify.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public final class SudokuGui extends JFrame implements ActionListener, MouseListener {

    private static final int GRID_SIZE = 9;
    private static final int BOX_SIZE = 3;
    private static final String NO_FOCUS = "(-1,-1)";

    private final Container container;
    private final Dimension screenSize;
    private final JTextField[][] cellFields;
    private final JLabel[] boxPanels;
    private final int[] selectedCell;
    private final JRadioButton[] difficultyButtons;
    private final ButtonGroup difficultyGroup;
    private final JButton[] digitButtons;

    private final JPanel panel;
    private final GroupLayout layout;
    private final JLabel gridPanel;
    private final JLabel timeLabel;
    private final JLabel scoreLabel;
    private final JButton startButton;
    private final JButton okButton;
    private final JButton cancelButton;
    private final JLabel levelPanel;
    private final JLabel digitsPanel;

    @Nullable private DifficultyLevel selectedDifficulty;
    @Nullable private SudokuGame currentGame;

    public SudokuGui() {
        this.container = getContentPane();
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.cellFields = new JTextField[GRID_SIZE][GRID_SIZE];
        this.boxPanels = new JLabel[GRID_SIZE];
        this.selectedCell = new int[]{-1, -1};
        this.difficultyButtons = new JRadioButton[DifficultyLevel.values().length];
        this.difficultyGroup = new ButtonGroup();
        this.digitButtons = new JButton[GRID_SIZE];

        this.panel = new JPanel();
        this.layout = new GroupLayout(this.panel);
        this.panel.setPreferredSize(new Dimension(
                (int) (screenSize.getWidth() * 0.34),
                (int) (screenSize.getHeight() * 0.805)));
        this.panel.setLayout(this.layout);
        this.layout.setAutoCreateGaps(true);
        this.layout.setAutoCreateContainerGaps(true);

        this.gridPanel = buildGrid();
        this.timeLabel = buildTimeLabel();
        this.scoreLabel = buildScoreLabel();
        this.startButton = buildStartButton();
        this.okButton = buildOkButton();
        this.cancelButton = buildCancelButton();
        this.levelPanel = buildLevelPanel();
        this.digitsPanel = buildDigitsPanel();
        arrangeLayout();
    }

    public void start() {
        applySystemLookAndFeel();
        container.add(panel);
        pack();
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void applySystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }

    private JLabel buildGrid() {
        final JLabel grid = new JLabel();
        grid.setPreferredSize(new Dimension(
                (int) (screenSize.getWidth() * 0.3294289897511),
                (int) (screenSize.getHeight() * 0.5859375)));
        grid.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        grid.setLayout(new GridLayout(BOX_SIZE, BOX_SIZE));
        for (int i = 0; i < GRID_SIZE; i++) {
            boxPanels[i] = new JLabel();
            boxPanels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            boxPanels[i].setLayout(new GridLayout(BOX_SIZE, BOX_SIZE));
            grid.add(boxPanels[i]);
            final int[] bounds = boxBounds(i);
            for (int row = bounds[1]; row < bounds[3]; row++) {
                for (int col = bounds[0]; col < bounds[2]; col++) {
                    cellFields[row][col] = createCell();
                    boxPanels[i].add(cellFields[row][col]);
                }
            }
        }
        return grid;
    }

    private JTextField createCell() {
        final JTextField field = new JTextField("");
        field.setHorizontalAlignment(SwingConstants.CENTER);
        field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        field.setFont(new Font("Arial", Font.BOLD, 25));
        field.setEditable(false);
        field.setEnabled(false);
        return field;
    }

    private int[] boxBounds(final int boxIndex) {
        final int colStart = (boxIndex % BOX_SIZE) * BOX_SIZE;
        final int rowStart = (boxIndex / BOX_SIZE) * BOX_SIZE;
        return new int[]{colStart, rowStart, colStart + BOX_SIZE, rowStart + BOX_SIZE};
    }

    private JLabel buildTimeLabel() {
        final TitledBorder border = BorderFactory.createTitledBorder(null, "Time Used");
        border.setTitleJustification(TitledBorder.LEFT);
        final JLabel label = new JLabel("");
        label.setBorder(border);
        return label;
    }

    private JLabel buildScoreLabel() {
        final TitledBorder border = BorderFactory.createTitledBorder(null, "Score(s)");
        border.setTitleJustification(TitledBorder.LEFT);
        final JLabel label = new JLabel("");
        label.setBorder(border);
        return label;
    }

    private JButton buildStartButton() {
        final JButton button = new JButton("Start");
        button.setPreferredSize(new Dimension(
                (int) (screenSize.getWidth() * 0.06),
                (int) (screenSize.getHeight() * 0.04)));
        button.addActionListener(this);
        button.setFocusPainted(false);
        button.setFocusable(false);
        return button;
    }

    private JButton buildOkButton() {
        final JButton button = new JButton("OK");
        button.setPreferredSize(new Dimension(
                (int) (screenSize.getWidth() * 0.06),
                (int) (screenSize.getHeight() * 0.04)));
        button.setEnabled(false);
        button.addActionListener(this);
        return button;
    }

    private JButton buildCancelButton() {
        final JButton button = new JButton("Cancel");
        button.setPreferredSize(new Dimension(
                (int) (screenSize.getWidth() * 0.06),
                (int) (screenSize.getHeight() * 0.04)));
        button.setEnabled(false);
        button.addActionListener(this);
        return button;
    }

    private JLabel buildLevelPanel() {
        final TitledBorder border = BorderFactory.createTitledBorder(null, "Levels");
        border.setTitleJustification(TitledBorder.LEFT);
        final JLabel lPanel = new JLabel();
        lPanel.setLayout(new GridLayout(4, 2));
        final DifficultyLevel[] levels = DifficultyLevel.values();
        for (int i = 0; i < levels.length; i++) {
            difficultyButtons[i] = new JRadioButton(levels[i].getDisplayName());
            difficultyButtons[i].setEnabled(false);
            difficultyButtons[i].addActionListener(this);
            difficultyGroup.add(difficultyButtons[i]);
            lPanel.add(difficultyButtons[i]);
        }
        lPanel.setBorder(border);
        lPanel.setPreferredSize(new Dimension(
                (int) (screenSize.getWidth() * 0.014),
                (int) (screenSize.getHeight() * 0.138)));
        return lPanel;
    }

    private JLabel buildDigitsPanel() {
        final TitledBorder border = BorderFactory.createTitledBorder(null, "Digits");
        border.setTitleJustification(TitledBorder.LEFT);
        final JLabel dPanel = new JLabel();
        dPanel.setLayout(new GridLayout(BOX_SIZE, BOX_SIZE,
                (int) (screenSize.getWidth() * 0.002),
                (int) (screenSize.getHeight() * 0.004)));
        for (int i = 0; i < GRID_SIZE; i++) {
            digitButtons[i] = new JButton(Integer.toString(i + 1));
            digitButtons[i].addActionListener(this);
            dPanel.add(digitButtons[i]);
            digitButtons[i].setEnabled(false);
        }
        dPanel.setBorder(border);
        dPanel.setPreferredSize(new Dimension(0, (int) (screenSize.getHeight() * 0.14)));
        return dPanel;
    }

    private void arrangeLayout() {
        layout.setHorizontalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(gridPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(timeLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scoreLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(startButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(okButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(levelPanel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(digitsPanel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout
                .createSequentialGroup()
                .addComponent(gridPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(timeLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scoreLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(startButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(okButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(levelPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(digitsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        clearButtonFocusPaint();
        final String command = event.getActionCommand();
        if ("Start".equals(command) || "Renew".equals(command)) {
            handleStartOrRenew(command);
        } else if ("OK".equals(command)) {
            handleOk();
        } else if ("Cancel".equals(command)) {
            handleCancel();
        } else if (isDifficultyCommand(command)) {
            handleDifficultySelection(command);
        } else {
            handleDigitInput(command);
        }
    }

    private void clearButtonFocusPaint() {
        startButton.setFocusPainted(false);
        okButton.setFocusPainted(false);
        cancelButton.setFocusPainted(false);
        for (final JRadioButton button : difficultyButtons) {
            button.setFocusPainted(false);
        }
    }

    private void handleStartOrRenew(final String command) {
        if (selectedCell[0] != -1) {
            cellFields[selectedCell[1]][selectedCell[0]].setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        startButton.setEnabled(false);
        cancelButton.setEnabled(true);
        if ("Renew".equals(command)) {
            okButton.setEnabled(true);
        }
        setDifficultyButtonsEnabled(true);
        setDigitButtonsEnabled(false);
        resetAllCells();
        selectedCell[0] = -1;
        selectedCell[1] = -1;
        timeLabel.setText("");
        scoreLabel.setText("");
        currentGame = null;
    }

    private void handleOk() {
        if (selectedDifficulty == null) {
            return;
        }
        okButton.setEnabled(false);
        cancelButton.setEnabled(false);
        startButton.setText("Renew");
        startButton.setEnabled(true);
        setDifficultyButtonsEnabled(false);
        final int[][] solution = new SudokuGenerator(new Random()).generate();
        final boolean[][] revealed = new RandomCellSelector(new Random()).select(selectedDifficulty.getVisibleCells());
        final SudokuBoard board = new SudokuBoard(solution, revealed);
        currentGame = new SudokuGame(board, selectedDifficulty);
        applyBoardToGrid(board);
        timeLabel.setText("N/A");
        scoreLabel.setText("N/A");
    }

    private void applyBoardToGrid(final SudokuBoard board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board.isInitiallyRevealed(row, col)) {
                    cellFields[row][col].setText(Integer.toString(board.getSolution(row, col)));
                    cellFields[row][col].setName(NO_FOCUS);
                } else {
                    cellFields[row][col].setText("");
                    cellFields[row][col].setName("(" + col + "," + row + ")");
                }
                cellFields[row][col].addMouseListener(this);
                cellFields[row][col].setEnabled(true);
                cellFields[row][col].setBackground(Color.WHITE);
            }
        }
    }

    private void handleCancel() {
        okButton.setEnabled(false);
        cancelButton.setEnabled(false);
        startButton.setEnabled(true);
        startButton.setText("Start");
        setDifficultyButtonsEnabled(false);
        difficultyGroup.clearSelection();
        selectedDifficulty = null;
        currentGame = null;
    }

    private boolean isDifficultyCommand(final String command) {
        for (final DifficultyLevel level : DifficultyLevel.values()) {
            if (level.getDisplayName().equals(command)) {
                return true;
            }
        }
        return false;
    }

    private void handleDifficultySelection(final String command) {
        for (final DifficultyLevel level : DifficultyLevel.values()) {
            if (level.getDisplayName().equals(command)) {
                selectedDifficulty = level;
                okButton.setEnabled(true);
                return;
            }
        }
    }

    private void handleDigitInput(final String command) {
        if (currentGame == null || selectedCell[0] == -1) {
            return;
        }
        final int row = selectedCell[1];
        final int col = selectedCell[0];
        final int value = Integer.parseInt(command);
        cellFields[row][col].setText(command);
        cellFields[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cellFields[row][col].removeMouseListener(this);
        setDigitButtonsEnabled(false);
        if (currentGame.guess(row, col, value)) {
            cellFields[row][col].setBackground(Color.GREEN);
            if (currentGame.isCompleted()) {
                timeLabel.setText(currentGame.getElapsedTimeString());
                scoreLabel.setText(Integer.toString(currentGame.calculateFinalScore()));
            }
        } else {
            cellFields[row][col].setBackground(Color.RED);
            removeAllCellListeners();
            timeLabel.setText("You did not finish this puzzle.");
            scoreLabel.setText("0");
        }
    }

    private void removeAllCellListeners() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cellFields[row][col].removeMouseListener(this);
            }
        }
    }

    private void resetAllCells() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cellFields[row][col].setEnabled(false);
                cellFields[row][col].setBackground(UIManager.getColor("Panel.background"));
                cellFields[row][col].setText("");
                cellFields[row][col].removeMouseListener(this);
            }
        }
    }

    private void setDifficultyButtonsEnabled(final boolean enabled) {
        for (final JRadioButton button : difficultyButtons) {
            button.setEnabled(enabled);
        }
    }

    private void setDigitButtonsEnabled(final boolean enabled) {
        for (final JButton button : digitButtons) {
            button.setEnabled(enabled);
            if (enabled) {
                button.setFocusable(false);
                button.setFocusPainted(false);
            }
        }
    }

    @Override
    public void mousePressed(final MouseEvent event) {
        if (selectedCell[0] != -1) {
            cellFields[selectedCell[1]][selectedCell[0]].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            setDigitButtonsEnabled(false);
        }
        final JTextField source = (JTextField) event.getSource();
        final String name = source.getName();
        final String coords = name.substring(name.indexOf('(') + 1, name.indexOf(')'));
        final String[] parts = coords.split(",");
        selectedCell[0] = Integer.parseInt(parts[0]);
        selectedCell[1] = Integer.parseInt(parts[1]);
        if (selectedCell[0] != -1) {
            cellFields[selectedCell[1]][selectedCell[0]].setBorder(BorderFactory.createLineBorder(Color.ORANGE, 5));
            setDigitButtonsEnabled(true);
        }
    }

    @Override
    public void mouseClicked(final MouseEvent event) {
    }

    @Override
    public void mouseReleased(final MouseEvent event) {
    }

    @Override
    public void mouseEntered(final MouseEvent event) {
    }

    @Override
    public void mouseExited(final MouseEvent event) {
    }
}

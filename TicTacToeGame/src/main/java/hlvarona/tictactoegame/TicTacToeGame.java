/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package hlvarona.tictactoegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGame extends JFrame {
    private static final int BOARD_SIZE = 3;
    private JButton[][] boardButtons;
    private JButton newGameButton;
    private JButton closeButton;
    private JLabel statusLabel;
    private boolean playerX;
    private boolean gameFinished;

    public TicTacToeGame() {
        super("Tic-Tac-Toe");

        boardButtons = new JButton[BOARD_SIZE][BOARD_SIZE];
        newGameButton = new JButton("New Game");
        closeButton = new JButton("Close");
        statusLabel = new JLabel("Player X turn");
        playerX = true;
        gameFinished = false;

        JPanel boardPanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JButton button = new JButton();
                button.addActionListener(new ButtonClickListener(row, col));
                boardButtons[row][col] = button;
                boardPanel.add(button);
            }
        }

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(newGameButton);
        controlPanel.add(closeButton);

        setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);

        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void resetGame() {
        playerX = true;
        gameFinished = false;
        statusLabel.setText("Player X turn");

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                boardButtons[row][col].setText("");
                boardButtons[row][col].setEnabled(true);
            }
        }
    }

    private void performMove(int row, int col) {
        if (!gameFinished && boardButtons[row][col].getText().isEmpty()) {
            boardButtons[row][col].setText(playerX ? "X" : "O");
            boardButtons[row][col].setEnabled(false);

            if (checkWin(playerX)) {
                String winner = playerX ? "X" : "O";
                JOptionPane.showMessageDialog(this, "Player " + winner + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                gameFinished = true;
            } else if (checkDraw()) {
                JOptionPane.showMessageDialog(this, "Game tied!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                gameFinished = true;
            } else {
                playerX = !playerX;
                statusLabel.setText("Player " + (playerX ? "X" : "O") + " turn");
            }
        }
    }

    private boolean checkWin(boolean player) {
        String symbol = player ? "X" : "O";
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (boardButtons[row][0].getText().equals(symbol) &&
                    boardButtons[row][1].getText().equals(symbol) &&
                    boardButtons[row][2].getText().equals(symbol)) {
                return true;
            }
        }

        for (int col = 0; col < BOARD_SIZE; col++) {
            if (boardButtons[0][col].getText().equals(symbol) &&
                    boardButtons[1][col].getText().equals(symbol) &&
                    boardButtons[2][col].getText().equals(symbol)) {
                return true;
            }
        }

        if (boardButtons[0][0].getText().equals(symbol) &&
                boardButtons[1][1].getText().equals(symbol) &&
                boardButtons[2][2].getText().equals(symbol)) {
            return true;
        }

        if (boardButtons[0][2].getText().equals(symbol) &&
                boardButtons[1][1].getText().equals(symbol) &&
                boardButtons[2][0].getText().equals(symbol)) {
            return true;
        }

        return false;
    }

    private boolean checkDraw() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (boardButtons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            performMove(row, col);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeGame();
            }
        });
    }
}

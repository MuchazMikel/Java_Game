import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'O'; // Start with pla     yer 'O'
    private boolean gameOver = false;
    private int scoreO = 0; // Score for player 'O'
    private int scoreX = 0; // Score for player 'X'
    private JLabel scoreLabel; // Label to display scores
    private JButton restartButton; // Button to restart the game

    public TicTacToe() {
        // Set up the frame
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set to fullscreen
        setLayout(new BorderLayout());

        // Initialize buttons and add them to the frame
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton(" ");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[row][col].setBackground(Color.LIGHT_GRAY);
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(this);
                boardPanel.add(buttons[row][col]);
            }
        }

        // Create a panel to add margins around the board
        JPanel centeredPanel = new JPanel();
        centeredPanel.setLayout(new BorderLayout());
        centeredPanel.add(boardPanel, BorderLayout.CENTER);
        centeredPanel.setBorder(BorderFactory.createEmptyBorder(20, 400, 20, 400)); // Top, left, bottom, right margins

        // Add the centered panel to the frame
        add(centeredPanel, BorderLayout.CENTER);

        // Create a panel for the score display
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(2, 1)); // Two rows for title and score
        scorePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // Margin around the score panel

        // Create a label for the score title
        JLabel scoreTitle = new JLabel("Current Score");
        scoreTitle.setFont(new Font("Arial", Font.BOLD, 24));
        scoreTitle.setHorizontalAlignment(SwingConstants.CENTER);
        scorePanel.add(scoreTitle); // Add title to score panel

        // Create the score label
        scoreLabel = new JLabel("O: " + scoreO + " | X: " + scoreX);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scorePanel.add(scoreLabel); // Add score label to score panel

        // Add score panel to the frame
        add(scorePanel, BorderLayout.NORTH); 

        // Create and add the restart button below the score
        restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 20));
        restartButton.addActionListener(e -> restartGame());
        JPanel restartPanel = new JPanel(); // Create a new panel for the restart button
        restartPanel.add(restartButton); // Add the restart button to the panel
        add(restartPanel, BorderLayout.SOUTH); // Add the restart panel to the frame

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver)
            return;

        JButton clickedButton = (JButton) e.getSource();
        if (clickedButton.getText().equals(" ")) {
            clickedButton.setText(String.valueOf(currentPlayer));
            clickedButton.setBackground(currentPlayer == 'O' ? Color.CYAN : Color.MAGENTA);
            if (checkWin()) {
                JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
                if (currentPlayer == 'O') {
                    scoreO++;
                } else {
                    scoreX++;
                }
                updateScoreDisplay();
                gameOver = true;
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(this, "It's a draw!");
                gameOver = true;
            } else {
                currentPlayer = (currentPlayer == 'O') ? 'X' : 'O'; // Switch player
            }
        }
    }

    private void restartGame() {
        currentPlayer = 'O'; // Reset to player 'O'
        gameOver = false;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(" ");
                buttons[row][col].setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    private void updateScoreDisplay() {
        scoreLabel.setText("O: " + scoreO + " | X: " + scoreX);
    }

    private boolean checkWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                    buttons[2][i].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }
        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][0].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
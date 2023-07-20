
    import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolverUI extends JFrame {

    private JTextField[][] sudokuGrid;
    private JButton solveButton;

    public SudokuSolverUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Sudoku Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        sudokuGrid = new JTextField[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuGrid[i][j] = new JTextField(1);
                gridPanel.add(sudokuGrid[i][j]);
            }
        }

        solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSudoku();
            }
        });

        add(gridPanel, BorderLayout.CENTER);
        add(solveButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean isValidMove(int[][] board, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        int boxStartRow = row - row % 3;
        int boxStartCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[boxStartRow + i][boxStartCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean solveSudoku() {
        int[][] board = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    board[i][j] = Integer.parseInt(sudokuGrid[i][j].getText());
                } catch (NumberFormatException ex) {
                    board[i][j] = 0;
                }
            }
        }

        if (solveSudokuUtil(board)) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    sudokuGrid[i][j].setText(String.valueOf(board[i][j]));
                }
            }
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "No solution found.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean solveSudokuUtil(int[][] board) {
        int[] emptyCell = findEmptyCell(board);
        int row = emptyCell[0];
        int col = emptyCell[1];

        if (row == -1 && col == -1) {
            return true; // Sudoku is solved
        }

        for (int num = 1; num <= 9; num++) {
            if (isValidMove(board, row, col, num)) {
                board[row][col] = num;

                if (solveSudokuUtil(board)) {
                    return true;
                }

                board[row][col] = 0; // Backtrack
            }
        }

        return false; // No valid move found, trigger backtracking
    }

    private int[] findEmptyCell(int[][] board) {
        int[] emptyCell = new int[]{-1, -1};

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    emptyCell[0] = i;
                    emptyCell[1] = j;
                    return emptyCell;
                }
            }
        }

        return emptyCell; // No empty cell found
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SudokuSolverUI());
    }
}



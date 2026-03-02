import java.util.Scanner;

public class NQueens {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Solve N-Queens");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter value of n: ");
                    int n = sc.nextInt();
                    solveNQueens(n);
                    break;
                case 2:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    public static void solveNQueens(int n) {
        char[][] board = new char[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }

        if(!placeQueens(board, 0)){
                    System.out.println("No solution exists.");

        }
    }

    private static boolean placeQueens(char[][] board, int row) {
        int n = board.length;

        if (row == n) {
            printBoard(board);
            System.out.println();
            return true ;
        }
       

        for (int col = 0; col < n; col++) {
            if (isSafe(board, row, col)) {
                board[row][col] = 'Q';
                  if (placeQueens(board, row + 1)) {
                return true; 
            }

                board[row][col] = '.';
            }
        }
        return false;
    }

    private static boolean isSafe(char[][] board, int row, int col) {
        int n = board.length;

        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') {
                return false;
            }
        }

        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }

        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }

        return true;
    }

    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}

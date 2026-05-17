public class nqueen {
    public static boolean isSafe(int[][] board, int row, int col){
        int n = board[0].length;
        //upper coloumn
        for(int i=0;i<row;i++ ){
            if(board[i][col]==1) return false;
        }
        //left diag
        for(int i=row,j=col;i>=0 && j>=0;i--,j--){
            if(board[i][j]==1) return false;
        }
        // right diag
        for(int i=row,j=col;i>=0 &&j<n;i--,j++ ){
            if(board[i][j]==1) return false;
        }
        return true;
    }
    public static boolean solve(int[][]board, int row){
        if(row== board.length) return true;
        for(int col=0;col< board[0].length;col++){
            if(isSafe(board,row,col)){
                board[row][col] = 1;
                if(solve(board,row+1)){
                    return true;
                }
                board[row][col] = 0;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        int[][] board = new int[5][5];
        if(solve(board,0)){
            System.out.println("Solved");
            for(int i=0;i<board.length;i++){
                for (int j=0;j<board[0].length;j++){
                    if(board[i][j]==1){
                        System.out.print("Q ");
                    }
                    else {
                        System.out.print("- ");
                    }
                }
                System.out.println();

            }
        }
    }
}
